

package com.example.administrator.anyview.my_views;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MyTouchMoveLayout extends LinearLayout {

    View mMoveView;

    View mAutoBackView;
    Point mPoint = new Point();

//    View mEdgeView;

    ViewDragHelper mHelper;

    public MyTouchMoveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mMoveView || child == mAutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return 0;//水平不可移动
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == mAutoBackView){
                    mHelper.settleCapturedViewAt(mPoint.x, mPoint.y);
                }
                invalidate();
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getHeight();
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
//                mHelper.captureChildView(mEdgeView, pointerId);
            }
        });
        mHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);//边界检测
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mPoint.x = mAutoBackView.getLeft();
        mPoint.y = mAutoBackView.getTop();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMoveView = getChildAt(0);
        mAutoBackView = getChildAt(1);
//        mEdgeView = getChildAt(2);
    }
}
