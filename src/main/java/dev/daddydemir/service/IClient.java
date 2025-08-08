package dev.daddydemir.service;

public interface IClient {

    void get();
    void getWithQuery();
    void getWithPath();
    void postWithBody();
    void postWithFormData();
    void postWithMultipart();
}
