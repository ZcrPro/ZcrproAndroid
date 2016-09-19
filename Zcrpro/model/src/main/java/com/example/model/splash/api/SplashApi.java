package com.example.model.splash.api;

import com.example.model.splash.Splash;

import rx.Observable;

/**
 * Created by zcrpro on 16/9/19.
 */
public interface SplashApi {
        Observable<Splash> getSplash();
}
