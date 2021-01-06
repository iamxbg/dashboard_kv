package com.example.dashboard_kv.api

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import com.google.gson.Gson
import retrofit2.Call
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
  // private var executor:ExecutorService = ThreadPoolExecutor(3,10,10,TimeUnit.SECONDS,workQueue)

    private val  executor:ExecutorService = Executors.newFixedThreadPool(10);




    /**
     * 显示请求服务
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         super.onStartCommand(intent, flags, startId)

         loginApi = WebUtil.getService(LoginApi::class.java)

        //这个参数是否正确???
        return Service.START_STICKY

    }

    /**
     * 绑定请求服务
     */
//    override fun bindService(service: Intent?, conn: ServiceConnection, flags: Int): Boolean {
//        return super.bindService(service, conn, flags)
//    }


    fun loginInfo():String {

       val future =  executor.submit(Callable{
            this.loginApi.info(Date().time).execute().message();
        })

        while (!future.isDone){
            Thread.sleep(100)
            break;
        }

        return future.get()
    }


    inner class WebBinder: Binder(){
        fun getLoginService() :WebService {
            return this@WebService;
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder;
    }



}