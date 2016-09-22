package com.zhengzheng.zcrpro.login.sms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tools.BuildConfig;
import com.zhengzheng.zcrpro.R;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by zcrpro on 16/9/22.
 */

public class SmsFragment extends Fragment implements SmsContract.View {

    private TextView tv_sms_code;
    private EditText ed_mobile;
    private Button btn_send_message;

    private SmsContract.Presenter mPresenter;

    public SmsFragment() {
        // Requires empty public constructor
    }

    public static SmsFragment newInstance() {
        return new SmsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.sms_fragment, container, false);

        tv_sms_code = (TextView) root.findViewById(R.id.tv_sms_code);
        ed_mobile = (EditText) root.findViewById(R.id.ed_mobile);
        btn_send_message = (Button) root.findViewById(R.id.btn_send_message);

        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.subscribe();
            }
        });

        return root;
    }


    @Override
    public void showLoadingUi(boolean active) {
            if (active){
                if (BuildConfig.DEBUG) Log.d("SmsFragment", "正在发送短信");
            }else {
                if (BuildConfig.DEBUG) Log.d("SmsFragment", "发送短信请求完成");
            }
    }

    @Override
    public void showError(String error_info) {
        if (BuildConfig.DEBUG) Log.d("SmsFragment", "错误:"+error_info);
    }

    @Override
    public void success(String sms_code) {
            tv_sms_code.setText(sms_code);
    }

    @Override
    public void setPresenter(SmsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
