package com.example.dashboard_kv.api

import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.example.dashboard_kv.fragment.LoginFragment
import com.example.dashboard_kv.fragment.window.*
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

var UNCATCH_ERROR_MSG ="验证码加载,未捕捉异常，请联系developer!"

var API_URL:String ="192.168.1.11"

/**
 * 加密公钥
 */
val PUBLIC_KEY:String ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCU7GKkferYtm3Z2JiiYUVWb52KHoanPD8K/f4jexS0Asi52ONmiKdeT2vyRc7s78wCcvHfx02SbrGUtKDIfRzPqmp6uF7Nx8+24FkLGI8iIJbm3HTsSBR5j3JbIU4FbYg0C7b+8RMEJGGRtGE0X7YLnlknzR+euEP5tjVDinLxHQIDAQAB"


/**
 * 测试时在此处填写
 */
var TOKEN:String? = "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjNlOWM2MmE2LTE1N2UtNDBiZC04YjcxLTdjMWEzZjM5M2Y0OSJ9.RXYmy6tZ3CkVATLAnzoWTRVFCoXO-wWDhTenxmXH6KrO9_bOZeyYD_96YJ5lOF2FjbvUi3G7eLzYXW53CGinmQ"

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
                    this.addHeader("Keep-Alive","300")
                }

                val newReq = reqBuilder.build();

                try {
                    return chain.proceed(newReq)


                }catch (e: SocketTimeoutException){
                    e.printStackTrace()

                    throw e

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
                .retryOnConnectionFailure(false)
                .addInterceptor(loginInterceptor)
                .addNetworkInterceptor(tokenIntercetpr)
                .build();

        /**
         * 访问应用者
         */
        @JvmStatic
        var retrofit = Retrofit.Builder()
                .baseUrl("http://"+API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        fun rebuild(){
            retrofit =Retrofit.Builder()
                .baseUrl("http://"+API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

            CurrentTaskInfoWindow?.taskApi = getService(TaskApi::class.java)
            MakeFilesWindow?.fileApi = getService(FtpFilesApi::class.java)
            NotificationWindow?.notificationApi = getService(NotificationApi::class.java)
            ProjectListWindow?.projectApi = getService(ProjectApi::class.java)
            SupplyStaffWindow?.supplyApi = getService(SuppliesApi::class.java)
            TaskDetailWindow?.taskApi = getService(TaskApi::class.java)
            TaskOrderWindow?.taskApi = getService(TaskApi::class.java)

            LoginFragment?.loginApi = getService(LoginApi::class.java)
        }


        fun <T:Any> preInteceptor(response: retrofit2.Response<ResponseEntity<T>>):retrofit2.Response<ResponseEntity<T>>? {

            when(response.body()?.code) {
                401 -> return null;
                403 -> return null;
            }

            return response;

        }

    }






}