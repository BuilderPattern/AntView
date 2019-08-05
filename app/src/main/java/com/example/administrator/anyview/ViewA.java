package com.example.anyview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ViewA extends View {
    public ViewA(Context context) {
        super(context);
    }

    public ViewA(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("----------ViewA：", "dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        Log.e("----------ViewA：", "setOnTouchListener");
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("----------ViewA：", "onTouchEvent");
        return super.onTouchEvent(event);
    }
}
