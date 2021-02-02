package com.example.dashboard_kv

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.dashboard_kv.api.API_URL
import com.example.dashboard_kv.api.WebService
import com.example.dashboard_kv.api.WebUtil


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


        //判断是否存在配置的URL
        val sharedPreferences = this?.getPreferences(Context.MODE_PRIVATE)
        if(sharedPreferences!=null){
            val apiBaseUri = sharedPreferences?.getString(resources.getString(R.string.api_base_url),null)
            if(apiBaseUri!=null){
                API_URL = apiBaseUri;
                if(WebUtil.retrofit!=null){
                    WebUtil.rebuild()
                }
            }else{

            }

        }
//        with(sharedPreferences.edit()){
//            val apiBaseUrl = getString(R.string.api_base_url)
//            if(apiBaseUrl == null || apiBaseUrl.equals("")){
//                Toast.makeText(applicationContext,"NO_API_BASE_URL",Toast.LENGTH_LONG).show()
//            }
//        }

    }

    override fun onStop() {
        super.onStop()
        webService.unbindService(connection)
    }

}