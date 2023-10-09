package ru.otus.spring.dao.dto;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import ru.otus.spring.domain.Answer;

public class AnswerCsvConverter extends AbstractCsvConverter {

    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String[] answerData = value.split("%");
        return new Answer(answerData[0], Boolean.parseBoolean(answerData[1]));
    }
}
