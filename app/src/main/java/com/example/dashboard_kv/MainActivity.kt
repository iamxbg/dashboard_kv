package com.example.dashboard_kv

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.example.dashboard_kv.api.WebService

/**
 * Loads [MainFragment].
 */
class MainActivity : FragmentActivity() {


    lateinit var  webService: WebService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //val inputMethodManager =this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //inputMethodManager.hideSoftInputFromWindow()

    }


    /**
     * 启动时绑定web服务
     */
    override fun onStart() {
        super.onStart()

        bindService(
            Intent(this,WebService::class.java),

        )
        //webService =
    }


    private val connection = object:ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            TODO("Not yet implemented")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }
    }

}