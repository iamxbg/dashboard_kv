package com.example.dashboard_kv.fragment.window

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dashboard_kv.R
import kotlin.properties.Delegates


open abstract class BaseWindow(var title:String, var windowId:String):Fragment() {

    open var rootLayoutId by Delegates.notNull<Int>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(rootLayoutId,null);

    }


}