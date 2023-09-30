package com.example.earthquakemonitor.main

import com.example.earthquakemonitor.api.EarthQuakeJsonResponse
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.api.service
import com.example.earthquakemonitor.database.EarthQuakeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: EarthQuakeDatabase) {
    suspend fun fetchEarthQuakes(): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {
            val earthQuakeJsonResponse = service.getLastHourEarthQuakes()
            val eqList = parseEarthQuakeResult(earthQuakeJsonResponse)

            //Guardar todos los terremotos en la base de datos
            database.eqDao.insertAll(eqList)

            //Tomamos los terremotos de la base de datos y ya no directo desde la api
            val earthquakes = database.eqDao.getEarthquakes()
            earthquakes
        }
    }

    private fun parseEarthQuakeResult(earthQuakeJsonResponse: EarthQuakeJsonResponse): MutableList<Earthquake> {
        val eqList = mutableListOf<Earthquake>()
        val featureList = earthQuakeJsonResponse.features

        for(feature in featureList){
            val properties = feature.properties
            val id = feature.id
            val magnitude = properties.mag
            val place = properties.place
            val time = properties.time

            val geometry = feature.geometry
            val longitude = geometry.longitude
            val latitude = geometry.latitude

            eqList.add(Earthquake(id, place, magnitude, time, longitude, latitude))
        }
        return eqList
    }
}