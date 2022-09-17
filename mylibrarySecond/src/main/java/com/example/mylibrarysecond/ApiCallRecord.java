package com.example.mylibrarysecond;

import com.example.enums.BodyType;

import java.util.Map;

public class ApiCallRecord {

    private String callName;
    private String apiUrl;
    private Map<String, String> headers;
    private Map<String, String> params;
    private String body;
    private BodyType bodyType;

    public ApiCallRecord() {
    }

    public ApiCallRecord(String callName, String apiUrl, Map<String, String> headers, Map<String, String> params, String body, BodyType bodyType) {
        this.callName = callName;
        this.apiUrl = apiUrl;
        this.headers = headers;
        this.params = params;
        this.body = body;
        this.bodyType = bodyType;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public String toString() {
        return "ApiCallRecord{" +
                "callName='" + callName + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", headers=" + headers +
                ", params=" + params +
                ", body='" + body + '\'' +
                ", bodyType=" + bodyType +
                '}';
    }
}
