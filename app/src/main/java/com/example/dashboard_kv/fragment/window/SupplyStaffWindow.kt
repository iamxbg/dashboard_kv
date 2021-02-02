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
import com.example.dashboard_kv.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val supplyStaffTitle = "物料"
const val supplyStaffWindowKey = "supplyStaff"

/**
 * 物料窗口
 */
class SupplyStaffWindow: BaseWindow(supplyStaffTitle, supplyStaffWindowKey){

    override var rootLayoutId: Int
        get() = R.layout.fragment_window_supply_files
        set(value) {}


    companion object
    {
        var supplyApi = WebUtil.getService(SuppliesApi::class.java)

    }

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

    private lateinit var  tv_planCount:TextView
    private lateinit var  tv_checkCount:TextView



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

                                    tv_planCount.text = t?.size.toString()
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

                                    tv_checkCount.text = t?.size.toString()

                                }
                        }

                    })
            }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root  = super.onCreateView(inflater, container, savedInstanceState) as ConstraintLayout



        root.findViewById<ImageButton>(R.id.imageButton_refresh_window)
                .apply {

                    setOnClickListener {
                        loadStaff()
                    }
                }

        tv_planCount = root.findViewById(R.id.textView_planCount)
        tv_checkCount = root.findViewById(R.id.textView_checkCount)

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

                    //设置拖拽事件
                    setOnDragListener { v, event ->

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

                    //物料编号
                    findViewById<TextView>(R.id.textView_supply_code_content)
                        .text = staff.suppliesCode
                    //物料名称
                    findViewById<TextView>(R.id.textView_supply_name_content)
                        .text = staff.suppliesName

                    //规格/型号
                    findViewById<TextView>(R.id.textView_specification_content)
                        .text = staff.specification

                    //批次
                    findViewById<TextView>(R.id.textView_batchNumber_content)
                        .text = staff.batchNumber


                    //数量
                    findViewById<TextView>(R.id.textView_supplyNumber_content)
                        .text = staff.suppliesNumber

                    //单位
                    findViewById<TextView>(R.id.textView_supplyUnit_content)
                        .text = staff.suppliesUnit

                    //出库指令号
                    findViewById<TextView>(R.id.textView_exportNum_content)
                        .text = staff.exportCommendNum

                    //出库状态
                    findViewById<TextView>(R.id.textView_export_status_content)
                        .text = staff.exportStatus.toString()

                    //段位
                    findViewById<TextView>(R.id.textView_gradingNumber_content)
                        .text =staff.gradingNumber

                }



        }
    }


    override fun onStart() {
        super.onStart()

        loadStaff()
    }


    fun loadStaff(){
        /**
         * 验收物料
         */
        supplyApi.infoList(listOf("3"))
                .enqueue(object : Callback<ResponseEntity<SupplyStaff>> {
                    override fun onResponse(
                            call: Call<ResponseEntity<SupplyStaff>>,
                            response: Response<ResponseEntity<SupplyStaff>>
                    ) {

                        checkStaffViewModel.supplyStaffs?.value?.clear()

                        val rows = response.body()?.rows
                        if(rows!=null) {
                            checkStaffViewModel.addSupplyStaffs(rows)
                        }


                    }

                    override fun onFailure(call: Call<ResponseEntity<SupplyStaff>>, t: Throwable) {


                    }

                })

        /**
         * 计划物料
         */
        supplyApi.infoList( listOf("1"))
                .enqueue(object : Callback<ResponseEntity<SupplyStaff>> {
                    override fun onResponse(
                            call: Call<ResponseEntity<SupplyStaff>>,
                            response: Response<ResponseEntity<SupplyStaff>>
                    ) {

                        planStaffViewModel.supplyStaffs?.value?.clear()

                        val rows = response.body()?.rows
                        if(rows!= null) planStaffViewModel.addSupplyStaffs(rows)
                    }

                    override fun onFailure(call: Call<ResponseEntity<SupplyStaff>>, t: Throwable) {

                    }

                })
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