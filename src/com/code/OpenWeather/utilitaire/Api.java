package com.code.OpenWeather.utilitaire;

public class Api {
  private static final String FORECAST_API_BASE_URL =
      "https://api.openweathermap.org/data/2.5/weather?q=";
  private static final String FORECAST_API_KEY = "068b5b4be6315ebc78fb93019fee66c8";

  public static String getForecastUrl(String city) {
    return FORECAST_API_BASE_URL + city + "&appid=" + FORECAST_API_KEY;

  }
}
