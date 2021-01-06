package com.example.dashboard_kv.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class WebUtil private constructor() {


    companion object {

        @JvmStatic
        val loginInterceptor:HttpLoggingInterceptor  = HttpLoggingInterceptor().apply{
            this.level = HttpLoggingInterceptor.Level.BODY;
        }



        @JvmStatic
        val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.11")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor(loginInterceptor).build())
                .build();


        @JvmStatic
        fun <T> getService(api:Class<T> ):T{
            //return retrofit.create(api::class.java)
            return retrofit.create(api)
        }

    }



}