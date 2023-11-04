package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import ru.otus.spring.config.TestConfig;
import ru.otus.spring.domain.TestResult;

@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final IOService ioService;

    public ResultServiceImpl(TestConfig testConfig, IOService ioService) {
        this.testConfig = testConfig;
        this.ioService = ioService;
    }

    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine("");
        ioService.printLine("Test results: ");
        ioService.printFormattedLine("Student: %s", testResult.getStudent().getFullName());
        ioService.printFormattedLine("Answered questions count: %d", testResult.getAnsweredQuestions().size());
        ioService.printFormattedLine("Right answers count: %d", testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            ioService.printLine("Congratulations! You have passed the test!");
            return;
        }
        ioService.printLine("Sorry. You have failed the test.");
    }
}
