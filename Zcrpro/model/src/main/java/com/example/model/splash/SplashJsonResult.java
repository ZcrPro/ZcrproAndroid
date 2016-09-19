package com.example.model.splash;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by zcrpro on 16/9/19.
 */
@AutoValue
public abstract class SplashJsonResult implements Parcelable {

    public abstract boolean error();

    public abstract Splash splash();

    //auto-value-gson扩展
    public static TypeAdapter<SplashJsonResult> typeAdapter(Gson gson) {
        return new AutoValue_SplashJsonResult.GsonTypeAdapter(gson);
    }
}
