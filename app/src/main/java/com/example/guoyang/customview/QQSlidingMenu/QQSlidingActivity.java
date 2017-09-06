package com.example.guoyang.customview.QQSlidingMenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.guoyang.customview.R;

public class QQSlidingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqsliding);
    }

    public void toast(View view){
        Toast.makeText(this,"cecece",Toast.LENGTH_LONG).show();
    }
}
