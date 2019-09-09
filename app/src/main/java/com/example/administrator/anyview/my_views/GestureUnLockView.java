package com.example.administrator.anyview.my_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GestureUnLockView extends View {

    private static final int DEFAULT_COLOR = 0xFF444444;//默认颜色
    private static final int SELECTED_COLOR = 0xFFAE8F00;//选中颜色

    private BigPointView[][] mBigPointArray;//所有圆心的数组

    private List<BigPointView> mSelectedBigPointList;//所有选中的BigPointView集合

    private Paint mBigPointPaint;//绘制圆的画笔
    private float mRadius;//圆的半径

    private Paint mLinePoint;//绘制线的画笔
    private float mLineSize = 16;//线的宽度尺寸

    private boolean isSelected;//在onTouch事件中，判断是否触摸到任意一个BigPointView

    private boolean isFinished;//在onTouch事件中，判断触摸事件是否结束

    private float mCurrentX;//当前手势x坐标
    private float mCurrentY;//当前手势y坐标

    public GestureUnLockView(Context context) {
        super(context);
        init();
    }

    public GestureUnLockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBigPointPaint = new Paint();
        mBigPointPaint.setColor(DEFAULT_COLOR);
        mBigPointPaint.setStyle(Paint.Style.FILL);
        mBigPointPaint.setDither(true);

        mLinePoint = new Paint();
        mLinePoint.setColor(SELECTED_COLOR);
        mLinePoint.setStrokeWidth(mLineSize);
        mLinePoint.setDither(true);
        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());

        mBigPointArray = new BigPointView[3][3];
        mSelectedBigPointList = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawBigPoints(canvas);

        /**
         * 对选中的BigPointView重新绘制
         */
        if (mSelectedBigPointList.size() > 0) {
            for (int i = 0; i < mSelectedBigPointList.size(); i++) {
                BigPointView checkPoint = mSelectedBigPointList.get(i);
                mBigPointPaint.setColor(SELECTED_COLOR);
                canvas.drawCircle(checkPoint.x, checkPoint.y, mRadius, mBigPointPaint);
                mBigPointPaint.setColor(DEFAULT_COLOR);
            }

            BigPointView lastPoint = mSelectedBigPointList.get(0);//上一个选中的
            for (int i = 0; i < mSelectedBigPointList.size(); i++) {
                BigPointView currentPoint = mSelectedBigPointList.get(i);
                canvas.drawLine(lastPoint.x, lastPoint.y, currentPoint.x, currentPoint.y, mLinePoint);
                lastPoint = currentPoint;
            }

            if (!isFinished) {
                canvas.drawLine(lastPoint.x, lastPoint.y, mCurrentX, mCurrentY, mLinePoint);
            }
        }
        super.onDraw(canvas);
    }

    /**
     * 首先绘制出所有的圆
     *
     * @param canvas
     */
    int index = 1;

    private void drawBigPoints(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int cX = (int) (mRadius * (j * 4 + 3));
                int cY = (int) (mRadius * (i * 4 + 3));
                BigPointView bigPointView = new BigPointView(cX, cY);
                bigPointView.setPosition(index);//设置密码编号
                mBigPointArray[i][j] = bigPointView;
                canvas.drawCircle(cX, cY, mRadius, mBigPointPaint);
                index++;
            }
        }
        index = 1;
    }

    int mWidth;//设置控件的宽高相等

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());//取宽高中较小的

        mRadius = mWidth / 14;

        mLineSize = mRadius / 5;
        setMeasuredDimension(mWidth, mWidth);
    }

    StringBuilder passWord;//密码

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentX = event.getX();
        mCurrentY = event.getY();
        BigPointView bigPointView = null;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mSelectedBigPointList.clear();
                isFinished = false;
                bigPointView = isTouchAnyPoint();
                if (bigPointView != null) {
                    isSelected = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("----------view:", "move");
                isFinished = false;
                if (isSelected) {
                    bigPointView = isTouchAnyPoint();
                }

                break;
            case MotionEvent.ACTION_UP:
                isFinished = true;
                isSelected = false;
                break;
        }

        if (bigPointView != null) {
            if (!mSelectedBigPointList.contains(bigPointView)) {
                mSelectedBigPointList.add(bigPointView);
            }
        }

        if (isFinished) {
            if (mSelectedBigPointList.size() < 6) {
                if (onGestureFailedListener != null) {
                    onGestureFailedListener.onFailed("图形密码为5个以上");
                }
                mSelectedBigPointList.clear();
            } else {
                if (passWord != null) {
                    passWord.setLength(0);
                } else {
                    passWord = new StringBuilder();
                }
                for (int i = 0; i < mSelectedBigPointList.size(); i++) {
                    passWord.append(mSelectedBigPointList.get(i).getPosition());
                }
                if (onGestureSucceedListener != null) {
                    onGestureSucceedListener.onSucceed(passWord.toString());
                }
                Log.e("------passWord：", passWord.toString());
            }
        }

        invalidate();
        return true;
    }

    private BigPointView isTouchAnyPoint() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BigPointView bigPointView = mBigPointArray[i][j];
                /**
                 * 利用勾股定理算出触摸点距离圆心的距离和半径比较
                 */
                if (isInPoint(bigPointView)) {
                    return bigPointView;
                }
            }
        }

        return null;
    }

    private boolean isInPoint(BigPointView bigPointView) {
        if (Math.sqrt(Math.pow(mCurrentX - bigPointView.x, 2) + Math.pow(mCurrentY - bigPointView.y, 2)) <= mRadius) {
            return true;
        }
        return false;
    }

    public void setOnGestureonSucceedListener(OnGestureSucceedListener onGestureSucceedListener) {
        this.onGestureSucceedListener = onGestureSucceedListener;
    }

    public void setOnGestureFailedListener(OnGestureFailedListener onGestureFailedListener) {
        this.onGestureFailedListener = onGestureFailedListener;
    }

    public OnGestureSucceedListener onGestureSucceedListener;
    public OnGestureFailedListener onGestureFailedListener;

    public interface OnGestureSucceedListener {
        void onSucceed(String password);
    }

    public interface OnGestureFailedListener {
        void onFailed(String msg);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("-----viewdis:", "dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    /**
     * BigPointView用于绘制圆，以及记录每个点的位置
     */
    public class BigPointView extends Point {
        private int position;//BigPoint对应的位置

        public BigPointView(int x, int y) {
            super(x, y);
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}