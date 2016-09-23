package com.example.model.base.service;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by zcrpro on 16/9/22.
 */

public interface CommonService {

    /**
     * 获取手机验证码
     * @return
     */
    @Multipart
    @POST("send_sms_securitycode/")
    Observable<Object> getSmsCode(@Part("json") RequestBody requestBody,@Part("sign") String sign);
}
