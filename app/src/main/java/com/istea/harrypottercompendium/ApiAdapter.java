package com.istea.harrypottercompendium;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {
    private static Retrofit instance;
    private static Retrofit instanceBis;
    private static final String BASE_URL = "https://hp-api.herokuapp.com/api/";
    private static final String BASE_URL_SPELL = "https://fedeperin-harry-potter-api.herokuapp.com/";

    //En esta clase seteamos un patrón de diseño "singleton" para tener una instancia de Retrofit para comunicarnos con la API
    public static Retrofit getInstance(){
        if (instance == null)
        {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60,TimeUnit.SECONDS).addInterceptor(interceptor).build();

            Gson gson = new GsonBuilder().setLenient().create();

            instance = new Retrofit.Builder().baseUrl(BASE_URL).client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }

        return instance;
    }

    public static Retrofit getInstanceBis(){
        if (instanceBis == null)
        {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60,TimeUnit.SECONDS).addInterceptor(interceptor).build();

            Gson gson = new GsonBuilder().setLenient().create();

            instanceBis = new Retrofit.Builder().baseUrl(BASE_URL_SPELL).client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }

        return instanceBis;
    }


}
