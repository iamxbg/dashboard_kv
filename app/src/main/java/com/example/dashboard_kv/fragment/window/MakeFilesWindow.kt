package com.example.dashboard_kv.fragment.window

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.FtpFile

const val makeFileTitle ="工艺文件"
const val makeFileWindowId ="make_file_window"
class MakeFilesWindow: BaseWindow(makeFileTitle, makeFileWindowId){

    private lateinit  var makeFilesGridView: GridView;

    private lateinit var root: LinearLayout;

    private lateinit var viewModel:MakeFilesViewModel;

    override var rootLayoutId: Int
        get() = R.layout.fragment_make_files
        set(value) {}



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =  ViewModelProvider(this).get(MakeFilesViewModel::class.java)
                .also {

                    val makeFilesObserver = Observer<List<FtpFile>> {
                        files ->
                        //bing to recycleView's adapter

                        Log.d("file-size",files?.size.toString())
                        ( makeFilesGridView?.adapter as ArrayAdapter<FtpFile>).apply {


                            this.clear()
                            this.addAll(files)


                        }
                    }

                    //observer
                    it.getFiles().observe(viewLifecycleOwner
                            ,makeFilesObserver)
                }

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root  = super.onCreateView(inflater, container, savedInstanceState) as LinearLayout


        root.findViewById<Button>(R.id.testBtn).setOnClickListener({
            v->
            val f1 = FtpFile("",111,"","","","","",12,"","",
                    "",1,"","","","Test-1","","",111,"","",null,"1","",
                    "")

            val f2 = FtpFile("",111,"","","","","",12,"","",
                    "",2,"","","","Test-2","","",111,"","",null,"2","",
                    "")


            viewModel.addFile(f1)
            viewModel.addFile(f2)
        })

        makeFilesGridView = root.findViewById(R.id.grid_view_task_files);

        FtpFileArrayAdapter(requireContext(), R.layout.widget_make_file_folder)
                .apply {

            makeFilesGridView.adapter = this;
        }

        return root;

    }


    inner class FtpFileArrayAdapter( @NonNull context :Context, @LayoutRes  resource:Int) : ArrayAdapter<FtpFile>(context,resource) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            Log.w("Array Adapter getView:","enter!")

            val ftpFile = getItem(position)
            val ll =  LayoutInflater.from(context).inflate(R.layout.widget_file_view,null) as ConstraintLayout

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
                                    Toast.makeText(context,"INSIDE",Toast.LENGTH_LONG).show()
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


    }

}

/**
 *  make files' view model
 */
class MakeFilesViewModel:ViewModel(){


     val rawFiles = MutableLiveData<MutableList<FtpFile>>()

    fun getFiles():MutableLiveData<MutableList<FtpFile>> {
        return rawFiles
    }

    fun addFile(file:FtpFile){
        //val newFiles = rawFiles.value?.plus(file)

        val files:MutableList<FtpFile> = rawFiles.value?:ArrayList<FtpFile>();
        files.add(file)
        rawFiles.postValue(files)
    }

}
