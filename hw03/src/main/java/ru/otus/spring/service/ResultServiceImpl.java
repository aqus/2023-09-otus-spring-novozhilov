package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import ru.otus.spring.config.TestConfig;
import ru.otus.spring.domain.TestResult;

@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final IOService ioService;
    
    private final LocalizationService localizationService;

    public ResultServiceImpl(TestConfig testConfig, IOService ioService, LocalizationService localizationService) {
        this.testConfig = testConfig;
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine("");
        ioService.printLine(localizationService.getMessage("test.results.title"));
        ioService.printFormattedLine(localizationService.getMessage("test.student.title"),
                testResult.getStudent().getFullName());
        ioService.printFormattedLine(localizationService.getMessage("test.answered.count"),
                testResult.getAnsweredQuestions().size());
        ioService.printFormattedLine(localizationService.getMessage("test.right.answers"),
                testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            ioService.printLine(localizationService.getMessage("test.success"));
            return;
        }
        ioService.printLine(localizationService.getMessage("test.fail"));
    }
}
