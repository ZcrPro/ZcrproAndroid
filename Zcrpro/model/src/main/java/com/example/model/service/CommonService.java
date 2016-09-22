package com.example.model.service;

import rx.Observable;

/**
 * Created by zcrpro on 16/9/22.
 */

public interface CommonService {
    /**
     * 获取手机验证码
     * @return
     */
    Observable<String> getSmsCode();
}
