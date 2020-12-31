package com.example.dashboard_kv.fragment.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dashboard_kv.R
import com.example.dashboard_kv.fragment.BaseFragment

val project_info_title:String ="项目信息"
val project_windowKey:String ="project_info"

class ProjectInfoFragment(): BaseFragment(project_info_title, project_windowKey) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_project_info,container,false);
    }

}