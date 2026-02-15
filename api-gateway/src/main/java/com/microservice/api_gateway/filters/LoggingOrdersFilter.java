package com.microservice.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class LoggingOrdersFilter  extends AbstractGatewayFilterFactory<LoggingOrdersFilter.Config> {


    public LoggingOrdersFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("Logging from LoggingOrdersFilter pre - " + exchange.getRequest().getURI());
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        System.out.println("Logging from LoggingOrdersFilter post - " + exchange.getResponse().getStatusCode());
                    }));
        };
    }

    public static class Config {
        // Put the configuration properties
    }

}

