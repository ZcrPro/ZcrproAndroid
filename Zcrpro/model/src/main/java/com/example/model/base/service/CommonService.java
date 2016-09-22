package com.example.model.base.service;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zcrpro on 16/9/22.
 */

public interface CommonService {

    /**
     * 获取手机验证码
     * @return
     */
    @POST("send_sms_securitycode/")
    Observable<Object> getSmsCode(@Body RequestBody requestBody,RequestBody requestBody2);
}
