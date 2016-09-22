package com.axinpay.sharedprefercence;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;


/**
 * Created by John on 2015/11/11.
 */
abstract public class OnPersistDataChangedListener implements AbsPersistData.OnDataChangedListener {
    private static final String TAG = "OnPersistDataChangedListener";
    private Fragment fragment;
    private Activity activity;

    public OnPersistDataChangedListener(Fragment fragment) {
        this.fragment = fragment;
    }

    public OnPersistDataChangedListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onDataChanged(String fieldName, Object oldValue, Object newValue) {
        Log.v(TAG, String.format("Got data changed"));
        if(fragment != null && fragment.getActivity() != null){
            Log.v(TAG, String.format("fragment and parent activity is alive"));
            notifyDataChangedOnUIThread(fragment.getActivity(), fieldName, oldValue, newValue);
        }
        else if(activity != null){
            notifyDataChangedOnUIThread(activity, fieldName, oldValue, newValue);
        }
    }

    private void notifyDataChangedOnUIThread(final Activity activity, final String fieldName, final Object oldValue, final Object newValue){
        Log.v(TAG, String.format("notify by activity. isFinishing: " + activity.isFinishing()));
        if(activity != null && !activity.isFinishing()){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onDataChanged(activity, fieldName, oldValue, newValue);
                }
            });
        }
    }

    abstract protected void onDataChanged(Activity activity, String fieldName, Object oldValue, Object newValue);
}
