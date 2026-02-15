package com.microservice.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter {

     @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
         log.info("Logging from Global pre - {}", exchange.getRequest().getURI());
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {;
                    log.info("Logging from Global post - {}", exchange.getResponse().getStatusCode());
                }));
    }
}
