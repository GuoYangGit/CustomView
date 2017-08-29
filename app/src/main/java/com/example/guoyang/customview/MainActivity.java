package com.example.guoyang.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.guoyang.customview.colorTrackTextView.ColorTrackActivity;
import com.example.guoyang.customview.progressBarView.ProgressBarActivity;
import com.example.guoyang.customview.qqStepView.QQStepActivity;
import com.example.guoyang.customview.ratingBarView.RatingBarActivity;
import com.example.guoyang.customview.shapeView.ShapeView;
import com.example.guoyang.customview.shapeView.ShapeViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void QQStepview(View view){
        startActivity(new Intent(this,QQStepActivity.class));
    }

    public void ColorTrackTextview(View view) {
        startActivity(new Intent(this,ColorTrackActivity.class));
    }

    public void ProgressBarView(View view) {
        startActivity(new Intent(this,ProgressBarActivity.class));
    }

    public void ShapeView(View view) {
        startActivity(new Intent(this,ShapeViewActivity.class));
    }

    public void RatingBar(View view) {
        startActivity(new Intent(this,RatingBarActivity.class));
    }
}
