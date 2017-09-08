package com.example.guoyang.customview.statusbar

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * Created by guoyang on 2017/9/8.
 */
class MyScrollView:ScrollView{
    var mListener:ChangeListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (mListener != null){
            mListener!!.onScroll(l,t,oldl,oldt)

        }
    }

    interface ChangeListener{
        fun onScroll(l:Int,t:Int,oldl: Int,oldt: Int)
    }

    fun setOnChangeListener(scrollChangeListener: ChangeListener){
        mListener = scrollChangeListener
    }
}