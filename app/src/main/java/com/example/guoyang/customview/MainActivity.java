package com.example.guoyang.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.guoyang.customview.colorTrackTextView.ColorTrackActivity;
import com.example.guoyang.customview.progressBarView.ProgressBarActivity;
import com.example.guoyang.customview.qqStepView.QQStepActivity;
import com.example.guoyang.customview.shapeView.ShapeView;

public class MainActivity extends AppCompatActivity {

    private ShapeView shapeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shapeView = (ShapeView) findViewById(R.id.shape_view);
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
}
