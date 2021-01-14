package com.example.dashboard_kv.fragment.window

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.FtpFile
import com.example.dashboard_kv.api.SupplyStaff
import com.example.dashboard_kv.api.TaskModel

const val supplyStaffTitle = "物料"
const val supplyStaffWindowKey = "supplyStaff"

/**
 * 物料窗口
 */
class SupplyStaffWindow: BaseWindow(supplyStaffTitle, supplyStaffWindowKey){

    override var rootLayoutId: Int
        get() = R.layout.fragment_supply_files
        set(value) {}

    private lateinit var  root :ConstraintLayout

    /**
     * 计划物料
      */
    private lateinit var  ListView_planSupplyStaffs : ListView

    /**
     * 审核物料
     */
    private lateinit var ListView_checkSupplyStaffs : ListView

    /**
     * 计划物料的ViewModel
     */
    private lateinit var  planStaffViewModel: SupplyStaffViewModel

    /**
     * 审核物料的ViewModel
     */
    private lateinit var  checkStaffViewModel: SupplyStaffViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        planStaffViewModel =  ViewModelProvider(this).get("planStaffs",SupplyStaffViewModel::class.java)
            .also {

                //bind observer
                it.supplyStaffs.observe(viewLifecycleOwner
                    , object: Observer<List<SupplyStaff>>{
                        override fun onChanged(t: List<SupplyStaff>?) {

                            ( ListView_planSupplyStaffs.adapter as ArrayAdapter<SupplyStaff>)
                                .apply {

                                    this.clear()
                                    this.addAll(t)

                                }

                        }

                    })
            }


        checkStaffViewModel = ViewModelProvider(this).get("checkStaffs",SupplyStaffViewModel::class.java)
            .also {

                it.supplyStaffs.observe(viewLifecycleOwner,
                    object : Observer<List<SupplyStaff>> {
                        override fun onChanged(t: List<SupplyStaff>?) {

                            ( ListView_checkSupplyStaffs.adapter as ArrayAdapter<SupplyStaff>)
                                .apply {

                                    this.clear()
                                    this.addAll(t)

                                }
                        }

                    })
            }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root  = super.onCreateView(inflater, container, savedInstanceState) as ConstraintLayout

        ListView_planSupplyStaffs = root.findViewById<ListView>(R.id.listView_supplies_plan)
            .apply {
                adapter = SupplyStaffArrayAdapter(requireContext(),R.layout.list_view_supply_staffs,"PLAN")


                setOnDragListener { v, event ->

                    Log.w("event-type:",event.action.toString())
                    if(event == null){
                        Log.e("NULL","event")
                    }
                    if(event.action == DragEvent.ACTION_DROP){
                        //这里使用parcle替换更合理!
                        //event.result
                        val staff = event.localState as SupplyStaff

                        checkStaffViewModel.removeSupplyStaff(staff)
                        planStaffViewModel.addSupplyStaff(staff)

                    }
                    if(event.clipDescription == null){
                        Log.e("clipDescriptionNULL","event")
                        Log.e("ACTION-type:",event.action.toString())
                        return@setOnDragListener false
                    }
                    if(event.clipDescription.label.equals("CHECK")){

                        Log.w("ListView-check","LISTENING")

                        true;
                    }else
                        false;


                }
            }


        ListView_checkSupplyStaffs = root.findViewById<ListView>(R.id.listView_supplies_check)
             .apply {
                 adapter = SupplyStaffArrayAdapter(requireContext(),R.layout.list_view_supply_staffs,"CHECK")


                 setOnDragListener { v, event ->

                        Log.w("event-type:",event.action.toString())
                        if(event == null){
                            Log.e("NULL","event")
                        }
                     if(event.action == DragEvent.ACTION_DROP){
                         //这里使用parcle替换更合理!
                        //event.result
                         val staff = event.localState as SupplyStaff

                         planStaffViewModel.removeSupplyStaff(staff)
                         checkStaffViewModel.addSupplyStaff(staff)

                     }
                     if(event.clipDescription == null){
                         Log.e("clipDescriptionNULL","event")
                         Log.e("ACTION-type:",event.action.toString())
                         return@setOnDragListener false
                     }
                        if(event.clipDescription.label.equals("PLAN")){

                            Log.w("ListView-check","LISTENING")

                             true;
                        }else
                             false;


                 }
             }

