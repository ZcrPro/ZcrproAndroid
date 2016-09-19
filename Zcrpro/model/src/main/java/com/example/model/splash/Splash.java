package com.example.model.splash;

import android.os.Parcelable;
import android.support.annotation.NonNull;

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

    public static final Factory<Splash> FACTORY = new Factory<>(new splashModel.Creator<Splash>() {
        @Override
        public Splash create(long _id, @NonNull String text, @NonNull String img) {
            return new AutoValue_Splash(_id, text, img);
        }
    });

}
