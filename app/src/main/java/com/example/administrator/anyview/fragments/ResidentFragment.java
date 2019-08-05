package com.example.administrator.anyview.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anyview.R;

public class ResidentFragment extends Fragment {

    View view;

    int lastX;
    int lastY;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_resident, container,false);

        return view;
    }
}
