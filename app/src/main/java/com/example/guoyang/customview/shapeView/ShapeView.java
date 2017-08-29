package com.example.guoyang.customview.shapeView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by guoyang on 2017/8/29.
 */

public class ShapeView extends View{
    public enum Shape{
        Circls,Square,Triangle
    }
    private Shape mCurrentShape = Shape.Triangle;
    private Paint mPaint;
    private Path mPath;

    public ShapeView(Context context) {
        this(context,null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在布局文件中可能是 wrap_content 宽度高度不一致取最小值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //设置view为矩形，选取最小的值为宽高
        setMeasuredDimension(width>height?height:width,width>height?height:width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mCurrentShape){
            case Circls:
                //画圆
                int center = getWidth()/2;
                mPaint.setColor(Color.YELLOW);
                canvas.drawCircle(center,center,center,mPaint);
                break;
            case Square:
                //画正方形
                mPaint.setColor(Color.BLUE);
                canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);
                break;
            case Triangle:
                //画三角
                mPaint.setColor(Color.RED);
                if (mPath == null) {
                    mPath = new Path();
                    mPath.moveTo(getWidth()/2,0);
                    mPath.lineTo(0, (float) ((getWidth()/2)*Math.sqrt(3)));
                    mPath.lineTo(getWidth(),(float) ((getWidth()/2)*Math.sqrt(3)));
//                    mPath.lineTo(getWidth(),getHeight());
                    mPath.close();
                }

                canvas.drawPath(mPath,mPaint);
                break;
        }
    }

    public void exchange(){
        switch (mCurrentShape){
            case Circls:
                mCurrentShape = Shape.Square;
                break;
            case Square:
                mCurrentShape = Shape.Triangle;
                break;
            case Triangle:
                mCurrentShape = Shape.Circls;
                break;
        }

        invalidate();
    }
}
