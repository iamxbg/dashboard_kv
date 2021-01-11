package com.example.dashboard_kv.fragment.window

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dashboard_kv.R

const val currentTaskInfoTitle="当前任务"
const val currentTaskInfoWindowId="currentTaskInfo"

/**
 * 当前任务窗口
 */
class CurrentTaskInfoWindow: BaseWindow(currentTaskInfoTitle, currentTaskInfoWindowId){

    override var rootLayoutId: Int
        get() = R.layout.fragment_current_task_info
        set(value) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

        //return inflater.inflate(R.layout.fragment_current_task_info,container);

    }

}