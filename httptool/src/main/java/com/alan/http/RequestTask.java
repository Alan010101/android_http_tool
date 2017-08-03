package com.alan.http;

import android.os.AsyncTask;

import java.net.HttpURLConnection;

public class RequestTask extends AsyncTask<Void,Integer,Object> {

    private Request request;

    private int curRetryIndex;

    public RequestTask(Request request) {
        this.request = request;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public Object request() {
        try {
            HttpURLConnection connection = HttpUrlConnectionUtil.execute(request);
            if (request.enableProgressUpdated) {
                return request.iCallBack.parse(connection, new OnProgressUpdatedListener() {
                    @Override
                    public void onProgressUpdated(int curLen, int totalLen) {
                        publishProgress(curLen, totalLen);
                    }
                });
            } else {
                return request.iCallBack.parse(connection);
            }

        } catch (AppException e) {
            if (e.type == AppException.ErrorType.TIMEOUT) {
                if (curRetryIndex < request.maxRetryCount) {
                    curRetryIndex++;
                    return request();
                }
            }
            return e;
        }
    }

    @Override
    protected Object doInBackground(Void... params) {
        return request();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(o instanceof Exception){
            if(request.onGlobalExceptionListener!=null){
                // GlobalExceptionListener 如果没有处理，就调用request.iCallBack.onFailure((Exception)o);
                if(!request.onGlobalExceptionListener.handleException((AppException)o)){
                    request.iCallBack.onFailure((AppException)o);
                }
            }
        }else{
            request.iCallBack.onSuccess(o);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        request.iCallBack.onProgressUpdated(values[0],values[1]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
