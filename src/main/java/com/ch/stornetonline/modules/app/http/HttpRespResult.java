package com.ch.stornetonline.modules.app.http;

public class HttpRespResult {
    public HttpRespResult(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    private int httpStatusCode;
    private String httpRespBody;

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getHttpRespBody() {
        return httpRespBody;
    }

    public void setHttpRespBody(String httpResqBody) {
        this.httpRespBody = httpResqBody;
    }
}
