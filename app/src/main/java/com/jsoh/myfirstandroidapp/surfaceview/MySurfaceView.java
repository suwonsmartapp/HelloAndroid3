package com.jsoh.myfirstandroidapp.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.WorkerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private static final String TAG = MySurfaceView.class.getSimpleName();
    private final Paint mPaint;
    private final SurfaceHolder mSurfaceHolder;

    private Thread mThread;

    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");

        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(final SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");

        mThread = null;
    }

    int x = 0;
    int y = 0;

    @WorkerThread
    @Override
    public void run() {
        while (mThread != null) {
            // 화면을 lock 하고 그 순간의 canvas를 얻는다
            Canvas canvas = mSurfaceHolder.lockCanvas();

            if (canvas != null) {
                // 그리기
                canvas.drawColor(Color.RED);
                canvas.drawCircle(x, y, 100, mPaint);

                x += 10;
                y += 10;
                // 화면에 반영
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }
}
