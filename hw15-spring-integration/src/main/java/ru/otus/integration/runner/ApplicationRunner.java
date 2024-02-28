package ru.otus.integration.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.integration.domain.Butterfly;
import ru.otus.integration.domain.Caterpillar;
import ru.otus.integration.services.ButterflyGateway;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private static final Logger LOG = Logger.getLogger(ApplicationRunner.class.getName());

    private final ButterflyGateway butterflyGateway;

    public ApplicationRunner(ButterflyGateway butterflyGateway) {
        this.butterflyGateway = butterflyGateway;
    }

    @Override
    public void run(String... args)  {
        LOG.info("Start integration");
        List<Caterpillar> caterpillars = List.of(
                new Caterpillar("Гусеница 1"),
                new Caterpillar("Гусеница 2"),
                new Caterpillar("Гусеница 3"),
                new Caterpillar("Гусеница 4")
        );
        Collection<Butterfly> result = butterflyGateway.process(caterpillars);
        LOG.info("Transformation from caterpillars to butterflies has finished");
        LOG.info(result.toString());
    }
}
