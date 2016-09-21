package com.zhengzheng.zcrpro.daily;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.model.ApiFactory;
import com.example.model.RetrofitFactory;
import com.example.model.daily.Daily;
import com.example.model.daily.api.DailyApi;
import com.example.model.daily.api.DailyApiImpl;
import com.example.model.daily.dao.DailyDaoImpl;
import com.example.model.database.DataBaseManager;
import com.example.model.service.BaseUrl;
import com.example.model.service.DailyService;
import com.zhengzheng.zcrpro.utils.schedulers.BaseSchedulerProvider;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by zcrpro on 16/9/20.
 */
public class DailyPresenter implements DailyContract.Presenter {

    @NonNull
    private Context context;

    @NonNull
    private final DailyContract.View mDailyView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    private boolean mFirstLoad = true;

    public DailyPresenter(Context context, @NonNull DailyContract.View mDailyView, @NonNull BaseSchedulerProvider mSchedulerProvider) {
        this.context = context;
        this.mDailyView = mDailyView;
        this.mSchedulerProvider = mSchedulerProvider;

        mDailyView = checkNotNull(mDailyView);
        context = checkNotNull(context);
        mSchedulerProvider = checkNotNull(mSchedulerProvider);

        mSubscriptions = new CompositeSubscription();
        mDailyView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadDaily(false);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadDaily(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadDaily(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadDaily(final boolean forceUpdate, final boolean showLoadingUI) {

        if (showLoadingUI) {
            mDailyView.showLoadingUi(true);
        }
        if (!forceUpdate) {
            //从缓存中取
        }

        mSubscriptions.clear();

        RetrofitFactory.setBaseUrl(BaseUrl.Daily.DAILY);
        DailyService dailyService = ApiFactory.getFactory().create(DailyService.class);
        DailyApi dailyApi = new DailyApiImpl(dailyService, new DailyDaoImpl(DataBaseManager.getBriteDatabase(context)));

        Subscription subscription = dailyApi.getDaily()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<Daily>() {
                    @Override
                    public void onCompleted() {
                        mDailyView.showLoadingUi(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDailyView.showError(e.toString());
                    }

                    @Override
                    public void onNext(Daily daily) {
                        mDailyView.showDaily(daily);
                    }

                });

        mSubscriptions.add(subscription);
    }
}