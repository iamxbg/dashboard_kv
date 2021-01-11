package com.example.dashboard_kv.fragment.window

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dashboard_kv.R
import com.example.dashboard_kv.fragment.BaseWindow

const val project_order_title:String="排名"
const val project_order_windowKey :String="project_order"

/**
 * 任务排名窗口
 */
class ProjectOrderWindow: BaseWindow(project_order_title, project_order_windowKey) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_project_order,container,false);
    }

}