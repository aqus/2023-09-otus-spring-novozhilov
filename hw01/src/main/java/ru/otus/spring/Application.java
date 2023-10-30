package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.otus.spring.service.TestRunnerService;

public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestRunnerService testRunnerService = ctx.getBean(TestRunnerService.class);
        testRunnerService.run();
    }
}
