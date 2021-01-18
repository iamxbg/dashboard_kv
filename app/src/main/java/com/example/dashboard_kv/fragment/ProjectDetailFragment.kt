package com.example.dashboard_kv.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.dashboard_kv.R

class ProjectDetailFragment: Fragment() {


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState);

        val root: ViewGroup = inflater.inflate(R.layout.fragment_project_detail,container,false) as ViewGroup;

//        childFragmentManager.beginTransaction().replace(
//            R.id.fragment_left, ProjectListWindow())
//            .commit()
//
//        childFragmentManager.beginTransaction().replace(
//            R.id.fragment_right_top,
//            ProjectOrderWindow()
//        ).commit()
//
//         childFragmentManager.beginTransaction().replace(
//            R.id.fragment_right_bottom,
//            CurrentTaskInfoWindow()
//        ).commit()

        return root;

    }



}