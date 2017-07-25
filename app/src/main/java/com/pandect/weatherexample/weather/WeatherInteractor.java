package com.pandect.weatherexample.weather;

import com.pandect.weatherexample.mvpbase.BaseInteractor;
import com.pandect.weatherexample.request.weathertoday.WeatherTodayRequest;
import com.pandect.weatherexample.request.weathertoday.WeatherTodayResponseObject;

/**
 * Created by PROFESSORCORE on 7/22/17.
 */

public interface WeatherInteractor extends BaseInteractor {

    void makeRequest(WeatherTodayRequest request);

    interface ResponseCallback {
        void onWeatherTodaySuccess(WeatherTodayResponseObject weatherTodayResponseObject);
        void onWeatherTodayFailure();
    }
}
