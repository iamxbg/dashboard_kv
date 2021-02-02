package com.example.dashboard_kv.fragment.window

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val project_order_title:String="排名"
const val project_order_windowKey :String="project_order"

/**
 * 任务排名窗口
 */
class TaskOrderWindow: BaseWindow(project_order_title, project_order_windowKey) {

    companion object{
        var taskApi = WebUtil.getService(TaskApi::class.java)

    }

    override var rootLayoutId: Int
        get() = R.layout.fragment_window_project_order
        set(value) {}

    /**
     * 任务排序数据模型
     */
    lateinit var  listView_taskOrder:ListView

    lateinit var  taskOrderViewModel:TaskOrderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val root =super.onCreateView(inflater, container, savedInstanceState) as ViewGroup

        //刷新按钮
        root.findViewById<ImageButton>(R.id.imageButton_refresh_window)
                .apply {
                    setOnClickListener {
                        loadTaskRankings()
                    }
                }

        listView_taskOrder =     root?.findViewById<ListView>(R.id.listView_taskOrder)
                    .apply {

                        adapter = TaskOrderArrayAdapter(requireContext(),R.layout.list_view_task_order)

                    }

        return root;
    }




    /**
     * 任务排序的适配器
     */
    inner class TaskOrderArrayAdapter(@NonNull context : Context, @LayoutRes resource:Int) : ArrayAdapter<TaskModel>(context,resource){

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val task = getItem(position)

            return layoutInflater.inflate(R.layout.widget_task_order,null)
                    .apply {
                        //行号
                        findViewById<TextView>(R.id.tv_rowIndex)
                                .text = (position+1).toString();
                        //任务名称
                        findViewById<TextView>(R.id.tv_project_no)
                                .text = task.name

                        findViewById<TextView>(R.id.tv_task_no)
                                .text = task.projectTaskNo

                        findViewById<TextView>(R.id.tv_createTime)
                                .text = task.createTime
                    }

        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        taskOrderViewModel = ViewModelProvider(this).get(TaskOrderViewModel::class.java)
                .apply {

                    taskList.observe(viewLifecycleOwner,object: Observer<List<TaskModel>>{
                        override fun onChanged(t: List<TaskModel>?) {

                            ( listView_taskOrder.adapter as ArrayAdapter<TaskModel>)
                                    .apply {

                                        this.clear()
                                        this.addAll(t)

                                    }

                        }

                    })

                }


    }

    override fun onStart() {
        super.onStart()

        loadTaskRankings()
    }



    private fun loadTaskRankings(){

        taskApi.taskRankingList()
                .enqueue(object:Callback<ResponseEntity<TaskModel>>{
                    override fun onResponse(call: Call<ResponseEntity<TaskModel>>, response: Response<ResponseEntity<TaskModel>>) {



                        val tasks = response.body()?.rows
                            if(tasks!=null){
                                taskOrderViewModel.taskList.value?.clear();
                                taskOrderViewModel.setTasks(tasks as MutableList<TaskModel>)
                            }


                    }

                    override fun onFailure(call: Call<ResponseEntity<TaskModel>>, t: Throwable) {

                    }


                })

    }


}

/**
 * 任务排序数据模型定义
 */
class TaskOrderViewModel :ViewModel() {

    val taskList = MutableLiveData<MutableList<TaskModel>>()

    init{
        taskList.value?:ArrayList<TaskModel>().apply {
            taskList.value = this
        }
    }

    fun addTask(vararg ts:TaskModel){
        for(t in ts)
            taskList.value?.add(t)
        taskList.value = taskList.value
    }

    fun setTasks(ts:MutableList<TaskModel>){
        for(t in ts)
            taskList.value?.add(t)
        taskList.value = taskList.value
    }


}

