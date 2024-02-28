package ru.otus.integration.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.domain.Butterfly;
import ru.otus.integration.domain.Caterpillar;

import java.util.Collection;

@MessagingGateway
public interface ButterflyGateway {

    @Gateway(requestChannel = "caterpillarsChannel", replyChannel = "butterflyChannel")
    Collection<Butterfly> process(Collection<Caterpillar> caterpillars);
}
