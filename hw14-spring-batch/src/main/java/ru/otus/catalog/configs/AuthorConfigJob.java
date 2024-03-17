package ru.otus.catalog.configs;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ItemProcessListener;
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
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
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
import ru.otus.catalog.services.BatchService;
import ru.otus.catalog.services.cleanup.AuthorCleanUpService;
import ru.otus.catalog.services.processors.AuthorProcessor;

import java.util.List;
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

    private final AuthorCleanUpService authorCleanUpService;

    public AuthorConfigJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                           EntityManagerFactory entityManagerFactory, BatchService batchService,
                           AuthorCleanUpService authorCleanUpService) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.entityManagerFactory = entityManagerFactory;
        this.batchService = batchService;
        this.authorCleanUpService = authorCleanUpService;
    }

    @StepScope()
    @Bean
    public JpaPagingItemReader<Author> authorItemReader(
            @Value("#{jobParameters['currentItemCount']}") String currentItemCount) {
        return new JpaPagingItemReaderBuilder<Author>()
                .name("authorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Author a WHERE a.imported = false")
                .currentItemCount(Integer.parseInt(currentItemCount))
                .pageSize(CHUNK_SIZE)
                .build();
    }

    @StepScope()
    @Bean
    public MongoItemWriter<MongoAuthor> authorItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoAuthor>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }

    @StepScope()
    @Bean
    public AuthorProcessor authorItemProcessor() {
        return new AuthorProcessor(batchService);
    }

    @Bean
    public Step transformAuthorStep(
            JpaPagingItemReader<Author> reader,
            MongoItemWriter<MongoAuthor> writer,
            ItemProcessor<Author, MongoAuthor> processor) {

        return new StepBuilder("transformAuthorStep", jobRepository)
                .<Author, MongoAuthor>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        LOG.info("Начало ощи");
                    }

                    public void afterRead(@NonNull Author o) {
                        LOG.info("Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        LOG.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<MongoAuthor>() {
                    public void beforeWrite(@NonNull List<MongoAuthor> list) {
                        LOG.info("Начало записи");
                    }

                    public void afterWrite(@NonNull List<MongoAuthor> list) {
                        LOG.info("Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List<MongoAuthor> list) {
                        LOG.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(@NonNull Author o) {
                        LOG.info("Начало обработки");
                    }

                    public void afterProcess(@NonNull Author o, MongoAuthor o2) {
                        LOG.info("Конец обработки");
                    }

                    public void onProcessError(@NonNull Author o, @NonNull Exception e) {
                        LOG.info("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        LOG.info("Начало пачки");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        LOG.info("Конец пачки");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        LOG.info("Ошибка пачки");
                    }
                })
                .build();
    }

    @Bean
    public Job importAuthorJob(Step transformAuthorStep, Step cleanUpAuthorStep) {
        return new JobBuilder(IMPORT_AUTHOR_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorStep)
                .next(cleanUpAuthorStep)
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

    @Bean
    public MethodInvokingTaskletAdapter cleanUpAuthorTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(authorCleanUpService);
        adapter.setTargetMethod("cleanUp");

        return adapter;
    }

    @Bean
    public Step cleanUpAuthorStep() {
        return new StepBuilder("cleanUpAuthorStep", jobRepository)
                .tasklet(cleanUpAuthorTasklet(), platformTransactionManager)
                .build();
    }
}
