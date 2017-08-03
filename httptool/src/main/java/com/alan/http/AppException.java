package com.alan.http;

/**
 * Created by wangbiao on 2017/8/2.
 */

public class AppException extends Exception{
    public int statusCode;
    public String responseMessage;

    public AppException(String message) {
        super(message);
    }



    public enum ErrorType {CANCEL,TIMEOUT,SERVER,JSON,IO,FILE_NOT_FOUND}
    public ErrorType type;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public AppException(String responseMessage, ErrorType type) {
        this.responseMessage = responseMessage;
        this.type = type;
    }

    public AppException() {
    }

    public AppException(int statusCode, String responseMessage,ErrorType type) {
        super(responseMessage);
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.type = type;
    }
}
