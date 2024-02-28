package ru.otus.integration.services;

import org.springframework.stereotype.Service;
import ru.otus.integration.domain.Butterfly;
import ru.otus.integration.domain.Caterpillar;

@Service
public class ButterflyServiceImpl implements ButterflyService {
    @Override
    public Butterfly transform(Caterpillar caterpillar) {
        return new Butterfly(caterpillar.nickname());
    }
}
