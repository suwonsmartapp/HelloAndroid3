package com.jsoh.myfirstandroidapp.exam_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener {
    // 나만의 액션
    public static final String ACTION_MY = "com.jsoh.myfirstandroidapp.ACTION_MY";
    private MyReceiver mMyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ACTION_MY);

        // 브로드캐스트 발송
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 리시버 등록
        mMyReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_MY);
        registerReceiver(mMyReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 리시버 해제
        unregisterReceiver(mMyReceiver);
    }

    // 이 Activity 에서만 동작하는 리시버
    // manifest에 작성 안 함
    private static class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_MY)) {
                Toast.makeText(context, "잘 받았다", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
