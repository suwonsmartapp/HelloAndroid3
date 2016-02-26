package com.jsoh.myfirstandroidapp.exam_parsing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonParsingActivity extends AppCompatActivity {

    private List<Map<String, String>> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parsing);

        ListView listView = (ListView) findViewById(R.id.list);

        mData = new ArrayList<>();

        SimpleAdapter adapter = new SimpleAdapter(this,
                mData,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "age"},
                new int[]{android.R.id.text1, android.R.id.text2});

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 파싱 시작
                try {
                    final String result = parsing("http://oh8112191.cafe24.com/test.json");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(JsonParsingActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    OkHttpClient client = new OkHttpClient();

    String parsing(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}