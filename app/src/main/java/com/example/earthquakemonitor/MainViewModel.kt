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
            val earthQuakeListString = service.getLastHourEarthQuakes()
            val eqList = parseEarthQuakeResult(earthQuakeListString)
            eqList
        }
    }

    private fun parseEarthQuakeResult(earthQuakeListString: String): MutableList<Earthquake> {
        val eqJsonObject = JSONObject(earthQuakeListString)
        val featuresJsonArray: JSONArray = eqJsonObject.getJSONArray("features")

        val eqList = mutableListOf<Earthquake>()

        for (i in 0 until featuresJsonArray.length()) {
            val featuresJSONObject = featuresJsonArray[i] as JSONObject
            val id = featuresJSONObject.getString("id")

            val propertiesJsonObject = featuresJSONObject.getJSONObject("properties")
            val magnitude = propertiesJsonObject.getDouble("mag")
            val place = propertiesJsonObject.getString("place")
            val time = propertiesJsonObject.getLong("time")

            val geometryJsonObject = featuresJSONObject.getJSONObject("geometry")
            val coordinatesJsonArray = geometryJsonObject.getJSONArray("coordinates")
            val longitude = coordinatesJsonArray.getDouble(0)
            val latitude = coordinatesJsonArray.getDouble(1)

            val earthquake = Earthquake(id, place, magnitude, time, longitude, latitude)
            eqList.add(earthquake)
        }
        return eqList
    }
}