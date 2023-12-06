package com.example.earthquakemonitor.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.database.getDatabase
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = MainRepository(database)

    val earthquakeList = repository.eqList

    init {
        viewModelScope.launch {
            try {
                repository.fetchEarthQuakes()
            } catch (e: UnknownHostException){
                Log.d(TAG,"No internet conection",e)
            }
        }
    }
}