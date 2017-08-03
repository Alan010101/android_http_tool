package com.alan.http;

public abstract class FileCallBack extends AbstractCallBack<String> {

    @Override
    protected String bindData(String result) {
            return result;
    }
}
