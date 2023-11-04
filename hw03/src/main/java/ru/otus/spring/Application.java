package ru.otus.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.otus.spring.service.TestRunnerService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		TestRunnerService testRunnerService = ctx.getBean(TestRunnerService.class);
		testRunnerService.run();
	}

}
