package com.example.guoyang.customview.lockpatternView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.ArrayList

/**
 * Created by guoyang on 2017/9/7.
 */

class LockPattenView : View {
    // 二维数组初始化，int[3][3]
    private var mPoints: Array<Array<Point?>> = Array(3) { Array<Point?>(3, { null }) }
    private var mIsInit = false
    // 外圆的半径
    private var mDotRadius: Int = 0

    //画笔
    private lateinit var mLinePaint: Paint
    private lateinit var mPressedPaint: Paint
    private lateinit var mErrorPaint: Paint
    private lateinit var mNormalPaint: Paint
    private lateinit var mArrowPaint: Paint

    //颜色
    private var mOuterPressedColor = 0xff8cbad8.toInt()
    private var mInnerPressedColor = 0xff0596f6.toInt()
    private var mOuterNormalColor = 0xffd9d9d9.toInt()
    private var mInnerNormalColor = 0xff929292.toInt()
    private var mOuterErrorColor = 0xff901032.toInt()
    private var mInnerErrorColor = 0xffea0945.toInt()

    //按下的时候是否在一个点上
    private var mIsTouchPoint = false
    //选中的点
    private var mSelectPoints = ArrayList<Point>()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        if (!mIsInit) {
            initDot()
            initPaint()
            mIsInit = true
        }
        drawShow(canvas)
    }

    private fun drawShow(canvas: Canvas) {
        for (i in 0..2) {
            for (point in mPoints[i]) {

                if (point!!.statusIsPressed()){
                    //先绘制外圆
                    mPressedPaint.color = mOuterPressedColor
                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat()
                            , mDotRadius.toFloat(), mPressedPaint)
                    mPressedPaint.color = mInnerPressedColor
                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat()
                            , mDotRadius / 6.toFloat(), mPressedPaint)
                }

                if (point.statusIsError()){
                    //先绘制外圆
                    mErrorPaint.color = mOuterErrorColor
                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat()
                            , mDotRadius.toFloat(), mErrorPaint)
                    mErrorPaint.color = mInnerErrorColor
                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat()
                            , mDotRadius / 6.toFloat(), mErrorPaint)
                }

                if (point.statusIsNormal()){
                    //先绘制外圆
                    mNormalPaint.color = mOuterNormalColor
                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat()
                            , mDotRadius.toFloat(), mNormalPaint)
                    mNormalPaint.color = mInnerNormalColor
                    canvas.drawCircle(point.centerX.toFloat(), point.centerY.toFloat()
                            , mDotRadius / 6.toFloat(), mNormalPaint)
                }
            }
        }
    }

    /**
     * 初始化画笔
     * 3个点状态的画笔,线的画笔,箭头的画笔
     */
    private fun initPaint() {
        // 线的画笔
        mLinePaint = Paint()
        mLinePaint.color = mInnerPressedColor
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.isAntiAlias = true
        mLinePaint.strokeWidth = (mDotRadius / 9).toFloat()
        // 按下的画笔
        mPressedPaint = Paint()
        mPressedPaint.style = Paint.Style.STROKE
        mPressedPaint.isAntiAlias = true
        mPressedPaint.strokeWidth = (mDotRadius / 6).toFloat()
        // 错误的画笔
        mErrorPaint = Paint()
        mErrorPaint.style = Paint.Style.STROKE
        mErrorPaint.isAntiAlias = true
        mErrorPaint.strokeWidth = (mDotRadius / 6).toFloat()
        // 默认的画笔
        mNormalPaint = Paint()
        mNormalPaint.style = Paint.Style.STROKE
        mNormalPaint.isAntiAlias = true
        mNormalPaint.strokeWidth = (mDotRadius / 9).toFloat()
        // 箭头的画笔
        mArrowPaint = Paint()
        mArrowPaint.color = mInnerPressedColor
        mArrowPaint.style = Paint.Style.FILL
        mArrowPaint.isAntiAlias = true
    }

    /**
     * 初始化点
     */
    private fun initDot() {
        //九个宫格，存入集合 Point[3][3]
        //不断绘制的时候这几个点都有状态，而且后面肯定需要回调密码，点都有下标，点肯定是一个对象
        var width = this.width
        var height = this.height
        //兼容横竖屏
        var offsetX = 0
        var offsetY = 0
        when (height > width) {
            true -> offsetY = (height - width) / 2
            false -> {
                offsetX = (width - height) / 2
                width = height
            }
        }
        var squareWidth = width / 3

        mDotRadius = width / 12
        //计算中心的位置
        mPoints[0][0] = Point(offsetX + squareWidth / 2, offsetY + squareWidth / 2, 0)
        mPoints[0][1] = Point(offsetX + squareWidth * 3 / 2, offsetY + squareWidth / 2, 1)
        mPoints[0][2] = Point(offsetX + squareWidth * 5 / 2, offsetY + squareWidth / 2, 2)
        mPoints[1][0] = Point(offsetX + squareWidth / 2, offsetY + squareWidth * 3 / 2, 3)
        mPoints[1][1] = Point(offsetX + squareWidth * 3 / 2, offsetY + squareWidth * 3 / 2, 4)
        mPoints[1][2] = Point(offsetX + squareWidth * 5 / 2, offsetY + squareWidth * 3 / 2, 5)
        mPoints[2][0] = Point(offsetX + squareWidth / 2, offsetY + squareWidth * 5 / 2, 6)
        mPoints[2][1] = Point(offsetX + squareWidth * 3 / 2, offsetY + squareWidth * 5 / 2, 7)
        mPoints[2][2] = Point(offsetX + squareWidth * 5 / 2, offsetY + squareWidth * 5 / 2, 8)
    }

    //手指触摸的位置
    private var mMovingX = 0f
    private var mMovingY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mMovingX = event.x
        mMovingY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val point = point
                if (point != null){
                    mIsTouchPoint = true
                    mSelectPoints.add(point)
                    point.setStatusPressed()
                }
            }
            MotionEvent.ACTION_MOVE -> {

            }
            MotionEvent.ACTION_UP -> {

            }
        }
        invalidate()
        return true
    }

    /**
     * 获取按下的点
     * @return 当前按下的点
     */
    private val point: Point?
        get() {
            for (i in 0..2) {
                mPoints[i]
                        .filter {
                            checkInRound(it!!.centerX.toFloat(), it.centerY.toFloat(),
                                    mDotRadius.toFloat(), mMovingX, mMovingY)
                        }
                        .forEach { return it }
            }
            return null
        }

    private fun checkInRound(sx: Float, sy: Float, r: Float, x: Float, y: Float): Boolean =
            Math.sqrt(((sx - x) * (sx - x) + (sy - y) * (sy - y)).toDouble()) < r

    /**
     * 宫格的类
     */
    class Point(var centerX: Int, var centerY: Int, var index: Int) {

        private val STATUS_NORMAL = 1
        private val STATUS_PRESSED = 2
        private val STATUS_ERROR = 3
        //当前的点的状态
        private var status = STATUS_NORMAL

        fun setStatusPressed(){
            status = STATUS_PRESSED
        }

        fun setStatusNormal(){
            status = STATUS_NORMAL
        }

        fun setStatusError(){
            status = STATUS_ERROR
        }

        fun statusIsPressed():Boolean = status == STATUS_PRESSED

        fun statusIsNormal():Boolean = status == STATUS_NORMAL

        fun statusIsError():Boolean = status == STATUS_ERROR

    }
}
