package com.zhengzheng.zcrpro.daily;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhengzheng.zcrpro.R;
import com.zhengzheng.zcrpro.utils.ActivityUtils;
import com.zhengzheng.zcrpro.utils.schedulers.SchedulerProvider;

/**
 * Created by zcrpro on 16/9/20.
 */
public class DailyActivity extends AppCompatActivity {

    private DailyPresenter mDailyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_activity);

        DailyFragment dailyFragment =
                (DailyFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (dailyFragment == null) {
            // Create the fragment
            dailyFragment = DailyFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), dailyFragment, R.id.contentFrame);
        }

        // Create the presenter
        mDailyPresenter = new DailyPresenter(this,dailyFragment, SchedulerProvider.getInstance());

    }
}