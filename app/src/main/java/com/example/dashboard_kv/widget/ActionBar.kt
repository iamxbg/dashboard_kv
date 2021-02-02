package com.example.dashboard_kv.widget


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.API_URL
import com.example.dashboard_kv.api.TOKEN
import com.example.dashboard_kv.api.UserModel
import com.example.dashboard_kv.api.WebUtil
import com.example.dashboard_kv.fragment.CURRENT_TASK_ID
import com.example.dashboard_kv.fragment.DesktopFragment
import com.example.dashboard_kv.fragment.ProjectDetailFragment
import com.example.dashboard_kv.fragment.UnloginFragment


/**
 * 用户的ViewModel
 */
var User_View_Model:UserViewModel? = null;


class ActionBar:Fragment() {

    /**
     * 登录/退出按钮
     */
    lateinit var  loginBtn:ImageView;

    /**
     * 用户别名
     */
    lateinit var  tv_nickname:TextView

    /**
     * 登录欢迎提示
     */
    lateinit var  tv_login_tip:TextView


    /**
     * 后退按钮
     */
    lateinit var backButton:ImageButton

    /**
     * 设置按钮
     */
    lateinit var configButton:ImageButton

    /**
     * 设置Api URL的窗口
     */
    lateinit var apiUrlSettingPopWindow: PopupWindow


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
         val root:View  = inflater.inflate(R.layout.fragment_action_bar,container);

        /**
         *  用户别名
         */
        tv_nickname = root.findViewById<TextView>(R.id.textView_nickname)

        /**
         *  登录提示
         */
        tv_login_tip= root.findViewById<TextView>(R.id.textView_login_tip)

