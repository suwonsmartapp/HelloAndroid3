
package com.jsoh.myfirstandroidapp.exam_parsing;

import com.jsoh.myfirstandroidapp.exam_parsing.models.Result;
import com.jsoh.myfirstandroidapp.exam_parsing.models.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by junsuk on 16. 4. 14..
 */
public interface WeatherService {
    @GET("test/{user}/weather.php")
    Call<List<Weather>> getWeatherList(@Path("user") String user);

    // http://suwonsmartapp.iptime.org/test/ojs/insert_weather.php?weather=맑음&country=서울&temperature=20
    @GET("test/{user}/insert_weather.php")
    Call<Result> insertWeather(@Path("user") String user,
                               @Query("weather") String weather,
                               @Query("country") String country,
                               @Query("temperature") String temperature);

}
