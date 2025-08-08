package dev.daddydemir.handler;

import dev.daddydemir.service.IClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web-client")
@RequiredArgsConstructor
public class WebClientController {

    private final IClient webIClientImpl;

    @GetMapping("/get")
    public void get() {
        webIClientImpl.get();
    }

    @GetMapping("/get-with-query")
    public void getWithQuery() {
        webIClientImpl.getWithQuery();
    }

    @GetMapping("/get-with-path")
    public void getWithPath() {
        webIClientImpl.getWithPath();
    }

    @GetMapping("/post-with-body")
    public void postWithBody() {
        webIClientImpl.postWithBody();
    }

    @GetMapping("/post-with-form-data")
    public void postWithFormData() {
        webIClientImpl.postWithFormData();
    }

    @GetMapping("/post-with-multipart")
    public void postWithMultipart() {
        webIClientImpl.postWithMultipart();
    }
}
