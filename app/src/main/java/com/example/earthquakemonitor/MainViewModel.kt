package com.example.earthquakemonitor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {
    private var _earthQuakeList = MutableLiveData<MutableList<Earthquake>>()
    val earthquakeList: LiveData<MutableList<Earthquake>>
    get() = _earthQuakeList
    init {
        viewModelScope.launch {
            _earthQuakeList.value = fetchEarthQuakes()
        }
    }
    private suspend fun fetchEarthQuakes(): MutableList<Earthquake> {
        return withContext(Dispatchers.IO){
            val earthQuakeList: MutableList<Earthquake> = mutableListOf<Earthquake>()
            earthQuakeList.add(Earthquake("1", "Buenos Aires", 1.3, 273846152L, -102.4756, 28.47365))
            earthQuakeList.add(Earthquake("2", "Lima", 2.9, 273846152L, -102.4756, 28.47365))
            earthQuakeList.add(Earthquake("3","Ciudad de Mexico",6.3,273846152L,-102.4756,28.47365))
            earthQuakeList.add(Earthquake("4", "Bogotá", 6.8, 273846152L, -102.4756, 28.47365))
            earthQuakeList.add(Earthquake("5", "Caracas", 3.5, 273846152L, -102.4756, 28.47365))
            earthQuakeList.add(Earthquake("6", "Madrid", 7.5, 273846152L, -102.4756, 28.47365))
            earthQuakeList.add(Earthquake("7", "Montevideo", 5.1, 273846152L, -102.4756, 28.47365))
            earthQuakeList
        }
    }
}