package com.pandect.weatherexample.request.weathertoday;

import com.google.auto.value.AutoValue;

/**
 * Created by PROFESSORCORE on 7/23/17.
 */

@AutoValue
public abstract class WeatherTodayRequest {

    //not important now but if we had multiple servers to hit for testing
    public abstract String baseUrl();
    public abstract String apiKey();
    public abstract int lat();
    public abstract int lng();

    public static Builder builder(String baseUrl) {
        return new AutoValue_WeatherTodayRequest.Builder()
                .setBaseUrl(baseUrl)
                .setApiKey("")
                .setLat(0)
                .setLng(0);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setBaseUrl(String baseUrl);
        public abstract Builder setApiKey(String apiKey);
        public abstract Builder setLat(int lat); //for now just the number on the left of the decimal
        public abstract Builder setLng(int lng);
        public abstract WeatherTodayRequest build();
    }

}
