package com.example.earthquakemonitor.main

import android.app.Application
import androidx.lifecycle.*
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.database.getDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = MainRepository(database)

    /*    private var _earthQuakeList = MutableLiveData<MutableList<Earthquake>>()  */
    val earthquakeList = repository.eqList
    /*  REEMPLAZO AL INGRESAR LIVEDATA en DAO
    : LiveData<MutableList<Earthquake>>
        get() = _earthQuakeList   */

    init {
        viewModelScope.launch {
            /*    _earthQuakeList.value = */
            repository.fetchEarthQuakes()
        }
    }
}