package com.example.dashboard_kv.widget

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.WebUtil


class ActionBar:Fragment() {

    lateinit var  configBtn:ImageView;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
         val root:View  = inflater.inflate(R.layout.fragment_action_bar,container);

        configBtn = root?.findViewById<ImageView>(R.id.btn_config)!!

        configBtn.setOnClickListener { view ->

           // val navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)
            try{
                val navHosFragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)
                navHosFragment?.findNavController()?.navigate(R.id.action_unloginFragment_to_loginFragment)
            }catch(e:java.lang.IllegalArgumentException){
                Log.e("跳转异常:",e.message)
            }



        }

       return root;

    }
}