package ru.otus.catalog.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("library-client", p -> p
                        .path("/library-client/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://LIBRARY-CLIENT"))
                .build();
    }
}
