package com.example.guoyang.customview.letterSideBar;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.guoyang.customview.R;

public class LetterSideBarActivity extends AppCompatActivity {
    private TextView mLetterTv;
    private LetterSideBar letterSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_side_bar);
        mLetterTv = (TextView) findViewById(R.id.textview);
        letterSideBar = (LetterSideBar) findViewById(R.id.LetterSideBar);
        letterSideBar.setOnLetterTouchListener(new LetterSideBar.LetterTouchListener() {
            @Override
            public void touch(String letter) {
                mLetterTv.setVisibility(View.VISIBLE);
                mLetterTv.setText(letter);
            }

            @Override
            public void unTouch() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLetterTv.setVisibility(View.GONE);
                    }
                },300);
            }
        });
    }
}
