package ru.otus.spring.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import ru.otus.spring.domain.Student;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.StudentService;
import ru.otus.spring.service.TestRunnerService;

@ShellComponent
public class TestCommands {

    private final StudentService studentService;

    private final TestRunnerService testRunnerService;

    private final LocalizationService localizationService;

    private Student student;

    public TestCommands(StudentService studentService, TestRunnerService testRunnerService,
                        LocalizationService localizationService) {
        this.studentService = studentService;
        this.testRunnerService = testRunnerService;
        this.localizationService = localizationService;
    }

    @ShellMethod(value = "Login method", key = {"l", "login"})
    public void login() {
        student = studentService.determineCurrentStudent();
    }

    @ShellMethod(value = "Start test", key = {"s", "start"})
    @ShellMethodAvailability("isTestAvailable")
    public void start() {
        testRunnerService.run(student);
    }

    private Availability isTestAvailable() {
        return student == null
                ? Availability.unavailable(localizationService.getMessage("test.login.request"))
                : Availability.available();
    }
}
