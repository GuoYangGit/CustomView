package com.example.guoyang.customview.KGSlidingMenu;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.example.guoyang.customview.R;

/**
 * Created by guoyang on 2017/9/5.
 */

public class SlidingMenu extends HorizontalScrollView {
    private int mMenuWidth;
    private View mContentView;
    private View mMenuView;
    private GestureDetector mGestureDetector;
    private boolean mMenuIsOpen = false;
    private boolean isIntercept = false;


    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.KGSlidingMenu);
        float rightMargin = array.getDimension(R.styleable.KGSlidingMenu_KGSlidingMenuMarginRight, dip2px(context, 50));
        mMenuWidth = (int) (getScreenWidth(context) - rightMargin);
        array.recycle();
        mGestureDetector = new GestureDetector(context, mGestureListener);
    }

    private GestureDetector.OnGestureListener mGestureListener = new
            GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    //只关注快速滑动，只要快速滑动就会回调
                    //条件 打开的时候往右快速滑动切换（关闭），关闭的时候往左快速滑动切换（打开）
                    //快速往左边滑动是一个负数，快速往右边滑动是一个正数
                    if (mMenuIsOpen) {
                        //打开的时候往右快速滑动切换（关闭）
                        if (velocityX < 0) {
                            closeMenu();
                            return true;
                        }
                    } else {
                        //关闭的时候往左快速滑动切换（打开）
                        if (velocityX > 0) {
                            openMenu();
                            return true;
                        }
                    }
                    return super.onFling(e1,e2,velocityX,velocityY);
                }
            };

    @Override
    protected void onFinishInflate() {
        //布局解析完毕才会回调
        super.onFinishInflate();
        //指定宽高 1.内容页的宽度是屏幕的宽度
        //2. 菜单页的宽度是屏幕的宽度-右边一小部分的距离
        ViewGroup container = (ViewGroup) getChildAt(0);
        int childCount = container.getChildCount();
        if (childCount != 2) throw new RuntimeException("只能放置两个子view");
        mMenuView = container.getChildAt(0);
        ViewGroup.LayoutParams menuParams = mMenuView.getLayoutParams();
        menuParams.width = mMenuWidth;
        mMenuView.setLayoutParams(menuParams);
        mContentView = container.getChildAt(1);
        ViewGroup.LayoutParams contentParams = mContentView.getLayoutParams();
        contentParams.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(contentParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //初始化进来默认是关闭的
        scrollTo(mMenuWidth, 0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isIntercept = false;
        if (mMenuIsOpen){
            float currentX = ev.getX();
            if (currentX>mMenuWidth){
                closeMenu();
                isIntercept = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    //处理右边的缩放，左边的位移与透明度变化，需要不断的获取当前滚动的位置
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = 1f * l / mMenuWidth;
        //右边的缩放
        float rightScale = 0.8f + 0.2f * scale;
        ViewCompat.setPivotX(mContentView, 0);
        ViewCompat.setPivotY(mContentView, mContentView.getMeasuredHeight() / 2);
        ViewCompat.setScaleX(mContentView, rightScale);
        ViewCompat.setScaleY(mContentView, rightScale);
        //左边的位移与透明度变化
        float alpha = 0.5f + (1 - scale) * 0.5f;
        ViewCompat.setAlpha(mMenuView, alpha);
        float leftScale = 0.7f + (1 - scale) * 0.3f;
        ViewCompat.setScaleX(mMenuView, leftScale);
        ViewCompat.setScaleY(mMenuView, leftScale);
        ViewCompat.setTranslationX(mMenuView, 0.2f * l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isIntercept) return true;
        if (mGestureDetector.onTouchEvent(ev)) return true;
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //手指抬起是二选一，要不关闭要不打开,根据当前滚动的距离来判断
            int currentScrollX = getScrollX();
            if (currentScrollX > mMenuWidth / 2) {
                //关闭
                closeMenu();
            } else {
                //打开
                openMenu();
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单 滚动到0的位置
     */
    private void openMenu() {
        smoothScrollTo(0,0);
        mMenuIsOpen = true;
    }

    /**
     * 关闭菜单 滚动到mMenuWidth的位置
     */
    private void closeMenu() {
        smoothScrollTo(mMenuWidth,0);
        mMenuIsOpen = false;
    }

    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
