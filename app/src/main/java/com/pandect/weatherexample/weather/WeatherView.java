package com.pandect.weatherexample.weather;

import com.pandect.weatherexample.mvpbase.BaseView;

/**
 * Created by PROFESSORCORE on 7/22/17.
 */

public interface WeatherView extends BaseView {

    void getUserLocation();
    void showLocationPermissionDeniedDialog();
    void showLocationPermissionDeniedNeverAskAgainDialog();

    void showErrorDialog();
    void showNoConnectionErrorDialog();
    boolean isConnectedToData();

    void render(WeatherViewState viewState);
}
