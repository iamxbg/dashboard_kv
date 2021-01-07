package com.example.dashboard_kv.fragment


import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.dashboard_kv.R
import java.util.*

class LoginFragment:Fragment() {

    lateinit var et_username:EditText;
    lateinit var et_password:EditText;

    /**
     * captcha button
     */
    lateinit var ib_captcha:ImageButton

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater?.inflate(R.layout.fragment_login,null);

        et_username = rootView.findViewById(R.id.et_username)
        et_password = rootView.findViewById(R.id.et_password)
        ib_captcha = rootView.findViewById(R.id.ib_captcha)


        val bitmapDrawable = testLoadCaptcha(base64Str);

        ib_captcha.setImageBitmap(bitmapDrawable.bitmap)

        //??? 为何是哦那个requiredContext替换context!!
        //SoftKeyboardUtil.hideSoftKeyboard(requireContext(), listOf(et_username,et_password))



        return rootView;
    }

    val base64Str :String ="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";

    @RequiresApi(Build.VERSION_CODES.O)
    fun testLoadCaptcha(base64Str:String):BitmapDrawable{

        val byteArray:ByteArray = android.util.Base64.decode(base64Str,Base64.DEFAULT)

        val bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)

        return BitmapDrawable(bitmap)
    }


    override fun onStart() {
        super.onStart()

        //获取activity中绑定的webService，进行info和captcha请求

        //throw exception!
//        val mainActivity = activity as MainActivity
//
//         val loginMsg = mainActivity.webService.loginInfo()
//
//       for(k in loginMsg.keys){
//           Log.w("key:",k)
//       }



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