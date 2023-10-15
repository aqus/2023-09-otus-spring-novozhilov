package ru.otus.spring.service;

public class StreamsIOService implements IOService {
    
    public StreamsIOService() {
    }

    @Override
    public void printLine(String s) {
        System.out.println(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        System.out.printf(s + "%n", args);
    }
}
