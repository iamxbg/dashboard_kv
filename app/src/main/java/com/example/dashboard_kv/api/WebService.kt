package com.example.dashboard_kv.api

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

/**
 *  web请求服务类
 *
 */
class WebService :Service() {


    /**
     * 显示请求服务
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 绑定请求服务
     */
    override fun bindService(service: Intent?, conn: ServiceConnection, flags: Int): Boolean {
        return super.bindService(service, conn, flags)
    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }



}