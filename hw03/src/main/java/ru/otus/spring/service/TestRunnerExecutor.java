package ru.otus.spring.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestResult;

@ConditionalOnProperty(
        prefix = "test.commandLineRunner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@Component
public class TestRunnerExecutor implements CommandLineRunner {
    
    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    public TestRunnerExecutor(TestService testService, StudentService studentService, ResultService resultService) {
        this.testService = testService;
        this.studentService = studentService;
        this.resultService = resultService;
    }

    @Override
    public void run(String... args) {
        Student student = studentService.determineCurrentStudent();
        TestResult testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}
