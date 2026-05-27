package com.code.prodapp.apigateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// This is a GlobalFilter which affects all requests coming to the API Gateway
@Component
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    final Logger logger = LoggerFactory.getLogger(GlobalLoggingFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // Pre-Filter -> Runs Before routing to the correct microservice and inspects/changes the REQUEST
        logger.info("Pre-Global-Filter, now request routing to : "+exchange.getRequest().getURI());
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    // Post-Global Filter -> Runs after the request has been routed to the correct microservice and response is returning to client
                    logger.info("Post-Global-Filter , now response coming from "+exchange.getRequest().getURI());
                }));


    }

    @Override
    public int getOrder() {
        // Highest Priority, runs always first
        return Integer.MIN_VALUE;
    }
}
