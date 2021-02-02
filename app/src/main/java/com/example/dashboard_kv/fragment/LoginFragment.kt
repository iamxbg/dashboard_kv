package com.example.dashboard_kv.fragment


import android.content.Context
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
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.*
import com.example.dashboard_kv.util.EncryptUtil
import com.example.dashboard_kv.widget.ActionBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


var dashBoardTemplateViewModel:DashBoardTemplateViewModel = DashBoardTemplateViewModel();

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
     * 模板选择器
     */
    lateinit var listView_templateChooser:ListView

    lateinit var  cl_templateChooser:ConstraintLayout

    /**
     * 验证码图片
     */
    lateinit var ib_captcha:ImageButton

    var uuid:String?=null;

    companion object {

        var loginApi = WebUtil.getService(LoginApi::class.java)

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

        (parentFragment?.parentFragmentManager?.findFragmentById(R.id.action_bar_fragment) as ActionBar)
            .apply {

                backButton.visibility = View.INVISIBLE

            }

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

        cl_templateChooser = rootView.findViewById(R.id.template_chooser)

        listView_templateChooser = rootView.findViewById(R.id.listView_template_chooser)



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
                      Toast.makeText(requireContext(), "用户名不为空！", Toast.LENGTH_SHORT).show()
                      return@setOnClickListener
                  }

                  //密码
                  val passwordText = et_password.text.toString()
                  if(passwordText.length ==0){
                      Toast.makeText(requireContext(), "密码不能为空!", Toast.LENGTH_SHORT).show()
                      return@setOnClickListener
                  }
                  val encdoedPasswd = EncryptUtil.getEcryptedPasswordAndroid(passwordText)


                  //验证码
                  val code = et_captchaCode.text.toString();
                  if(code.length ==0){
                      Toast.makeText(requireContext(), "请输入验证码!", Toast.LENGTH_SHORT).show()
                      return@setOnClickListener
                  }

                  println("code:$code ,password:$passwordText username:$username, uuid:$uuid")

                  if(uuid == null || uuid.equals("")){
                     Toast.makeText(requireContext(),"请填写验证码!",Toast.LENGTH_SHORT).show()
                      return@setOnClickListener
                  }

                  val loginReq =  LoginReq(code, encdoedPasswd, username, uuid!!)
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


                                              /**
                                               * 获取用户信息
                                               */
                                              loginApi.getUserInfo().enqueue(object:Callback<Map<String,Any>>{

                                                  override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {

                                                      val map = (response.body() as Map<String,Any>).get("user") as Map<String, Object>

                                                      val user = UserModel(
                                                              map.get("admin").toString().toBoolean(),
                                                              map.get("avatar").toString(),
                                                              map.get("createBy").toString(),
                                                              map.get("createTime").toString(),
                                                              map.get("delFlag").toString(),
                                                              if(map.get("deptId")==null) 0 else map.get("deptId").toString().toDouble().toInt(),
                                                              map.get("email").toString(),
                                                              map.get("loginDate").toString(),
                                                              map.get("loginIp").toString(),
                                                              map.get("nickName").toString(),
                                                              map.get("phonenumber").toString(),
                                                              map.get("remark").toString(),
                                                              map.get("sex").toString(),
                                                              map.get("status").toString(),
                                                              map.get("userId").toString().toDouble().toLong(),
                                                              map.get("userName").toString(),
                                                              null)

                                                      /**
                                                       * 设置用户信息
                                                       */

                                                      (parentFragment?.parentFragmentManager?.findFragmentById(R.id.action_bar_fragment) as ActionBar)
                                                              .setLogin(user);


                                                  }

                                                  override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {

                                                  }

                                              })



                                              /**
                                               * 获取看板信息
                                               */

                                              //是否立刻跳转?
                                              var jumpNow:Boolean = true;


                                              loginApi.getDashBoardTempaltes().enqueue(object:Callback<ResponseEntity<DashboardTemplate>>{
                                                  override fun onResponse(call: Call<ResponseEntity<DashboardTemplate>>, response: Response<ResponseEntity<DashboardTemplate>>) {

                                                     val templates =  response.body()?.rows

                                                      if(templates!=null && templates.size>0){



                                                          if(templates.size==1){
                                                              //使用默认模板
//                                                             val aa = DashBoardTemplateArrayAdapter(requireContext(),R.layout.list_view_project_info);
//                                                                      aa.addAll(templates);
//                                                              listView_templateChooser.adapter =aa
//                                                              jumpNow = false
//                                                              cl_templateChooser.visibility = View.VISIBLE
                                                                dashBoardTemplateViewModel.setTemplate(templates.get(0))
                                                          }else{
                                                              //弹出让用户进行选择
                                                              val aa = DashBoardTemplateArrayAdapter(requireContext(),R.layout.list_view_project_info);
                                                                      aa.addAll(templates);


                                                              listView_templateChooser.adapter =aa


                                                              jumpNow = false
                                                              cl_templateChooser.visibility = View.VISIBLE

                                                          }
                                                      }else{
                                                        //直接跳转



                                                      }

                                                      if(jumpNow){
                                                          val navHosFragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)
                                                          navHosFragment?.findNavController()?.navigate(R.id.action_loginFragment_to_desktopFragment)
                                                      }


                                                  }

                                                  override fun onFailure(call: Call<ResponseEntity<DashboardTemplate>>, t: Throwable) {

                                                  }

                                              })







                                          }
                                          500 -> {
                                              val errMsg = response.body()?.msg

                                              Toast.makeText(requireContext(), response.body()?.msg, Toast.LENGTH_SHORT).show()

                                              loadCaptchaImage()

                                              return;
                                          }
                                          else -> {

                                          }
                                      }

                                  }

                                  override fun onFailure(call: Call<LoginResp>, t: Throwable) {


                                  }

                              }
                      )

               }

            }

        /**
         * 取消登录
         */
