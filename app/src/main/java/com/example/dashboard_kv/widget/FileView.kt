package com.example.dashboard_kv.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dashboard_kv.R
import com.example.dashboard_kv.api.FtpFile


/**
 * 文件和文件夹的自定义视图
 */
@Deprecated("暂不采用自定义视图，不够熟悉对应的设置方式")
class FileView(val rawFile:FtpFile,context:Context) : LinearLayout(context) {



    private lateinit var imgSrouce:String
    private lateinit var fileName:String

    //private lateinit var image:ImageView
   // private lateinit var tvFileName:TextView

    @SuppressLint("ResourceAsColor")
    constructor(rawFile:FtpFile,context: Context, attributeSet: AttributeSet):this(rawFile,context){

        orientation = VERTICAL
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

        val ta = context.obtainStyledAttributes(attributeSet,
                com.example.dashboard_kv.R.styleable.FileView, 0, 0)

       ImageView(context)
            .apply {

                //set basic styles
                layoutParams = LayoutParams(150,150)
                scaleType = ImageView.ScaleType.FIT_CENTER

                //set file icon
                when(rawFile.type){
                    "1" -> (resources.getDrawable(R.drawable.folder) as BitmapDrawable)
                            .apply { setImageDrawable(this) }
                    "2" -> (resources.getDrawable(R.drawable.file) as BitmapDrawable)
                            .apply { setImageDrawable(this) }
                }

            }.also {
                this.addView(it)
            }

        TextView(context)
            .apply {
                layoutParams = LayoutParams(150,50)
                background  = resources.getDrawable(R.drawable.default_background)
                //text = ta.getString(com.example.dashboard_kv.R.styleable.FileView_fileName)
                text = rawFile.name
                textSize = 10f
                setTextColor(R.color.sys_blue)

            }.also {
                this.addView(it)
            }

    }







}