package ru.otus.spring.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.dao.dto.QuestionDto;
import ru.otus.spring.domain.Question;

public class CsvQuestionDao implements QuestionDao {
    
    private final TestFileNameProvider testFileNameProvider;

    public CsvQuestionDao(TestFileNameProvider testFileNameProvider) {
        this.testFileNameProvider = testFileNameProvider;
    }

    @Override
    public List<Question> findAll() throws CsvValidationException, IOException {
        return getQuestionsFromFile(testFileNameProvider.getTestFileName());
    }

    private List<Question> getQuestionsFromFile(String testFileName) throws CsvValidationException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(testFileName);

        if (inputStream == null) {
            throw new RuntimeException("Questions input stream is null");
        }

        CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
        ColumnPositionMappingStrategy<QuestionDto> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(QuestionDto.class);
        CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(reader)
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<QuestionDto> parsedQuestions = csvToBean.parse();

        return parsedQuestions.stream().map(QuestionDto::toDomainObject).toList();
    }
}
