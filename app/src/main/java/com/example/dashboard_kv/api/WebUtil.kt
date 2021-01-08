package com.example.dashboard_kv.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.http.promisesBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Headers
import java.net.SocketTimeoutException

/**
 *  通用的Web访问实现工具类
 */
class WebUtil private constructor() {

    companion object {

        object tokenIntercetpr : Interceptor {

            @JvmStatic
            val token:String ="eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjZjMzU0MmQwLTdjMmMtNDk5Ny05YTk3LTZjMGE3OTM2ZWVhMSJ9.M9kIHwQdnybwvEh4j2whgmaDWZwxaH9ZN3nIyreu8o7guetHFHrLebyZRLlFIeQjvdUhlRiXSL1V9S7Y5Intpg"
            //val token:String ="";

            override fun intercept(chain: Interceptor.Chain): Response {
                val req = chain.request()

                val newReq = req.newBuilder().addHeader("Authorization",token)
                        .addHeader("accept","application/json").build()

                try {
                    val resp  = chain.proceed(newReq)

                    if(resp.code == 200){

                        return resp;

                    }else {
                        TODO("根据返回的错误码，返回对应的错误信息!")

                    }
                }catch (e: SocketTimeoutException){
                    TODO("提示超时信息")
                }


            }

        }


        /**
         * 登录过滤器
         */
        @JvmStatic
        val loginInterceptor:HttpLoggingInterceptor  = HttpLoggingInterceptor().apply{
            this.level = HttpLoggingInterceptor.Level.BODY;
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(loginInterceptor)
                .addNetworkInterceptor(tokenIntercetpr)
                .build();

        /**
         * 访问应用者
         */
        @JvmStatic
        val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.11")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        /**
         * 获取web服务
         */
        @JvmStatic
        fun <T:WebApi> getService(api:Class<T> ):T{
            //return retrofit.create(api::class.java)
            return retrofit.create(api)
        }

    }



}