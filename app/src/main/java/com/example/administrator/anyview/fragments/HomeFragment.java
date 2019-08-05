package com.example.administrator.anyview.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anyview.R;

public class HomeFragment extends Fragment {

    View view;
//    MyCountView myCountView;
//    MyColorFullView myColorFullView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

//        myCountView = view.findViewById(R.id.myCountView);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                myCountView.addNum(8);
//            }
//        }).start();

        initViews();
        return view;
    }

    private void initViews() {
//        myColorFullView = view.findViewById(R.id.myColorFullTextView);
//        myColorFullView.setOnTestClickListener(new onTestListener() {
//            @Override
//            public void onTestClick() {
//
//            }
//        });
    }
}
