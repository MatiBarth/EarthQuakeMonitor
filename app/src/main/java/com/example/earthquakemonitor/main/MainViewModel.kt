package com.example.earthquakemonitor.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earthquakemonitor.Earthquake
import kotlinx.coroutines.launch

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