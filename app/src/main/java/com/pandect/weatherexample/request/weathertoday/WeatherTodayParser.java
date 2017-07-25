package com.pandect.weatherexample.request.weathertoday;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PROFESSORCORE on 7/23/17.
 *
 *
 */

public class WeatherTodayParser {

    public static WeatherTodayResponseObject parse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);

            JSONObject coord = jsonResponse.getJSONObject("coord");
            double lat = coord.getDouble("lat");
            double lng = coord.getDouble("lon");

            JSONArray weather = jsonResponse.getJSONArray("weather");
            ArrayList<WeatherObject> weatherObjects = new ArrayList<>();
            for(int i=0; i<weather.length(); i++) {
                int id = weather.getJSONObject(i).getInt("id");
                String main = weather.getJSONObject(i).getString("main");
                String description = weather.getJSONObject(i).getString("description");
                String icon = weather.getJSONObject(i).getString("icon");
                weatherObjects.add(WeatherObject.builder()
                                    .setId(id)
                                    .setMain(main)
                                    .setDescription(description)
                                    .setIcon(icon)
                                    .build());
            }

            JSONObject main = jsonResponse.getJSONObject("main");
            double temp = main.getDouble("temp");
            double pressure = main.getDouble("pressure");
            double humidity = main.getDouble("humidity");
            double tempMin = main.getDouble("temp_min");
            double tempMax = main.getDouble("temp_max");

            MainObject mainObject = MainObject.builder()
                    .setTemp(temp)
                    .setPressure(pressure)
                    .setHumidity(humidity)
                    .setTempMin(tempMin)
                    .setTempMax(tempMax)
                    .build();

            return WeatherTodayResponseObject.builder()
                    .setLat(lat)
                    .setLng(lng)
                    .setWeatherObjects(weatherObjects)
                    .setMainObject(mainObject)
                    .build();


        }
        catch (JSONException e) {
            e.printStackTrace();
            return WeatherTodayResponseObject.builder().build();
        }
    }
}

/*
  "main": {
    "temp": 285.514,
    "pressure": 1013.75,
    "humidity": 100,
    "temp_min": 285.514,
    "temp_max": 285.514,
    "sea_level": 1023.22,
    "grnd_level": 1013.75
  },
  "wind": {
    "speed": 5.52,
    "deg": 311
  },
  "clouds": {
    "all": 0
  },
  "dt": 1485792967,
  "sys": {
    "message": 0.0025,
    "country": "JP",
    "sunrise": 1485726240,
    "sunset": 1485763863
  },
  "id": 1907296,
  "name": "Tawarano",
  "cod": 200
}
 */