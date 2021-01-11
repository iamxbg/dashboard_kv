package com.example.dashboard_kv.fragment.window

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dashboard_kv.R
import com.example.dashboard_kv.fragment.BaseWindow

const val notification_title:String = "通知"
const val notification_windowKey : String ="notification"

class NotificationWindow(): BaseWindow(notification_title, notification_windowKey) {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_notification,container,false);
    }


    override fun onStart() {
        super.onStart()



    }

}