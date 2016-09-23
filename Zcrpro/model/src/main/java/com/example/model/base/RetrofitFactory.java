package com.example.model.base;

import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zcrpro on 16/9/19.
 */
public class RetrofitFactory {
    private static Retrofit retrofit;
    private static String baseUrl;

    public static void setBaseUrl(String url) {
        baseUrl = url;
    }

    /**
     * 获取配置好的retrofit对象来生产Manager对象
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            if (baseUrl == null || baseUrl.length() <= 0)
                throw new IllegalStateException("请在调用getFactory之前先调用setBaseUrl");

            Retrofit.Builder builder = new Retrofit.Builder();

            builder.baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 参考RxJava
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                            .create())); // 参考与GSON的结合

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build();
                builder.client(client);

            retrofit = builder.build();
        }

        return retrofit;
    }
}
