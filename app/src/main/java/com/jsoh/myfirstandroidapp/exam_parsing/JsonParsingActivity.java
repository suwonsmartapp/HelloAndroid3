package com.jsoh.myfirstandroidapp.exam_parsing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonParsingActivity extends AppCompatActivity {

    private static final String TAG = JsonParsingActivity.class.getSimpleName();
    private List<Map<String, String>> mData;

    private OkHttpClient client = new OkHttpClient();

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

        new ParsingAsyncTask().execute("http://oh8112191.cafe24.com/test.json");
    }

    private String parsing(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private class ParsingAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // 파싱 시작
            String result = null;
            try {
                result = parsing(params[0]);

                // { }
                JSONObject jsonObject = new JSONObject(result);

                // [ ]
                JSONArray jsonArray = jsonObject.getJSONArray("person");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("name");
                    int age = object.getInt("age");
                    Log.d(TAG, name + ", " + age);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(JsonParsingActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}