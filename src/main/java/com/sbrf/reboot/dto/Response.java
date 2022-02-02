package com.sbrf.reboot.dto;


public class Response {
    String statusCode;

    public Response(String statusCode) {
        this.statusCode = statusCode;
    }

    public Response() {}

    public String getStatusCode() {
        return statusCode;
    }
}
