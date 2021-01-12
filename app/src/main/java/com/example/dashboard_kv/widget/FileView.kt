package com.example.dashboard_kv.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


/**
 * 文件和文件夹的自定义视图
 */
class FileView(context:Context) : LinearLayout(context) {

    private lateinit var imgSrouce:String
    private lateinit var fileName:String

    private lateinit var image:ImageView
    private lateinit var tvFileName:TextView

    constructor(context: Context,attributeSet: AttributeSet):this(context){

        //this.orientation = VERTICAL
        //this.layoutParams.width = WRAP_CONTENT
        //this.layoutParams.height = WRAP_CONTENT

        val ta = context.obtainStyledAttributes(attributeSet,
                com.example.dashboard_kv.R.styleable.FileView, 0, 0)

        //can set default value for view widget
        //imgSrouce = ta.getString(com.example.dashboard_kv.R.styleable.FileView_imgSource)



        image = ImageView(context)
            .apply {

                //this.layoutParams.width = WRAP_CONTENT
                //this.layoutParams.height = WRAP_CONTENT

                minimumWidth =50
                maxWidth = 70
                minimumHeight = 50
                maxHeight = 70
                scaleType = ImageView.ScaleType.FIT_CENTER

                val img  = resources.getDrawable(com.example.dashboard_kv.R.drawable.file) as BitmapDrawable
                setImageDrawable(img)

            }.also {
                addView(it)
            }

//        tvFileName = TextView(context)
//            .apply {
//                 text = ta.getString(com.example.dashboard_kv.R.styleable.FileView_fileName)
//                width = WRAP_CONTENT
//                height = WRAP_CONTENT
//            }.also {
//                this.addView(it)
//            }

    }


    private fun getMeasurementSize(measureSpec: Int, defaultSize: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        return when (mode) {
            MeasureSpec.EXACTLY -> size
            MeasureSpec.AT_MOST -> Math.min(defaultSize, size)
            MeasureSpec.UNSPECIFIED -> defaultSize
            else -> defaultSize
        }
    }

     val  DEFAULT_SIZE =560

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
       // setMeasuredDimension(100,100)


        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /*
        val width = getMeasurementSize(widthMeasureSpec, DEFAULT_SIZE)
        val height = getMeasurementSize(
            heightMeasureSpec,
            DEFAULT_SIZE
        )
        setMeasuredDimension(width, height)

         */
    }

    override fun addViewInLayout(
        child: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ): Boolean {
        return super.addViewInLayout(child, index, params)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)

    }


}