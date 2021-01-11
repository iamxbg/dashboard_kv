package com.example.dashboard_kv.fragment.window

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dashboard_kv.R

const val relatedProjectTitle = "相关项目"
const val relatedProjectWindowId ="related_project_window"

/**
 * 相关项目窗口
 */
class RelatedProjectWindow: BaseWindow(relatedProjectTitle, relatedProjectWindowId){

    override var rootLayoutId: Int
        get() = R.layout.fragment_related_project
        set(value) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}