        /**
         *  登录/退出按钮
         */
        loginBtn = root?.findViewById<ImageView>(R.id.btn_config)!!
            .apply {
                setOnClickListener { view ->

                    try{
                        val navHosFragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)
                        /**
                         * 登出
                         */
                        if(User_View_Model?.user?.value != null){


                            AlertDialog.Builder(requireContext())
                                    .setPositiveButton("确定退出?",object: DialogInterface.OnClickListener{
                                        override fun onClick(dialog: DialogInterface?, which: Int) {
                                            val dest = navHosFragment?.findNavController()?.currentDestination
                                            setLogout()

                                            CURRENT_TASK_ID = null;

                                            when(dest?.label){

                                                DesktopFragment::class.java.simpleName -> navHosFragment?.findNavController()?.navigate(R.id. action_desktopFragment_to_unloginFragment)
                                                ProjectDetailFragment::class.java.simpleName -> navHosFragment?.findNavController()?.navigate(R.id. action_projectDetailFragment_to_unloginFragment)

                                            }
                                        }
                                    })
                                    .setNegativeButton("取消",object:DialogInterface.OnClickListener{
                                        override fun onClick(dialog: DialogInterface?, which: Int) {
                                           dialog?.cancel()
                                        }

                                    }).setTitle("?").setMessage("确认退出?").show();

                        /**
                         * 登录
                         */
                        }else{

                            val dest = navHosFragment?.findNavController()?.currentDestination

                            navHosFragment?.findNavController()?.navigate(R.id.action_unloginFragment_to_loginFragment)


                        }

                    }catch(e:java.lang.IllegalArgumentException){
                        Log.e("跳转异常:",e.message)
                    }

                }
            }


        /**
         *  配置URL按钮
         */
        configButton = root.findViewById<ImageButton>(R.id.btn_config_1)
            .apply {

                setOnClickListener {

                    val view = layoutInflater.inflate(R.layout.widget_api_url_pop_window,null)

                    //设置访问的IP地址
                    val apiUrl = view.findViewById<EditText>(R.id.et_api_url)
                        .apply {
                            this?.showSoftInputOnFocus =false
                        }

                    //设置port
                    val apiPort = view.findViewById<EditText>(R.id.et_api_port)
                        .apply {
                            this?.showSoftInputOnFocus =false

                        }

                    //getSystemService(Context.LAYOUT_INFLATER_SERVICE)

                    val sp = activity?.getPreferences(Context.MODE_PRIVATE)

                    val saveUri = sp?.getString(resources.getString(R.string.api_base_url),null)

                    if(saveUri!=null && !"".equals(saveUri)){
                        if(saveUri.contains(':',true)){
                            val parts = saveUri.split(":")
                            apiUrl.setText(parts[0])
                            apiPort.setText(parts[1])
                        }else{
                            apiUrl.setText(saveUri)
                        }
                    }




                    //保存设置
                    view.findViewById<Button>(R.id.btn_save)
                        .apply {
                            setOnClickListener {
                                val ip = apiUrl.text.toString()
                                val port = apiPort.text.toString()

                                if(ip!=null && !"".equals(ip) ){
                                  val sharedPreference = activity?.getPreferences(Context.MODE_PRIVATE)
                                    with(sharedPreference!!.edit()){
                                        val apiurl = StringBuilder(ip)
                                        if(port!=null && !"".equals(port)){
                                            apiurl.append(":")
                                            apiurl.append(port)
                                        }

                                        val url = apiurl.toString()
                                         putString(resources.getString(R.string.api_base_url), url)
                                        commit()

                                        apiUrlSettingPopWindow.dismiss()
                                        
                                        Toast.makeText(requireContext(),"保存成功!",Toast.LENGTH_SHORT).show()

                                        //设置全局访问的API
                                        API_URL = url
                                        WebUtil.rebuild()

                                    }
                                }else{
                                    Toast.makeText(requireContext(),"请至少填写IP地址!",Toast.LENGTH_SHORT).show()

                                }

                            }
                        }

                    /**
                     * 悬浮窗
                     */
                    apiUrlSettingPopWindow =  PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                        .apply {

                            //设置是否获取焦点?
                            this.isFocusable = true

                            //设置显示位置，第一个参数并不需要一定为parent Container或者某个container
                            //see: http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0702/1627.html
                            showAtLocation(configButton, Gravity.CENTER,0,0)


                            //设置背景
                            //this.setBackgroundDrawable(Resources.getSystem().getDrawable(R.drawable.background,null))
                            //no functions
                            //this.setBackgroundDrawable(resources.getDrawable(R.drawable.movie,null))
                        }


                }
            }





        /**
         * 回退按钮
         */
        backButton = root.findViewById<ImageButton>(R.id.btn_back)
            .apply {

                this.setOnClickListener {
                    val navHosFragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    val navLabel = navHosFragment?.findNavController()?.currentDestination?.label

                    when(navLabel){
                        UnloginFragment::class.java.simpleName ->{
                            //DO NOTHING
                        }
                        DesktopFragment::class.java.simpleName ->{
                            //DO NOTHING
                        }
                        //详情页->跳转到->桌面页
                        ProjectDetailFragment::class.java.simpleName ->{
                            CURRENT_TASK_ID = null
                            navHosFragment?.findNavController()?.navigate(R.id.action_projectDetailFragment_to_desktopFragment)

                        }


                    }
                }

            }


       return root;

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        User_View_Model = ViewModelProvider(this).get(UserViewModel::class.java)
                .apply {
                    user.observe(viewLifecycleOwner,object: Observer<UserModel?> {
                        override fun onChanged(t: UserModel?) {

                            if(t!==null){
                                tv_login_tip.text = "欢迎"

                                tv_nickname.text = t?.nickName
                                tv_nickname.visibility = View.VISIBLE

                                loginBtn.setImageDrawable(getDrawable(requireActivity(), R.drawable.logout))


                            }else{
                                tv_login_tip.text = "请登录"
                                tv_nickname.visibility = View.GONE
                                loginBtn.setImageDrawable(getDrawable(requireActivity(), R.drawable.login))
                                TOKEN = null;
                            }



                        }

                    })
                }

    }

    /**
     * 应该修改为使用ViewModel的方式
     */
    fun setLogin(user:UserModel?){
        User_View_Model?.setUser(user)
    }


    fun setLogout(){
        User_View_Model?.setUser(null)
    }





}

/**
 * 用户的 ViewModel
 */
class UserViewModel:ViewModel(){

     val user = MutableLiveData<UserModel?>()


    fun setUser(um:UserModel?){
        this.user.value = um;

    }


}