package com.example.guoyang.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.guoyang.customview.ColorTrackTextView.ColorTrackActivity;
import com.example.guoyang.customview.QQStepView.QQStepActivity;
import com.example.guoyang.customview.QQStepView.QQStepView;

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
}
