package com.example.mylibrarysecond;

public class ApiCallResponse {
  private Object jsonBody;
  private int statusCode;

  public ApiCallResponse(Object jsonBody, int statusCode) {
    this.jsonBody = jsonBody;
    this.statusCode = statusCode;
  }

  public boolean result() {
    if (statusCode >= 200 && statusCode < 300) {
      return true;
    }
    return false;
  }
}

