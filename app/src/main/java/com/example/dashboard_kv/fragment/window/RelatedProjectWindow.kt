package com.example.dashboard_kv.fragment.window

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.ProjectInfo
import com.example.dashboard_kv.api.ResponseEntity
import com.example.dashboard_kv.api.WebUtil
import com.example.dashboard_kv.widget.User_View_Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val relatedProjectTitle = "相关项目"
const val relatedProjectWindowId ="related_project_window"

/**
 * 相关项目窗口
 */
class RelatedProjectWindow: BaseWindow(relatedProjectTitle, relatedProjectWindowId){

    override var rootLayoutId: Int
        get() = R.layout.fragment_window_related_project
        set(value) {}

    lateinit var viewModel:RelationProjectViewModel

    lateinit var  listView_projectInfo:ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return   (super.onCreateView(inflater, container, savedInstanceState) as ViewGroup)
            .apply{
                listView_projectInfo =  this.findViewById<ListView>(R.id.listView_related_projects)
                    .apply {
                        adapter = RelationProjectArrayAdapter(requireContext(),R.layout.list_view_project_info)

                    }


                //刷新按钮
                findViewById<ImageButton>(R.id.imageButton_refresh_window)
                        .apply {
                            setOnClickListener {
                                loadProjects();
                            }
                        }
            }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(RelationProjectViewModel::class.java)
            .apply {


                projects.observe(viewLifecycleOwner,object: Observer<List<ProjectInfo>> {

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

    inner class RelationProjectArrayAdapter(@NonNull context : Context, @LayoutRes resource:Int) : ArrayAdapter<ProjectInfo>(context,resource) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val projectInfo = getItem(position)

            return layoutInflater.inflate(R.layout.widget_project_info,null)
                .apply {



                    //行号
                    findViewById<TextView>(R.id.textView_rowIndex)
                            .text = (position+1).toString()

                    //项目名称
                    findViewById<TextView>(R.id.textView_project_name)
                       .apply {

                           this.text = projectInfo.name



                       }

                    var score:Int =0;
                    if(projectInfo.status==null || projectInfo.status.equals("")){
                        score =0;
                    }else{
                        val statusCode = projectInfo.status?.toInt()?:0

                        score = (statusCode*12.5).toInt()
                    }


                    //进度
                    findViewById<ProgressBar>(R.id.tv_process_score)
                            .apply {
                                progress = score
                            }
                }

        }

    }


    /**
     * 异步加载Ftp文件列表
     */
    private fun loadProjects(){

        val user = User_View_Model?.user?.value

        if(user!=null){



            ProjectListWindow.projectApi.projectList()
                    .enqueue(object : Callback<ResponseEntity<ProjectInfo>> {

                        override fun onResponse(call: Call<ResponseEntity<ProjectInfo>>, response: Response<ResponseEntity<ProjectInfo>>) {

                            if(WebUtil.preInteceptor(response) == null){
//
                                parentFragment!!.parentFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()?.navigate(R.id.action_unloginFragment_to_loginFragment)

                                return
                            }else{

                                viewModel.projects?.value?.clear()

                                val projects = response.body()?.rows as MutableList<ProjectInfo>

                                if(projectListTitle!=null){
                                    viewModel.setProjects(projects)
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseEntity<ProjectInfo>>, t: Throwable) {

                        }

                    })
        }




    }

    override fun onStart() {
        super.onStart()

        loadProjects()

    }



}



class RelationProjectViewModel: ViewModel(){

    val projects = MutableLiveData<MutableList<ProjectInfo>>()

    init{
        projects.value?:ArrayList<ProjectInfo>().apply { projects.value = this }
    }

    fun addProjects(vararg ps:ProjectInfo){
        for(p in ps)
        projects.value?.add(p)

        projects.value = projects.value
    }

    fun setProjects(ps:MutableList<ProjectInfo>){

        projects.value = ps

    }

}