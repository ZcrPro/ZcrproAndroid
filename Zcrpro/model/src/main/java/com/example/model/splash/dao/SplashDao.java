package com.example.model.splash.dao;

import com.example.model.splash.Splash;

import rx.Observable;

/**
 * Created by zcrpro on 16/9/19.
 */
public interface SplashDao {
    /**
     * 查询数据库中的splash
     * @return
     */
    Observable<Splash> getSplash();

    /**
     * 存入方式抽象的存入方法
     * @param splash
     */
    void insertSplash(Splash splash);
}
