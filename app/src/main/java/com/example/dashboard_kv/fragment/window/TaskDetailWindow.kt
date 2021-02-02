package com.example.dashboard_kv.fragment.window

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.*
import com.example.dashboard_kv.fragment.CURRENT_TASK_ID
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val title ="任务基本信息"
const val windowKey = "taskInfo"




class TaskDetailWindow: BaseWindow(title, windowKey){

    override var rootLayoutId: Int
        get() = R.layout.fragment_window_task_detail
        set(value) {}

    companion object{
        var taskApi = WebUtil.getService(TaskApi::class.java)

    }

    lateinit var  taskViewModel:TaskDetailViewModel;

    lateinit var tv_projectNo:TextView
    lateinit var tv_projectName:TextView
    lateinit var tv_bussinessKey:TextView
    lateinit var tv_instanceId:TextView
    lateinit var tv_createTime:TextView
    lateinit var tv_taskType:TextView
    lateinit var tv_taskStatus:TextView
    lateinit var tv_delivery:TextView
    lateinit var tv_assemble:TextView
    lateinit var tv_check:TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return (super.onCreateView(inflater, container, savedInstanceState) as ConstraintLayout)
                .apply {

                    tv_projectNo = findViewById(R.id.textView_projectNo_content)
                    tv_projectName = findViewById(R.id.textView_projectName_content)
                    tv_bussinessKey = findViewById(R.id.textView_bussinessKey_content)
                    tv_instanceId = findViewById(R.id.textView_instanceId_content)
                    tv_createTime = findViewById(R.id.textView_createTime_content)
                    tv_taskType = findViewById(R.id.textView_taskType_content)
                    tv_taskStatus = findViewById(R.id.textView_taskStatus_content)
                    tv_delivery = findViewById(R.id.textView_delivery_content)
                    tv_assemble = findViewById(R.id.textView_assemble_content)
                    tv_check = findViewById(R.id.textView_check_content)


                    findViewById<ImageButton>(R.id.imageButton_refresh_window)
                            .apply {

                               setOnClickListener {
                                   loadTaskDeatail()
                               }

                            }

                }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        taskViewModel = ViewModelProvider(this).get(TaskDetailViewModel::class.java)
                .apply {
                    task.observe(viewLifecycleOwner,object:Observer<TaskModel>{
                        override fun onChanged(t: TaskModel?) {
                            if(t!=null){
                                tv_projectNo.text = t!!.projectNo
                                tv_projectName.text = t!!.name
                                tv_bussinessKey.text = t!!.businessKey
                                tv_instanceId.text = t!!.instanceId
                                tv_createTime.text = t!!.createTime
                                tv_taskType.text = TaskType.getDescByCode(t!!.taskType.toInt())
                                //tv_taskStatus.text = TaskStatus.getDescByCode(t!!.status.toInt())
                                tv_delivery.text = t!!.deliveryUserName
                                tv_assemble.text = t!!.assembleUserName
                                tv_check.text = t!!.qaUserName
                            }


                        }

                    })
                }

    }


    override fun onStart() {
        super.onStart()

        loadTaskDeatail()
    }


    fun loadTaskDeatail(){

        taskApi.taskDetail(CURRENT_TASK_ID!!)
                .enqueue(object : Callback<ResponseEntity<TaskModel>> {
                    override fun onResponse(call: Call<ResponseEntity<TaskModel>>, response: Response<ResponseEntity<TaskModel>>) {

                        taskViewModel.task.value = null;
                        taskViewModel.setTask(response.body()?.data!!)

                    }

                    override fun onFailure(call: Call<ResponseEntity<TaskModel>>, t: Throwable) {

                    }


                })
    }

}


/**
 * 任务详情ViewModel
 */
class TaskDetailViewModel:ViewModel(){

    val task = MutableLiveData<TaskModel>()

    fun setTask(t:TaskModel){

        task.value = t
    }


}

