package ru.otus.spring.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.dao.CsvQuestionDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("TestServiceImplTest class")
@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {

    private final static String TEST_FILE_NAME = "test.csv";

    @Mock
    private TestFileNameProvider testFileNameProvider;

    private IOService ioService;

    private CsvQuestionDao questionDao;

    private TestServiceImpl testService;

    private final ByteArrayOutputStream resultOutput = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(resultOutput));
        given(testFileNameProvider.getTestFileName()).willReturn(TEST_FILE_NAME);
        ioService = new StreamsIOService(System.out);
        questionDao = new CsvQuestionDao(testFileNameProvider);
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
        testService.executeTest();
        assertEquals(expectedOutput, resultOutput.toString());
    }
}
