
package com.jsoh.myfirstandroidapp.exam_thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsoh.myfirstandroidapp.R;

public class ThreadActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = ThreadActivity.class.getSimpleName();

    private TextView mCountTextView;
    private int mCount = 0;
    private Button mButton;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // Message 처리 부분
            Log.d(TAG, "" + msg.what);
        }
    };

    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Main Thread = UI Thread = Foreground Thread
        setContentView(R.layout.activity_thread);

        mCountTextView = (TextView) findViewById(R.id.count_text);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    mCount++;

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // UI Thread 에서 그리는 코드
                            mCountTextView.setText("" + mCount);
                        }
                    });

                    // 메세지를 담아서 보내는 방법 1
                    Message message = new Message();
                    message.what = 10;
                    mHandler.sendMessage(message);

                    // 2초 후에 what 값 보내기
                    mHandler.sendEmptyMessageDelayed(20, 2000);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // 작업 쓰레드 (worker thread) = background Thread
        // Thread에서 UI 갱신 불가!!!
        Thread thread = new Thread(mRunnable);
        thread.start();

        // 일반적인 방법
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread1.start();

    }

    @Override
    public void onClick(View v) {
        // Activity 에서 제공하는 Handler 기능
        runOnUiThread(mRunnable);

        // View 에서 제공하는 Handler
        v.post(mRunnable);
    }

}
