package ru.otus.catalog.configs;


import de.flapdoodle.embed.mongo.spring.autoconfigure.EmbeddedMongoAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.catalog.repositories.mongo.MongoAuthorRepository;
import ru.otus.catalog.repositories.mongo.MongoBookRepository;
import ru.otus.catalog.repositories.mongo.MongoGenreRepository;
import ru.otus.catalog.services.BatchService;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.catalog.configs.AuthorConfigJob.IMPORT_AUTHOR_JOB_NAME;
import static ru.otus.catalog.configs.BookConfigJob.IMPORT_BOOK_JOB_NAME;
import static ru.otus.catalog.configs.GenreConfigJob.IMPORT_GENRE_JOB_NAME;

@SpringBootTest
@SpringBatchTest
@Import(EmbeddedMongoAutoConfiguration.class)
public class JobConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private ListableJobLocator jobLocator;

    @MockBean
    private MongoAuthorRepository mongoAuthorRepository;

    @MockBean
    private MongoGenreRepository mongoGenreRepository;

    @MockBean
    private MongoBookRepository mongoBookRepository;

    @MockBean
    private ApplicationConfiguration applicationConfiguration;

    @MockBean
    private BatchService batchService;

    @Autowired
    private MongoOperations mongoOperations;

    @BeforeEach
    void setUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testAuthorJob() throws Exception {
        jobLauncherTestUtils.setJob(jobLocator.getJob(IMPORT_AUTHOR_JOB_NAME));
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_AUTHOR_JOB_NAME);

        JobParameters parameters = new JobParametersBuilder()
                .addJobParameter("currentItemCount", 0, Integer.class).toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }

    @Test
    void testGenreJob() throws Exception {
        jobLauncherTestUtils.setJob(jobLocator.getJob(IMPORT_GENRE_JOB_NAME));
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_GENRE_JOB_NAME);

        JobParameters parameters = new JobParametersBuilder()
                .addJobParameter("currentItemCount", 0, Integer.class).toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }

    @Test
    void testBookJob() throws Exception {
        jobLauncherTestUtils.setJob(jobLocator.getJob(IMPORT_BOOK_JOB_NAME));
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_BOOK_JOB_NAME);

        JobParameters parameters = new JobParametersBuilder()
                .addJobParameter("currentItemCount", 0, Integer.class).toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }
}
