package com.pandect.weatherexample.request.weathertoday;

import com.google.auto.value.AutoValue;

/**
 * Created by PROFESSORCORE on 7/23/17.
 */

@AutoValue
public abstract class WeatherObject {
    //id, main, description, icon
    public abstract int id();
    public abstract String main();
    public abstract String description();
    public abstract String icon();

    static Builder builder() {
        return new AutoValue_WeatherObject.Builder()
                .setId(-1)
                .setMain("")
                .setDescription("")
                .setIcon("");
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setId(int id);
        abstract Builder setMain(String main);
        abstract Builder setDescription(String description);
        abstract Builder setIcon(String icon);
        abstract WeatherObject build();
    }
}
