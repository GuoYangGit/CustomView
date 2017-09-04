package com.example.guoyang.customview.taglayout;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by guoyang on 2017/9/4.
 */

public abstract class BaseTagAdapter {
    public abstract int getCount();

    public abstract View getView(int position, ViewGroup parent);
}
