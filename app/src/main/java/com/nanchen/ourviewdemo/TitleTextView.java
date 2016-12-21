package com.nanchen.ourviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author nanchen
 * @fileName OurViewDemo
 * @packageName com.nanchen.ourviewdemo
 * @date 2016/12/21  13:36
 */

public class TitleTextView extends View {


    private Paint mPaint;
    private RectF mRectF;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private String mTitleText;
    private Rect mBound;
    private Random random;

    public TitleTextView(Context context) {
        this(context,null);
    }

    public TitleTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,R.styleable.TitleTextView,defStyleAttr,0
        );

        try{
            // 设置字体
            mTitleText = typedArray.getString(R.styleable.TitleTextView_titleText);
            // 默认字体为黑色
            mTitleTextColor = typedArray.getColor(R.styleable.TitleTextView_titleTextColor, Color.BLACK);
            // 设置默认字体大小为16sp,TypeValue也可以把sp转化为Px
            mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.TitleTextView_titleTextSize,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,20,getResources().getDisplayMetrics()));
        }finally {
            typedArray.recycle();
        }

        if (mTitleText == null){
            mTitleText = "1234";
        }
        mBound = new Rect();
        mPaint.setTextSize(mTitleTextSize);
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
        random = new Random();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitleText = getRandomText();
                postInvalidate();
            }
        });
    }


    private String getRandomText(){

        Set<Integer> set = new HashSet<>();
        while (set.size() < 4){
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuilder sb = new StringBuilder();
        for (Integer i : set) {
            sb.append(i);
        }
        return sb.toString();
    }

    public int getTitleTextColor() {
        return mTitleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        mTitleTextColor = titleTextColor;
    }

    public int getTitleTextSize() {
        return mTitleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        mTitleTextSize = titleTextSize;
    }

    public String getTitleText() {
        return mTitleText;
    }

    public void setTitleText(String titleText) {
        mTitleText = titleText;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int defWidth = getPaddingLeft() + mBound.width() + getPaddingRight();
        int defHeight = getPaddingBottom() + mBound.height() + getPaddingTop();

        int width = getMySize(defWidth,widthMeasureSpec);
        int height = getMySize(defHeight,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    private int getMySize(int defSize,int measureSpec){
        int mySize = defSize;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);
        switch (mode){
            case MeasureSpec.UNSPECIFIED: // 没有指定大小就是默认大小
            case MeasureSpec.AT_MOST: // 表示子布局限制在一个最大值内，一般为warp_content
                mySize = defSize;
                break;
            case MeasureSpec.EXACTLY: // 一般是明确的值或者match_parent
                mySize = size;
                break;
        }
        return mySize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText,getWidth()/2 - mBound.width()/2,getHeight()/2+mBound.height()/2,mPaint);
    }
}
