package com.jsoh.myfirstandroidapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class IntentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCallBtn;
    private Button mWebBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        mCallBtn = (Button) findViewById(R.id.call_btn);
        mWebBtn = (Button) findViewById(R.id.web_btn);

        mCallBtn.setOnClickListener(this);
        mWebBtn.setOnClickListener(this);

        // findViewById(R.id.call_btn).setOnClickListener(this);
        // findViewById(R.id.web_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_btn:
                dialPhoneNumber("010-4899-3829");
                break;
            case R.id.web_btn:
                openWebPage("http://www.naver.com");
                break;
        }
    }

    public void dialPhoneNumber(String phoneNumber) {
//        Intent intent2 = new Intent();
//        intent2.setAction(Intent.ACTION_DIAL);

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        // 이 intent를 처리할 수 있는 Activity가 있을 때 수행
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
