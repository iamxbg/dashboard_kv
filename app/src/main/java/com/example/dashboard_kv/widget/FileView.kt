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


/**
 * 文件和文件夹的自定义视图
 */
class FileView(context:Context) : LinearLayout(context) {

    private lateinit var imgSrouce:String
    private lateinit var fileName:String

    private lateinit var image:ImageView
    private lateinit var tvFileName:TextView

    @SuppressLint("ResourceAsColor")
    constructor(context: Context, attributeSet: AttributeSet):this(context){

        orientation = VERTICAL

        this.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

        val ta = context.obtainStyledAttributes(attributeSet,
                com.example.dashboard_kv.R.styleable.FileView, 0, 0)

        image = ImageView(context)
            .apply {

                this.layoutParams = LayoutParams(50,50)

                scaleType = ImageView.ScaleType.FIT_CENTER
                val img  = resources.getDrawable(R.drawable.file) as BitmapDrawable
                setImageDrawable(img)

            }.also {
                this.addView(it)
            }

        tvFileName = TextView(context)
            .apply {
                layoutParams = LayoutParams(50,20)
                background  = resources.getDrawable(R.drawable.default_background)
                //text = ta.getString(com.example.dashboard_kv.R.styleable.FileView_fileName)
                text = "Hello World"
                textSize = 30.0f
                setTextColor(R.color.lb_default_search_color)

            }.also {
                this.addView(it)
            }

    }







}