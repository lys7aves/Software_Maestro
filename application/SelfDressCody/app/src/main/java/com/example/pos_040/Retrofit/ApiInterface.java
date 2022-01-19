package com.example.pos_040.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("weather?appid=616a33722b78b5f54f37ac648b144d5b&units=metric")
    Call<Example> getWeatherData(@Query("lat") double lat, @Query("lon") double lon);
}
