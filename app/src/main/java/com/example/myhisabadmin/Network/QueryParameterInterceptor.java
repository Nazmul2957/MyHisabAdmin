package com.example.myhisabadmin.Network;

import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;

import com.example.myhisabadmin.Utils.MySharedPreference;
import com.example.myhisabadmin.Utils.constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class QueryParameterInterceptor implements Interceptor {

    Context context;

    QueryParameterInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {

        String Token = MySharedPreference.getInstance(context).getString(constants.TOKEN, "not found");
        Log.e("tessrt", Token);
        HttpUrl url = chain.request().url().newBuilder()
                .addQueryParameter("token", Token)
                .build();

        Request request = chain.request().newBuilder()
                .url(url)
                .addHeader("Authorization","Bearer "+Token)
                .build();

        return chain.proceed(request);
    }
}
