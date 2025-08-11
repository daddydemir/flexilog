package dev.daddydemir.config;

import dev.daddydemir.filter.WebClientLoggingFilter;
import dev.daddydemir.interceptors.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;


@Configuration
public class Client {

    @Bean
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new LoggingInterceptor()));
        return  restTemplate;
    }

    @Bean
    public WebClient createWebClient() {
        return WebClient.builder()
                .filter(WebClientLoggingFilter.logRequestAndResponse())
                .build();
    }
}
    