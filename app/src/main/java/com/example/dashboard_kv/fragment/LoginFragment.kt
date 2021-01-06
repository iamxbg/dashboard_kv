package com.example.dashboard_kv.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.dashboard_kv.MainActivity
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.LoginApi
import com.example.dashboard_kv.api.WebUtil
import com.example.dashboard_kv.util.SoftKeyboardUtil
import retrofit2.Response
import java.util.Date

class LoginFragment:Fragment() {

    lateinit var et_username:EditText;
    lateinit var et_password:EditText;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater?.inflate(R.layout.fragment_login,null);

        et_username = rootView.findViewById(R.id.et_username)
        et_password = rootView.findViewById(R.id.et_password)

        //??? 为何是哦那个requiredContext替换context!!
        SoftKeyboardUtil.hideSoftKeyboard(requireContext(), listOf(et_username,et_password))



        return rootView;
    }


    override fun onStart() {
        super.onStart()

        //获取activity中绑定的webService，进行info和captcha请求

        val mainActivity = activity as MainActivity

         val loginMsg :String= mainActivity.webService.loginInfo()

        Log.w("TEST",loginMsg)

        /*
        Thread({

            val loginApi = WebUtil.getService(LoginApi::class.java)

            val resp  = loginApi.info(Date().time).execute().message()

            val captch = loginApi.captchaImage().execute().message();

            val respStr =  resp.toString()
            Log.w("SOMETAG",respStr)

            Log.w("ANOTHER_TAG",captch.toString())

        }).start()

         */


    }
}