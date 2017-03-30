package com.algo.mapclustering.network;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by macbookpro on 3/30/17.
 */

public class RetrofitClient {
    //https://gist.githubusercontent.com/heinhtetaung92/6bf811b2f375b2486c8deebf38fc28fa/raw/d93669a57df381c2f9beb6d60815f693ad9f5ed7/SampleGeoData
    public static String URL = "https://gist.githubusercontent.com";
    private static RetrofitClient mInstance;
    private RetrofitService mService;

    private RetrofitClient() {

        final OkHttpClient httpClient;

            httpClient = new OkHttpClient().newBuilder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build();

        //this code is to work both Realm and Gson
//        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
//            @Override
//            public boolean shouldSkipField(FieldAttributes f) {
//                return false;
//            }
//
//            @Override
//            public boolean shouldSkipClass(Class<?> clazz) {
//                return false;
//            }
//        }).serializeNulls().create();

        try {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            mService = retrofit.create(RetrofitService.class);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }


    }

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public RetrofitService getService() {
        return mService;
    }

}
