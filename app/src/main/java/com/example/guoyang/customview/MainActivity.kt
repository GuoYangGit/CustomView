package com.example.guoyang.customview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.example.guoyang.customview.KGSlidingMenu.KGSlidingActivity
import com.example.guoyang.customview.QQSlidingMenu.QQSlidingActivity
import com.example.guoyang.customview.colorTrackTextView.ColorTrackActivity
import com.example.guoyang.customview.letterSideBar.LetterSideBarActivity
import com.example.guoyang.customview.lockpatternView.LockPattenActivity
import com.example.guoyang.customview.progressBarView.ProgressBarActivity
import com.example.guoyang.customview.qqStepView.QQStepActivity
import com.example.guoyang.customview.ratingBarView.RatingBarActivity
import com.example.guoyang.customview.shapeView.ShapeViewActivity
import com.example.guoyang.customview.statusbar.StatusBarActivity
import com.example.guoyang.customview.taglayout.TagLayoutActivity
import com.example.guoyang.customview.verticaldraglistview.VerticalDragActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun QQStepview(view: View) {
        startActivity<QQStepActivity>()
    }

    fun ColorTrackTextview(view: View) {
        startActivity<ColorTrackActivity>()
    }

    fun ProgressBarView(view: View) {
        startActivity<ProgressBarActivity>()
    }

    fun ShapeView(view: View) {
        startActivity<ShapeViewActivity>()
    }

    fun RatingBar(view: View) {
        startActivity<RatingBarActivity>()
    }

    fun LetterSideBar(view: View) {
        startActivity<LetterSideBarActivity>()
    }

    fun Laglatyout(view: View) {
        startActivity<TagLayoutActivity>()
    }

    fun KGSlidingMenu(view: View) {
        startActivity<KGSlidingActivity>()
    }

    fun QQSlidingMenu(view: View) {
        startActivity<QQSlidingActivity>()
    }

    fun VerticalDragListView(view: View) {
        startActivity<VerticalDragActivity>()
    }

    fun LockPattenView(view: View) {
        startActivity<LockPattenActivity>()
    }

    fun Statusbar(view:View){
        startActivity<StatusBarActivity>()
    }
}
