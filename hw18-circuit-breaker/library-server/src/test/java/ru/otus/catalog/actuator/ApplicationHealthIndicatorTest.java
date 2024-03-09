package ru.otus.catalog.actuator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ApplicationHealthIndicator.class})
class ApplicationHealthIndicatorTest {

    @Autowired
    private ApplicationHealthIndicator applicationHealthIndicator;

    @DisplayName("должен возвращать статус UP")
    @Test
    void shouldReturnUpHealthResult() {
        Health health = applicationHealthIndicator.health();
        assertThat(health.getStatus()).isEqualTo(Status.UP);
        assertThat(health.getDetails().get("answer")).isEqualTo("PONG");
    }
}