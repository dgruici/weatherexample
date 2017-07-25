package com.pandect.weatherexample.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PROFESSORCORE on 7/11/17.
 */

public interface ApiEndpoints {

    @GET("/data/2.5/weather?units=metric")
    Call<ResponseBody> getWeather(@Query("lat") int lat,
                                  @Query("lon") int lng,
                                  @Query("appid") String appId);
}
