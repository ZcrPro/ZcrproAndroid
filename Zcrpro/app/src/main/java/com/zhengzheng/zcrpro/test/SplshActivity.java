package com.zhengzheng.zcrpro.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.model.base.ApiFactory;
import com.example.model.base.RetrofitFactory;
import com.example.model.base.database.DataBaseManager;
import com.example.model.base.service.BaseUrl;
import com.example.model.base.service.SplashService;
import com.example.model.splash.Splash;
import com.example.model.splash.api.SplashApi;
import com.example.model.splash.api.SplashApiImple;
import com.example.model.splash.dao.SplashDaoImpl;
import com.example.model.base.utils.RxJavaUtils;
import com.zhengzheng.zcrpro.BuildConfig;
import com.zhengzheng.zcrpro.R;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zcrpro on 16/9/19.
 */
public class SplshActivity extends Activity {

    private SplashApi splashApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        RetrofitFactory.setBaseUrl(BaseUrl.Splash.splash);
        SplashService splashService = ApiFactory.getFactory().create(SplashService.class);
        splashApi = new SplashApiImple(splashService,new SplashDaoImpl(DataBaseManager.getBriteDatabase(this)));
        Observable<Splash> ob = splashApi.getSplash();
        ob = RxJavaUtils.schedulerOnAndroid(ob);
        ob.subscribe(new Action1<Splash>() {
            @Override
            public void call(Splash splash) {
                if (BuildConfig.DEBUG) Log.d("test", splash.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (BuildConfig.DEBUG) Log.d("test", "throwable:" + throwable);
            }
        });
    }
}
