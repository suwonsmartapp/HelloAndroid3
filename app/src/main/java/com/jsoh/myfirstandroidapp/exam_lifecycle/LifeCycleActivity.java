
package com.jsoh.myfirstandroidapp.exam_lifecycle;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LifeCycleActivity extends AppCompatActivity {

    private static final String TAG = LifeCycleActivity.class.getSimpleName();

    private String mValue = "값 없음";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = new Button(this);
        button.setText("버튼");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(LifeCycleActivity.this,
                // ListViewActivity.class));
                mValue = "값 바뀜!!";
            }
        });

        setContentView(button);

        mValue = "값 있음!!!!!!";

        Log.d(TAG, "onCreate : 생성");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume : 재개");

        Log.d(TAG, "값 : " + mValue);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause : 일시정지");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart : 시작");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart : 재시작");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop : 중지");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy : 소멸");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d(TAG, "onConfigurationChanged");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState");

        // 강제 종료 시 보존해야 하는 값을 outState 에 저장
        outState.putString("value", mValue);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState");

        // 복원
        if (savedInstanceState != null) {
            mValue = savedInstanceState.getString("value");
        }
    }
}
