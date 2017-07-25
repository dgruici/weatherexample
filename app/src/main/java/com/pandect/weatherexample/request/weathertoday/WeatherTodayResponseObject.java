package com.pandect.weatherexample.request.weathertoday;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PROFESSORCORE on 7/23/17.
 */

@AutoValue
public abstract class WeatherTodayResponseObject {

    public abstract double lat();
    public abstract double lng();
    public abstract List<WeatherObject> weatherObjects();
    public abstract MainObject mainObject();

    public static Builder builder() {
        return new AutoValue_WeatherTodayResponseObject.Builder()
                .setLat(-1)
                .setLng(-1)
                .setWeatherObjects(new ArrayList<WeatherObject>())
                .setMainObject(MainObject.builder().build());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setLat(double lat);
        public abstract Builder setLng(double lng);
        public abstract Builder setWeatherObjects(List<WeatherObject> weatherObject);
        public abstract Builder setMainObject(MainObject mainObject);
        public abstract WeatherTodayResponseObject build();
    }

}
