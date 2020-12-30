package com.example.dashboard_kv.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.dashboard_kv.R
import com.example.dashboard_kv.R.*


class LoginFragment:Fragment(){


    private lateinit var loginBtn:Button;

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)

         val root:ViewGroup = inflater.inflate(R.layout.fragment_login,null) as ViewGroup;

        loginBtn =  root.findViewById(R.id.btn_login);

        loginBtn.setOnClickListener({ v->
            v.findNavController().navigate(R.id.action_loginFragment_to_desktopFragment)
        })

        return root;

    }
}