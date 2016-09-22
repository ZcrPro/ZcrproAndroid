package com.example.model.app;

import android.app.Application;

import com.example.model.customer.AXFUser;

import java.lang.ref.WeakReference;

/**
 * Created by zcrpro on 16/9/22.
 */

public class BaseApp extends Application {

    public static WeakReference<BaseApp> sInstance;

    public static BaseApp app() {
        return sInstance.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = new WeakReference<BaseApp>(this);
        AXFUser.initialize(this);
        AXFUser.getInstance().load();
    }
}
