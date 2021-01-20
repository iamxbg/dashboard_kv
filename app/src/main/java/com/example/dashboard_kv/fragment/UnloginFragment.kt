package com.example.dashboard_kv.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.dashboard_kv.R
import com.example.dashboard_kv.fragment.window.*
import com.example.dashboard_kv.widget.ActionBar


class UnloginFragment:Fragment(){


 //   private lateinit var leftPanel:LinearLayout
    private lateinit var rightTopPanel:LinearLayout
    private lateinit var rightBottomPanel:LinearLayout

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)

         val root:ViewGroup = inflater.inflate(R.layout.fragment_unlogin,container,false) as ViewGroup;

         val template =dashBoardTemplateViewModel.template.value
        if(template!=null && template.pageInfoList!=null && template.pageInfoList.size>0){

            var dynamicLoad:Boolean =false;
            val dashBoardMap = HashMap<Int,BaseWindow>()
           for( p in template.pageInfoList){

               if(p.pageId==1){
                   for(w in p.elementLocationList){
                       var baseWindow =
                               when(w.elementId){
                                   1 -> ProjectListWindow()
                                   2 -> TaskOrderWindow()
                                   3 -> NotificationWindow()
                                   else -> null
                               }

                        var locationId = when(w.pageLocationId){
                            1-> R.id.fragment_left
                            2 -> R.id.fragment_right_top
                            3 -> R.id.fragment_right_bottom
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


        (parentFragment?.parentFragmentManager?.findFragmentById(R.id.action_bar_fragment) as ActionBar)
            .apply {

                backButton.visibility = View.INVISIBLE

            }

        return root;

    }
}