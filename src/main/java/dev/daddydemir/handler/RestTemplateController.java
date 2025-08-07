package dev.daddydemir.handler;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest-template")
@RequiredArgsConstructor
public class RestTemplateController {

    private final IClient restTemplateImpl;

    @GetMapping("/get")
    public void get() {
        restTemplateImpl.get();
    }

    @GetMapping("/get-with-query")
    public void getWithQuery() {
        restTemplateImpl.getWithQuery();
    }

    @GetMapping("/get-with-path")
    public void getWithPath() {
        restTemplateImpl.getWithPath();
    }

    @GetMapping("/post-with-body")
    public void postWithBody() {
        restTemplateImpl.postWithBody();
    }

    @GetMapping("/post-with-form-data")
    public void postWithFormData() {
        restTemplateImpl.postWithFormData();
    }

    @GetMapping("/post-with-multipart")
    public void postWithMultipart() {
        restTemplateImpl.postWithMultipart();
    }

}
