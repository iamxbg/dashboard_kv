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
import com.example.dashboard_kv.fragment.module.NotificationFragment
import com.example.dashboard_kv.fragment.module.ProjectInfoFragment
import com.example.dashboard_kv.fragment.module.ProjectOrderFragment


class LoginFragment:Fragment(){


 //   private lateinit var leftPanel:LinearLayout
    private lateinit var rightTopPanel:LinearLayout
    private lateinit var rightBottomPanel:LinearLayout

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)

         val root:ViewGroup = inflater.inflate(R.layout.fragment_login,container,false) as ViewGroup;

        childFragmentManager.beginTransaction().replace(R.id.fragment_left,ProjectInfoFragment()).commit()
        childFragmentManager.beginTransaction().replace(R.id.fragment_right_top,ProjectOrderFragment()).commit()
        childFragmentManager.beginTransaction().replace(R.id.fragment_right_bottom,NotificationFragment()).commit()

        return root;

    }
}