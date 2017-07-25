package com.pandect.weatherexample.weather;

import com.pandect.weatherexample.mvpbase.BasePresenter;

/**
 * Created by PROFESSORCORE on 7/22/17.
 */

public interface WeatherPresenter extends BasePresenter<WeatherView> {

    void setInteractor(WeatherInteractor interactor);

    void onLocationReceived(int lat, int lng);
    void onLocationPermissionGranted();
    void onLocationPermissionDenied();
    void onLocationPermissionDeniedNeverAskAgain();

}
