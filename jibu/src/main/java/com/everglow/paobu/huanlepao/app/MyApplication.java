package com.everglow.paobu.huanlepao.app;

import android.app.Application;


/**
 * Created by yuandl on 2016-10-18.
 */

public class MyApplication extends Application {

    public static  MyApplication APP;
    public static MyApplication getApplication() {
        return APP;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
    }
}
