package com.jsoh.myfirstandroidapp.exam_broadcast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jsoh.myfirstandroidapp.R;

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        Intent intent = new Intent(Intent.ACTION_BATTERY_CHANGED);
    }
}
