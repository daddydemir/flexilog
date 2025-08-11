package dev.daddydemir.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("Request URI: {}", request.getURI());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request Headers: {}", request.getHeaders());
        log.info("Request Body: {}", new String(body, StandardCharsets.UTF_8));

        ClientHttpResponse response = execution.execute(request, body);

        log.info("Response Status Code: {}", response.getStatusCode());
        log.info("Response Headers: {}", response.getHeaders());

        byte[] responseBody = StreamUtils.copyToByteArray(response.getBody());
        log.info("Response Body: {}", new String(responseBody, StandardCharsets.UTF_8));

        return new BufferedClientHttpResponse(response, responseBody);
    }

    private static class BufferedClientHttpResponse implements ClientHttpResponse {
        private final ClientHttpResponse originalResponse;
        private final byte[] responseBody;

        public BufferedClientHttpResponse(ClientHttpResponse originalResponse, byte[] responseBody) {
            this.originalResponse = originalResponse;
            this.responseBody = responseBody;
        }


        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return originalResponse.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return originalResponse.getStatusText();
        }

        @Override
        public void close() {
            originalResponse.close();
        }

        @Override
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(responseBody);
        }

        @Override
        public org.springframework.http.HttpHeaders getHeaders() {
            return originalResponse.getHeaders();
        }
    }
}