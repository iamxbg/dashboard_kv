package com.example.dashboard_kv.fragment.window

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.*
import com.example.dashboard_kv.widget.FileView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val projectListTitle:String ="项目信息"
const val projectListWindowId:String ="project_info_list"

/**
 * 项目信息
 */
class ProjectListWindow: BaseWindow(projectListTitle, projectListWindowId){

    override var rootLayoutId: Int
        get() = R.layout.fragment_project_list
        set(value) {}


    lateinit var root:ViewGroup


    lateinit var projectInfoViewModel: ProjectInfoViewModel


    lateinit var listView_projectInfo:ListView;


    companion object {

        val projectApi = WebUtil.getService(ProjectApi::class.java)

    }


    /**
     * 异步加载Ftp文件列表
     */
    private fun loadProjects(){


        projectApi.projectList()
                .enqueue(object :Callback<ResponseEntity<ProjectInfo>> {

                    override fun onResponse(call: Call<ResponseEntity<ProjectInfo>>, response: Response<ResponseEntity<ProjectInfo>>) {

                       if(WebUtil.preInteceptor(response) == null){
//
//                        val navHosFragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)
//                        navHosFragment?.findNavController()?.navigate(R.id.action_unloginFragment_to_loginFragment)
//
                          //parentFragmentManager.primaryNavigationFragment!!.findNavController().navigate(R.id.action_unloginFragment_to_loginFragment)

                          parentFragment!!.parentFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()?.navigate(R.id.action_unloginFragment_to_loginFragment)

                           return
                        }else{




                           Log.d("project-API:",response.message())
                           val projects = response.body()?.rows as MutableList<ProjectInfo>

                           projectInfoViewModel.addProjects(projects)
                        }


                    }

                    override fun onFailure(call: Call<ResponseEntity<ProjectInfo>>, t: Throwable) {
                        Log.e("loadFiles failure!",t.message)
                        TODO("Not yet implemented")


                    }

                })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

         root = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
            //val file = FileView(this.requireContext());

        //root.addView(file)


        listView_projectInfo =  root.findViewById<ListView>(R.id.listView_projectInfo)
                .apply {
                    adapter = ProjectInfoArrayAdapter(requireContext(),R.layout.list_view_project_info)

                }


        return root;
    }



    inner class ProjectInfoArrayAdapter(@NonNull context : Context, @LayoutRes resource:Int) : ArrayAdapter<ProjectInfo>(context,resource){


        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            //return super.getView(position, convertView, parent)

            val projectInfo = getItem(position)

            return layoutInflater.inflate(R.layout.widget_project_info,null)
                    .also {

                        it.findViewById<TextView>(R.id.textView_rowIndex)
                                .text = position.toString()

                        it.findViewById<TextView>(R.id.textView_project_name)
                                .apply {

                                    text = projectInfo.name



                                }

                    }


        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        projectInfoViewModel = ViewModelProvider(this).get(ProjectInfoViewModel::class.java)
                .apply {

                    projects.observe(viewLifecycleOwner,object:Observer<List<ProjectInfo>> {

                        override fun onChanged(t: List<ProjectInfo>?) {

                            ( listView_projectInfo.adapter as ArrayAdapter<ProjectInfo>)
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

        loadProjects()

    }

    class ProjectInfoViewModel : ViewModel(){

        val projects = MutableLiveData<MutableList<ProjectInfo>>();


        init{
            projects.value?:ArrayList<ProjectInfo>().apply { projects.value = this }
        }

        fun addProjects(vararg project:ProjectInfo){
            for(p in project)
                projects.value?.add(p)

            projects.value = projects.value
        }

        fun addProjects(ps:MutableList<ProjectInfo>){

            for(p in ps)
                projects.value?.add(p)
            projects.value = projects.value
        }

    }

}