package ru.otus.catalog.configs;

import jakarta.persistence.EntityManagerFactory;
import org.bson.types.ObjectId;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.catalog.models.nosql.MongoAuthor;
import ru.otus.catalog.models.relational.Author;
import ru.otus.catalog.models.relational.BatchItem;
import ru.otus.catalog.services.BatchService;
import ru.otus.catalog.services.processors.AuthorProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Configuration
public class AuthorConfigJob {

    public static final String IMPORT_AUTHOR_JOB_NAME = "importAuthorJob";

    private static final int CHUNK_SIZE = 2;

    private static final Logger LOG = Logger.getLogger(AuthorConfigJob.class.getName());

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final EntityManagerFactory entityManagerFactory;

    private final BatchService batchService;

    public AuthorConfigJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                           EntityManagerFactory entityManagerFactory, BatchService batchService) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.entityManagerFactory = entityManagerFactory;
        this.batchService = batchService;
    }

    @StepScope()
    @Bean
    public JpaPagingItemReader<Author> authorItemReader(
            @Value("#{jobParameters['currentItemCount']}") String currentItemCount) {
        return new JpaPagingItemReaderBuilder<Author>()
                .name("authorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Author a")
                .currentItemCount(Integer.parseInt(currentItemCount))
                .pageSize(CHUNK_SIZE)
                .build();
    }

    @Bean
    public MongoItemWriter<MongoAuthor> authorItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoAuthor>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }

    @Bean
    public AuthorProcessor authorItemProcessor() {
        return new AuthorProcessor();
    }

    @Bean
    public Step transformAuthorStep(JpaPagingItemReader<Author> reader, MongoItemWriter<MongoAuthor> writer,
                                    ItemProcessor<Author, MongoAuthor> processor) {
        return new StepBuilder("transformAuthorStep", jobRepository)
                .<Author, MongoAuthor>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(mongoAuthors -> {
                    Map<String, String> relationalToDocumentId = new HashMap<>(mongoAuthors.size());
                    List<BatchItem> batchItems = mongoAuthors.getItems()
                            .stream()
                            .map(ma -> {
                                String relationalId = ma.getId();
                                relationalToDocumentId.put(relationalId, ObjectId.get().toString());
                                return new BatchItem(IMPORT_AUTHOR_JOB_NAME, relationalId,
                                        relationalToDocumentId.get(relationalId));
                            }).toList();
                    batchService.saveAll(batchItems);
                    mongoAuthors.getItems().forEach(mongoAuthor ->
                            mongoAuthor.setId(relationalToDocumentId.get(mongoAuthor.getId())));
                    writer.write(mongoAuthors);
                })
                .listener(new AuthorReadListener())
                .listener(new AuthorWriteListener())
                .listener(new AuthorChunkListener())
                .build();
    }

    private static class AuthorReadListener implements ItemReadListener<MongoAuthor> {
        public void onReadError(@NonNull Exception e) {
            LOG.info("Ошибка чтения автора");
        }
    }

    private static class AuthorWriteListener implements ItemWriteListener<MongoAuthor> {
        public void onWriteError(@NonNull Exception e, @NonNull List<MongoAuthor> list) {
            LOG.info("Ошибка записи автора");
        }
    }

    private static class AuthorChunkListener implements ChunkListener {
        public void afterChunkError(@NonNull ChunkContext chunkContext) {
            LOG.info("Ошибка чанка с авторами");
        }
    }

    @Bean
    public Job importAuthorJob(Step transformAuthorStep) {
        return new JobBuilder(IMPORT_AUTHOR_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        LOG.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        LOG.info("Конец job");
                    }
                })
                .build();
    }
}
