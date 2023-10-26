package ru.otus.spring.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

public class TestServiceImpl implements TestService {

    private final static Logger logger = Logger.getLogger(TestServiceImpl.class.getName());

    private final IOService ioService;

    private final QuestionDao questionDao;

    public TestServiceImpl(IOService ioService, QuestionDao dao) {
        this.ioService = ioService;
        this.questionDao = dao;
    }

    @Override
    public void executeTest() {
        try {
            printTest(questionDao.findAll());
        } catch (Exception e) {
            logger.severe("Ошибка чтения вопросов для теста" + e);
        }
    }

    private void printTest(List<Question> questions) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below");

        for (Question question : questions) {
            ioService.printFormattedLine("%n" + question.text());

            for (Answer answer : question.answers()) {
                ioService.printLine(answer.text());
            }
        }
    }
}
