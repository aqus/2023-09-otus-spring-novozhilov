package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import ru.otus.spring.config.TestConfig;
import ru.otus.spring.domain.TestResult;

@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final PrinterFacade printerFacade;

    public ResultServiceImpl(TestConfig testConfig, PrinterFacade printerFacade) {
        this.testConfig = testConfig;
        this.printerFacade = printerFacade;
    }

    @Override
    public void showResult(TestResult testResult) {
        printerFacade.printLine("test.results.title");
        printerFacade.printFormattedLine("test.student.title", testResult.getStudent().getFullName());
        printerFacade.printFormattedLine("test.answered.count", testResult.getAnsweredQuestions().size());
        printerFacade.printFormattedLine("test.right.answers", testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            printerFacade.printLine("test.success");
            return;
        }
        printerFacade.printLine("test.fail");
    }
}
