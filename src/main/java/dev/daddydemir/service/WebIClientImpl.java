package dev.daddydemir.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebIClientImpl implements IClient {

    private final WebClient webClient;

    @Override
    public void get() {
        String response = webClient.get()
                .uri("https://httpbin.org/get")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("Response: {}", response);
    }

    @Override
    public void getWithQuery() {
        String response = webClient.get()
                .uri("https://httpbin.org/get?foo=bar&test=123")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("getWithQuery: {}", response);
    }

    @Override
    public void getWithPath() {
        String response = webClient.get()
                .uri("https://httpbin.org/anything/foo/bar")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("getWithPath: {}", response);
    }

    @Override
    public void postWithBody() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "mehmet");
        requestBody.put("email", "mehmet@test.com");

        String response = webClient.post()
                .uri("https://httpbin.org/post")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("postWithBody: {}", response);
    }

    @Override
    public void postWithFormData() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", "mehmet");
        formData.add("password", "gizliSifre");

        String response = webClient.post()
                .uri("https://httpbin.org/post")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("postWithFormData: {}", response);
    }

    @Override
    public void postWithMultipart() {
        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        multipartRequest.add("file", new ByteArrayResource("Hello World!".getBytes()) {
            @Override
            public String getFilename() {
                return "deneme.txt";
            }
        });
        multipartRequest.add("description", "Test dosya açıklaması");

        String response = webClient.post()
                .uri("https://httpbin.org/post")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartRequest))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("postWithMultipart: {}", response);
    }
}
