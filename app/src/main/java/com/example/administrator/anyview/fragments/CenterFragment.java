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


public class CenterFragment extends Fragment implements View.OnClickListener {

    View view;
    MainActivity mActivity;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_center, container, false);
        mActivity = (MainActivity) getActivity();

        TextView mDragTv = (TextView) view.findViewById(R.id.dragTv);
        TextView mAutoBackTv = (TextView) view.findViewById(R.id.autoBackTv);
//        TextView mEdgeTv = (TextView) view.findViewById(R.id.edgeTv);
        mDragTv.setOnClickListener(this);
        mAutoBackTv.setOnClickListener(this);
//        mEdgeTv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dragTv:
                Toast.makeText(mActivity, "自由控件", Toast.LENGTH_SHORT).show();
                break;
            case R.id.autoBackTv:
                Toast.makeText(mActivity, "自动返回控件", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.edgeTv:
//                Toast.makeText(mActivity, "边界控件", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }
    }
}
