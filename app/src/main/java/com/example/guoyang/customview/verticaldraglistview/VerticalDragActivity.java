package com.example.guoyang.customview.verticaldraglistview;

import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guoyang.customview.R;

import java.util.ArrayList;
import java.util.List;

public class VerticalDragActivity extends AppCompatActivity {
    private ListView mListView;
    private List<String> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_drag);
        mListView = (ListView) findViewById(R.id.listview);
        mItems = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            mItems.add("i -> "+i);
        }
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public Object getItem(int position) {
                return mItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) LayoutInflater.from(VerticalDragActivity.this)
                        .inflate(R.layout.item_verticaldrag_content,parent,false)
                        .findViewById(R.id.item_textview);
                item.setText(mItems.get(position));
                return item;
            }
        });
    }
}
