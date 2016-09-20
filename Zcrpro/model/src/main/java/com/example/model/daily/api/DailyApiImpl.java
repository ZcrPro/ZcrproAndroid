package com.example.model.daily.api;

import com.example.model.daily.Daily;
import com.example.model.daily.dao.DailyDao;
import com.example.model.service.DailyService;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zcrpro on 16/9/20.
 */
public class DailyApiImpl implements DailyApi {

    private DailyService dailyService;
    private DailyDao dailyDao;

    public DailyApiImpl(DailyService dailyService, DailyDao dailyDao) {
        this.dailyService = dailyService;
        this.dailyDao = dailyDao;
    }

    @Override
    public Observable<Daily> getDaily() {
        return dailyService.getDaily().doOnNext(new Action1<Daily>() {
            @Override
            public void call(Daily daily) {
                dailyDao.insertDaily(daily);
            }
        });
    }
}
