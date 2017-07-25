package com.pandect.weatherexample.weather;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.pandect.weatherexample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends AppCompatActivity implements WeatherView {

    @BindView(R.id.text_view_temperature)
    TextView textViewTemperature;
    @BindView(R.id.text_view_temp_min_value)
    TextView textViewTempMinValue;
    @BindView(R.id.text_view_temp_max_value)
    TextView textViewTempMaxValue;
    @BindView(R.id.text_view_forecast_value)
    TextView textViewForecastValue;
    private FusedLocationProviderClient locationClient;
    private LocationRequest locationRequest;
    private WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        locationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest()
                .setInterval(2000) //2 seconds
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        presenter = new WeatherPresenterImpl();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        presenter.attachView(this);
        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachView();
        stopLocationUpdates();
    }

    //Location Services
    private LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            if (!locationAvailability.isLocationAvailable()) {
                //this will trigger after prolonged periods of no location available, even if we already got one
                onLocationError();
            }
        }

        @Override
        public void onLocationResult(LocationResult locationResult) {
            onLocationReceived(locationResult.getLastLocation());
            stopLocationUpdates();
        }
    };

    private void onLocationError() {
        AlertDialog.Builder locationError = new AlertDialog.Builder(this);
        locationError.setTitle("Error!")
                .setMessage("Unable to get your location at this time. Please try again later!")
                .setPositiveButton("Okay", null);
        locationError.create().show();
    }

    /*
    Per API samples, lat and lng come in as ints
    https://openweathermap.org/current
     */
    private void onLocationReceived(Location location) {
        presenter.onLocationReceived((int) location.getLatitude(), (int) location.getLongitude());
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            checkPermissions();
        } else {
            locationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    private void stopLocationUpdates() {
        if (null != locationClient) {
            locationClient.removeLocationUpdates(locationCallback);
        }
    }

    //Permissions Request
    final private int REQUEST_LOCATION_SERVICES = 1009;

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                presenter.onLocationPermissionDenied();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_SERVICES);
            }
        } else {
            presenter.onLocationPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_SERVICES:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdates();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showLocationPermissionDeniedDialog();
                    } else {
                        showLocationPermissionDeniedNeverAskAgainDialog();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void getUserLocation() {
        requestLocationUpdates();
    }

    @Override
    public void showLocationPermissionDeniedDialog() {
        AlertDialog.Builder permissionDenied = new AlertDialog.Builder(this);
        permissionDenied.setTitle("Permission Denied!")
                .setCancelable(false)
                .setMessage("You can't use the app if you can't use your Location!")
                .setNegativeButton("Nuts!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(WeatherActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_LOCATION_SERVICES);
                    }
                });
        permissionDenied.create().show();
    }

    @Override
    public void showLocationPermissionDeniedNeverAskAgainDialog() {
        AlertDialog.Builder permissionDenied = new AlertDialog.Builder(this);
        permissionDenied.setTitle("Permission Denied!")
                .setMessage("You chose to never to permit Location on this! Go to your settings and fix this!")
                .setNegativeButton("Nuts!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
        permissionDenied.create().show();
    }

    @Override
    public void showErrorDialog() {
        AlertDialog.Builder errorFetchingWeather = new AlertDialog.Builder(this);
        errorFetchingWeather.setTitle("Error!")
                .setMessage("Unable to Fetch weather for your location.")
                .setPositiveButton("Nuts!", null);

        errorFetchingWeather.create().show();
    }

    @Override
    public void showNoConnectionErrorDialog() {
        AlertDialog.Builder errorFetchingWeather = new AlertDialog.Builder(this);
        errorFetchingWeather.setTitle("Error!")
                .setMessage("Can't reach out to the servers for weather data. Check your data connection!")
                .setPositiveButton("Nuts!", null);

        errorFetchingWeather.create().show();
    }

    @Override
    public boolean isConnectedToData() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }

    @Override
    public void render(WeatherViewState viewState) {
        textViewTemperature.setText(viewState.temperature());
        textViewTempMaxValue.setText(viewState.maxTemp());
        textViewTempMinValue.setText(viewState.minTemp());
        textViewForecastValue.setText(viewState.weatherDescription());
    }
}
