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
import ru.otus.catalog.models.nosql.MongoGenre;
import ru.otus.catalog.models.relational.Author;
import ru.otus.catalog.models.relational.Genre;
import ru.otus.catalog.services.BatchService;
import ru.otus.catalog.services.cleanup.GenreCleanUpService;
import ru.otus.catalog.services.processors.GenreProcessor;

import java.util.List;
import java.util.logging.Logger;

@Configuration
public class GenreConfigJob {

    public static final String IMPORT_GENRE_JOB_NAME = "importGenreJob";

    private static final int CHUNK_SIZE = 2;

    private static final Logger LOG = Logger.getLogger(AuthorConfigJob.class.getName());

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final EntityManagerFactory entityManagerFactory;

    private final BatchService batchService;

    private final GenreCleanUpService genreCleanUpService;

    public GenreConfigJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                          EntityManagerFactory entityManagerFactory, BatchService batchService,
                          GenreCleanUpService genreCleanUpService) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.entityManagerFactory = entityManagerFactory;
        this.batchService = batchService;
        this.genreCleanUpService = genreCleanUpService;
    }

    @StepScope()
    @Bean
    public JpaPagingItemReader<Genre> genreItemReader(
            @Value("#{jobParameters['currentItemCount']}") String currentItemCount) {
        return new JpaPagingItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT g FROM Genre g WHERE g.imported = false")
                .currentItemCount(Integer.parseInt(currentItemCount))
                .pageSize(CHUNK_SIZE)
                .build();
    }

    @StepScope()
    @Bean
    public GenreProcessor genreProcessor() {
        return new GenreProcessor(batchService);
    }

    @StepScope()
    @Bean
    public MongoItemWriter<MongoGenre> genreItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoGenre>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }

    @Bean
    public Step transformGenreStep(JpaPagingItemReader<Genre> reader, MongoItemWriter<MongoGenre> writer,
                                   ItemProcessor<Genre, MongoGenre> processor) {
        return new StepBuilder("transformGenreStep", jobRepository)
                .<Genre, MongoGenre>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new GenreItemReadListener())
                .listener(new GenreItemWriteListener())
                .listener(new GenreProcessListener())
                .listener(new GenreChunkListener())
                .build();
    }

    private static class GenreItemReadListener implements ItemReadListener<Author> {
        public void beforeRead() {
            LOG.info("Начало чтения");
        }

        public void afterRead(@NonNull Genre o) {
            LOG.info("Конец чтения");
        }

        public void onReadError(@NonNull Exception e) {
            LOG.info("Ошибка чтения");
        }
    }

    private static class GenreItemWriteListener implements ItemWriteListener<MongoGenre> {
        public void beforeWrite(@NonNull List<MongoGenre> list) {
            LOG.info("Начало записи");
        }

        public void afterWrite(@NonNull List<MongoGenre> list) {
            LOG.info("Конец записи");
        }

        public void onWriteError(@NonNull Exception e, @NonNull List<MongoGenre> list) {
            LOG.info("Ошибка записи");
        }
    }

    private static class GenreProcessListener implements ItemProcessListener<Genre, MongoGenre> {
        public void beforeProcess(@NonNull Genre o) {
            LOG.info("Начало обработки");
        }

        public void afterProcess(@NonNull Genre o, MongoGenre o2) {
            LOG.info("Конец обработки");
        }

        public void onProcessError(@NonNull Genre o, @NonNull Exception e) {
            LOG.info("Ошибка обработки");
        }
    }

    private static class GenreChunkListener implements ChunkListener {
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
    public Job importGenreJob(Step transformGenreStep, Step cleanUpGenreStep) {
        return new JobBuilder(IMPORT_GENRE_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformGenreStep)
                .next(cleanUpGenreStep)
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
    public MethodInvokingTaskletAdapter cleanUpGenreTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(genreCleanUpService);
        adapter.setTargetMethod("cleanUp");

        return adapter;
    }

    @Bean
    public Step cleanUpGenreStep() {
        return new StepBuilder("cleanUpGenreStep", jobRepository)
                .tasklet(cleanUpGenreTasklet(), platformTransactionManager)
                .build();
    }
}
