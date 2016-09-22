package com.zhengzheng.zcrpro.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhengzheng.zcrpro.R;
import com.zhengzheng.zcrpro.login.sms.SmsFragment;
import com.zhengzheng.zcrpro.login.sms.SmsPresenter;
import com.zhengzheng.zcrpro.utils.ActivityUtils;
import com.zhengzheng.zcrpro.utils.schedulers.SchedulerProvider;

/**
 * Created by zcrpro on 16/9/22.
 */

public class SignInUpActivity extends AppCompatActivity {

    private SmsPresenter smsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);

        SmsFragment smsFragment =
                (SmsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (smsFragment == null) {
            // Create the fragment
            smsFragment = SmsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), smsFragment, R.id.contentFrame);
        }

        // Create the presenter
        smsPresenter = new SmsPresenter(smsFragment, SchedulerProvider.getInstance());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        smsPresenter.unsubscribe();
    }
}
