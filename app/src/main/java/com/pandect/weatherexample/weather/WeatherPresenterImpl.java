package com.pandect.weatherexample.weather;

import com.pandect.weatherexample.BuildConfig;
import com.pandect.weatherexample.mvpbase.BasePresenterImpl;
import com.pandect.weatherexample.request.weathertoday.WeatherTodayRequest;
import com.pandect.weatherexample.request.weathertoday.WeatherTodayResponseObject;

/**
 * Created by PROFESSORCORE on 7/22/17.
 */

public class WeatherPresenterImpl
        extends
            BasePresenterImpl<WeatherView>
        implements
            WeatherPresenter,
            WeatherInteractor.ResponseCallback {

    private WeatherInteractor interactor;

    public WeatherPresenterImpl() {
        interactor = new WeatherInteractorImpl(this);
    }

    /*
    Prefer this is injected so we do not have code smell with test only functions
    For sake of time and testability, setter method here for unit testing
     */
    @Override
    public void setInteractor(WeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onLocationReceived(int lat, int lng) {
        WeatherTodayRequest request =
                WeatherTodayRequest.builder("http://api.openweathermap.org")
                    .setApiKey(BuildConfig.MAP_API_KEY)
                    .setLat(lat)
                    .setLng(lng)
                    .build();

        interactor.makeRequest(request);
    }

    @Override
    public void onLocationPermissionGranted() {
        if(!isViewAttached()) {
            return;
        }
        getView().getUserLocation();
    }

    @Override
    public void onLocationPermissionDenied() {
        if(!isViewAttached()) {
            return;
        }
        getView().showLocationPermissionDeniedDialog();
    }

    @Override
    public void onLocationPermissionDeniedNeverAskAgain() {
        if(!isViewAttached()) {
            return;
        }
        getView().showLocationPermissionDeniedNeverAskAgainDialog();
    }

    @Override
    public void onWeatherTodaySuccess(WeatherTodayResponseObject weatherTodayResponseObject) {
        if(!isViewAttached()) {
            return;
        }

        if(weatherTodayResponseObject.lat() == -1 && weatherTodayResponseObject.lng() == -1) {
            onWeatherTodayFailure();
            return;
        }

        String weatherDescription = "None!";
        if(weatherTodayResponseObject.weatherObjects().size() > 0) {
            weatherDescription = weatherTodayResponseObject.weatherObjects().get(0).description();
        }

        WeatherViewState viewState = WeatherViewState.builder()
                .setTemperature(Double.toString(weatherTodayResponseObject.mainObject().temp()) + " C")
                .setMaxTemp(Double.toString(weatherTodayResponseObject.mainObject().tempMax()) + " C")
                .setMinTemp(Double.toString(weatherTodayResponseObject.mainObject().tempMin()) + " C")
                .setWeatherDescription(weatherDescription.toUpperCase())
                .build();

        getView().render(viewState);

    }

    @Override
    public void onWeatherTodayFailure() {
        if(!isViewAttached()) {
            return;
        }
        if(getView().isConnectedToData()) {
            getView().showErrorDialog();
        }
        else {
            getView().showNoConnectionErrorDialog();
        }
    }
}
