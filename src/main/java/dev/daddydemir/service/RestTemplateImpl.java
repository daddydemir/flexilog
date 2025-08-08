package dev.daddydemir.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestTemplateImpl implements IClient {

    private final RestTemplate restTemplate;

    @Override
    public void get() {
        ResponseEntity<String> response = restTemplate.getForEntity("https://httpbin.org/get", String.class);
        log.info("Response: {}", response);
    }

    @Override
    public void getWithQuery() {
        ResponseEntity<String> response = restTemplate.getForEntity("https://httpbin.org/get?foo=bar&test=123", String.class);
        log.info("getWithQuery: {}", response);
    }

    @Override
    public void getWithPath() {
        ResponseEntity<String> res = restTemplate.getForEntity("https://httpbin.org/anything/foo/bar", String.class);
        log.info("getWithPath: {}", res);
    }

    @Override
    public void postWithBody() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "mehmet");
        requestBody.put("email", "mehmet@test.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://httpbin.org/post", request, String.class);
        log.info("postWithBody: {}", response);
    }

    @Override
    public void postWithFormData() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", "mehmet");
        formData.add("password", "gizliSifre");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://httpbin.org/post", request, String.class);
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(multipartRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://httpbin.org/post", request, String.class);
        System.out.println(response.getBody());

    }
}
