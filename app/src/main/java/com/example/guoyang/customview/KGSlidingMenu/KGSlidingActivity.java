package com.example.guoyang.customview.KGSlidingMenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.guoyang.customview.R;

public class KGSlidingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kgsliding_menu);
    }

    public void toast(View view){
        Toast.makeText(this,"cecece",Toast.LENGTH_LONG).show();
    }
}
