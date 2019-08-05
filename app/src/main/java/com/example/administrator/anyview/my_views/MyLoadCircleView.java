

package com.example.administrator.anyview.my_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.anyview.R;


public class MyLoadCircleView extends View {

    int loadColor = Color.RED;
    int startAngle = 0;
    int sweepAngle = 0;
    int progress = 0;
    Paint mLoadPaint;

    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 200;
    public MyLoadCircleView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyLoadCircleView);
        if (typedArray != null){
            loadColor = typedArray.getColor(R.styleable.MyLoadCircleView_loadColor, loadColor);
            startAngle = typedArray.getInteger(R.styleable.MyLoadCircleView_startAngle, startAngle);

            typedArray.recycle();//每次typedArray使用完成之后，进行回收，确保其它地方也可以调用typedArray
        }
        mLoadPaint = new Paint();
        mLoadPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLoadPaint.setAntiAlias(true);
        mLoadPaint.setColor(loadColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int mHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (mWidthMode){
            case MeasureSpec.AT_MOST:
                float density = getResources().getDisplayMetrics().density;
                mWidthSize = (int) (DEFAULT_WIDTH*density);
                mHeightSize = (int) (DEFAULT_HEIGHT*density);
                break;
            case MeasureSpec.EXACTLY:
                mWidthSize = mHeightSize = Math.min(mWidthSize, mHeightSize);
                break;
            default:
                break;
        }
        setMeasuredDimension(mWidthSize, mHeightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawArc(rectF,0,sweepAngle,true, mLoadPaint);
//        sweepAngle = sweepAngle + speed;
//        sweepAngle = sweepAngle > 360 ? 0 : sweepAngle;
//        invalidate();
    }
    public void setProgress(int progress){
        if (progress <= 100){
            this.progress = progress;
            sweepAngle = (int) (3.6 * progress);
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
