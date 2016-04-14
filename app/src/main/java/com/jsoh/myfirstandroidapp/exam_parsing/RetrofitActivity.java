package com.jsoh.myfirstandroidapp.exam_parsing;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.exam_parsing.models.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity implements Callback<List<Weather>> {
    private static final String TAG = RetrofitActivity.class.getSimpleName();
    private ListView mListView;
    private ProgressBar mProgress;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WeatherAdapter mAdapter;

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);

        mListView = (ListView) findViewById(R.id.list_view);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLACK, Color.YELLOW, Color.GREEN);

        mAdapter = new WeatherAdapter(null);
        mListView.setAdapter(mAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://suwonsmartapp.iptime.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final WeatherService service = retrofit.create(WeatherService.class);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        service.getWeatherList("ojs").enqueue(RetrofitActivity.this);
                    }
                }, 3000);

            }
        });

        service.getWeatherList("ojs").enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Weather>> call, retrofit2.Response<List<Weather>> response) {
        if (response.body() != null) {
            mAdapter.mData = response.body();
            mAdapter.notifyDataSetChanged();

            Toast.makeText(RetrofitActivity.this, "날씨가 갱신되었습니다.", Toast.LENGTH_SHORT).show();

            mProgress.setVisibility(View.GONE);
        }

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onFailure(Call<List<Weather>> call, Throwable t) {

    }

    private static class WeatherAdapter extends BaseAdapter {

        private List<Weather> mData;

        public WeatherAdapter(List<Weather> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            if (mData == null) {
                return 0;
            }
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
