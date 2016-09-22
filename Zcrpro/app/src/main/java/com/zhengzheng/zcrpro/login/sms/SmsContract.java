package com.zhengzheng.zcrpro.login.sms;

import com.zhengzheng.zcrpro.BasePresenter;
import com.zhengzheng.zcrpro.BaseView;

/**
 * Created by zcrpro on 16/9/22.
 */

public interface SmsContract {

    interface View extends BaseView<Presenter> {

        void showLoadingUi(boolean active);

        void showError(String error_info);

        void success(String sms_code);

    }

    interface Presenter extends BasePresenter {
        void loadSms();
    }

}
