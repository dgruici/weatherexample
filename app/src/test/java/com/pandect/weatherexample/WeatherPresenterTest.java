package com.pandect.weatherexample;

import com.pandect.weatherexample.request.weathertoday.WeatherTodayRequest;
import com.pandect.weatherexample.request.weathertoday.WeatherTodayResponseObject;
import com.pandect.weatherexample.weather.WeatherInteractor;
import com.pandect.weatherexample.weather.WeatherInteractorImpl;
import com.pandect.weatherexample.weather.WeatherPresenter;
import com.pandect.weatherexample.weather.WeatherPresenterImpl;
import com.pandect.weatherexample.weather.WeatherView;
import com.pandect.weatherexample.weather.WeatherViewState;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.VoidMethodStubbable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by PROFESSORCORE on 7/25/17.
 */

public class WeatherPresenterTest {

    private WeatherView view;
    private WeatherPresenter presenter;
    private WeatherInteractor interactor;

    @Before
    public void setup() {
        view = Mockito.mock(WeatherView.class);
        interactor = Mockito.mock(WeatherInteractorImpl.class);
        presenter = new WeatherPresenterImpl();
        presenter.attachView(view);
        presenter.setInteractor(interactor);
    }

    @Test
    public void testOnLocationReceived() {
        presenter.onLocationReceived(0,0);
        verify(interactor).makeRequest(any(WeatherTodayRequest.class));
    }

    @Test
    public void testOnLocationPermissionGranted() {
        presenter.onLocationPermissionGranted();
        verify(view).getUserLocation();
    }

    @Test
    public void testOnLocationPermissionDenied() {
        presenter.onLocationPermissionDenied();
        verify(view).showLocationPermissionDeniedDialog();
    }
    
    @Test
    public void testOnLocationReceivedAndWeatherRequestSuccessfulWithBadData() {
        when(view.isConnectedToData()).thenReturn(true);
        ((WeatherInteractor.ResponseCallback)presenter).onWeatherTodaySuccess(WeatherTodayResponseObject.builder().build());
        verify(view).showErrorDialog();
    }

    @Test
    public void testOnLocationReceivedAndWeatherRequestSuccessfulWithGoodData() {
        when(view.isConnectedToData()).thenReturn(true);
        ((WeatherInteractor.ResponseCallback)presenter).onWeatherTodaySuccess(
                WeatherTodayResponseObject.builder()
                        .setLat(1)
                        .setLng(1)
                        .build());
        verify(view).render(any(WeatherViewState.class));
    }


}
