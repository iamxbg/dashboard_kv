package com.example.dashboard_kv.fragment.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.dashboard_kv.R

class MakeFilesFragment:Fragment() {

    private lateinit  var makeFilesGridView: GridView ;

    private lateinit var root:LinearLayout;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)

        var root = inflater.inflate(R.layout.fragment_make_files,container);

        makeFilesGridView = root.findViewById(R.id.grid_view_task_files);

        return root;

    }

    override fun onStart() {
        super.onStart()


        var adapter :ArrayAdapter<String> = ArrayAdapter(requireContext(),R.layout.widget_make_file_folder)

            adapter.add("文件夹-1")
            adapter.add("文件夹-2")
            adapter.add("文件夹-3")
            adapter.add("文件夹-4")
            adapter.add("文件夹-5")
            adapter.add("文件夹-6")
            adapter.add("文件夹-7")
            adapter.add("文件夹-8")
            adapter.add("文件夹-9")
            adapter.add("文件夹-10")

        makeFilesGridView.adapter = adapter;

        val onClick = fun(parent:AdapterView<ArrayAdapter<String>>, v:View, position:Int,id:Long) :Unit{


        }


    }

}

