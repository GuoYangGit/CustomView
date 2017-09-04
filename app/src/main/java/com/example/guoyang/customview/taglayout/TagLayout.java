package com.example.guoyang.customview.taglayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoyang on 2017/9/4.
 */

public class TagLayout extends ViewGroup {
    private List<List<View>> mChildViews = new ArrayList<>();
    private BaseTagAdapter mAdapter;

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mChildViews.clear();
        //for循环测量子view
        int childCount = getChildCount();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getPaddingBottom() + getPaddingTop();
        int lineWidth = getPaddingLeft() + getPaddingRight();
        List<View> childViews = new ArrayList<>();
        mChildViews.add(childViews);
        int maxHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //获取子view的宽高，因为会调用子view的onMeasure()方法
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //margin
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            if (lineWidth + (childView.getMeasuredWidth() + params.rightMargin + params.leftMargin) > width) {
                height += maxHeight;
                lineWidth = childView.getMeasuredWidth() + params.rightMargin + params.leftMargin;
                childViews = new ArrayList<>();
                mChildViews.add(childViews);

            } else {
                lineWidth += childView.getMeasuredWidth() + params.rightMargin + params.leftMargin;
                maxHeight = Math.max(childView.getMeasuredHeight() + params.topMargin + params.bottomMargin, maxHeight);
            }
            childViews.add(childView);
        }
        height += maxHeight;
        //根据子view计算和指定自己的布局
        setMeasuredDimension(width, height);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top = getPaddingTop(), right, bottom;
        for (List<View> childViews : mChildViews) {
            left = getPaddingLeft();
            int maxHeight = 0;
            for (View childView : childViews) {
                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                left += params.leftMargin;
                int childTop = top + params.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = childTop + childView.getMeasuredHeight();
                childView.layout(left, childTop, right, bottom);
                left += childView.getMeasuredWidth() + params.rightMargin;
                int childHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                maxHeight = Math.max(maxHeight, childHeight);
            }
            top += maxHeight;
        }
    }

    public void setAdapter(BaseTagAdapter adapter) {
        if (adapter == null) throw new IllegalArgumentException("this adtapter is null");
        mAdapter = null;
        mAdapter = adapter;
        int childCount = mAdapter.getCount();
        for (int i = 0; i < childCount; i++) {
            View childView = mAdapter.getView(i, this);
            addView(childView);
        }
    }
}
