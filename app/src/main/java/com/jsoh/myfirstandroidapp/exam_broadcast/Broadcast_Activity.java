package com.jsoh.myfirstandroidapp.exam_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;

public class Broadcast_Activity extends AppCompatActivity implements View.OnClickListener {

    // 나만의 ACTION을 만듬
    public static final String ACTION_MY = "com.jsoh.myfirstandroidapp.ACTION_MY";
    private MyReceiver mMyReceiver;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_);


        findViewById(R.id.broad_btn).setOnClickListener(this);
        findViewById(R.id.kakaotalk_send_btn).setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.message_edt);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 리시버 등록
        mMyReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_MY);
        registerReceiver(mMyReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // 리시버 해제
        unregisterReceiver(mMyReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.broad_btn: {
                Intent intent = new Intent(ACTION_MY);
                // 브로드캐스트 발송
                sendBroadcast(intent);
                break;
            }
            case R.id.kakaotalk_send_btn: {
                Intent kakao = new Intent(Intent.ACTION_SEND);
                kakao.setPackage("com.kakao.talk");
                kakao.setType("text/plain");
                kakao.putExtra(Intent.EXTRA_TEXT, mEditText.getText().toString());
                if (kakao.resolveActivity(getPackageManager()) != null) {
                    startActivity(kakao);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.hyunseo.donghae.maplestoryexpmanager"));
                    startActivity(intent);
                }
                break;
            }
        }

    }

    // 이 액티비티 에서만 동작하는 리시버
    // 얘는 메니페스트 파일에 작성을 안함. 앱이 떠있는 동안에만 작동하게 만듬.
    // 내부클래스는 static 으로 사용하는게 좋음.
    private static class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_MY)) {
                Toast.makeText(context, "잘 받음 ㅋ", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
