package com.pandect.weatherexample.weather;

import android.support.annotation.NonNull;

import com.pandect.weatherexample.mvpbase.BaseInteractorImpl;
import com.pandect.weatherexample.request.weathertoday.WeatherTodayParser;
import com.pandect.weatherexample.request.weathertoday.WeatherTodayRequest;
import com.pandect.weatherexample.request.weathertoday.WeatherTodayResponseObject;
import com.pandect.weatherexample.retrofit.ApiEndpoints;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by PROFESSORCORE on 7/22/17.
 */

public class WeatherInteractorImpl
        extends
            BaseInteractorImpl
        implements
            WeatherInteractor,
            Callback<ResponseBody> {

    private static final String WEATHER_TODAY = "weatherTodayRequest";
    private String request;

    private WeatherInteractor.ResponseCallback callback;

    public WeatherInteractorImpl(WeatherInteractor.ResponseCallback callback) {
        this.callback = callback;
        request = WEATHER_TODAY;
    }

    @Override
    public void makeRequest(WeatherTodayRequest request) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(request.baseUrl())
                .build();

        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);
        Call<ResponseBody> call = endpoints.getWeather(request.lat(), request.lng(), request.apiKey());
        System.out.println("WeatherInteractor URL: " + call.request().url());
        call.enqueue(this);
    }

    /*
    In a real world use, the Retrofit client would be separate
    This would allow us to instantiate it once and make use of the Executor pool in the case we need
    to make multiple requests at once (ex. a session token refresh and a data fetch)
    For the sake of brevity, and that we have only one call being made, I extended the response callbacks
    here.
     */
    @Override
    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
        if(response.isSuccessful()) {
            try {
                String body = response.body().string();
                onRequestSuccess(body);
            }
            /*
            Even though these are the same, I would still be interested in logging them separately
            as non-fatals.
             */
            catch (IOException e) {
                e.printStackTrace();
                callback.onWeatherTodayFailure();
            } catch (NullPointerException e) {
                e.printStackTrace();
                callback.onWeatherTodayFailure();
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
        /*
        As we are currently undefined on error structure, a simple "Sorry, there was a problem"
        should be sufficient
        */
        onRequestFailure(call, t);
    }

    /*
    With Retrofit being stand alone, this would take on the task of being the call back from the
    Retrofit client. I prefer to parse items in the interactor as I have had APIs in the past give
    a 200 to show success, but also attach error codes in the success response body giving the
    illusion that it worked. This way I can still trigger an onFailure state if I do have that
    situation.
    Also, with parsing here, I can mock interactors far more easily as I don't care if it was a
    successful call, just what data object I am returning.
     */
    @Override
    public void onRequestSuccess(String responseBody) {
        System.out.println("WeatherInteractor - SuccessResponse: " + responseBody);
        WeatherTodayResponseObject responseObject = WeatherTodayParser.parse(responseBody);
        callback.onWeatherTodaySuccess(responseObject);
    }

    @Override
    public void onRequestFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
        System.out.println("WeatherInteractor - FailureResponse:");
        t.printStackTrace();
        callback.onWeatherTodayFailure();
    }
}
