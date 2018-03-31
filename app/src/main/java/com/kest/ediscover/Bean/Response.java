package com.kest.ediscover.Bean;

/**
 * Created by dk on 2018/1/4.
 */

public class Response {
    private int code;
    private String errorMessage;
    private String data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Response(){}
    public Response(int code, String errorMessage, String data) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.data = data;
    }
}
