package com.fuzhenfeng.http;

import java.util.Map;

public class HttpReq {
    private String realmName;
    private String method;
    private Map<String, String> header;
    private String body;

    public HttpReq(String uri, String method, Map<String, String> header, String body) {
        this.header = header;
        this.body = body;
        this.realmName = uri;
        this.method = method;
    }

    public String getRealmName() {
        return realmName;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}
