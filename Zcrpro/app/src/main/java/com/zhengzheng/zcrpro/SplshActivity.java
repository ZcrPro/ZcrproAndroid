package com.zhengzheng.zcrpro;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.model.ApiFactory;
import com.example.model.RetrofitFactory;
import com.example.model.service.BaseUrl;
import com.example.model.service.SplashService;
import com.example.model.splash.Splash;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zcrpro on 16/9/19.
 */
public class SplshActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        RetrofitFactory.setBaseUrl(BaseUrl.Splash.splash);
        SplashService splashService = ApiFactory.getFactory().create(SplashService.class);
        Observable<Splash> observable = splashService.getsplash();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Splash>() {
                    @Override
                    public void call(Splash o) {
                        if (BuildConfig.DEBUG) Log.d("test", o.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (BuildConfig.DEBUG) Log.d("test", "throwable:" + throwable);
                    }
                });
    }
}
