package ru.otus.spring.config;

import java.util.Locale;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "test")
public class TestProperties implements TestFileNameProvider, TestConfig, LocaleProvider {

    private final Map<Locale, String> testFileNameByLocale;

    private final int rightAnswersCountToPass;
    
    private final Locale locale;

    @ConstructorBinding
    public TestProperties(Map<Locale, String> testFileNameByLocale,
                          int rightAnswersCountToPass,
                          Locale locale) {
        this.testFileNameByLocale = testFileNameByLocale;
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.locale = locale;
    }
    
    @Override
    public String getTestFileName() {
        return testFileNameByLocale.get(locale);
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
