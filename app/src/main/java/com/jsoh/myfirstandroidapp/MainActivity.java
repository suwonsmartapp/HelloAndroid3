
package com.jsoh.myfirstandroidapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 화면에 layout 표시
        setContentView(R.layout.activity_main);

    }

    // activity_main.xml 에 연결 됨
    public void onClick(View view) {
        Log.d(TAG, "클릭 잘 됨");
    }
}
