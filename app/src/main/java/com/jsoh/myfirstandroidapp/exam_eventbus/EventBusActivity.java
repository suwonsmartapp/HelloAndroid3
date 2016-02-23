
package com.jsoh.myfirstandroidapp.exam_eventbus;

import com.jsoh.myfirstandroidapp.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class EventBusActivity extends AppCompatActivity {
    // 인터페이스
    public interface MyEventListener {
        void onEvent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        MyClass myClass = new MyClass();
        myClass.setOnMyEventListener(new MyEventListener() {
            @Override
            public void onEvent() {
                // 콜 백
                Toast.makeText(EventBusActivity.this, "콜백 받았다", Toast.LENGTH_SHORT).show();
            }
        });
        myClass.eventPublish();
    }

    // 이벤트 발생 하는 클래스
    class MyClass {

        private MyEventListener mmListener;

        public void setOnMyEventListener(MyEventListener listener) {
            mmListener = listener;
        }

        public void eventPublish() {
            try {
                Thread.sleep(3000);
                if (mmListener != null) {
                    mmListener.onEvent();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
