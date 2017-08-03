package com.alan.http;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class JsonCallBack<T> extends AbstractCallBack<T> {
    @Override
    protected T bindData(String result) {
            Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Gson gson = new Gson();
            return gson.fromJson(result,type);
    }
}
