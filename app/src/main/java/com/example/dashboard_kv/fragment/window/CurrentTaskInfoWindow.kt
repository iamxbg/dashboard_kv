package com.example.dashboard_kv.fragment.window

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
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

const val currentTaskInfoTitle="当前任务"
const val currentTaskInfoWindowId="currentTaskInfo"

/**
 * 当前任务窗口
 */
class CurrentTaskInfoWindow: BaseWindow(currentTaskInfoTitle, currentTaskInfoWindowId){

    override var rootLayoutId: Int
        get() = R.layout.fragment_current_task_info
        set(value) {}


    companion object {
        val taskApi = WebUtil.getService(TaskApi::class.java)
    }

    private lateinit var viewModel:TaskModelViewModel;
    private lateinit var listView_tasks :ListView;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root =  super.onCreateView(inflater, container, savedInstanceState) as LinearLayout

        listView_tasks = root.findViewById<ListView>(R.id.listView_taskInfo)

        TaskModelArrayAdapter(requireContext(), R.layout.widget_make_file_folder)
            .apply {

                listView_tasks.adapter = this;
            }


        return root;

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //bind view model
        viewModel = ViewModelProvider(this).get(TaskModelViewModel::class.java)
            .also {

                val taskModelObserver = Observer<List<TaskModel>> {
                    tasks->
                    ( listView_tasks.adapter as ArrayAdapter<TaskModel>)
                        .apply {
                            this.clear()
                            this.addAll(tasks)
                        }

                }

                it.taskModels.observe(viewLifecycleOwner,taskModelObserver)

            }

    }

    /**
     * ArrayAdapter for List View
     */
    inner class  TaskModelArrayAdapter(@NonNull context : Context, @LayoutRes resource:Int):ArrayAdapter<TaskModel>(context,resource){


        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            //get data
            val taskModel = getItem(position)

            return layoutInflater.inflate(R.layout.widget_task_basic_info,null)
                    .apply {
                        //行号
                       findViewById<TextView>(R.id.tv_row_index)
                                .apply {
                                    text = (position +1).toString()
                                }
                        //任务名称
                        findViewById<TextView>(R.id.tv_task_name)
                                .apply {
                                    text = taskModel.name
                                }

                        //任务详情
                        findViewById<TextView>(R.id.tv_task_detail)
                                .apply {
                                    setOnClickListener {
                                        v ->
                                        try{
                                            TODO("显示任务详情悬浮窗")
                                        }
                                        catch (e:NotImplementedError){
                                            Log.e("TODO",e.message)
                                            return@setOnClickListener
                                        }
                                    }
                                }
                    }

        }

    }

    override fun onStart() {
        super.onStart()

        loadTasks()

    }


    private fun loadTasks():Unit{

        taskApi.taskList()
                .enqueue(object: Callback<ResponseEntity<TaskModel>> {

                    override fun onResponse(call: Call<ResponseEntity<TaskModel>>, response: Response<ResponseEntity<TaskModel>>) {

                        val files = response.body()?.rows as MutableList<TaskModel>

                        viewModel.setTasks(files)


                    }

                    override fun onFailure(call: Call<ResponseEntity<TaskModel>>, t: Throwable) {
                        //TODO("Not yet implemented")
                        Log.e("loadFiles failure!",t.message)
                    }
                })

    }

}

/**
 * View Model for task Model
 */
class TaskModelViewModel:ViewModel(){

    val taskModels = MutableLiveData<MutableList<TaskModel>>();

    init {
        taskModels.value?:ArrayList<TaskModel>().apply { taskModels.value = this }
    }


    /**
     * 添加任务
     */
    fun addTask(task:TaskModel){

        taskModels.value?.add(task)

        taskModels.value = taskModels.value
    }

    /**
     * 添加任务s
     */
    fun addTasks(vararg  tasks:TaskModel){

        for(t in tasks)
            taskModels.value?.add(t)

        taskModels.value =taskModels.value
    }

    /**
     * 设置任务s
     */
    fun setTasks(tasks:MutableList<TaskModel> ){
        taskModels.value = tasks
    }

}


