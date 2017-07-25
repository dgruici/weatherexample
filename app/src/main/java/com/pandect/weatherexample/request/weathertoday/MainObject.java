package com.pandect.weatherexample.request.weathertoday;

import com.google.auto.value.AutoValue;

/**
 * Created by PROFESSORCORE on 7/23/17.
 */

@AutoValue
public abstract class MainObject {

    public abstract double temp();
    public abstract double pressure();
    public abstract double humidity();
    public abstract double tempMin();
    public abstract double tempMax();
    public abstract double seaLevel();
    public abstract double groundLevel();

    static Builder builder() {
        return new AutoValue_MainObject.Builder()
                .setTemp(0)
                .setPressure(0)
                .setHumidity(0)
                .setTempMin(0)
                .setTempMax(0)
                .setSeaLevel(0)
                .setGroundLevel(0);
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setTemp(double temp);
        abstract Builder setPressure(double pressure);
        abstract Builder setHumidity(double humidity);
        abstract Builder setTempMin(double tempMin);
        abstract Builder setTempMax(double tempMax);
        abstract Builder setSeaLevel(double seaLevel);
        abstract Builder setGroundLevel(double groundLevel);
        abstract MainObject build();
    }


}
//    "main": {
//        "temp": 285.514,
//                "pressure": 1013.75,
//                "humidity": 100,
//                "temp_min": 285.514,
//                "temp_max": 285.514,
//                "sea_level": 1023.22,
//                "grnd_level": 1013.75
//    }