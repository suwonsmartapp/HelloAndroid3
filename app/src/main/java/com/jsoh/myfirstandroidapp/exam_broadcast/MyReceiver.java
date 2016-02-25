package com.jsoh.myfirstandroidapp.exam_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            Toast.makeText(context, "충전 상태 변경 됨", Toast.LENGTH_SHORT).show();

            // 내가 수신 한 다음에 방송을 제거
            abortBroadcast();
        }
    }
}
