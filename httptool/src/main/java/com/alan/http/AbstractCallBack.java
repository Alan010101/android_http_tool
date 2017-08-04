package com.alan.http;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * Created by wangbiao on 2017/8/1.
 */

public abstract class AbstractCallBack<T> implements ICallBack<T> {

    private String path;

    public boolean isCancelled;

    @Override
    public T parse(HttpURLConnection connection) throws AppException {
        return parse(connection,null);
    }

    @Override
    public T parse(HttpURLConnection connection, OnProgressUpdatedListener listener) throws AppException {
        try {
            checkIfCancelled();
            int status = connection.getResponseCode();
            if (status == 200) {
                if (path == null) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    InputStream in = null;
                    String encoding = connection.getContentEncoding();
                    if(encoding!=null&&"gzip".equalsIgnoreCase(encoding)){
                        in = new GZIPInputStream(connection.getInputStream());
                    }else if(encoding!=null&&"deflate".equalsIgnoreCase(encoding)){
                        in = new InflaterInputStream(connection.getInputStream());
                    }else{
                        in = connection.getInputStream();
                    }
                    byte[] buf = new byte[2048];
                    int len;
                    while ((len = in.read(buf)) != -1) {
                        checkIfCancelled();
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.flush();
                    out.close();
                    checkIfCancelled();
                    String result = new String(out.toByteArray());
                    T t = bindData(result);
                    return postRequest(t);
                } else {
                    FileOutputStream out = new FileOutputStream(path);
                    int totalLen = connection.getContentLength();
                    InputStream in = connection.getInputStream();
                    int curLen = 0;
                    byte[] buf = new byte[2048];
                    int len;
                    while ((len = in.read(buf)) != -1) {
                        checkIfCancelled();
                        out.write(buf, 0, len);
                        curLen += len;
                        listener.onProgressUpdated(curLen, totalLen);
                    }
                    in.close();
                    out.flush();
                    out.close();
                    checkIfCancelled();
                    return bindData(path);
                }
            }else{
                throw new AppException(status,connection.getResponseMessage(), AppException.ErrorType.SERVER);
            }
        } catch (Exception exception) {
            throw new AppException(exception.getMessage());
        }
    }

    @Override
    public void onProgressUpdated(int curLen, int totalLen) {
    }

    protected abstract T bindData(String result);

    public ICallBack setCachePath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }

    public void checkIfCancelled() throws AppException {
        if(isCancelled){
            Log.e("download","AbstractCallBack checkIfCancelled invoked!");
            throw new AppException("the request is cancelled",AppException.ErrorType.CANCEL);
        }
    }

    @Override
    public T postRequest(T t) {
        return t;
    }

    @Override
    public T preRequest() {
        return null;
    }
}
