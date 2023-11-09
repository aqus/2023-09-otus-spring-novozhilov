package ru.otus.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestResult;

@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;
    
    private final PrinterFacade printerFacade;

    public TestServiceImpl(IOService ioService, QuestionDao dao, PrinterFacade printerFacade) {
        this.ioService = ioService;
        this.questionDao = dao;
        this.printerFacade = printerFacade;
    }

    @Override
    public TestResult executeTestFor(Student student) {
        printerFacade.printFormattedLine("test.title");
        List<Question> questions = questionDao.findAll();
        TestResult testResult = new TestResult(student);

        for (Question question: questions) {
            ioService.printLine(question.text());
            Answer answer = readAnswer(question);
            testResult.applyAnswer(question, answer.isCorrect());
        }
        return testResult;
    }
    
    private Answer readAnswer(Question question) {
        List<Answer> answers = question.answers();
        StringBuilder stringBuilder = new StringBuilder();
        for (Answer answer : answers) {
            stringBuilder.append(answer.text()).append("\n");
        }
        int answerNumber = printerFacade.readIntForRangeWithPrompt(1, answers.size(), stringBuilder.toString(),
                "test.invalid.answer.number");
        return answers.get(answerNumber - 1);
    }
}
