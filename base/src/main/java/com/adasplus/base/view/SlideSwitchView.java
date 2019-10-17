package com.adasplus.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

import com.adasplus.base.R;


/**
 * Author:刘净辉
 * Date : 2019/9/23 16:49
 * Description :
 */
public class SlideSwitchView extends View implements View.OnClickListener {


    private int mCloseBackground = R.drawable.widget_icon_slidebutton_write_bg;
    private int mOpenBackground = R.drawable.widget_icon_slidebutton_yellow_bg;
    private int mSlideImage = R.drawable.widget_icon_slidebutton_write_slider;

    private Bitmap mOpenBitmap;
    private Bitmap mCloseBitmap;
    private Bitmap mSlideBitmap;
    private Bitmap mBackground;

    private boolean mIsOpen;
    private int mSlideTotalWidth;
    private int mSlideLeft;
    private int mSlideMax;

    private OnSwitchStatusChangeListener mOnSwitchStatusChangeListener;

    public SlideSwitchView(Context context) {
        this(context, null);
    }

    public SlideSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.SlideSwitchView);
        int openResourceId = typedArray.getResourceId(R.styleable.SlideSwitchView_openBackground, -1);
        int closeResourceId = typedArray.getResourceId(R.styleable.SlideSwitchView_closeBackground, -1);
        int slideImageResourceId = typedArray.getResourceId(R.styleable.SlideSwitchView_slideImage, -1);
        mIsOpen = typedArray.getBoolean(R.styleable.SlideSwitchView_isOpenSwitch, false);
        Log.e("mIsOpen", "mIsOpen:" + mIsOpen);
        if (openResourceId != -1) {
            mOpenBackground = openResourceId;
        }

        if (closeResourceId != -1) {
            mCloseBackground = closeResourceId;
        }

        if (slideImageResourceId != -1) {
            mSlideImage = slideImageResourceId;
        }
        typedArray.recycle();

        init();
    }

    private void init() {

        mOpenBitmap = BitmapFactory.decodeResource(getResources(), mOpenBackground);
        mCloseBitmap = BitmapFactory.decodeResource(getResources(), mCloseBackground);
        mSlideBitmap = BitmapFactory.decodeResource(getResources(), mSlideImage);
        mSlideTotalWidth = mOpenBitmap.getWidth();
        mSlideMax = mSlideTotalWidth - mSlideBitmap.getWidth();

        if (mIsOpen) {
            mSlideLeft = mSlideMax;
        }
        setOpen(mIsOpen);
        setOnClickListener(this);
        // setOnTouchListener(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //计算宽高比
        float v = (float) mBackground.getWidth() / mBackground.getHeight();
        if (widthSpecMode == MeasureSpec.EXACTLY
                && heightSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        } else if (widthSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSpecSize, (int) (widthSpecSize / v));
        } else if (heightSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension((int) (widthSpecSize * v), heightSpecMode);
        } else {
            setMeasuredDimension(mBackground.getWidth(), mBackground.getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBackground, 0, 0, null);
        canvas.drawBitmap(mSlideBitmap, mSlideLeft, 0, null);
    }

    private void setBackground(boolean isOpen) {
        mBackground = isOpen ? mOpenBitmap : mCloseBitmap;
    }


    public boolean isOpen() {
        return mIsOpen;
    }


    public void setOpen(boolean isOpen) {
        mIsOpen = isOpen;
        setBackground(isOpen);
        if (mIsOpen) {
            mSlideLeft = mSlideMax;
        } else {
            mSlideLeft = 0;
        }
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        setOpen(!mIsOpen);
        setBackground(mIsOpen);
        invalidate();
        if (mOnSwitchStatusChangeListener != null) {
            mOnSwitchStatusChangeListener.onSwitchStatus(mIsOpen);
        }
    }


    public interface OnSwitchStatusChangeListener {
        void onSwitchStatus(boolean status);
    }

    public void setOnSwitchStatusChangeListener(OnSwitchStatusChangeListener listener) {
        mOnSwitchStatusChangeListener = listener;
    }
}
