package com.jsoh.myfirstandroidapp.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by junsuk on 16. 4. 8..
 */
public class CustomButton2 extends Button {
    private static final String TAG = CustomButton2.class.getSimpleName();

    public CustomButton2(Context context) {
        super(context);
    }

    public CustomButton2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent");
        Log.i(TAG, event.toString());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        Log.i(TAG, event.toString());
        return super.onTouchEvent(event);
    }
}
