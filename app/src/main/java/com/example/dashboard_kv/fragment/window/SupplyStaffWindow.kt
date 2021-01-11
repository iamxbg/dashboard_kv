package com.example.dashboard_kv.fragment.window

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dashboard_kv.R

const val supplyStaffTitle = "物料"
const val supplyStaffWindowKey = "supplyStaff"

/**
 * 物料窗口
 */
class SupplyStaffWindow: BaseWindow(supplyStaffTitle, supplyStaffWindowKey){

    override var rootLayoutId: Int
        get() = R.layout.fragment_supply_files
        set(value) {}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }



}