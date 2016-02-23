
package com.jsoh.myfirstandroidapp.exam_eventbus;

import com.jsoh.myfirstandroidapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class EventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        EventBus.getDefault().register(this);

        MyClass myClass = new MyClass();
        myClass.eventPublish();

    }

    @Subscribe
    public void onEvent(String data) {
        Toast.makeText(EventBusActivity.this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 이벤트 발생 하는 클래스
    class MyClass {

        public void eventPublish() {
            EventBus.getDefault().post("잘 됐다");
        }
    }
}
