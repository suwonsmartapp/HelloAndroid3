package com.jsoh.myfirstandroidapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jsoh.myfirstandroidapp.exam_broadcast.BroadcastActivity;
import com.jsoh.myfirstandroidapp.exam_coffee.CoffeeActivity;
import com.jsoh.myfirstandroidapp.exam_eventbus.EventBusActivity;
import com.jsoh.myfirstandroidapp.exam_fab_dialog.FabAndDialogActivity;
import com.jsoh.myfirstandroidapp.exam_fragment.FragmentExamActivity;
import com.jsoh.myfirstandroidapp.exam_fragment.exam1.Exam203Activity;
import com.jsoh.myfirstandroidapp.exam_fragment.exam2.Exam212Activity;
import com.jsoh.myfirstandroidapp.exam_lifecycle.LifeCycleActivity;
import com.jsoh.myfirstandroidapp.exam_listview.ListViewActivity;
import com.jsoh.myfirstandroidapp.exam_parsing.JsonParsingActivity;
import com.jsoh.myfirstandroidapp.exam_thread.AsyncTaskActivity;
import com.jsoh.myfirstandroidapp.exam_thread.ThreadActivity;
import com.jsoh.myfirstandroidapp.exam_viewpager.ScreenSlideActivity;
import com.jsoh.myfirstandroidapp.exam_webview.WebViewActivity;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 예제의 첫 화면
 */
public class MainListActivity extends ListActivity {

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                private final Collator collator = Collator.getInstance();

                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    return collator.compare(map1.get("title"), map2.get("title"));
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_1, new String[] {
                "title"
        },
                new int[] {
                        android.R.id.text1
                }));
        getListView().setTextFilterEnabled(true);
    }

    protected List<Map<String, Object>> getData() {
        List<Map<String, Object>> myData = new ArrayList<>();

        // 메뉴 추가 부분
        addItem(myData, "버튼 이벤트", MainActivity.class);
        addItem(myData, "ScrollView", ScrollActivity.class);
        addItem(myData, "암시적 인텐트", IntentActivity.class);
        addItem(myData, "커피 주문 예제", CoffeeActivity.class);
        addItem(myData, "WebView 예제", WebViewActivity.class);
        addItem(myData, "ListView 예제", ListViewActivity.class);
        addItem(myData, "LifeCycle", LifeCycleActivity.class);
        addItem(myData, "Fab + dialog", FabAndDialogActivity.class);
        addItem(myData, "fragment", FragmentExamActivity.class);
        addItem(myData, "fragment - exam1", Exam203Activity.class);
        addItem(myData, "fragment - exam2", Exam212Activity.class);
        addItem(myData, "ViewPager", ScreenSlideActivity.class);
        addItem(myData, "EventBus", EventBusActivity.class);
        addItem(myData, "Thread", ThreadActivity.class);
        addItem(myData, "AsyncTask", AsyncTaskActivity.class);
        addItem(myData, "Broadcast Receiver", BroadcastActivity.class);
        addItem(myData, "Json Parsing", JsonParsingActivity.class);
        // ----- 메뉴 추가 여기까지

        // 이름 순 정렬
        // Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    protected void addItem(List<Map<String, Object>> data, String name, Class cls) {
        this.addItem(data, name, new Intent(this, cls));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);

        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }
}
