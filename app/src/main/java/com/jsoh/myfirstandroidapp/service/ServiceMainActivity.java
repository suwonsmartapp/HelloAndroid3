package com.jsoh.myfirstandroidapp.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;

public class ServiceMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);

        findViewById(R.id.start_service_button).setOnClickListener(this);
        findViewById(R.id.intentservice_button).setOnClickListener(this);
        findViewById(R.id.bound_service_button).setOnClickListener(this);
        findViewById(R.id.call_method_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service_button:
                Intent intent = new Intent("com.jsoh.myAction");
                intent.setClass(this, MyService.class);

                startService(intent);
                break;
            case R.id.intentservice_button:
                startService(new Intent(this, MyIntentService.class));
                break;
            case R.id.bound_service_button:
                bindService(new Intent(this, MyService.class),
                        mConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.call_method_button:
                if (mBound) {
                    Toast.makeText(ServiceMainActivity.this, "" + mService.getNumber(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private MyService mService;
    private boolean mBound;
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            Log.d("ServiceConnection", "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Log.d("ServiceConnection", "onServiceDisconnected");
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}
