package ru.otus.spring.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestResult {

    private final Student student;

    private final List<Question> answeredQuestions;

    private int rightAnswersCount;

    public TestResult(Student student) {
        this.student = student;
        this.answeredQuestions = new ArrayList<>();
    }

    public void applyAnswer(Question question, boolean isRightAnswer) {
        answeredQuestions.add(question);
        if (isRightAnswer) {
            rightAnswersCount++;
        }
    }

    public Student getStudent() {
        return student;
    }

    public List<Question> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public int getRightAnswersCount() {
        return rightAnswersCount;
    }

    public void setRightAnswersCount(int rightAnswersCount) {
        this.rightAnswersCount = rightAnswersCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestResult that = (TestResult) o;
        return rightAnswersCount == that.rightAnswersCount && Objects.equals(student, that.student)
                && Objects.equals(answeredQuestions, that.answeredQuestions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, answeredQuestions, rightAnswersCount);
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "student=" + student +
                ", answeredQuestions=" + answeredQuestions +
                ", rightAnswersCount=" + rightAnswersCount +
                '}';
    }
}
