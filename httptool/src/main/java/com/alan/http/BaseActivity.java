package com.alan.http;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by wangbiao on 2017/8/2.
 */

public class BaseActivity extends AppCompatActivity implements OnGlobalExceptionListener {

    @Override
    public boolean handleException(AppException AppException) {
        //// TODO: 2017/8/2  handle 了返回true,未处理返回false
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e("download","onStop invoked!");
        RequestManager.getInstance().cancelAllRequest();
    }

}
