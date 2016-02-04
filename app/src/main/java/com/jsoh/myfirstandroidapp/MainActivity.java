
package com.jsoh.myfirstandroidapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 화면에 layout 표시
        setContentView(R.layout.activity_main);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "클릭 매우 잘 됨", Toast.LENGTH_SHORT).show();
    }

    // // activity_main.xml 에 연결 됨
    // public void onClick(View view) {
    // Log.d(TAG, "클릭 잘 됨");
    // Toast.makeText(MainActivity.this, "클릭 잘 됨", Toast.LENGTH_SHORT).show();
    // }
}
