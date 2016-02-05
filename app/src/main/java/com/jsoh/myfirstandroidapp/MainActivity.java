
package com.jsoh.myfirstandroidapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 화면에 layout 표시
        setContentView(R.layout.activity_main);

        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Toast.makeText(MainActivity.this, "button2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                Toast.makeText(MainActivity.this, "button3", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // // activity_main.xml 에 연결 됨
    // public void onClick(View view) {
    // Log.d(TAG, "클릭 잘 됨");
    // Toast.makeText(MainActivity.this, "클릭 잘 됨", Toast.LENGTH_SHORT).show();
    // }
}
