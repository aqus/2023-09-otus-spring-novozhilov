package ru.otus.integration.services;

import ru.otus.integration.domain.Butterfly;
import ru.otus.integration.domain.Caterpillar;

public interface ButterflyService {

    Butterfly transform(Caterpillar caterpillar);
}
