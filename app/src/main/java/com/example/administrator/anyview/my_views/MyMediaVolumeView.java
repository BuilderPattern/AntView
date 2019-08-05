package com.example.administrator.anyview.my_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.graphics.Color.RED;
import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;

public class MyMediaVolumeView extends View {
    Paint mPaint;
    public MyMediaVolumeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(RED);
    }

    float offset = 10;//间隙
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mTotalCount; i++) {
            float mCurrHeight = (float) (Math.random()*mRectHeight);//当前音条的高度
            canvas.drawRect((float) (mWidth*0.3/2+offset+mRectWidth*i), mCurrHeight, (float) (mWidth*0.3/2+mRectWidth*(i+1)),mRectHeight, mPaint);
        }
        postInvalidateDelayed(500);
    }

    float mWidth;//整个View的宽度
    float mRectWidth;//一条的宽度
    float mRectHeight;//一条的高度，最高时的高度

    int mTotalCount = 10;//十列

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mRectWidth = (float) (mWidth*0.7/mTotalCount);
        mRectHeight = getHeight()/2;
        LinearGradient linearGradient = new LinearGradient(0, 0, mRectWidth, mRectHeight, Color.YELLOW, Color.RED, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
    }
    private static final int DEFAULT_WIDTH = 350;
    private static final int DEFAULT_HEIGHT = 200;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("-------:", widthMeasureSpec+"");
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (wMode){
            case AT_MOST:
                float density = getResources().getDisplayMetrics().density;
                width = (int) (DEFAULT_WIDTH*density);
                height = (int) (DEFAULT_HEIGHT*density);
                break;
            case EXACTLY:
                break;
            default:
                break;
        }
        setMeasuredDimension(width, height);
    }
}
