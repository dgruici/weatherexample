package com.pandect.weatherexample.weather;

import com.google.auto.value.AutoValue;

/**
 * Created by PROFESSORCORE on 7/23/17.
 *
 * This is something I have wanted to try, Model View Intent.
 * Instead of the regular Model View Presenter route where it calls separate items on the view for
 * updates, you create an immutable view state that can be verified in test
 */

@AutoValue
abstract class WeatherViewState {

    abstract String temperature();
    abstract String maxTemp();
    abstract String minTemp();
    abstract String weatherDescription();

    static Builder builder() {
        return new AutoValue_WeatherViewState.Builder()
                .setTemperature("0.0")
                .setMaxTemp("0.0")
                .setMinTemp("0.0")
                .setWeatherDescription("none");

    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setTemperature(String temperature);
        abstract Builder setMaxTemp(String maxTemp);
        abstract Builder setMinTemp(String minTemp);
        abstract Builder setWeatherDescription(String weatherDescription);
        abstract WeatherViewState build();
    }

}
