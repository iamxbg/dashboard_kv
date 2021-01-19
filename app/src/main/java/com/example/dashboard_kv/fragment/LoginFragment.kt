package com.example.dashboard_kv.fragment


import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.*
import com.example.dashboard_kv.util.EncryptUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 加密公钥
 */
val PUBLIC_KEY:String ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCU7GKkferYtm3Z2JiiYUVWb52KHoanPD8K/f4jexS0Asi52ONmiKdeT2vyRc7s78wCcvHfx02SbrGUtKDIfRzPqmp6uF7Nx8+24FkLGI8iIJbm3HTsSBR5j3JbIU4FbYg0C7b+8RMEJGGRtGE0X7YLnlknzR+euEP5tjVDinLxHQIDAQAB"


class LoginFragment:Fragment() {

    /**
     * 用户名
     */
    lateinit var et_username:EditText;

    /**
     * 密码
     */
    lateinit var et_password:EditText;

    /**
     * 验证码输入框
     */
    lateinit var et_captchaCode:EditText;

    /**
     * 验证码图片
     */
    lateinit var ib_captcha:ImageButton

    var uuid:String?=null;

    companion object {

        val loginApi = WebUtil.getService(LoginApi::class.java)




        fun testLoadCaptcha(base64Str: String): BitmapDrawable {

            val byteArray: ByteArray = Base64.decode(base64Str, Base64.DEFAULT)

            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            return BitmapDrawable(bitmap)
        }

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        val rootView = inflater?.inflate(R.layout.fragment_login, null);

        /**
         * 用户名
         */
        et_username = rootView.findViewById<EditText>(R.id.et_username)
            .apply{
                showSoftInputOnFocus = false
            }

        /**
         * 用户密码
         */
        et_password = rootView.findViewById<EditText>(R.id.et_password)
            .apply{
                showSoftInputOnFocus = false
            }

        /**
         * 验证码
         */
        ib_captcha = rootView.findViewById<ImageButton>(R.id.imageButton_captcha)
            .apply{
                setOnClickListener { v->
                   loadCaptchaImage()
                }
            }


        /**
         * 验证码填写栏
         */
        et_captchaCode = rootView.findViewById<EditText>(R.id.et_captchaCode)
            .apply{
                showSoftInputOnFocus =false
            }


        /**
         * 登录按钮
         */
        rootView.findViewById<Button>(R.id.btn_login)
            .apply {

              this.setOnClickListener { v->

                  Log.i("登录:", "----------------------->")

                    //用户名
                  val username = et_username.text.toString()
                  if(username.length ==0)
                  {
                      Toast.makeText(requireContext(), "用户名不为空！", Toast.LENGTH_LONG).show()
                      return@setOnClickListener
                  }

                  //密码
                  val passwordText = et_password.text.toString()
                  if(passwordText.length ==0){
                      Toast.makeText(requireContext(), "密码不能为空!", Toast.LENGTH_LONG).show()
                      return@setOnClickListener
                  }

                  //val encdoedPasswd = EncryptUtil.getEcryptedPassword(passwordText)
                  //验证码
                  val code = et_captchaCode.text.toString();
                  if(code.length ==0){
                      Toast.makeText(requireContext(), "请输入验证码!", Toast.LENGTH_LONG).show()
                      return@setOnClickListener
                  }

                  println("code:$code ,password:$passwordText username:$username, uuid:$uuid")


                  val encryptedPasswd = EncryptUtil.getEcryptedPasswordAndroid("111111")

                  val loginReq =  LoginReq(code, encryptedPasswd, "admin", uuid!!)
                  loginApi.login(loginReq)
                      .enqueue(
                              object : Callback<LoginResp> {
                                  override fun onResponse(
                                          call: Call<LoginResp>,
                                          response: Response<LoginResp>
                                  ) {
                                      Log.w("登录结果:", response.message())

                                      when (response.body()?.code) {
                                          200 -> {

                                              TOKEN = response.body()?.token!!

                                              val navHosFragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)
                                              navHosFragment?.findNavController()?.navigate(R.id.action_loginFragment_to_desktopFragment)
                                          }
                                          500 -> {
                                              val errMsg = response.body()?.msg

                                              Toast.makeText(requireContext(), response.body()?.msg, Toast.LENGTH_SHORT).show()

                                              when (errMsg) {
                                                  "验证码已失效", "验证码错误" -> {
                                                      loadCaptchaImage()
                                                  }
                                              }

                                              return;
                                          }
                                          else -> {
                                              TODO("没有登录成功，添加分支逻辑!")
                                          }
                                      }

                                  }

                                  override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                                      Log.e("登录失败!", t.message)
                                      call.execute().apply {
                                          Log.e("错误码:", this.body()?.code.toString())
                                          Log.e("错误信息:", this.body()?.msg)

                                      }

                                  }

                              }
                      )

               }

            }

        return rootView;
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        loadCaptchaImage()
    }


    fun loadCaptchaImage(){

        loginApi.captchaImage().enqueue(
                object : Callback<CaptchaImage> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                            call: Call<CaptchaImage>,
                            response: Response<CaptchaImage>
                    ) {

                        when(response.code()){
                            403 ->{
                                Toast.makeText(requireContext(),"加载失败：${response.message()}",Toast.LENGTH_SHORT).show()
                                return
                            }

                            200 ->{
                                Log.d("加载captcha成功!", response?.body()?.msg?:"加载失败!")

                                val bitmapDrawable = testLoadCaptcha(response?.body()?.img!!);
                                ib_captcha.setImageBitmap(bitmapDrawable.bitmap)
                                uuid = response?.body()?.uuid!!;
                                println("uuid: $uuid")
                            }
                        }


                    }

                    override fun onFailure(call: Call<CaptchaImage>, t: Throwable) {

                    }

                }
        )
    }




}