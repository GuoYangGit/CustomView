package com.example.guoyang.customview.QQStepView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.guoyang.customview.R;

/**
 * Created by guoyang on 2017/8/28.
 */

public class QQStepView extends View{
    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mBorderWidth = 20;
    private int mStepTextSize;
    private int mStepTextColor;
    private Paint mOutPaint,mInnerPaint,mTextPaint;

    //总共步数
    private int mMaxStep = 0;
    //当前步数
    private int mCurrentStep = 0;

    public QQStepView(Context context) {
        this(context,null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.分析效果
        //2.确定自定义属性，便携attrs.xml
        //3.在布局中使用
        //4.在自定义view中获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor,mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor,mInnerColor);
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth,mBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize,mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor,mStepTextColor);
        array.recycle();

        //外圆弧画笔
        mOutPaint = new Paint();
        //抗锯齿
        mOutPaint.setAntiAlias(true);
        //画笔的宽度
        mOutPaint.setStrokeWidth(mBorderWidth);
        //画笔的颜色
        mOutPaint.setColor(mOuterColor);
        //画笔冒样式
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        //画笔空心
        mOutPaint.setStyle(Paint.Style.STROKE);

        //内圆弧画笔
        mInnerPaint = new Paint();
        //抗锯齿
        mInnerPaint.setAntiAlias(true);
        //画笔的宽度
        mInnerPaint.setStrokeWidth(mBorderWidth);
        //画笔的颜色
        mInnerPaint.setColor(mInnerColor);
        //画笔冒样式
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        //画笔空心
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);
    }

    //5.onMeasure()
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在布局文件中可能是 wrap_content 宽度高度不一致取最小值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //设置view为矩形，选取最小的值为宽高
        setMeasuredDimension(width>height?height:width,width>height?height:width);
    }

    //6.画内圆弧，外圆弧，文字
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.画外圆弧
//        int center = getWidth()/2;
//        int radius = getWidth()/2-mBorderWidth;
//        RectF rectF = new RectF(center-radius,center-radius,center+radius,center+radius);
        RectF rectF = new RectF(mBorderWidth,mBorderWidth,getWidth()-mBorderWidth,getHeight()-mBorderWidth);
        canvas.drawArc(rectF,135,270,false,mOutPaint);
        //2.画内圆弧，百分比，用户设置
        if (mMaxStep == 0) return;
        float sweepAngle = (float) mCurrentStep/mMaxStep;
        canvas.drawArc(rectF,135,sweepAngle*270,false,mInnerPaint);
        //3.画文字
        String stepText = mCurrentStep+"步";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(stepText,0,stepText.length(),textBounds);
        int dx = getWidth()/2 - textBounds.width()/2;
        //基线 baseLine
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2 + dy;
        canvas.drawText(stepText,dx,baseLine,mTextPaint);
    }

    public synchronized void setMaxStep(int MaxStep){
        this.mMaxStep = MaxStep;
    }

    public synchronized void setCurrentStep(int CurrentStep){
        this.mCurrentStep = CurrentStep;
        //不断的绘制
        invalidate();
    }


}
