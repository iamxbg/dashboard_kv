package com.example.dashboard_kv.fragment.window

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dashboard_kv.R
import com.example.dashboard_kv.widget.FileView

const val projectListTitle:String ="项目信息"
const val projectListWindowId:String ="project_info_list"

/**
 * 项目信息
 */
class ProjectListWindow: BaseWindow(projectListTitle, projectListWindowId){

    override var rootLayoutId: Int
        get() = R.layout.fragment_project_list
        set(value) {}


    lateinit var root:ViewGroup

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

         root = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
            //val file = FileView(this.requireContext());

        //root.addView(file)


        return root;
    }

}