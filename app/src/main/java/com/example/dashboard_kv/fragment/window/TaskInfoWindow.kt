package com.example.dashboard_kv.fragment.window

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dashboard_kv.R

const val title ="任务基本信息"
const val windowKey = "taskInfo"
class TaskInfoWindow: BaseWindow(title, windowKey){

    override var rootLayoutId: Int
        get() = R.layout.fragment_task_base_info
        set(value) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

        //return inflater.inflate(R.layout.fragment_task_base_info,container);
    }

}