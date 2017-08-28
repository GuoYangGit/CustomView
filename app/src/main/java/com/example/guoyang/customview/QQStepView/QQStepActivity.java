package com.example.guoyang.customview.QQStepView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.example.guoyang.customview.R;

public class QQStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqstep);
        final QQStepView qqStepView = (QQStepView) findViewById(R.id.step_view);
        qqStepView.setMaxStep(4000);
        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3000);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float)valueAnimator.getAnimatedValue();
                qqStepView.setCurrentStep((int) animatedValue);
            }
        });
        valueAnimator.start();
    }
}
