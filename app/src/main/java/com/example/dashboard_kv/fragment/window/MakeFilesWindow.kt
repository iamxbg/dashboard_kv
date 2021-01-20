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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.FtpFile
import com.example.dashboard_kv.api.FtpFilesApi
import com.example.dashboard_kv.api.ResponseEntity
import com.example.dashboard_kv.api.WebUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

const val makeFileTitle ="工艺文件"
const val makeFileWindowId ="make_file_window"
class MakeFilesWindow: BaseWindow(makeFileTitle, makeFileWindowId){

    /**
     * 文件显示框
     */
    private lateinit  var makeFilesGridView: GridView;

    /**
     * Root View
     */
    private lateinit var root: LinearLayout;

    /**
     * view model
     */
    private lateinit var viewModel:MakeFilesViewModel;

    private lateinit var  currentFolderIdViewModel:StackViewModel<Long>;



    companion object{
       val fileApi:FtpFilesApi = WebUtil.getService(FtpFilesApi::class.java)

        val KEY_CURRENT_FOLDER_ID ="CURRENT_FOLDER_ID"

    }

    /**
     * 资源文件ID
     */
    override var rootLayoutId: Int
        get() = R.layout.fragment_window_make_files
        set(value) {}



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currentFolderIdViewModel = ViewModelProvider(this).get(KEY_CURRENT_FOLDER_ID,StackViewModel::class.java) as StackViewModel<Long>;

        currentFolderIdViewModel.stack.push(1);

        viewModel =  ViewModelProvider(this).get(MakeFilesViewModel::class.java)
                .also {

                    //define observer
                    val makeFilesObserver = Observer<List<FtpFile>> {
                        files ->
                        //bing to recycleView's adapter

                        Log.d("file-size",files?.size.toString())
                        ( makeFilesGridView?.adapter as ArrayAdapter<FtpFile>).apply {


                            this.clear()
                            this.addAll(files)


                        }
                    }

                    //bind observer
                    it.rawFiles.observe(viewLifecycleOwner
                            ,makeFilesObserver)
                }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root  = super.onCreateView(inflater, container, savedInstanceState) as LinearLayout

        makeFilesGridView = root.findViewById(R.id.grid_view_task_files);



        /**
         * 返回按钮
         */
         root.findViewById<ImageButton>(R.id.imageButton_back)
            .apply {
                setOnClickListener {
                    if(currentFolderIdViewModel.stack.size>=2)
                        currentFolderIdViewModel.stack.pop()
                        loadFiles(currentFolderIdViewModel.stack.peek())
                }
            }

        /**
         * 刷新按钮
         */

        root.findViewById<ImageButton>(R.id.imageButton_refresh_window)
            .apply {
                setOnClickListener {
                    loadFiles(currentFolderIdViewModel.stack.peek())
                }
            }


        FtpFileArrayAdapter(requireContext(), R.layout.widget_make_file_folder)
                .apply {

            makeFilesGridView.adapter = this;
        }

        return root;

    }

    /**
     * 异步加载Ftp文件列表
     */
    private fun loadFiles(fileId:Long?):Unit{

        fileApi.fileList(fileId?:1)
                .enqueue(object: Callback<ResponseEntity<FtpFile>> {

            override fun onResponse(call: Call<ResponseEntity<FtpFile>>, response: Response<ResponseEntity<FtpFile>>) {

                viewModel.rawFiles?.value?.clear()

                val files = response.body()?.rows as MutableList<FtpFile>
                if(files!=null)  viewModel.setFiles(files)


            }

            override fun onFailure(call: Call<ResponseEntity<FtpFile>>, t: Throwable) {
                //TODO("Not yet implemented")
                Log.e("loadFiles failure!",t.message)
            }
        })

    }


    inner class FtpFileArrayAdapter( @NonNull context :Context, @LayoutRes  resource:Int) : ArrayAdapter<FtpFile>(context,resource) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val ftpFile = getItem(position)
            //LayoutInflater.from(context)
            val ll =  layoutInflater.inflate(R.layout.widget_file_view,null) as ConstraintLayout

            //set file name
            ll.findViewById<TextView>(R.id.widget_file_view_name)
                    .apply {
                        this.text = ftpFile.name
                    }

            //set file image
            ll.findViewById<ImageView>(R.id.widget_file_view_image)
                    .apply {
                        when(ftpFile.type){
                            "1" -> {
                                this.setImageDrawable(resources.getDrawable(R.drawable.folder,null))

                                setOnClickListener({
                                    v ->
                                    currentFolderIdViewModel.stack.push(ftpFile.id)
                                    loadFiles(ftpFile.id)

                                })
                            }
                            "2" -> /* file */ this.setImageDrawable(resources.getDrawable(R.drawable.file,null))
                            }
                        }
            return ll
        }
    }

    override fun onStart() {
        super.onStart()

      loadFiles(1)

    }

}


class  StackViewModel<T>():ViewModel(){

    val stack:LinkedList<T> = LinkedList();



}



/**
 *  make files' view model
 */
class MakeFilesViewModel:ViewModel(){


     val rawFiles = MutableLiveData<MutableList<FtpFile>>()

    init{
         rawFiles.value?:ArrayList<FtpFile>().apply { rawFiles.value = this }
    }


    fun addFile(file:FtpFile){

        rawFiles.value?.add(file)
        rawFiles.value = (rawFiles.value)
    }

    fun addFiles(vararg  files:FtpFile){

        for(f in files)
            rawFiles.value?.add(f)

        rawFiles.value = rawFiles.value
    }

    //如何使用var args
    fun setFiles(files:MutableList<FtpFile> ){
        rawFiles.value = files
    }



}
