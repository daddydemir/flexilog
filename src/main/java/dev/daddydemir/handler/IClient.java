package dev.daddydemir.handler;

public interface IClient {

    void get();
    void getWithQuery();
    void getWithPath();
    void postWithBody();
    void postWithFormData();
    void postWithMultipart();
}
