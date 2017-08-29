package com.example.guoyang.customview.progressBarView;

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
 * Created by guoyang on 2017/8/29.
 */

public class ProgressBarView extends View {
    private int mOuterColor = Color.BLUE;
    private int mInnerColor = Color.RED;
    private int mBorderWidth = 20;
    private int mProgressTextSize;
    private int mProgressTextColor;
    private Paint mOutPaint, mInnerPaint, mTextPaint;

    private int mProgressSize = 0;
    private int mMaxSize = 100;

    public ProgressBarView(Context context) {
        this(context, null);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarView);
        mOuterColor = array.getColor(R.styleable.ProgressBarView_progressOuterColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.ProgressBarView_progressInnerColor, mInnerColor);
        mBorderWidth = (int) array.getDimension(R.styleable.ProgressBarView_progressBorderWidth, mBorderWidth);
        mProgressTextColor = array.getColor(R.styleable.ProgressBarView_progressTextColor, mProgressTextColor);
        mProgressTextSize = array.getDimensionPixelSize(R.styleable.ProgressBarView_progressTextSize, mProgressTextSize);
        array.recycle();

        //外圆弧画笔
        mOutPaint = new Paint();
        //抗锯齿
        mOutPaint.setAntiAlias(true);
        //画笔的宽度
        mOutPaint.setStrokeWidth(mBorderWidth);
        //画笔的颜色
        mOutPaint.setColor(mOuterColor);
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
        //画笔空心
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextSize(mProgressTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在布局文件中可能是 wrap_content 宽度高度不一致取最小值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //设置view为矩形，选取最小的值为宽高
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(mBorderWidth, mBorderWidth, getWidth() - mBorderWidth, getHeight() - mBorderWidth);
        canvas.drawArc(rectF, 0, 360, false, mOutPaint);
        //2.画内圆弧，百分比，用户设置
        if (mProgressSize == 0) return;
        float percent = (float) mProgressSize / mMaxSize;
        canvas.drawArc(rectF, 90, percent * 360, false, mInnerPaint);
        //3.画文字
        String stepText = mProgressSize + "%";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(stepText, 0, stepText.length(), textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;
        //基线 baseLine
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mTextPaint);
    }


    public synchronized void setProgressSize(int progressSize) {
        this.mProgressSize = progressSize;
        //不断的绘制
        invalidate();
    }
}
