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

public class MyMediaView extends View {

    Paint mPaint;
    public MyMediaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    /**
     * 控件宽高
     */
    float mWidth;
    float mHeight;

    /**
     * 矩形的宽高
     */
    float mRecWidth;
    float mRecHeight;

    int offset = 10;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        LinearGradient linearGradient = new LinearGradient(0, 0, mRecWidth, mRecHeight, Color.RED, Color.YELLOW, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);

        for (int i = 0; i < 10; i++) {
            float mCurrHeight = (float) (Math.random()*mRecHeight);//每个矩形条随机取的高度
            canvas.drawRect((float)(mWidth*0.1+mRecWidth*i+offset), mCurrHeight, (float)(mWidth*0.1+(i+1)*mRecWidth), mRecHeight, mPaint);
        }
        postInvalidateDelayed(500);
    }
    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 200;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (wMode){
            case MeasureSpec.AT_MOST:
                float density = getResources().getDisplayMetrics().density;
                wSize = (int) (DEFAULT_WIDTH*density);
                hSize = (int) (DEFAULT_HEIGHT*density);
                break;
            case MeasureSpec.EXACTLY:
                wSize = hSize = Math.min(wSize, hSize);
                break;
            default:
                break;
        }
        mWidth = getMeasuredWidth();

        mRecHeight = getMeasuredHeight();
        mRecWidth = (float) (getMeasuredWidth()*0.8/10);

        setMeasuredDimension(wSize, hSize);
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }
}
