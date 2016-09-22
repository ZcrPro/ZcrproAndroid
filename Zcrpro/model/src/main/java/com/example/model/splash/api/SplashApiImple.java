package com.example.model.splash.api;

import com.example.model.base.service.SplashService;
import com.example.model.splash.Splash;
import com.example.model.splash.dao.SplashDao;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zcrpro on 16/9/19.
 */
public class SplashApiImple implements SplashApi {

    private SplashService splashService;
    private SplashDao splashDao;

    public SplashApiImple(SplashService splashService, SplashDao splashDao) {
        this.splashService = splashService;
        this.splashDao = splashDao;
    }

    @Override
    public Observable<Splash> getSplash() {
        return splashService.getsplash().doOnNext(new Action1<Splash>() {
            @Override
            public void call(Splash splash) {
                splashDao.insertSplash(splash);
            }
        });
//        return splashService.getsplash().map(new Func1<SplashJsonResult, Splash>() {
//            @Override
//            public Splash call(SplashJsonResult splashJsonResult) {
//                return splashJsonResult.result_splash();
//            }
//        }).doOnNext(new Action1<Splash>() {
//            @Override
//            public void call(Splash splash) {
//                splashDao.insertSplash(splash);
//            }
//        });
    }
}
