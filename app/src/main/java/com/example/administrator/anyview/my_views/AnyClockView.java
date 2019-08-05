package com.example.administrator.anyview.my_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.anyview.R;

import java.util.Calendar;

public class AnyClockView extends View {

    Paint mCirclePaint;
    int mCircleColor = 0x333333;
    float mCircleWidth;

    Paint mDegreePaint;
    int degreeColor;//刻度颜色
    float mDegreeWidth;
    float mDegreeLength;

    Paint mTextPaint;//绘制字
    int mTextColor = 0xff0000;//字体颜色
    float mTextSize = 25;//字体大小

    /**
     * 小时的画笔
     */
    Paint mHourPaint;
    float mHourWidth;
    float mHourLength;

    /**
     * 分钟的画笔
     */
    Paint mMinPaint;
    float mMinWidth;
    float mMinLength;

    /**
     * 秒的画笔
     */
    Paint mSecondPaint;
    float mSecondWidth;
    float mSecondLength;

    Paint mPointPaint;//绘制指针中间的圆点

    float indicatorBackLength;//指针后面伸出的长度

    int hourColor;
    int minColor;
    int secondColor;

    float defaultWidth = 560;
    float defaultHeight = 560;

    int width;//该控件宽高一样,测量尺寸

    Context mContext;

    private int num = 0;//控制特性时间点，重新绘制特定指针

    private boolean initialized = true;//为了确保只执行一次

    public AnyClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    /**
     * 基本的初始化
     * @param attrs
     */
    private void init(AttributeSet attrs) {

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MyClockView);
        if (typedArray != null) {
            if (typedArray.getDimension(R.styleable.MyClockView_textTimeSize, mTextSize) < 25) {
                mTextSize = typedArray.getDimension(R.styleable.MyClockView_textTimeSize, mTextSize);
            }
            mTextColor = typedArray.getColor(R.styleable.MyClockView_textTimeColor, Color.BLACK);
            mCircleColor = typedArray.getColor(R.styleable.MyClockView_circleColor, Color.BLACK);
            degreeColor = typedArray.getColor(R.styleable.MyClockView_degreeColor, Color.BLACK);
            hourColor = typedArray.getColor(R.styleable.MyClockView_hourIndicatorColor, Color.BLACK);
            minColor = typedArray.getColor(R.styleable.MyClockView_minIndicatorColor, Color.BLACK);
            secondColor = typedArray.getColor(R.styleable.MyClockView_secondIndicatorColor, Color.BLACK);
        }

        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(mCircleColor);

        mDegreePaint = new Paint();
        mDegreePaint.setAntiAlias(true);
        mDegreePaint.setDither(true);
        mDegreePaint.setColor(degreeColor);

        mHourPaint = new Paint();
        mHourPaint.setAntiAlias(true);
        mHourPaint.setDither(true);
        mHourPaint.setColor(hourColor);

        mMinPaint = new Paint();
        mMinPaint.setAntiAlias(true);
        mMinPaint.setDither(true);
        mMinPaint.setColor(minColor);

        mSecondPaint = new Paint();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setDither(true);
        mSecondPaint.setColor(secondColor);

        mPointPaint = new Paint();
        mPointPaint.setColor(Color.WHITE);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Calendar mCalendar = Calendar.getInstance();//因为要根据mCalendar绘制指定时间，所以每次绘制都要获取最新的

        drawSecondIndicator(canvas, mCalendar);//秒针每秒都绘制

        if (initialized){
            canvas.drawCircle(width / 2, width / 2, (width - mCircleWidth) / 2, mCirclePaint);//外边框圆
            drawDegree(canvas);//刻度
            drawCenterPoint(canvas);//中心点

            drawMinIndicator(canvas, mCalendar);
            drawHourIndicator(canvas, mCalendar);
        }

        /**
         * 绘制几个指针
         */

