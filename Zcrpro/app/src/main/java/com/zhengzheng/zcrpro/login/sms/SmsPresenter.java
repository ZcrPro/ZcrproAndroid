package com.zhengzheng.zcrpro.login.sms;

import android.support.annotation.NonNull;

import com.example.model.ApiFactory;
import com.example.model.service.CommonService;
import com.zhengzheng.zcrpro.utils.schedulers.BaseSchedulerProvider;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by zcrpro on 16/9/22.
 */

public class SmsPresenter implements SmsContract.Presenter {

    @NonNull
    private final SmsContract.View mSmsView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    public SmsPresenter(@NonNull SmsContract.View mSmsView, @NonNull BaseSchedulerProvider mSchedulerProvider) {
        this.mSmsView = mSmsView;
        this.mSchedulerProvider = mSchedulerProvider;
        mSmsView = checkNotNull(mSmsView);
        mSchedulerProvider = checkNotNull(mSchedulerProvider);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void loadSms() {

        mSubscriptions.clear();

        CommonService commonService = ApiFactory.getFactory().create(CommonService.class);

        Subscription subscription = commonService.getSmsCode()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mSmsView.showLoadingUi(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSmsView.showError(e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        mSmsView.success(s);
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void subscribe() {
        loadSms();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }


}
