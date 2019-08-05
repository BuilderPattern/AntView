

package com.example.administrator.anyview.my_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyVolumeView extends View {

    Paint mPaint;
    float mRectWidth;
    float mRectHeight;

    public MyVolumeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

    }

    int offset = 10;

    float mWidth;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 10; i++) {
            float mCurrHeight = (float) (Math.random()*mRectHeight);
            RectF rect = new RectF((float) (mWidth*0.1+i*mRectWidth+offset), mCurrHeight, (float) (mWidth*0.1+(i+1)*mRectWidth), mRectHeight);
            canvas.drawRect(rect,mPaint);
        }
        postInvalidateDelayed(500);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mRectWidth = (float) (getWidth()*0.8/10);
        mRectHeight = getHeight();

        LinearGradient linearGradient = new LinearGradient(0, 0, mRectWidth, mRectHeight, Color.BLACK, Color.WHITE, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
    }
}
