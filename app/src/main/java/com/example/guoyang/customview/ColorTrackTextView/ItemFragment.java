package com.example.guoyang.customview.ColorTrackTextView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guoyang.customview.R;

public class ItemFragment extends Fragment {

    public static ItemFragment newInstance(String item) {
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",item);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item,null);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        Bundle bundle = getArguments();
        textView.setText(bundle.getString("title"));
        return view;
    }
}
