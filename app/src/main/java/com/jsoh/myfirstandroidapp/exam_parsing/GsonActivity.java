package com.jsoh.myfirstandroidapp.exam_parsing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.exam_parsing.models.Weather;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GsonActivity extends AppCompatActivity {
    private static final String TAG = GsonActivity.class.getSimpleName();
    private ListView mListView;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);

        mListView = (ListView) findViewById(R.id.list_view);
        mProgress = (ProgressBar) findViewById(R.id.progress);

        new WeatherAsyncTask().execute();
    }

    private class WeatherAsyncTask extends AsyncTask<Void, Void, List<Weather>> {
        private OkHttpClient client = new OkHttpClient();

        @WorkerThread
        @Override
        protected List<Weather> doInBackground(Void... params) {
            List result = null;
            try {
//                JSONObject jsonObject = new JSONObject(parsing("https://raw.githubusercontent.com/ChoiJinYoung/iphonewithswift2/master/weather.json"));
//                JSONObject weatherinfo = jsonObject.getJSONObject("weatherinfo");
//                JSONArray local = weatherinfo.getJSONArray("local");

                String local = parsing("http://suwonsmartapp.iptime.org/test/ojs/weather.php");

                Gson gson = new Gson();
                result = gson.fromJson(local.toString(), new TypeToken<List<Weather>>(){}.getType());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @UiThread
        @Override
        protected void onPostExecute(List<Weather> result) {
            if (result != null) {
                WeatherAdapter adapter = new WeatherAdapter(result);
                mListView.setAdapter(adapter);

                mProgress.setVisibility(View.GONE);
            }

        }

        private String parsing(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }


    private static class WeatherAdapter extends BaseAdapter {

        private final List<Weather> mData;

        public WeatherAdapter(List<Weather> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
                holder = new ViewHolder();
                holder.country = (TextView) convertView.findViewById(R.id.country);
                holder.temperature = (TextView) convertView.findViewById(R.id.temperature);
                holder.weather = (ImageView) convertView.findViewById(R.id.weather);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Weather weather = (Weather) getItem(position);

            holder.temperature.setText(weather.getTemperature());
            holder.country.setText(weather.getCountry());

            int imageRes = R.mipmap.ic_launcher;
            switch (weather.getWeather()) {
                case "맑음":
                    imageRes = R.drawable.sunny;
                    break;
                case "비":
                    imageRes = R.drawable.rainy;
                    break;
                case "흐림":
                    imageRes = R.drawable.cloudy;
                    break;
                case "눈":
                    imageRes = R.drawable.snow;
                    break;
                case "우박":
                    imageRes = R.drawable.blizzard;
                    break;
            }
            holder.weather.setImageResource(imageRes);

            return convertView;
        }

        static class ViewHolder {
            ImageView weather;
            TextView country;
            TextView temperature;
        }
    }
}
