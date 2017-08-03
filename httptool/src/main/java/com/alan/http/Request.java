package com.alan.http;

import java.util.Map;

/**
 * Created by wangbiao on 2017/7/31.
 */

public class Request {

    public enum RequestMethod{GET,POST,PUT,DELETE};
    public String url;
    public String content;
    public Map<String,String> headers;
    public RequestMethod method;

    public ICallBack iCallBack;

    public static boolean enableProgressUpdated;

    public OnGlobalExceptionListener onGlobalExceptionListener;

    public int maxRetryCount = 3;

    public volatile boolean isCancelled;

    public void cancel(){
        isCancelled = true;
        iCallBack.cancel();
    }

    public void enableProgressUpdated(boolean enableProgressUpdated) {
        this.enableProgressUpdated = enableProgressUpdated;
    }

    public Request(String url,RequestMethod method){
        this.url = url;
        this.method = method;
    }

    public Request(String url){
        this.url = url;
        this.method = RequestMethod.GET;
    }

    public void setCallback(ICallBack iCallBack){
        this.iCallBack = iCallBack;
    }


    public void setGlobalExcetionListener(OnGlobalExceptionListener onGlobalExceptionListener) {
        this.onGlobalExceptionListener = onGlobalExceptionListener;
    }


    public void checkIfCancelled() throws AppException {
        if(isCancelled){
            throw new AppException("the request is cancelled",AppException.ErrorType.CANCEL);
        }
    }
}
