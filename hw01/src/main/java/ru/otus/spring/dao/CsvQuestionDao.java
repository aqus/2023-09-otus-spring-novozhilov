package ru.otus.spring.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.domain.Question;

public class CsvQuestionDao implements QuestionDao {
    
    private final TestFileNameProvider testFileNameProvider;

    public CsvQuestionDao(TestFileNameProvider testFileNameProvider) {
        this.testFileNameProvider = testFileNameProvider;
    }

    @Override
    public List<Question> findAll() throws URISyntaxException, IOException {
        String testFileName = testFileNameProvider.getTestFileName();
        // читаем вопросы из файла тут
        return null;
    }
}
