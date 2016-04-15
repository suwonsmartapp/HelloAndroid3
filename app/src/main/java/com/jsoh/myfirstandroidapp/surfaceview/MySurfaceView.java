package com.jsoh.myfirstandroidapp.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.WorkerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jsoh.myfirstandroidapp.R;

import java.util.ArrayList;
import java.util.List;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, SensorEventListener {

    private static final String TAG = MySurfaceView.class.getSimpleName();
    private final Paint mPaint;
    private final SurfaceHolder mSurfaceHolder;

    private Thread mThread;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float mPitch = 0;
    private float mRoll = 0;

    private Ball mBall;

    private Drawable mDrawable;
    private int mWidth = 0;
    private int mHeight = 0;

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

        // 센서 준비
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        Log.d(TAG, deviceSensors.toString());

        mDrawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);

        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(final SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");

        mThread = null;
    }

    int x = 0;
    int y = 0;

    long mTime = 0;
    List<Missile> mMissileList;

    @WorkerThread
    @Override
    public void run() {
        mMissileList = new ArrayList<>();
        mTime = System.currentTimeMillis();
        while (mThread != null) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 화면을 lock 하고 그 순간의 canvas를 얻는다
            Canvas canvas = mSurfaceHolder.lockCanvas();

            if (canvas != null) {
                // 그리기
                canvas.drawColor(Color.RED);
                canvas.drawCircle(mPitch, mRoll, 50, mPaint);

                if (System.currentTimeMillis() - mTime > 1000) {
                    mTime = System.currentTimeMillis();
                    Missile missile = new Missile();
                    missile.setTarget(mPitch, mRoll);
                    mMissileList.add(missile);
                }

                // 미사일들
                for (Missile missile : mMissileList) {
                    missile.draw(canvas);
                }

                // 화면에 반영
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        Log.d(TAG, "" + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);

        if (mPitch == 0) {
            mPitch = event.values[0];
        } else {
            mPitch -= event.values[0];
        }
        if (mRoll == 0) {
            mRoll = event.values[1];
        } else {
            mRoll += event.values[1];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
