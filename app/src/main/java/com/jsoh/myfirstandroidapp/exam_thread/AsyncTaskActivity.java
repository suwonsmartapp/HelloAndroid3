
package com.jsoh.myfirstandroidapp.exam_thread;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsoh.myfirstandroidapp.R;

/**
 * Created by junsuk on 16. 2. 24..
 */
public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AsyncTaskActivity.class.getSimpleName();
    private TextView mCountTextView;
    private Button mButton;
    private MyAsyncTask mTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        mCountTextView = (TextView) findViewById(R.id.count_text);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);

        // AsyncTask 제약사항
        // 1. 반드시 UI Thread 에서 실행해야 함
        // 2. new 도 UI Thread 에서 생성해야 함
        // 3. 절대로 직접 콜백을 호출하지 말 것
        // 4. 한번만 실행할 수 있다
        mTask = new MyAsyncTask();

        // 순서대로 실행
        mTask.execute();
    }

    @Override
    public void onClick(View v) {
        // 동시에 여러 AsyncTask가 동작
        // new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        mTask.cancel(true);
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private int mmCount = 0;
        private ProgressDialog mmDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mmDialog = new ProgressDialog(AsyncTaskActivity.this);
            mmDialog.setTitle("기다리세요");
            // mmDialog.setCancelable(false); // 취소 막기
            mmDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Background Thread
            for (int i = 0; i < 4; i++) {
                mmCount++;

                // UI 갱신
                // onProgressUpdate 를 호출
                publishProgress();

                // onProgressUpdate() 절대로 직접 콜백을 호출하지 말 것

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            // UI Thread
            mCountTextView.setText("" + mmCount);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mmDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            Log.d(TAG, "onCancelled");
        }
    }
}
