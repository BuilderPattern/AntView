

package com.example.administrator.anyview.my_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.anyview.R;


public class MyLoadingView extends View {


    int loadingColor = Color.GREEN;
    int startAngle = 0;
    int sweepAngle = 0;
    int speed = 10;
    Paint mLoadPaint;

    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 200;

    Paint mPaint;

    public MyLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        mPaint.setColor(getResources().getColor(R.color.color_9d9d9d));
        mLoadPaint = new Paint();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyLoadingView);

        if (typedArray != null){
            loadingColor = typedArray.getColor(R.styleable.MyLoadingView_loadColor, loadingColor);
            startAngle = typedArray.getInteger(R.styleable.MyLoadingView_startAngle, startAngle);
            speed = typedArray.getInteger(R.styleable.MyLoadingView_speed, speed);

            typedArray.recycle();
        }

        mLoadPaint = new Paint();
        mLoadPaint.setStyle(Paint.Style.STROKE);
        mLoadPaint.setStrokeWidth(50);
        mLoadPaint.setAntiAlias(true);
        mLoadPaint.setColor(loadingColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(getMeasuredWidth()/4, getMeasuredWidth()/4, getMeasuredWidth()*3/4, getMeasuredHeight()*3/4);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mLoadPaint);
        sweepAngle = sweepAngle + speed;
        sweepAngle = sweepAngle >= 360 ? 0 : sweepAngle;
        invalidate();
    }

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
        setMeasuredDimension(wSize, hSize);
    }
}
