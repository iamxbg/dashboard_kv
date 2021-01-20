package com.example.dashboard_kv

import android.app.Activity
import android.app.TaskStackBuilder
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import com.example.dashboard_kv.api.WebService
import com.example.dashboard_kv.api.WebUtil
import com.example.dashboard_kv.widget.UserViewModel
import java.util.ArrayList




class MainActivity : FragmentActivity() {

    @Deprecated("前期设计方案,未使用")
    lateinit var  webService: WebService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val navHostFragment = findViewById<FragmentContainerView>(R.id.nav_host_fragment)
        //WebUtil.init(navHostFragment?.findNavController()!!)
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //val inputMethodManager =this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //inputMethodManager.hideSoftInputFromWindow()


    }

    override fun onResumeFragments() {
        super.onResumeFragments()
    }

    private val connection = object:ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            val binder = service as WebService.WebBinder
                webService = binder.getService();
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            println("unbound")
        }
    }

    /**
     * 启动时绑定web服务
     */
    override fun onStart() {
        super.onStart()

        //不能使用this
        Intent(applicationContext,WebService::class.java)
            .also {
                intent ->
               val success  = bindService(intent,connection, Context.BIND_AUTO_CREATE)
                println(success)
            }

    }

    override fun onStop() {
        super.onStop()
        webService.unbindService(connection)
    }

}