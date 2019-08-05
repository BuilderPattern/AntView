package com.example.administrator.anyview.my_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.anyview.R;

public class MyAttrView extends TextView implements View.OnClickListener {

    public MyAttrView(Context context) {
        this(context, null);
    }

    public MyAttrView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    Paint mStrPaint;
    Paint mBgPaint;

    Context mContext;

    String text = "0";
    int textColor;
    int textSize;
    int mGravity;
    int bgColor;

    public MyAttrView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyAttrView);
        if (!TextUtils.isEmpty(typedArray.getString(R.styleable.MyAttrView_text))){
            text = typedArray.getString(R.styleable.MyAttrView_text);
        }else {
            text = "";
        }
        textColor = typedArray.getColor(R.styleable.MyAttrView_textColor, Color.GREEN);
        textSize = typedArray.getDimensionPixelSize(R.styleable.MyAttrView_textSize, 12);
        mGravity = typedArray.getInt(R.styleable.MyAttrView_gravity, 0);
        bgColor = typedArray.getInt(R.styleable.MyAttrView_background, Color.BLUE);

        mStrPaint = new Paint();
        mStrPaint.setColor(Color.WHITE);

        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(bgColor);

        this.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRect(rectF, mBgPaint);

        mStrPaint.setTextSize(textSize);
        mStrPaint.setColor(textColor);
        mStrWidth = mStrPaint.measureText(text);
        mStrHeight = mStrWidth/text.length();

        switch (mGravity){
            case 0:
                canvas.drawText(text, 0, text.length(), mWidth/2-mStrWidth/2, mHeight/2+mStrHeight/2, mStrPaint);
                break;
            case 1:
                canvas.drawText(text, 0, text.length(), 0, (float) (mStrHeight*1.35), mStrPaint);
                break;
            case 2:
                canvas.drawText(text, 0, text.length(), 0, mHeight, mStrPaint);
                break;
            case 3:
                canvas.drawText(text, 0, text.length(), 0, (float) (mStrHeight*1.35), mStrPaint);
                break;
            case 4:
                canvas.drawText(text, 0, text.length(), mWidth-mStrWidth, (float) (mStrHeight*1.35), mStrPaint);
                break;
            case 5:
                canvas.drawText(text, 0, text.length(), mWidth/2-mStrWidth/2, (float) (mStrHeight*1.35), mStrPaint);
                break;
            case 6:
                canvas.drawText(text, 0, text.length(), 0, mHeight/2+mStrHeight/2, mStrPaint);
                break;
            default:
                break;
        }
    }

    float mWidth;
    float mHeight;
    float mStrWidth;
    float mStrHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();


    }
}