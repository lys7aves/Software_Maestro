package com.example.pos_040.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private List<Weather> weatherList;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeatherList() {return weatherList;}

    public void setWeatherList(List<Weather> weatherList) {this.weatherList = weatherList;}
}
