package dev.daddydemir.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@Slf4j
public class WebClientLoggingFilter {

    public static ExchangeFilterFunction logRequestAndResponse() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            log.info("WebClient Request: {} {}", request.method(), request.url());
            log.info("WebClient Request Headers: {}", request.headers());
            return Mono.just(request);
        }).andThen(ExchangeFilterFunction.ofResponseProcessor(response -> {
            log.info("WebClient Response Status: {}", response.statusCode());
            log.info("WebClient Response Headers: {}", response.headers().asHttpHeaders());
            return logResponseBody(response);
        }));
    }


    private static Mono<ClientResponse> logResponseBody(ClientResponse response) {
        return response.bodyToMono(String.class)
                .defaultIfEmpty("")
                .flatMap(body -> {
                    log.info("WebClient Response Body: {}", body);
                    return Mono.just(ClientResponse.create(response.statusCode())
                            .headers(headers -> headers.addAll(response.headers().asHttpHeaders()))
                            .body(body)
                            .build());
                });
    }
}
