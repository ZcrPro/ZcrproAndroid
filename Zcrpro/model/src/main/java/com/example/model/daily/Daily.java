package com.example.model.daily;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by zcrpro on 16/9/20.
 */
@AutoValue
public abstract class Daily implements dailyModel,Parcelable {

    public static TypeAdapter<Daily> typeAdapter(Gson gson) {
        return new AutoValue_Daily.GsonTypeAdapter(gson);
    }

    public static final dailyModel.Factory<Daily> FACTORY = new dailyModel.Factory<>(new dailyModel.Creator<Daily>() {
        @Override
        public Daily create(@NonNull String description, @NonNull String background, @NonNull String color, @NonNull String name, @NonNull String image, @NonNull String image_source) {
            return new AutoValue_Daily(description,background,color,name,image,image_source);
        }
    });

}
