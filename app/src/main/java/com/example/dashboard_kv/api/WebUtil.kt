package com.example.dashboard_kv.api

import android.util.Log
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
class WebUtil private constructor() {


  //  lateinit var  navController:NavController





    companion object {

        @JvmStatic
         //var  token:String?=null;
        var token:String = "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjJmOWMxM2Q4LWVmY2YtNDVlMy1hYmRjLTUyZTg0ZjI2NWI0YiJ9.410q8caC_trnKmXyHjeCAcZ4gV-kd51NXsCVrZx8A6qybJV9plYOT-Azfq-J7SePqDy-y_L5SZwhlfwHz2Z3OA"
//        lateinit var webUtil: WebUtil

//        fun init(navController:NavController){
//
//            if(webUtil==null)
//                synchronized(WebUtil::class){
//                    if(webUtil==null){
//                        webUtil =  WebUtil().apply{
//                            this.navController = navController
//                        }
//                    }
//
//                }
//
//        }

        /**
         * 获取web服务
         */

        fun <T:WebApi> getService(api:Class<T> ):T{
            //return retrofit.create(api::class.java)
            return retrofit.create(api)
        }

        object tokenIntercetpr : Interceptor {
//
//            @JvmStatic
//            val token:String ="eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjMxMWJjYWNmLTk4MDMtNGQyZi05MDIzLTRlOTJkYWNmZjk4ZSJ9.GmldJu2Nk7lHTthEl7o5BzJzj5fVND_uvDMht5Xx6ADMFPOkRx5DAoRoSg6wmsAHuMrTbRsRCPPjAYjD-lp_0A"

            override fun intercept(chain: Interceptor.Chain): Response {
                val req = chain.request()

                //如何返回自身?
                val reqBuilder = req.newBuilder().apply {
                    this.addHeader("Authorization",token?:"")
                    this.addHeader("accept","application/json")
                }

                val newReq = reqBuilder.build();

                try {
                    val resp  = chain.proceed(newReq)

                    if(resp.code == 200){

//                      val str = String(resp.body?.bytes()!!)
//                      val respMap =  ObjectMapper().readValue(str,Map::class.java)
//                        when(respMap.get("code") as Int){
//                            401 -> Log.e("token-intercepter","XXX 认证失败，无法访问系统资源")
//                        }


                        return resp;

                    }else {

                        when(resp.code){
                            401 -> Log.e("token-intercepter","XXX 认证失败，无法访问系统资源")
                        }


                        Log.e("httpStatus:",resp.code.toString())
                        println("erroCode:"+resp.code)
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


        fun <T:Any> preInteceptor(response: retrofit2.Response<ResponseEntity<T>>):retrofit2.Response<ResponseEntity<T>>? {

//            when(response.body()?.code){
//                401 -> {
//                    Log.e("TOKEN过期:",response.body()?.msg)
//                    null;
//                }
//
//
//            }

            if(response.body()?.code == 401){
                return null;
            }

            return response;

        }

    }






}