package com.alan.http;

import android.os.Build;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.Executor;

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

    public boolean isCancelled;

    public String tag;

    public RequestTask task;

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void cancel(boolean force) {
        isCancelled = true;
        iCallBack.cancel();
        if (force && task != null) {
            task.cancel(true);
        }
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
            Log.e("download","Request checkIfCancelled invoked!");
            throw new AppException("the request is cancelled",AppException.ErrorType.CANCEL);
        }
    }

    public void execute(Executor mExecutors) {
        task = new RequestTask(this);
        if (Build.VERSION.SDK_INT > 11) {
            task.executeOnExecutor(mExecutors);
        } else {
            task.execute();
        }
    }
}
