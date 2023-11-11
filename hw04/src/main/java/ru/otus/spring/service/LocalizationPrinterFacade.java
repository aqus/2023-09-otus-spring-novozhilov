package ru.otus.spring.service;

import org.springframework.stereotype.Service;

@Service
public class LocalizationPrinterFacade implements PrinterFacade {

    private final IOService ioService;

    private final LocalizationService localizationService;

    public LocalizationPrinterFacade(IOService ioService, LocalizationService localizationService) {
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public void printLine(String key) {
        ioService.printLine(localizationService.getMessage(key));
    }

    @Override
    public void printFormattedLine(String key, String data) {
        ioService.printFormattedLine(localizationService.getMessage(key), data);
    }

    @Override
    public void printFormattedLine(String key, int data) {
        ioService.printFormattedLine(localizationService.getMessage(key), data);
    }

    @Override
    public void printFormattedLine(String key, Object... args) {
        ioService.printFormattedLine(localizationService.getMessage(key), args);
    }

    @Override
    public String  readStringWithPrompt(String key) {
        return ioService.readStringWithPrompt(localizationService.getMessage(key));
    }

    @Override
    public int readIntForRangeWithPrompt(int min, int max, String prompt, String errorMessage) {
        return ioService.readIntForRangeWithPrompt(min, max, prompt, localizationService.getMessage(errorMessage));
    }
}
