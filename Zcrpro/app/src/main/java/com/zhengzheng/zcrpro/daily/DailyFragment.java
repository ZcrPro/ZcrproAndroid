package com.zhengzheng.zcrpro.daily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.model.daily.Daily;
import com.zhengzheng.zcrpro.BuildConfig;
import com.zhengzheng.zcrpro.R;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by zcrpro on 16/9/20.
 */
public class DailyFragment extends android.support.v4.app.Fragment implements DailyContract.View {

    private TextView tv_name;
    private TextView tv_author;

    private DailyContract.Presenter mPresenter;

    public DailyFragment() {
        // Requires empty public constructor
    }

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.daily_fragment, container, false);

        tv_name = (TextView) root.findViewById(R.id.name);
        tv_author = (TextView) root.findViewById(R.id.author);

//        mPresenter.loadDaily(true);

        return root;

    }

    @Override
    public void showLoadingUi(boolean active) {
        if (active){
            if (BuildConfig.DEBUG) Log.d("DailyFragment", "显示正在加载view......");
        }else {
            if (BuildConfig.DEBUG) Log.d("DailyFragment", "加载view完成,关闭加载框......");
        }

    }

    @Override
    public void showError(String error_info) {
        if (BuildConfig.DEBUG) Log.d("DailyFragment", "加载view失败"+error_info);
    }

    @Override
    public void showEmpty() {
        if (BuildConfig.DEBUG) Log.d("DailyFragment", "--加载成功--数据为空--");
    }

    @Override
    public void showDaily(Daily daily) {
        if (BuildConfig.DEBUG) Log.d("DailyFragment", "---数据获取成功---daily:" + daily.toString());
           tv_name.setText(daily.name());
           tv_author.setText(daily.description());
    }

    @Override
    public void setPresenter(DailyContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
