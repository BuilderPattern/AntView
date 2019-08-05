package com.example.administrator.anyview.my_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyCountView extends View implements View.OnClickListener {
    Paint mPaint;
    Paint mPaint1;
    RectF mRectF;

    int mCount;

    Context mContext;

    public MyCountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        mPaint1 = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setColor(Color.RED);
        mPaint1.setColor(Color.WHITE);
        mPaint1.setTextSize(80);
        mRectF = new RectF();
        setOnClickListener(this);

//        mBound = new Rect();
//        mPaint.getTextBounds(strText, 0, strText.length(), mBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRect(mRectF, mPaint);
        String strText = mCount+"";
        canvas.drawText(strText,0,0,mPaint1);
        /**
         * mPaint1.measureText(strText)是用于测量字符串的宽度
         */
        canvas.drawText(mCount+"",getMeasuredWidth()/2-mPaint1.measureText(strText)/2, getMeasuredHeight()/2+mPaint1.measureText(strText)/strText.length()/2, mPaint1);

//        canvas.drawText(strText,0,strText.length(), getMeasuredWidth()/2 - mPaint1.measureText(strText)/2, getMeasuredHeight()/2+mPaint1.measureText(strText)/strText.length()/2, mPaint1);
    }

    @Override
    public void onClick(View v) {
        mCount = mCount + num;
        postInvalidateDelayed(100);
    }
    int num;
    public void addNum(int num){
        this.num = num;
    }
}
