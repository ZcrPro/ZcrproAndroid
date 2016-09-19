package com.example.model.splash;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by zcrpro on 16/9/19.
 */
@AutoValue
public abstract class Splash implements splashModel,Parcelable {

    public static TypeAdapter<Splash> typeAdapter(Gson gson) {
        return new AutoValue_Splash.GsonTypeAdapter(gson);
    }

}
