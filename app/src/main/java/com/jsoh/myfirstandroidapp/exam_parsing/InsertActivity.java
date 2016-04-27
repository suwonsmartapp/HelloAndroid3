package com.jsoh.myfirstandroidapp.exam_parsing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.exam_parsing.models.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = InsertActivity.class.getSimpleName();
    private WeatherService mWeatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        findViewById(R.id.submit_button).setOnClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://suwonsmartapp.iptime.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mWeatherService = retrofit.create(WeatherService.class);
    }

    @Override
    public void onClick(View v) {
        String country = ((EditText)findViewById(R.id.country)).getText().toString();
        String weather = ((EditText)findViewById(R.id.weather)).getText().toString();
        String temperature = ((EditText)findViewById(R.id.temperature)).getText().toString();

        Call<Result> call = mWeatherService.insertWeather("ojs", weather, country, temperature);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Toast.makeText(InsertActivity.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(InsertActivity.this, "fail", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}
