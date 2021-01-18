package com.example.dashboard_kv.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.dashboard_kv.R

class DesktopFragment: Fragment() {


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         super.onCreateView(inflater, container, savedInstanceState);

        val root:ViewGroup = inflater.inflate(R.layout.fragment_desktop,container,false) as ViewGroup;


        return root;

    }



}