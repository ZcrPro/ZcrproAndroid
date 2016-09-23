package com.zhengzheng.zcrpro.login.sms;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.axinpay.sharedprefercence.Util;
import com.example.model.base.ApiFactory;
import com.example.model.base.RetrofitFactory;
import com.example.model.base.secure.BaseRequest;
import com.example.model.base.service.BaseUrl;
import com.example.model.base.service.CommonService;
import com.example.model.customer.CustomerMessageFactory;
import com.google.gson.Gson;
import com.zhengzheng.zcrpro.BuildConfig;
import com.zhengzheng.zcrpro.utils.schedulers.BaseSchedulerProvider;
import com.zhihuianxin.secure.Secure;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by zcrpro on 16/9/22.
 */

public class SmsPresenter implements SmsContract.Presenter {

    @NonNull
    private Context context;

    @NonNull
    private final SmsContract.View mSmsView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    private Secure mSecure;

    public SmsPresenter(Context context, @NonNull SmsContract.View mSmsView, @NonNull BaseSchedulerProvider mSchedulerProvider) {
        this.context = context;
        this.mSmsView = mSmsView;
        this.mSchedulerProvider = mSchedulerProvider;
        context = checkNotNull(context);
        mSmsView = checkNotNull(mSmsView);
        mSchedulerProvider = checkNotNull(mSchedulerProvider);
        mSmsView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
        mSecure = new Secure();
        mSecure.setIsDebug(BuildConfig.DEBUG);
        mSecure.initialize(context.getApplicationContext());
    }

    @Override
    public void loadSms(String mobile) {

        mSubscriptions.clear();

        RetrofitFactory.setBaseUrl(BaseUrl.Common.SMSCODE);
        CommonService commonService = ApiFactory.getFactory().create(CommonService.class);

        BaseRequest req = new CustomerMessageFactory().createBaseRequest(context);

        HashMap<String, Object> request = new HashMap<String, Object>();
        request.put("req", req);
        request.put("mobile", mobile);

        HashMap<String, String> params = new HashMap<String, String>();
        String json, sign = null;
        json = new Gson().toJson(request);
        params.put("json", json);
        try {
            sign = Util.byte2HexStr(mSecure.signMessage(json.getBytes("UTF-8")));
            params.put("sign", sign);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Subscription subscription = commonService.getSmsCode(body, sign)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                        mSmsView.showLoadingUi(false);
                        Log.d("SmsPresenter", "完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSmsView.showError(e.toString());
                        Log.d("SmsPresenter", "错误:" + e.toString());
                    }

                    @Override
                    public void onNext(Object s) {
                        mSmsView.success(s.toString());
                        Log.d("SmsPresenter", "成功");
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
        context = null;
    }

}
