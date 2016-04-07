package com.jsoh.myfirstandroidapp.surfaceview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Ball {
    private Drawable mDrawable;
    private int mWidth;
    private int mHeight;
    private Rect mRect;

    private Rect mViewRect;

    private double mDx;
    private double mDy;

    private final double mAcceleration = 1.5;

    private final double mAttenuator = 0.02;

    private final double mBound = 0.5;

    public Ball(Drawable drawable, Rect viewRect) {
        mDrawable = drawable;
        mWidth = drawable.getIntrinsicWidth() / 2;
        mHeight = drawable.getIntrinsicHeight() / 2;
        mRect = new Rect(0, 0, mWidth, mHeight);

        mViewRect = viewRect;

        mRect.offset(
                (mViewRect.right - mViewRect.left - mWidth) / 2,
                (mViewRect.bottom - mViewRect.top - mHeight) / 2);

        mDx = 0;
        mDy = 0;
    }

    public void move(float mPitch, float mRoll) {
        double dx = -Math.sin(Math.toRadians(mRoll))
                * mAcceleration;
        double dy = -Math.sin(Math.toRadians(mPitch))
                * mAcceleration;

        mDx += mDx * -mAttenuator + dx;
        mDy += mDy * -mAttenuator + dy;

        mRect.offset((int) mDx, (int) mDy);

        if (mRect.left < 0) {
            mDx = -mDx * mBound;
            mRect.left = 0;
            mRect.right = mRect.left + mWidth;
        } else if (mRect.top < 0) {
            mDy = -mDy * mBound;
            mRect.top = 0;
            mRect.bottom = mRect.top + mHeight;
        } else if (mRect.bottom > mViewRect.bottom) {
            mDy = -mDy * mBound;
            mRect.bottom = mViewRect.bottom;
            mRect.top = mRect.bottom - mHeight;
        } else if (mRect.right > mViewRect.right) {
            mDx = -mDx * mBound;
            mRect.right = mViewRect.right;
            mRect.left = mRect.right - mWidth;
        }
    }

    public void draw(Canvas canvas) {
        mDrawable.setBounds(mRect);
        mDrawable.draw(canvas);
    }
}
