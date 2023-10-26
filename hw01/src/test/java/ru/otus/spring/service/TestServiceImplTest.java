package ru.otus.spring.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("TestServiceImplTest class")
public class TestServiceImplTest {

    private IOService ioService;

    private QuestionDao questionDao;

    private TestService testService;

    private ByteArrayOutputStream resultOutput;

    @BeforeEach
    void setUp() {
        resultOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(resultOutput));
        questionDao = mock(QuestionDao.class);
        ioService = new StreamsIOService(System.out);
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @DisplayName("Should print all the questions")
    @Test
    void shouldPrintAllTheQuestions() {
        String expectedOutput = """

                Please answer the questions below

                What was the day on 15th august 1947?
                Friday
                Saturday
                Sunday
                """;
        Question returnQuestion = new Question("What was the day on 15th august 1947?", List.of(
                new Answer("Friday", true),
                new Answer("Saturday", false),
                new Answer("Sunday", false)));
        List<Question> returnQuestions = List.of(returnQuestion);
        when(questionDao.findAll()).thenReturn(returnQuestions);
        testService.executeTest();
        assertEquals(expectedOutput, resultOutput.toString());
    }
}
