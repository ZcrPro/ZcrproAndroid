package com.example.model.service;

import com.example.model.daily.Daily;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zcrpro on 16/9/20.
 */
public interface DailyService {
    @GET("11")
    Observable<Daily> getDaily();
}
