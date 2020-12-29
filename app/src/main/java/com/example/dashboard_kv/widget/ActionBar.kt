package com.example.dashboard_kv.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.leanback.app.BaseFragment
import androidx.leanback.app.BaseSupportFragment
import com.example.dashboard_kv.R

class ActionBar:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)

       return  inflater.inflate(R.layout.widget_action_bar,container);

    }
}