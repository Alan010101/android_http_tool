package com.alan.http;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by wangbiao on 2017/8/2.
 */

public class BaseActivity extends AppCompatActivity implements OnGlobalExceptionListener {

    @Override
    public boolean handleException(AppException AppException) {
        //// TODO: 2017/8/2  handle 了返回true,未处理返回false
        return true;
    }
}
