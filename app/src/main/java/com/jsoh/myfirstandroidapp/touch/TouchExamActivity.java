package com.jsoh.myfirstandroidapp.touch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;

import com.jsoh.myfirstandroidapp.R;

public class TouchExamActivity extends AppCompatActivity {


    private static final String TAG = TouchExamActivity.class.getSimpleName();

    private Animation mRotateAnimation;
    private Animation mShakeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_exam);

        findViewById(R.id.btn1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.translation_up));
        findViewById(R.id.btn2).startAnimation(AnimationUtils.loadAnimation(this, R.anim.translation_right_to_left));

        mRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(mRotateAnimation);
            }
        });

        mShakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        mShakeAnimation.setInterpolator(new CycleInterpolator(7));
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(mShakeAnimation);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent");
        Log.d(TAG, ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        Log.d(TAG, event.toString());
        return super.onTouchEvent(event);
    }

}
