package com.example.model.base.service;

import com.example.model.splash.Splash;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zcrpro on 16/9/19.
 */
public interface SplashService {
    @GET("start-image/1080*1776")
    Observable<Splash> getsplash();
}
