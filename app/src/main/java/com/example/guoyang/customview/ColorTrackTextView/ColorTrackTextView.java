package com.example.guoyang.customview.ColorTrackTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.guoyang.customview.R;

/**
 * Created by guoyang on 2017/8/28.
 */

public class ColorTrackTextView extends TextView {
    private Paint mOriginPaint;
    private Paint mChangePaint;

    private float mCurrentProgress = 0.0f;

    private Direction mDirection = Direction.LETF_TO_RIGHT;
    public enum Direction{
        LETF_TO_RIGHT,RIGHT_TO_LEFT;
    };

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);

        int originColor = array.getColor(R.styleable.ColorTrackTextView_originColor, getTextColors().getDefaultColor());
        int changeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, getTextColors().getDefaultColor());

        mOriginPaint = getPaintByColor(originColor);
        mChangePaint = getPaintByColor(changeColor);

        array.recycle();
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;
    }


    //利用clipRect() 可以裁剪 左边用一个画笔去画，右边用另一个画笔去画
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        //根据进度把中间值算出来
        int middle = (int) (mCurrentProgress * getWidth());
        if (mDirection == Direction.LETF_TO_RIGHT){
            //来画不变色的字体
            drawText(canvas,mChangePaint,0,middle);
            //绘制变色的字体
            drawText(canvas,mOriginPaint,middle,getWidth());
        }else {
            //来画不变色的字体
            drawText(canvas,mChangePaint,getWidth()-middle,getWidth());
            //绘制变色的字体
            drawText(canvas,mOriginPaint,0,getWidth()-middle);
        }
    }

    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        //canvas.clipRect()裁剪区域
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);

        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        //获取字体的宽度
        int x = getWidth() / 2 - bounds.width() / 2;
        //baseLine
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }

    public void setDirection(Direction direction){
        this.mDirection = direction;
    }

    public void setCurrentProgress(float currentProgress){
        this.mCurrentProgress = currentProgress;
        invalidate();
    }

    public void setChangeColor(int changeColor){
        this.mChangePaint.setColor(changeColor);
    }

    public void setOriginColor(int originColor){
        this.mOriginPaint.setColor(originColor);
    }
}
