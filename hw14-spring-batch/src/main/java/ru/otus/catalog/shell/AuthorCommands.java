package ru.otus.catalog.shell;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.catalog.configs.ApplicationConfiguration;

import java.util.Properties;

import static ru.otus.catalog.configs.AuthorConfigJob.IMPORT_AUTHOR_JOB_NAME;

@ShellComponent
public class AuthorCommands {

    private final ApplicationConfiguration applicationConfiguration;

    private final JobOperator jobOperator;

    private final JobExplorer jobExplorer;

    public AuthorCommands(ApplicationConfiguration applicationConfiguration, JobOperator jobOperator,
                          JobExplorer jobExplorer) {
        this.applicationConfiguration = applicationConfiguration;
        this.jobOperator = jobOperator;
        this.jobExplorer = jobExplorer;
    }


    @ShellMethod(value = "startAuthorMigrationJob", key = "author-sm")
    public void startMigrationJobWithJobOperator() throws Exception {
        Properties properties = new Properties();
        properties.put("jobCount", applicationConfiguration.getJobCount());
        properties.put("currentItemCount", applicationConfiguration.getAuthor());
        long executionId = jobOperator.start(IMPORT_AUTHOR_JOB_NAME, properties);
        System.out.println(jobOperator.getSummary(executionId));
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "showAuthorInfo", key = "author-i")
    public void showInfo() {
        // Author
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_AUTHOR_JOB_NAME));
    }
}
