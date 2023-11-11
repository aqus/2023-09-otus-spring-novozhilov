package ru.otus.spring.service;

import org.springframework.stereotype.Component;

import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestResult;

@Component
public class TestRunnerServiceImpl implements TestRunnerService {
    
    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    public TestRunnerServiceImpl(TestService testService, StudentService studentService, ResultService resultService) {
        this.testService = testService;
        this.studentService = studentService;
        this.resultService = resultService;
    }

    @Override
    public void run(Student student) {
        TestResult testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}
