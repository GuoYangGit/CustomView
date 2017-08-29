package com.example.guoyang.customview.shapeView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guoyang.customview.R;

public class ShapeViewActivity extends AppCompatActivity {
    private ShapeView shapeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_view);
        shapeView = (ShapeView) findViewById(R.id.shape_view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shapeView.exchange();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
