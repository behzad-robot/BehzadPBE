package com.behzad.behzadpbe.managers;

import com.behzad.behzadpbe.app.MyApplication;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2/21/2018.
 */

public class ServiceManager {
    private MyApplication app;

    public ServiceManager(MyApplication app){
        this.app = app;
    }
    public Retrofit getRetroFit(){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        return retrofit;
    }
    public UserService userApi(){
        return getRetroFit().create(UserService.class);
    }
}
