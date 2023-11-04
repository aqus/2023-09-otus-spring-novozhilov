package ru.otus.spring.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestResult;

@Service
public class TestRunnerService implements CommandLineRunner {
    
    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    public TestRunnerService(TestService testService, StudentService studentService, ResultService resultService) {
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
