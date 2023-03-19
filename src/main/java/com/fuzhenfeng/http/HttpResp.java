package com.fuzhenfeng.http;

public class HttpResp {
    private int code;
    private String body;

    public HttpResp(int code) {
        this.code = code;
    }

    public HttpResp(int code, String body) {
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }
}
