package ru.otus.spring.service;

public interface PrinterFacade {

    void printLine(String key);

    void printFormattedLine(String key, String data);

    void printFormattedLine(String key, int data);

    void printFormattedLine(String key, Object... args);

    String readStringWithPrompt(String key);

    int readIntForRangeWithPrompt(int min, int max, String prompt, String errorMessage);
}
