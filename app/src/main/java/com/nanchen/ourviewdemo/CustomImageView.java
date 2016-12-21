package com.nanchen.ourviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @author nanchen
 * @fileName OurViewDemo
 * @packageName com.nanchen.ourviewdemo
 * @date 2016/12/21  15:18
 */

public class CustomImageView extends View {


    private Paint mPaint;
    private String mTitle;
    private int mTextSize;
    private int mTextColor;
    private int mImageRes;
    private int mImageScaleType;
    private Rect mTextBound;
    private Bitmap mImage;

    private int mWidth;
    private int mHeight;
    private Rect mRect;
    private TextPaint textPaint;

    private final int IMAGE_SCALE_FITXY = 0;
    private final int IMAGE_SCALE_CENTER = 1;



    public CustomImageView(Context context) {
        this(context,null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs,
                        R.styleable.CustomImageView
                ,defStyleAttr,0);

        try {
            mImage = BitmapFactory.decodeResource(getResources(),typedArray.getResourceId(R.styleable.CustomImageView_imageRes,R.mipmap.ic_launcher));
            mTitle = typedArray.getString(R.styleable.CustomImageView_imageText);
            mTextColor = typedArray.getColor(R.styleable.CustomImageView_imageTextColor, Color.RED);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomImageView_imageTextSize,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,20,getResources().getDisplayMetrics()));
            mImageScaleType = typedArray.getInt(R.styleable.CustomImageView_imageScaleType, 0);
        } finally {
            typedArray.recycle();
        }

        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        if (mTitle == null){
            mTitle = "未设置文字";
        }
        mTextBound = new Rect();
        mRect = new Rect();
        textPaint = new TextPaint(mPaint);
        mPaint.getTextBounds(mTitle,0,mTitle.length(),mTextBound);
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desireByImg = getPaddingLeft() + mImage.getWidth() + getPaddingRight();
        int desireByText = getPaddingLeft() + mTextBound.width() + getPaddingRight();

        int desire = Math.max(desireByImg,desireByText);
        mWidth = getMySize(Math.min(desire,MeasureSpec.getSize(widthMeasureSpec)),widthMeasureSpec);

        desire = getPaddingBottom() + mImage.getHeight() + mTextBound.height() + getPaddingTop();

        mHeight = getMySize(Math.min(desire,MeasureSpec.getSize(heightMeasureSpec)),heightMeasureSpec);

        setMeasuredDimension(mWidth,mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);

        // 画边框
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mRect.left = getPaddingLeft();
        mRect.right = mWidth - getPaddingRight();
        mRect.top = getPaddingTop();
        mRect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Style.FILL);

        // 当前设置的宽度小于字体需要的宽度的时候，将字体改为xxx...
        if (mTextBound.width() > mWidth){
            String msg = TextUtils.ellipsize(mTitle,textPaint,mWidth - getPaddingLeft() - getPaddingRight(), TruncateAt.END).toString();
            canvas.drawText(msg,getPaddingLeft(),mHeight - getPaddingBottom(),mPaint);
        }else{
            // 正常情况下将字体居中
            canvas.drawText(mTitle,mWidth/2 - mTextBound.width()*1.0f/2,mHeight - getPaddingBottom(),mPaint);
        }

        // 取消使用掉的块
        mRect.bottom = mRect.bottom - mTextBound.height();
        if (mImageScaleType == IMAGE_SCALE_FITXY){
            canvas.drawBitmap(mImage,null,mRect,mPaint);
        }else{
            // 计算居中的矩形范围
            mRect.left = mWidth/2 - mImage.getWidth()/2;
            mRect.right = mWidth/2 + mImage.getWidth()/2;
            mRect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            mRect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage,null,mRect,mPaint);
        }
    }
}
