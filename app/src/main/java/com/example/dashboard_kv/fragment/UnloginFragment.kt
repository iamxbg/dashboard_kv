package com.example.dashboard_kv.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.dashboard_kv.R
import com.example.dashboard_kv.fragment.window.*


class UnloginFragment:Fragment(){


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

         val root:ViewGroup = inflater.inflate(R.layout.fragment_unlogin,container,false) as ViewGroup;

        childFragmentManager.beginTransaction().replace(R.id.fragment_left, SupplyStaffWindow()).commit()


        childFragmentManager.beginTransaction().replace(R.id.fragment_right_top,ProjectOrderWindow()).commit()
        childFragmentManager.beginTransaction().replace(R.id.fragment_right_bottom,NotificationWindow()).commit()
        //childFragmentManager.beginTransaction().replace(R.id.fragment_right_bottom,CurrentTaskInfoWindow()).commit()

        return root;

    }
}