package com.example.model.base;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by zcrpro on 16/9/22.
 */

public class HttpLoggingInterceptor implements Interceptor {
    private static final String TAG = "HttpLogging";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request(); // 获取请求
        long t1 = System.nanoTime();

        Buffer buffer = new Buffer();
        request.body().writeTo(buffer); //获取请求体

        Log.e(TAG, String.format("Sending request %s on %s%n%sRequest Params: %s",
                request.url(), chain.connection(), request.headers(), buffer.clone().readUtf8()));
        buffer.close();

        Response response = chain.proceed(request); //执行请求
        long t2 = System.nanoTime();

        BufferedSource source = response.body().source(); //获取请求结果
        source.request(Long.MAX_VALUE);
        buffer = source.buffer().clone(); //克隆返回结果, 因为buffer只能使用一次
        Log.e(TAG, String.format("Received response for %s in %.1fms%n%sResponse Json: %s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers(),
                buffer.readUtf8()));

        return response;
    }
}