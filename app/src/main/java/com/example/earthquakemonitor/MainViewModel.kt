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

    private val repository = MainRepository()
    init {
        viewModelScope.launch {
            _earthQuakeList.value = repository.fetchEarthQuakes()
        }
    }


}