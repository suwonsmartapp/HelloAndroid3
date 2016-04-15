package com.jsoh.myfirstandroidapp.surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Missile {
    private static final String TAG = Missile.class.getSimpleName();
    private final Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Rect mRect;

    private int mColor = Color.WHITE;
    private int mRadius = 10;
    private float mSpeed = 1;
    private static float DEFAULT_MOVEMENT = 3;

    private float mX = 0;
    private float mY = 0;
    private float m = 1;

    public Missile() {
        mRect = new Rect(0, 0, mRadius * 2, mRadius * 2);
        mPaint = new Paint();
        mPaint.setColor(mColor);
    }

    public void setTarget(float x, float y) {
        m = (y - mY) /(x - mX);   // 기울기
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(mX, mY, mRadius, mPaint);
        mX = mX + mSpeed * DEFAULT_MOVEMENT;
        mY = m * mX;
//        Log.d(TAG, "draw: " + hashCode());
    }
}
