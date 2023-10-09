package ru.otus.spring.service;

import java.io.IOException;
import java.net.URISyntaxException;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.exceptions.QuestionReadException;

public class TestServiceImpl implements TestService {
    
    private final IOService ioService;
    private final QuestionDao questionDao;

    public TestServiceImpl(IOService ioService, QuestionDao dao) {
        this.ioService = ioService;
        this.questionDao = dao;
    }

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        try {
            questionDao.findAll();
        } catch (URISyntaxException e) {
            throw new QuestionReadException("Ошибка чтения вопросов для теста", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
