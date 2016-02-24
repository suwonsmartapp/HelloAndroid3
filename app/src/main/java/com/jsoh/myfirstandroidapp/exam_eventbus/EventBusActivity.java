
package com.jsoh.myfirstandroidapp.exam_eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    public void onDataReceived(MyEvent data) {
        if (data instanceof MyEvent1) {
            MyEvent1 event = (MyEvent1) data;
            Toast.makeText(EventBusActivity.this, event.name + ", " + event.age, Toast.LENGTH_SHORT)
                    .show();
        } else if (data instanceof MyEvent2) {
            MyEvent2 event = (MyEvent2) data;
            Toast.makeText(EventBusActivity.this, event.isMarried + ", " + event.age,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 이벤트 발생 하는 클래스
    class MyClass {

        public void eventPublish() {
            EventBus.getDefault().post(new MyEvent1(10, "꼬마"));

            EventBus.getDefault().post(new MyEvent2(50, false));
        }
    }
}
