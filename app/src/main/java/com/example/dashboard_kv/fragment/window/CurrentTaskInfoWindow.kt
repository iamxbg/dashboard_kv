package com.example.dashboard_kv.fragment.window

import android.content.Context
import android.os.Bundle
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
import com.example.dashboard_kv.api.FtpFile
import com.example.dashboard_kv.api.TaskModel

const val currentTaskInfoTitle="当前任务"
const val currentTaskInfoWindowId="currentTaskInfo"

/**
 * 当前任务窗口
 */
class CurrentTaskInfoWindow: BaseWindow(currentTaskInfoTitle, currentTaskInfoWindowId){

    override var rootLayoutId: Int
        get() = R.layout.fragment_current_task_info
        set(value) {}


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

            //inflater View
            val cl = layoutInflater.inflate(R.layout.widget_task_basic_info,null);

            cl.findViewById<TextView>(R.id.tv_row_index)
                .apply {
                    text = (position +1).toString()
                }

            cl.findViewById<TextView>(R.id.tv_task_name)
                .apply {
                    text = taskModel.name
                }

            cl.findViewById<TextView>(R.id.tv_task_detail)
                .apply {
                    setOnClickListener {
                        v ->
                            TODO("show task detail! use hostNavigationFragment to jump!")
                    }
                }

            return cl
        }

    }

    override fun onStart() {
        super.onStart()

        val t1 = TaskModel("",1,"","","","",
        "","","","","任务1-1","","","","","",
        "","","",1,"","","");

        val t2 = TaskModel("",1,"","","","",
            "","","","","任务-2","","","","","",
            "","","",1,"","","");

        viewModel.addTask(t1)
        viewModel.addTask(t2)
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


    fun addTask(task:TaskModel){

        taskModels.value?.add(task)

        taskModels.value = taskModels.value
    }

    fun addTasks(vararg  tasks:TaskModel){

        for(t in tasks)
            taskModels.value?.add(t)

        taskModels.value =taskModels.value
    }


    fun setTasks(tasks:MutableList<TaskModel> ){
        taskModels.value = tasks
    }

}


// TODO: 2021/1/14  添加加载工程对应Tasks的逻辑！

