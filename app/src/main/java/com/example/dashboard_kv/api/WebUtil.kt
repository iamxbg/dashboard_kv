package com.example.dashboard_kv.api

import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.http.promisesBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Headers
import java.net.SocketTimeoutException

/**
 *  通用的Web访问实现工具类
 */

var current_project_id:Long = 0L;

var TOKEN:String? = "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjdhMGU2N2FkLWRmNTgtNDlkOC05NTU3LTUwYmEwNzc0MjE5ZCJ9.NefG9Q1qfRWijJDVoy68kMsTfJyxZjZqLVKxYuYV1HTH6_gzW53FKldcPlDBwhUJNp6bVxgQhyWsxfCOawPkXQ"

class WebUtil private constructor() {

    companion object {


        /**
         * 获取web服务
         */

        fun <T:WebApi> getService(api:Class<T> ):T{
            return retrofit.create(api)
        }

        object tokenIntercetpr : Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {
                val req = chain.request()

                //如何返回自身?
                val reqBuilder = req.newBuilder().apply {
                    this.addHeader("Authorization", TOKEN?:"")
                    this.addHeader("accept","application/json")
                }

                val newReq = reqBuilder.build();

                try {
                    val resp  = chain.proceed(newReq)

                    if(resp.code == 200){

                        return resp;

                    }
//                    else {
//
//                        when(resp.code){
//                            401 -> {
//                                Log.e("token-intercepter","X:XX 认证失败，无法访问系统资源")
//                                TODO("处理401错误")
//                            }
//                            403 -> {
//
//                                Log.e("请求被禁用,","URL:"+resp.request.url.toString())
//
//                            }
//                            else ->{
//                                TODO("根据返回的错误码，返回对应的错误信息!")
//                            }
//                        }
//
//                        return  resp;
//
//                    }
                    return resp;
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


        fun <T:Any> preInteceptor(response: retrofit2.Response<ResponseEntity<T>>):retrofit2.Response<ResponseEntity<T>>? {

            when(response.body()?.code) {
                401 -> return null;
                403 -> return null;
            }

            return response;

        }

    }






}