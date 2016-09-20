package com.example.model.daily.api;

import com.example.model.daily.Daily;

import rx.Observable;

/**
 * Created by zcrpro on 16/9/20.
 */
public interface DailyApi {
    Observable<Daily> getDaily();
}
