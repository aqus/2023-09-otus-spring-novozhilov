package ru.otus.spring.dao.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

public class QuestionDto {
    
    @CsvBindByPosition(position = 0)
    private String text;

    @CsvBindAndSplitByPosition(position = 1, collectionType = ArrayList.class, elementType = Answer.class,
            converter = AnswerCsvConverter.class, splitOn = "\\|")
    private List<Answer> answers;

    public QuestionDto(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public QuestionDto() {
    }

    public Question toDomainObject() {
        return new Question(text, answers);
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "text='" + text + '\'' +
                ", answers=" + answers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionDto that = (QuestionDto) o;
        return Objects.equals(text, that.text) && Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, answers);
    }
}
