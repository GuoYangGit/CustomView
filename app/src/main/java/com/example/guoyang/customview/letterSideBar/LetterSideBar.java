package com.example.guoyang.customview.letterSideBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.guoyang.customview.R;


/**
 * Created by guoyang on 2017/8/30.
 */

public class LetterSideBar extends View {
    private Paint mTextPaint;
    private Paint mCurrentPaint;
    public String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private String mCurrentTouchLetter;

    private int mTextColor = Color.BLUE;
    private int mCurrentColor = Color.RED;
    private int mTextSize = 12;

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        mTextColor = array.getColor(R.styleable.LetterSideBar_letterSideBarTextColor, mTextColor);
        mCurrentColor = array.getColor(R.styleable.LetterSideBar_letterSideBarCurrentColor, mCurrentColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.LetterSideBar_letterSideBarTextSize, mTextSize);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(sp2px(mTextSize));
        mTextPaint.setColor(mTextColor);

        mCurrentPaint = new Paint();
        mCurrentPaint.setAntiAlias(true);
        mCurrentPaint.setTextSize(sp2px(mTextSize));
        mCurrentPaint.setColor(mCurrentColor);
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int textWidth = (int) mTextPaint.measureText("A");
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = getPaddingLeft() + getPaddingRight() + textWidth;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
        for (int i = 0; i < letters.length; i++) {
            int letterCenterY = i * itemHeight + itemHeight / 2 + getPaddingTop();
            Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            int baseLine = letterCenterY + dy;
            int x = (int) (getWidth() / 2 - (mTextPaint.measureText(letters[i]) / 2));
            if (letters[i].equals(mCurrentTouchLetter)) {
                canvas.drawText(letters[i], x, baseLine, mCurrentPaint);
            } else {
                canvas.drawText(letters[i], x, baseLine, mTextPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                float currentMoveY = event.getY();
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
                int currentPosition = (int) ((currentMoveY - getPaddingTop()) / itemHeight);
                if (currentPosition < 0) currentPosition = 0;
                if (currentPosition >= letters.length) currentPosition = letters.length - 1;
                if (letters[currentPosition] == mCurrentTouchLetter) return true;
                mCurrentTouchLetter = letters[currentPosition];
                if (mListener != null) mListener.touch(mCurrentTouchLetter);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mListener != null) mListener.unTouch();
        }
        return true;
    }

    private LetterTouchListener mListener;

    public void setOnLetterTouchListener(LetterTouchListener listener) {
        this.mListener = listener;
    }

    public interface LetterTouchListener {
        void touch(String letter);

        void unTouch();
    }
}
