package com.example.earthquakemonitor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private var _earthQuakeList = MutableLiveData<MutableList<Earthquake>>()
    val earthquakeList: LiveData<MutableList<Earthquake>>
        get() = _earthQuakeList

    init {
        viewModelScope.launch {
            _earthQuakeList.value = fetchEarthQuakes()
        }
    }

    private suspend fun fetchEarthQuakes(): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {
            val earthQuakeJsonResponse = service.getLastHourEarthQuakes()
            val eqList = parseEarthQuakeResult(earthQuakeJsonResponse)
            eqList
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

            eqList.add(Earthquake(id,place,magnitude,time,longitude,latitude))
        }
        return eqList
    }
}