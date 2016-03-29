package com.jsoh.myfirstandroidapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = MyService.class.getSimpleName();
    private int count = 0;

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    Log.d(TAG, "count : " + count);

                    count++;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (count == 20000) {
                        break;
                    }
                }
            }
        }).start();

        // 시스템에 의해 죽었을 때 다시 살리겠다
//        return START_STICKY;

        // START_REDELIVER_INTENT : START_STICKY 같은데 intent 재활용
        return START_REDELIVER_INTENT;

        // START_NOT_STICKY 안살리겠다
    }


    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        MyService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int getNumber() {
        return 100;
    }
}
