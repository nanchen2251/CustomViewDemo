package com.nanchen.ourviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author nanchen
 * @fileName OurViewDemo
 * @packageName com.nanchen.ourviewdemo
 * @date 2016/12/21  10:32
 */

public class ClockView extends View {


    private static final String TAG = "ClockView";
    private Paint mPaint;
    private RectF mRectF;
    private int mColor;
    private float size;

    public ClockView(Context context) {
        this(context,null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs,R.styleable.ClockView,0,0);

        try{
            mColor = typedArray.getColor(R.styleable.ClockView_color, Color.RED);
            size = typedArray.getDimensionPixelSize(R.styleable.ClockView_size,150);
        } finally {
            typedArray.recycle();
        }
    }

    private void init() {
        mPaint = new Paint();
    }

    public int getColor() {
        return mColor;
    }


    public void setColor(int color) {
        mColor = color;
    }

    public float getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mColor);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(3);

        canvas.translate(canvas.getWidth()/2,200); // 将位置移到画纸的坐标点：150,150
        // 先画圆圈
        canvas.drawCircle(0,0,size,mPaint);

        // 再画圆心

        canvas.drawPoint(0,0,mPaint);

        // 再画刻度
        float start_x,start_y,end_x,end_y;
        mPaint.setStrokeWidth(4);
        for (int i = 0; i < 60; ++i) {
            // 三角函数 1 4 区间 cos为正，1 2 区间sin为正
            start_x = (float) (size * Math.cos(Math.PI/180 * i * 6)); // 三角函数
            start_y = (float) (size * Math.sin(Math.PI/180 * i * 6));
            if (i % 5 == 0){
                end_x = (float) ((size - 20) * Math.cos(Math.PI/180 * i * 6));
                end_y = (float) ((size - 20 )* Math.sin(Math.PI/180 * i * 6));
            }else{
                end_x = (float) ((size - 10) * Math.cos(Math.PI/180 * i * 6));
                end_y = (float) ((size - 10 )* Math.sin(Math.PI/180 * i * 6));
            }



            Log.e(TAG, "onDraw: x1:"+start_x+"   y1:"+start_y+"   x2:"+end_x+"   y2:"+end_y );
            canvas.drawLine(start_x,start_y,end_x,end_y,mPaint);
        }

    }
}
