package com.example.administrator.anyview.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.anyview.MainActivity;
import com.example.anyview.R;
import com.example.administrator.anyview.my_views.MyLoadCircleView;


public class LookForFragment extends Fragment {

    View view;

    int progress = 0;
    private MyLoadCircleView myLoadCircleView;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_look_for, container, false);
        myLoadCircleView = (MyLoadCircleView) view.findViewById(R.id.two_downLoadView);
        mainActivity = (MainActivity) getActivity();

        Toast.makeText(mainActivity, "提示fragment", Toast.LENGTH_LONG).show();
        TextView textView = (TextView) view.findViewById(R.id.clickTv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = 0;
                if (progress == 0){

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (progress <= 100){
                                mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        myLoadCircleView.setProgress(progress);
                                    }
                                });
                                try {
                                    Thread.sleep(100);
                                    progress ++;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        });

        return view;
    }
}