package com.example.dashboard_kv.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Headers

/**
 *  通用的Web访问实现工具类
 */
class WebUtil private constructor() {

    companion object {

        object intercetpr : Interceptor {

            var token:String ="eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6ImE1MjJkMTJhLTg1MTktNDE3MS05MzM4LTE2MjgxMjliYzBjMSJ9.Tml-dmHJTffu8nMD9iqHqnH8evVy7PWaOwdeWb_QyuSh41e7zpEMtytyoYaChKnfHl0IpIORw4lanY1olv1pfg"

            override fun intercept(chain: Interceptor.Chain): Response {
                val req = chain.request()

                req.newBuilder().addHeader("Authorization",token)
                        .addHeader("accept","application/json")


                val resp  = chain.proceed(req)

                if(resp.code == 200){

                    val re  = resp.body as ResponseEntity<Any>

                    if(re.code == 401 ){
                        TODO("应该让界面显示错误信息")
                    }

                }else {

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


        /**
         * 访问应用者
         */
        @JvmStatic
        val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.11")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder()
                .addInterceptor(loginInterceptor).build())
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