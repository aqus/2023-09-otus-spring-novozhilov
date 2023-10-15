package ru.otus.spring.service;

import java.util.List;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
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
        ioService.printFormattedLine("Please answer the questions below");
        try {
            List<Question> questions = questionDao.findAll();
            for (Question question : questions) {
                ioService.printFormattedLine("%n" + question.text());
                
                for (Answer answer : question.answers()) {
                    ioService.printLine(answer.text());
                }
            }
        } catch (Exception e) {
            throw new QuestionReadException("Ошибка чтения вопросов для теста", e.getCause());
        }
    }
}
