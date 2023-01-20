package com.code.OpenWeather.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentWeather {
  private String name;
  private long time;
  private double temperature;
  private double humidity;
  private double precipProbability;
  private String description;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getFormattedTime() {
    // convert seconds to milliseconds
    Date date = new Date(getTime() * 1000L);
    // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    // formatter.setTimeZone(TimeZone.getTimeZone("GMT+1"));
    formatter.setTimeZone(TimeZone.getTimeZone(getName()));
    String timeString = formatter.format(date);
    return timeString;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public int getTemperature() {
    return (int) Math.round(temperature);
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public double getHumidity() {
    return humidity;
  }

  public void setHumidity(double humidity) {
    this.humidity = humidity;
  }

  public int getPrecipProbability() {
    return (int) Math.round(precipProbability);
  }

  public void setPrecipProbability(double precipProbability) {
    this.precipProbability = precipProbability;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


}
