package com.alan.http;

import java.net.HttpURLConnection;

/**
 * Created by wangbiao on 2017/8/1.
 */

public interface ICallBack<T> {
    void onSuccess(T result);
    void onFailure(AppException e);
    T parse(HttpURLConnection connection,OnProgressUpdatedListener onProgressUpdatedListener)  throws AppException;
    T parse(HttpURLConnection connection)  throws AppException;
    void onProgressUpdated(int curLen, int totalLen);
    void cancel();
    T postRequest(T t);
    T preRequest();
}
