package com.example.guoyang.customview.progressBarView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;

import com.example.guoyang.customview.R;

public class ProgressBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        final ProgressBarView progressBarView = (ProgressBarView) findViewById(R.id.ProgressBarView);
        ValueAnimator animator = ObjectAnimator.ofFloat(0,100);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                progressBarView.setProgressSize((int) animatedValue);
            }
        });
        animator.start();
    }
}
