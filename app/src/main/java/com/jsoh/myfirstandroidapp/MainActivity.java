
package com.jsoh.myfirstandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CODE_PICTURE = 1000;
    public static final int REQUEST_CODE_CAMERA = 2000;
    private EditText mNameEditText;
    private EditText mAgeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 화면에 layout 표시
        setContentView(R.layout.activity_main);

        // 이름, 나이
        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mAgeEditText = (EditText) findViewById(R.id.age_edit_text);

        // 이벤트
        findViewById(R.id.next_activity_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // SecondActivity 로 전환하겠다는 intent
        Intent intent = new Intent(this, SecondActivity.class);

        // 이름, 나이 가져와서 intent에 추가
        intent.putExtra("name", mNameEditText.getText().toString());
        intent.putExtra("age", mAgeEditText.getText().toString());

        // intent의 정보를 토대로 다른 Activity를 시작
        startActivityForResult(intent, REQUEST_CODE_PICTURE);
    }

    // 결과를 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICTURE
                || resultCode == RESULT_OK
                || data != null) {
            // 사진을 선택
            String result = data.getStringExtra("result");
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

        } else if (requestCode == REQUEST_CODE_CAMERA) {
            // 사진찍어서 받을 때 처리
        }
    }
}
