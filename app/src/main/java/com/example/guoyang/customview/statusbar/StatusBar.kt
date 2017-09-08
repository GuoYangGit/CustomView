package com.example.guoyang.customview.statusbar

import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

/**
 * Created by guoyang on 2017/9/8.
 */

class StatusBar private constructor(private var mActivity: Activity?) {
    private var mWindow: Window?
    private var mDecorView: ViewGroup?
    private var mContentView: ViewGroup?

    init {
        this.mWindow = mActivity!!.window
        this.mDecorView = mWindow!!.decorView as ViewGroup
        this.mContentView = mDecorView!!.findViewById(android.R.id.content) as ViewGroup
    }

    companion object {
        var instances: StatusBar? = null

        fun getInstances(activity: Activity?): StatusBar {
            if (activity == null) {
                throw IllegalArgumentException("activity不能为空")
            }
            if (instances == null) {
                synchronized(StatusBar::class.java) {
                    if (instances == null) {
                        instances = StatusBar(activity)
                    }
                }
            }
            return instances!!
        }
    }

    fun transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDecorView!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            mWindow!!.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWindow!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    fun colorparentStatusBar(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWindow!!.statusBarColor = color
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            var view = View(mActivity)
            var params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight())
            view.layoutParams = params
            view.setBackgroundColor(color)
            mDecorView!!.addView(view)
            var activityView = mContentView!!.getChildAt(0)
            activityView.fitsSystemWindows = true
        }
    }

    private fun getStatusBarHeight():Int{
        var resources = mActivity!!.resources
        var statusBarHeightId = resources.getIdentifier("status_bar_height","dimen","android")
        return resources.getDimensionPixelOffset(statusBarHeightId)
    }

    fun destroy(){
        mActivity = null
        mWindow = null
        mDecorView = null
        mContentView = null
        instances = null
    }
}
