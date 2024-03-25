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
import ru.otus.catalog.models.nosql.MongoBook;
import ru.otus.catalog.models.relational.Book;
import ru.otus.catalog.services.cleanup.BookCleanupService;
import ru.otus.catalog.services.mongo.MongoBookService;
import ru.otus.catalog.services.processors.BookProcessor;

import java.util.List;
import java.util.logging.Logger;

@Configuration
public class BookConfigJob {

    public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    private static final int CHUNK_SIZE = 2;

    private static final Logger LOG = Logger.getLogger(BookConfigJob.class.getName());

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final EntityManagerFactory entityManagerFactory;

    private final BookCleanupService bookCleanupService;

    private final MongoBookService mongoBookService;

    public BookConfigJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                         EntityManagerFactory entityManagerFactory, BookCleanupService bookCleanupService,
                         MongoBookService mongoBookService) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.entityManagerFactory = entityManagerFactory;
        this.bookCleanupService = bookCleanupService;
        this.mongoBookService = mongoBookService;
    }

    @StepScope()
    @Bean
    public JpaPagingItemReader<Book> bookItemReader(
            @Value("#{jobParameters['currentItemCount']}") String currentItemCount) {
        return new JpaPagingItemReaderBuilder<Book>()
                .name("bookItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT b FROM Book b")
                .currentItemCount(Integer.parseInt(currentItemCount))
                .pageSize(CHUNK_SIZE)
                .build();
    }

    @Bean
    public BookProcessor bookItemProcessor() {
        return new BookProcessor(mongoBookService);
    }

    @Bean
    public MongoItemWriter<MongoBook> bookItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoBook>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }

    @Bean
    public Step transformBookStep(
            JpaPagingItemReader<Book> reader,
            MongoItemWriter<MongoBook> writer,
            ItemProcessor<Book, MongoBook> processor) {
        return new StepBuilder("transformBookStep", jobRepository)
                .<Book, MongoBook>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new BookReadListener())
                .listener(new BookWriteListener())
                .listener(new BookProcessListener())
                .listener(new BookChunkListener())
                .build();
    }

    private static class BookReadListener implements ItemReadListener<Book> {
        public void beforeRead() {
            LOG.info("Начало чтения");
        }

        public void afterRead(@NonNull Book o) {
            LOG.info("Конец чтения");
        }

        public void onReadError(@NonNull Exception e) {
            LOG.info("Ошибка чтения");
        }
    }

    private static class BookWriteListener implements ItemWriteListener<MongoBook> {
        public void beforeWrite(@NonNull List<MongoBook> list) {
            LOG.info("Начало записи");
        }

        public void afterWrite(@NonNull List<MongoBook> list) {
            LOG.info("Конец записи");
        }

        public void onWriteError(@NonNull Exception e, @NonNull List<MongoBook> list) {
            LOG.info("Ошибка записи");
        }
    }

    private static class BookProcessListener implements ItemProcessListener<Book, MongoBook> {
        public void beforeProcess(@NonNull Book o) {
            LOG.info("Начало обработки");
        }

        public void afterProcess(@NonNull Book o, MongoBook o2) {
            LOG.info("Конец обработки");
        }

        public void onProcessError(@NonNull Book o, @NonNull Exception e) {
            LOG.info("Ошибка обработки");
        }
    }

    private static class BookChunkListener implements ChunkListener {
        public void beforeChunk(@NonNull ChunkContext chunkContext) {
            LOG.info("Начало пачки");
        }

        public void afterChunk(@NonNull ChunkContext chunkContext) {
            LOG.info("Конец пачки");
        }

        public void afterChunkError(@NonNull ChunkContext chunkContext) {
            LOG.info("Ошибка пачки");
        }
    }

    @Bean
    public Job importBookJob(Step transformBookStep, Step cleanUpBookStep) {
        return new JobBuilder(IMPORT_BOOK_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformBookStep)
                .next(cleanUpBookStep)
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
    public MethodInvokingTaskletAdapter cleanUpBookTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(bookCleanupService);
        adapter.setTargetMethod("cleanUp");

        return adapter;
    }

    @Bean
    public Step cleanUpBookStep() {
        return new StepBuilder("cleanUpBookStep", jobRepository)
                .tasklet(cleanUpBookTasklet(), platformTransactionManager)
                .build();
    }
}
