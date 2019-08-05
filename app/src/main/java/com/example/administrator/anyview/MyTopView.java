package com.example.anyview;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MyTopView extends RelativeLayout{

    TextView mLeftTv;
    TextView mTitleTv;
    TextView mRightTv;

    Context mContext;

    public MyTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        LayoutInflater.from(context).inflate(R.layout.my_layout, this);
        mLeftTv = (TextView) findViewById(R.id.leftTv);
        mTitleTv = (TextView) findViewById(R.id.titleTv);
        mRightTv = (TextView) findViewById(R.id.rightTv);
    }
}