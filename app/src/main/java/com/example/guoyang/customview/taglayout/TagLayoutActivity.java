package com.example.guoyang.customview.taglayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guoyang.customview.R;

public class TagLayoutActivity extends AppCompatActivity {
    private TagLayout mTagLayout;
    private String[] items = {"新闻","娱乐","综艺","太原","搞笑","内涵段子","视频","图片"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_layout);
        mTagLayout = (TagLayout) findViewById(R.id.taglayout);
        mTagLayout.setAdapter(new BaseTagAdapter() {
            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TextView tagTv = (TextView) LayoutInflater.from(TagLayoutActivity.this).inflate(R.layout.item_tags, parent, false);
                tagTv.setText(items[position]);
                return tagTv;
            }
        });
    }
}
