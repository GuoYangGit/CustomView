package com.example.guoyang.customview.ratingBarView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.guoyang.customview.R;

/**
 * Created by guoyang on 2017/8/29.
 */

public class RatingBarView extends View {
    private Bitmap mStarNormalBitmap, mStarFousBitmap;
    private int mGradeNumber = 5;
    private int mCurrentGrade = 0;


    public RatingBarView(Context context) {
        this(context, null);
    }

    public RatingBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        int starNormalId = array.getResourceId(R.styleable.RatingBarView_starNormal, 0);
        int starFocusId = array.getResourceId(R.styleable.RatingBarView_starFocus, 0);
        if (starNormalId == 0) {
            throw new RuntimeException("请设置属性 starNormal");
        }
        if (starFocusId == 0) {
            throw new RuntimeException("请设置属性 starFocus");
        }
        mStarFousBitmap = BitmapFactory.decodeResource(getResources(), starFocusId);
        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = mStarNormalBitmap.getHeight();
        int width = mStarNormalBitmap.getWidth() * mGradeNumber;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mGradeNumber; i++) {
            int x = i * mStarNormalBitmap.getWidth();
            int top = getPaddingTop();
            if (mCurrentGrade>i){
                canvas.drawBitmap(mStarFousBitmap, x, top, null);
            }else {
                canvas.drawBitmap(mStarNormalBitmap, x, top, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                float moveX = event.getX();//获取手指当前按下x的位置
                //event.getRawX()获取屏幕X的位置
                int currentGrade = (int) (moveX/mStarNormalBitmap.getWidth()+1);
                if (currentGrade<0) currentGrade = 0;
                if (currentGrade>mGradeNumber) currentGrade = mGradeNumber;
                if (currentGrade == mCurrentGrade){
                    return true;
                }
                mCurrentGrade = currentGrade;
                invalidate();
                break;
        }
        return true;
    }
}
