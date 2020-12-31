package com.example.dashboard_kv.fragment

import android.app.ActionBar
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.findFragment
import com.example.dashboard_kv.R
import com.example.dashboard_kv.fragment.module.NotificationFragment
import com.example.dashboard_kv.fragment.module.ProjectInfoFragment
import com.example.dashboard_kv.fragment.module.ProjectOrderFragment

class DesktopFragment: Fragment() {

    private lateinit var  ll:LinearLayout

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       return  super.onCreateView(inflater, container, savedInstanceState);

       //return  inflater.inflate(R.layout.fragment_desktop,container,false);

    }



}