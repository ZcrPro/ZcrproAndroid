package com.zhengzheng.zcrpro.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.model.ApiFactory;
import com.example.model.RetrofitFactory;
import com.example.model.daily.Daily;
import com.example.model.daily.api.DailyApi;
import com.example.model.daily.api.DailyApiImpl;
import com.example.model.daily.dao.DailyDaoImpl;
import com.example.model.database.DataBaseManager;
import com.example.model.service.BaseUrl;
import com.example.model.service.DailyService;
import com.example.model.utils.RxJavaUtils;
import com.zhengzheng.zcrpro.BuildConfig;
import com.zhengzheng.zcrpro.R;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zcrpro on 16/9/20.
 */
public class DailyActivity extends Activity {

    private DailyApi dailyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_activity);

        RetrofitFactory.setBaseUrl(BaseUrl.Daily.DAILY);
        DailyService dailyService = ApiFactory.getFactory().create(DailyService.class);
        dailyApi = new DailyApiImpl(dailyService,new DailyDaoImpl(DataBaseManager.getBriteDatabase(this)));
        Observable<Daily> ob = dailyApi.getDaily();
        ob = RxJavaUtils.schedulerOnAndroid(ob);
        ob.subscribe(new Action1<Daily>() {
            @Override
            public void call(Daily daily) {
                if (BuildConfig.DEBUG) Log.d("test", "--------获取到日报信息:"+daily.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (BuildConfig.DEBUG) Log.d("test", "错误------throwable:" + throwable);
            }
        });
    }
}
