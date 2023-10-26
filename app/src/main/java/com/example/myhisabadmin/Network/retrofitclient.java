package com.example.myhisabadmin.Network;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitclient {

    private static Retrofit retrofit = null;

    private static Retrofit retrofitWithDifBase = null;
    private static String BaseUrls="https://myhishab.softzone24.com/";
    private static Retrofit retrofitWithNoIntercepter = null;

    public retrofitclient() {
    }

    public static Retrofit get(Context context) {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().
                    addInterceptor(new QueryParameterInterceptor(context));
            OkHttpClient client = httpClient.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrls)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit noInterceptor() {
        if (retrofitWithNoIntercepter == null) {
            retrofitWithNoIntercepter = new Retrofit.Builder()
                    .baseUrl(BaseUrls)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitWithNoIntercepter;
    }

    public static Retrofit difBaseUrle() {
        if (retrofitWithDifBase == null) {
            retrofitWithDifBase = new Retrofit.Builder()
                    .baseUrl(BaseUrls)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitWithDifBase;
    }
}
