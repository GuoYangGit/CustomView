package com.example.guoyang.customview.statusbar

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

import com.example.guoyang.customview.R
import kotlinx.android.synthetic.main.activity_status_bar.*;
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find

class StatusBarActivity : AppCompatActivity() {
    private var mViewHeight: Int = 0
    private var mTitleHeight: Int = 0
    private var mScrollview: MyScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_bar)
        StatusBar.getInstances(this).transparentStatusBar()
        titlebar.background.alpha = 0
        imageview.post {
            mViewHeight = imageview.height
        }
        titlebar.post {
            mTitleHeight = titlebar.height
        }
        mScrollview = find(R.id.scrollView)
        mScrollview?.setOnChangeListener(object : MyScrollView.ChangeListener {
            override fun onScroll(l: Int, t: Int, oldl: Int, oldt: Int) {
                if (mViewHeight == 0) return
                var height = mViewHeight-mTitleHeight
                var alpha = t/height.toFloat()
                if (alpha<=0){
                    alpha = 0.toFloat()
                }
                if(alpha>=1){
                    alpha = 1.toFloat()
                }
                titlebar.background.alpha = (alpha*255).toInt()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBar.getInstances(this).destroy()
    }
}
