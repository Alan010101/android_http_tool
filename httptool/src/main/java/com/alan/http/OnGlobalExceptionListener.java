package com.alan.http;

/**
 * Created by wangbiao on 2017/8/2.
 */

public interface OnGlobalExceptionListener {
    // TODO: 2017/8/2  handle 了返回true,未处理返回false
    boolean handleException(AppException exception);
}
