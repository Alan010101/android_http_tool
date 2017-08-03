package com.alan.http;

public abstract class StringCallBack extends AbstractCallBack<String> {

    @Override
    protected String bindData(String result) {
            return result;
    }
}