//        rootView.findViewById<Button>(R.id.btn_cancel_login)
//                .apply {
//                    val navHosFragment = parentFragment?.parentFragmentManager?.findFragmentById(R.id.nav_host_fragment)
//                    navHosFragment?.findNavController()?.navigate(R.id.action_loginFragment_to_unloginFragment)
//                }

        return rootView;
    }


    /**
     * 模板的内部类
     */
    inner class DashBoardTemplateArrayAdapter(@NonNull context : Context, @LayoutRes resource:Int):ArrayAdapter<DashboardTemplate>(context,resource) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val template = getItem(position)


            return layoutInflater.inflate(R.layout.widget_dashboard_template,null)
                    .apply {
                        //行号
                        findViewById<TextView>(R.id.textView_rowIndex)
                                .text = (position+1).toString()

                        //模板名称
                        findViewById<TextView>(R.id.textView_template_name)
                                .text = template.templateName

                        //选择模板
                        findViewById<Button>(R.id.button_choose)
                                .apply {

                                    setOnClickListener {
                                        /**
                                         * 设置dashboardTemplate的viewModel
                                         */
                                        dashBoardTemplateViewModel.setTemplate(template)

                                        //跳转
                                        val navHosFragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)
                                        navHosFragment?.findNavController()?.navigate(R.id.action_loginFragment_to_desktopFragment)

                                    }
                                }


                    }
        }

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
                                Toast.makeText(requireContext(),"验证码加载失败：${response.message()}",Toast.LENGTH_SHORT).show()
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
                            t.printStackTrace()

                    }

                }
        )
    }


    /**
     * 创建ViewModel
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ViewModelProvider(this).get(DashBoardTemplateViewModel::class.java)
                .apply {
                    dashBoardTemplateViewModel.template.observe(viewLifecycleOwner,object:Observer<DashboardTemplate?>{
                        override fun onChanged(t: DashboardTemplate?) {

                            if(t!=null){
//                                dashBoardTemplateViewModel.template.value = null
//                                dashBoardTemplateViewModel.setTemplate(t)
                            }
                        }

                    });
                }
    }


}

/**
 *   看板的ViewModel
 */
class DashBoardTemplateViewModel:ViewModel(){

    val template = MutableLiveData<DashboardTemplate?>()


    fun setTemplate(t:DashboardTemplate){
        template.value = t
    }



}

