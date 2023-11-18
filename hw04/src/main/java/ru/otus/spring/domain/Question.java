package ru.otus.spring.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
