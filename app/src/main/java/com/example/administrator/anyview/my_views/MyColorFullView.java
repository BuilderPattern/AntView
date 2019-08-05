package com.example.administrator.anyview.my_views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.anyview.onTestListener;

@SuppressLint("AppCompatCustomView")
public class MyColorFullView extends TextView {

    Paint mPaint1;

    public MyColorFullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint1 = new Paint();
        mPaint1.setColor(0x5000ff00);
        mPaint1.setStyle(Paint.Style.FILL);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onTestClick();
                }
            }
        });

        mPaint = getPaint();//获取当前绘制TextView的paint
    }
    onTestListener mListener;
    public void setOnTestClickListener(onTestListener mListener){
        this.mListener = mListener;
    }

    private Paint mPaint;
    private int mViewWidth;

    LinearGradient mLinearGradient;
    Matrix mMatrix;
    int mTranslateLocation;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0){
                mLinearGradient
                        = new LinearGradient(0,0,mViewWidth,0,new int[]{Color.BLUE, Color.WHITE, Color.BLUE}
                        ,null, Shader.TileMode.CLAMP);//创建一个自定义的着色器mLinearGradient
                mPaint.setShader(mLinearGradient);//给mPaint设置原生TextView没有的自定义的着色器mLinearGradient
                mMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint1);//用mPaint1绘制矩形

        if (mLinearGradient != null){
            mTranslateLocation += mViewWidth/5;
            /**
             * 当前位置大于整个TextView的宽度时，把起始位置向左移动一个TextView宽度的距离
             */
            if (mTranslateLocation > mViewWidth){
                mTranslateLocation = -mViewWidth;
            }
            mMatrix.setTranslate(mTranslateLocation,0);
            mLinearGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