        if (num/60 == 0){
            if (num/3600 == 0){//每过一小时，刷新一下时针
                drawHourIndicator(canvas, mCalendar);
            }else {//每过一分钟，绘制一下分针
                drawMinIndicator(canvas, mCalendar);
            }
        }
        num++;
        postInvalidateDelayed(1000);
    }

    /**
     * 绘制秒针
     *
     * @param canvas
     */
    private void drawSecondIndicator(Canvas canvas, Calendar calendar) {
        float secondAngle = calendar.get(Calendar.SECOND) / 60f * 360;
        float[] floats = getStartAndEndPointsXY(secondAngle, mSecondLength);
        canvas.drawLine(floats[0], floats[1], floats[2], floats[3], mSecondPaint);//秒针的四个顶点
    }

    /**
     * 绘制分针
     *
     * @param canvas
     */
    private void drawMinIndicator(Canvas canvas, Calendar calendar) {
        float minAngle = calendar.get(Calendar.MINUTE) / 60f * 360;
        float[] floats = getStartAndEndPointsXY(minAngle, mMinLength);
        canvas.drawLine(floats[0], floats[1], floats[2], floats[3], mMinPaint);
    }

    /**
     * 绘制时针
     *
     * @param canvas
     * @param calendar
     */
    private void drawHourIndicator(Canvas canvas, Calendar calendar) {
        float hourAngle = calendar.get(Calendar.HOUR) / 12f * 360 + calendar.get(Calendar.MINUTE) / 12f / 60 * 360;
        float[] floats = getStartAndEndPointsXY(hourAngle, mHourLength);
        canvas.drawLine(floats[0], floats[1], floats[2], floats[3], mHourPaint);
    }

    /**
     * 绘制指针交汇的中心点
     *
     * @param canvas
     */
    private void drawCenterPoint(Canvas canvas) {
        canvas.drawCircle(width / 2, width / 2, 4, mPointPaint);
    }

    /**
     * 根据所在象限及正、余弦定理
     * 获取起点和终点的XY坐标
     *
     * @param angle
     * @param length
     * @return
     */
    private float[] getStartAndEndPointsXY(float angle, float length) {
        float[] points = new float[4];//依次是起点x、y，终点x、y坐标组成的数组
        if (angle < 90f) {
            points[0] = -(float) Math.sin(angle * Math.PI / 180) * indicatorBackLength + width / 2;
            points[1] = (float) Math.cos(angle * Math.PI / 180) * indicatorBackLength + width / 2;
            points[2] = (float) Math.sin(angle * Math.PI / 180) * length + width / 2;
            points[3] = -(float) Math.cos(angle * Math.PI / 180) * length + width / 2;
        } else if (angle < 180f) {
            points[0] = -(float) Math.cos((angle - 90) * Math.PI / 180) * indicatorBackLength + width / 2;
            points[1] = -(float) Math.sin((angle - 90) * Math.PI / 180) * indicatorBackLength + width / 2;
            points[2] = (float) Math.cos((angle - 90) * Math.PI / 180) * length + width / 2;
            points[3] = (float) Math.sin((angle - 90) * Math.PI / 180) * length + width / 2;
        } else if (angle < 270f) {
            points[0] = (float) Math.sin((angle - 180) * Math.PI / 180) * indicatorBackLength + width / 2;
            points[1] = -(float) Math.cos((angle - 180) * Math.PI / 180) * indicatorBackLength + width / 2;
            points[2] = -(float) Math.sin((angle - 180) * Math.PI / 180) * length + width / 2;
            points[3] = (float) Math.cos((angle - 180) * Math.PI / 180) * length + width / 2;
        } else if (angle <= 360f) {
            points[0] = (float) Math.cos((angle - 270) * Math.PI / 180) * indicatorBackLength + width / 2;
            points[1] = (float) Math.sin((angle - 270) * Math.PI / 180) * indicatorBackLength + width / 2;
            points[2] = -(float) Math.cos((angle - 270) * Math.PI / 180) * length + width / 2;
            points[3] = -(float) Math.sin((angle - 270) * Math.PI / 180) * length + width / 2;
        }
        return points;
    }

    /**
     * 绘制刻度
     *
     * @param canvas
     */
    private void drawDegree(Canvas canvas) {
        for (int i = 0; i < 60; i++) {
            if (i % 15 == 0) {
                /**
                 * 0,3,6,9整点的刻度宽度和长度
                 */
                mDegreeLength = width / 16;
                mDegreeWidth = width / 50;
                /**
                 * 绘制一个小矩形，使字体在矩形内居中
                 */
                Rect rect = new Rect(width / 2 - 20, width / 10, width / 2 + 20, width / 6);
                mTextPaint.setColor(ContextCompat.getColor(mContext, R.color.color_00ffffff));
                canvas.drawRect(rect, mTextPaint);//这里的矩形只是确定字的区域，随便用那个画笔
                mTextPaint.setColor(mTextColor);

                Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
                int baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;//竖直方向的中间位置

                mTextPaint.setTextAlign(Paint.Align.CENTER);// 画笔定位到水平中间位置
                /**
                 * 绘制文字：0、3、6、9
                 */
                if ((i + 1) / 15 == 0) {
                    canvas.drawText("0", rect.centerX(), baseline, mTextPaint);
                } else if ((i + 1) / 15 == 1) {
                    canvas.rotate(-90f, rect.centerX(), rect.centerY());
                    canvas.drawText("3", rect.centerX(), baseline, mTextPaint);
                    canvas.rotate(90f, rect.centerX(), rect.centerY());
                } else if ((i + 1) / 15 == 2) {
                    canvas.drawText("9", rect.centerX(), baseline, mTextPaint);
                } else {
                    canvas.rotate(90f, rect.centerX(), rect.centerY());//为了字体方向竖直，顺时针旋转90°
                    canvas.drawText("9", rect.centerX(), baseline, mTextPaint);//旋转90°后绘制字
                    canvas.rotate(-90f, rect.centerX(), rect.centerY());//绘制完该字后，再逆时针旋转回原先的状态
                }
            } else if (i % 5 == 0) {//其他整点
                mDegreeLength = width / 22;
                mDegreeWidth = width / 50;
            } else {//整点以外的刻度长度和宽度
                mDegreeLength = width / 30;
                mDegreeWidth = width / 75;
            }
            mDegreePaint.setStrokeWidth(mDegreeWidth);
            canvas.drawLine(width / 2, 0, width / 2, mDegreeLength, mDegreePaint);//绘制当前刻度
            canvas.rotate(360 / 60f, width / 2, width / 2);//画布每次绕圆心，转动一定角度，分60次，因为有60个刻度。一周360°分60次旋转
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            width = (int) defaultWidth;
            setMeasuredDimension((int) defaultWidth, (int) defaultHeight);
        } else {
            width = Math.min(widthSize, heightSize);//该View的宽高相等，如果xml中的宽高设置不一样，就默认使用小的尺寸
            setMeasuredDimension(width, width);
        }


        mCircleWidth = width / 60;

        mSecondLength = (float) (width / 2 * 0.8);
        mSecondWidth = width / 100;

        mMinLength = (float) (width / 2 * 0.65);
        mMinWidth = width / 80;

        mHourLength = (float) (width / 2 * 0.45);
        mHourWidth = width / 50;

        mCirclePaint.setStrokeWidth(mCircleWidth);
        mDegreePaint.setStrokeWidth(mDegreeWidth);
        mHourPaint.setStrokeWidth(mHourWidth);
        mMinPaint.setStrokeWidth(mMinWidth);
        mSecondPaint.setStrokeWidth(mSecondWidth);

        indicatorBackLength = width / 16;

    }
}

