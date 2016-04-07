package com.jsoh.myfirstandroidapp.surfaceview;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsoh.myfirstandroidapp.R;

public class CompassActivity extends Activity implements SensorEventListener {

    private static final String TAG = CompassActivity.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mAccelSensor;
    private Sensor mMagneticSensor;
    private float[] mGravity;
    private float[] mGeomagnetic;
    private float[] mOrientation;
    private TextView mDegreeTextView;
    private ImageView mNeedleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        mDegreeTextView = (TextView) findViewById(R.id.degree_text_view);
        mNeedleImageView = (ImageView) findViewById(R.id.iv_needle);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mAccelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagneticSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                mGravity = event.values.clone();
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeomagnetic = event.values.clone();
        }

        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[16];
            float I[] = new float[16];
            mOrientation = new float[3];

            if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)) {
                SensorManager.getOrientation(R, mOrientation);

                float degree = mOrientation[0] * 360 / (2 * 3.14159f);

                mDegreeTextView.setText(degree + " 도");
                mNeedleImageView.setRotation(360 - degree);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, sensor.toString() + ", accuracy : " + accuracy);
    }
}