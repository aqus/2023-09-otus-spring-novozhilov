package ru.otus.catalog.shell;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.catalog.configs.ApplicationConfiguration;

import java.util.Properties;

import static ru.otus.catalog.configs.GenreConfigJob.IMPORT_GENRE_JOB_NAME;

@ShellComponent
public class GenreCommands {

    private final ApplicationConfiguration applicationConfiguration;

    private final JobOperator jobOperator;

    private final JobExplorer jobExplorer;

    public GenreCommands(ApplicationConfiguration applicationConfiguration, JobOperator jobOperator,
                         JobExplorer jobExplorer) {
        this.applicationConfiguration = applicationConfiguration;
        this.jobOperator = jobOperator;
        this.jobExplorer = jobExplorer;
    }

    @ShellMethod(value = "startGenreMigrationJob", key = "genre-sm")
    public void startMigrationJob() throws Exception {
        Properties properties = new Properties();
        properties.put("jobCount", applicationConfiguration.getJobCount());
        properties.put("currentItemCount", applicationConfiguration.getGenre());
        long executionId = jobOperator.start(IMPORT_GENRE_JOB_NAME, properties);
        System.out.println(jobOperator.getSummary(executionId));
    }

    @ShellMethod(value = "showGenreInfo", key = "genre-i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_GENRE_JOB_NAME));
    }
}
