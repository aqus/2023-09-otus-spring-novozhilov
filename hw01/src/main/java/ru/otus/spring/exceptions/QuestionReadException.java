package ru.otus.spring.exceptions;

public class QuestionReadException extends RuntimeException {

    public QuestionReadException(String message, Throwable ex) {
        super(message, ex);
    }

    public QuestionReadException(String message) {
        super(message);
    }
}
