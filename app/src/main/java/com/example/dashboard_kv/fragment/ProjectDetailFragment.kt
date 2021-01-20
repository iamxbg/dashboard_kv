package com.example.dashboard_kv.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.dashboard_kv.R
import com.example.dashboard_kv.fragment.window.*
import com.example.dashboard_kv.widget.ActionBar

var CURRENT_TASK_ID:Long? = null

class ProjectDetailFragment: Fragment() {


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState);

        val root: ViewGroup = inflater.inflate(R.layout.fragment_project_detail,container,false) as ViewGroup;

        (parentFragment?.parentFragmentManager?.findFragmentById(R.id.action_bar_fragment) as ActionBar)
            .apply {

                backButton.visibility = View.VISIBLE

            }


        val template =dashBoardTemplateViewModel.template.value
        if(template!=null && template.pageInfoList!=null && template.pageInfoList.size>0){

            var dynamicLoad:Boolean =false;
            val dashBoardMap = HashMap<Int, BaseWindow>()
            for( p in template.pageInfoList){

                if(p.pageId==1){
                    for(w in p.elementLocationList){
                        var baseWindow =
                                when(w.elementId){
                                    1 -> TaskDetailWindow()
                                    2 -> CurrentTaskInfoWindow()
                                    3 -> MakeFilesWindow()
                                    else -> null
                                }

                        var locationId = when(w.pageLocationId){
                            1-> R.id.fragment_top_left
                            2 -> R.id.fragment_top_right
                            3 -> R.id.fragment_bottom
                            else -> 0
                        }

                        if(baseWindow==null || locationId==0)
                            break;

                        dashBoardMap.put(locationId,baseWindow)
                    }

                }
            }

            if(dashBoardMap.size==3){
                dynamicLoad = true
            }

            if(dynamicLoad){
                for((k,v) in dashBoardMap){
                    childFragmentManager.beginTransaction().replace(k,v).commit()
                }
            }

        }

        return root;

    }



}