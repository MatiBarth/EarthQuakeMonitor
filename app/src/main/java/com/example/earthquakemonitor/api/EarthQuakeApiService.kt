package com.example.earthquakemonitor.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface EarthQuakeApiService {
       @GET("all_hour.geojson")
      suspend fun getLastHourEarthQuakes(): EarthQuakeJsonResponse
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: EarthQuakeApiService = retrofit.create(EarthQuakeApiService::class.java)