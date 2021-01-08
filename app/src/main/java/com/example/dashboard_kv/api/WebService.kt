package com.example.dashboard_kv.api

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.http.Query
import java.util.Date
import java.util.concurrent.*

/**
 *  web请求服务类
 *
 */
class WebService :Service() {

    lateinit var  loginApi: LoginApi ;

    private val binder:WebBinder = WebBinder()

    private val workQueue = LinkedBlockingDeque<Callable<String>>(20)
    private val  executor:ExecutorService = Executors.newFixedThreadPool(10);


    companion object  {

        val serviceMap = HashMap<String,WebApi>()


    }

    /**
     * 显示请求服务
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         super.onStartCommand(intent, flags, startId)

        //这个参数是否正确???
        return Service.START_STICKY

    }


    fun <T:WebApi> getApi(clazz: Class<T>): T {
        if(!serviceMap.containsKey(clazz.canonicalName)){
            val service:T  = WebUtil.getService(clazz);
            serviceMap.put(clazz.canonicalName,service)
        }
        return serviceMap.get(clazz.canonicalName) as T
    }


    inner class WebBinder: Binder(){
        fun getService() :WebService {
            return this@WebService;
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        loginApi = WebUtil.getService(LoginApi::class.java)
        return binder;
    }



}