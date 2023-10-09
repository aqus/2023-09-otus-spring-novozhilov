package ru.otus.spring.config;

public class AppConfig implements TestFileNameProvider {
    
    private final String testFileName;

    public AppConfig(String testFileName) {
        this.testFileName = testFileName;
    }

    @Override
    public String getTestFileName() {
        return testFileName;
    }
}
