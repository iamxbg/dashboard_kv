package com.example.dashboard_kv.fragment.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dashboard_kv.R
import com.example.dashboard_kv.fragment.BaseFragment

val notification_title:String = "通知"
val notification_windowKey : String ="notification"

class NotificationFragment(): BaseFragment(notification_title, notification_windowKey) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_notification,container,false);
    }

}