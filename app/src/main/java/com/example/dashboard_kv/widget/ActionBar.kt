package com.example.dashboard_kv.widget


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.TOKEN
import com.example.dashboard_kv.api.UserModel
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
    lateinit var  configBtn:ImageView;

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
         val root:View  = inflater.inflate(R.layout.fragment_action_bar,container);

        /**
         * 用户别名
         */
        tv_nickname = root.findViewById<TextView>(R.id.textView_nickname)

        /**
         * 登录提示
         */
        tv_login_tip= root.findViewById<TextView>(R.id.textView_login_tip)

        /**
         * 登录/退出按钮
         */
        configBtn = root?.findViewById<ImageView>(R.id.btn_config)!!
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

                                configBtn.setImageDrawable(getDrawable(requireActivity(), R.drawable.logout))


                            }else{
                                tv_login_tip.text = "请登录"
                                tv_nickname.visibility = View.GONE
                                configBtn.setImageDrawable(getDrawable(requireActivity(), R.drawable.login))
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