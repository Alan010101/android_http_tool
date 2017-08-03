package com.alan.http;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by wangbiao on 2017/7/28.
 */

public class HttpUrlConnectionUtil {


    public static HttpURLConnection execute(Request request) throws AppException {
        switch (request.method) {
            case GET:
            case DELETE:
                return get(request);
            case POST:
            case PUT:
                return post(request);
        }
        return null;
    }


    private static HttpURLConnection get(Request request) throws AppException {
        try {
            request.checkIfCancelled();
            HttpURLConnection connection = (HttpURLConnection) new URL(request.url).openConnection();
            connection.setRequestMethod(request.method.name());
            connection.setConnectTimeout(45 * 1000);
            connection.setReadTimeout(45 * 1000);
            addHeader(connection, request.headers);
            request.checkIfCancelled();
            return connection;
        } catch (Exception exception) {
            throw new AppException();
        }
    }

    private static HttpURLConnection post(Request request) throws AppException {
        try {
            request.checkIfCancelled();
            HttpURLConnection connection = (HttpURLConnection) new URL(request.url).openConnection();
            connection.setRequestMethod(request.method.name());
            connection.setConnectTimeout(30 * 1000);
            connection.setReadTimeout(30 * 1000);
            connection.setDoOutput(true);
            addHeader(connection, request.headers);
            request.checkIfCancelled();
            OutputStream os = connection.getOutputStream();
            os.write(request.content.getBytes());
            request.checkIfCancelled();
            return connection;
        } catch (Exception exception) {
            throw new AppException();
        }
    }

    private static void addHeader(HttpURLConnection connection, Map<String, String> headers) {
        if (headers == null || headers.size() == 0) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }
}