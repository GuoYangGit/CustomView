package com.example.guoyang.customview.ColorTrackTextView;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.guoyang.customview.R;

import java.util.ArrayList;
import java.util.List;

public class ColorTrackActivity extends AppCompatActivity {
    private String[] items = {"直播", "推荐", "视频", "图片", "段子"};
    private LinearLayout mIndicatorContainer;
    private List<ColorTrackTextView> mIndicators;
    private ViewPager mViewpager;
    private int lastposition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_track);
        mIndicators = new ArrayList<>();
        mIndicatorContainer = (LinearLayout) findViewById(R.id.indicator_view);
        mViewpager = (ViewPager) findViewById(R.id.viewpage);
        initIndicator();
        initViewPager();
    }

    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            colorTrackTextView.setTextSize(20);
            colorTrackTextView.setChangeColor(Color.RED);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            final int finalI = i;
            colorTrackTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewpager.setCurrentItem(finalI);
                }
            });
            mIndicatorContainer.addView(colorTrackTextView);
            mIndicators.add(colorTrackTextView);
        }
    }

    private void initViewPager() {
        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ColorTrackTextView left = mIndicators.get(position);
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1 - positionOffset);
                if (position == items.length - 1) return;
                ColorTrackTextView right = mIndicators.get(position + 1);
                right.setDirection(ColorTrackTextView.Direction.LETF_TO_RIGHT);
                right.setCurrentProgress(positionOffset);
                lastposition = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
