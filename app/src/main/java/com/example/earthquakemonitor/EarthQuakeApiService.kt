package com.example.earthquakemonitor

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


interface EarthQuakeApiService {
       @GET("all_hour.geojson")
       fun getLastHourEarthQuakes(): String
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

var service: EarthQuakeApiService = retrofit.create(EarthQuakeApiService::class.java)