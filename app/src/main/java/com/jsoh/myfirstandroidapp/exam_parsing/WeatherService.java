package com.jsoh.myfirstandroidapp.exam_parsing;

import com.jsoh.myfirstandroidapp.exam_parsing.models.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by junsuk on 16. 4. 14..
 */
public interface WeatherService {
    @GET("test/{user}/weather.php")
    Call<List<Weather>> getWeatherList(@Path("user") String user);

}