        return root;
    }


    inner class SupplyStaffArrayAdapter(@NonNull context : Context, @LayoutRes resource:Int,val listViewType:String) : ArrayAdapter<SupplyStaff>(context,resource) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


            val staff  = getItem(position)

            return   layoutInflater.inflate(R.layout.widget_supply_staff,null)
                .apply {

                    // why tag bind failed here!
                    // 在v.tag中的值为null
                   // tag = staff

                    this.findViewById<TextView>(R.id.textView_staff_name)
                        .apply {
                            text = staff.suppliesName



                            //设置拖拽事件
                            setOnDragListener { v, event ->

                                //val name = (v.tag as SupplyStaff).suppliesName
                                val name = staff.suppliesName

                                var actionStr:String =""

                                when(event.action){
                                    DragEvent.ACTION_DRAG_STARTED -> {
                                        actionStr = "STARTED"
                                    }

                                    DragEvent.ACTION_DRAG_ENTERED ->{
                                        actionStr = "ENTERED"
                                    }

                                    DragEvent.ACTION_DRAG_LOCATION ->{
                                        actionStr = "LOCATION"
                                    }

                                    DragEvent.ACTION_DRAG_EXITED ->{
                                        actionStr = "EXITED"
                                    }

                                    DragEvent.ACTION_DROP ->{
                                        actionStr = "DROP"
                                    }

                                    DragEvent.ACTION_DRAG_ENDED -> {
                                        actionStr = "END"
                                    }
                                }

                                Log.d("view-$name action- $actionStr. POS:","x-${event.x} y-${event.y}")

                                true
                            }



                            setOnLongClickListener { v->
                                // here v.tag is null
                               //val supplyStaff = v.tag as? SupplyStaff;
                                val str = staff?.suppliesNumber.toString()
                                val item = ClipData.Item(str)

                                var dragData = ClipData(ClipDescription(
                                    listViewType,
                                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)) ,item)

                                v.startDragAndDrop(dragData,View.DragShadowBuilder(this),staff,0)

                                true;
                            }
                        }



                }



        }
    }


    override fun onStart() {
        super.onStart()

        val s1 = SupplyStaff(1,"","","","","","","","",
        "","","","",1,"","","","",
            11,
        11,"","","物料-2","","","","","",11)

        val s2 = SupplyStaff(1,"","","","","","","","",
            "","","","",2,"","","","",
            11,
            11,"","","物料-1","","","","","",11)


        planStaffViewModel.addSupplyStaff(s1)
        planStaffViewModel.addSupplyStaff(s2)

    }

}





/**
 * 物料的ViewModel
 */
class SupplyStaffViewModel :ViewModel() {

    val supplyStaffs = MutableLiveData<MutableList<SupplyStaff>>()

    init {

        supplyStaffs.value?:ArrayList<SupplyStaff>().apply { supplyStaffs.value = this }
    }


    fun addSupplyStaff(staff:SupplyStaff){
        supplyStaffs.value?.add(staff)

        supplyStaffs.value = supplyStaffs.value
    }

    fun removeSupplyStaff(staff:SupplyStaff) {
        supplyStaffs.value?.remove(staff)

        supplyStaffs.value = supplyStaffs.value
    }

    fun addSupplyStaffs(staffs:List<SupplyStaff>){

        for(s in staffs)
            supplyStaffs.value?.add(s)


        supplyStaffs.value = supplyStaffs.value
    }


    fun setSupplyStaffs(staffs: MutableList<SupplyStaff>){

        supplyStaffs.value = staffs
    }


}