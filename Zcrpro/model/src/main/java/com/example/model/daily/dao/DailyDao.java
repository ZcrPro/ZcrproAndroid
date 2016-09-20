package com.example.model.daily.dao;

import com.example.model.daily.Daily;

import rx.Observable;

/**
 * Created by zcrpro on 16/9/20.
 */
public interface DailyDao {

    Observable<Daily> getDaily();

    void insertDaily(Daily daily);
}
