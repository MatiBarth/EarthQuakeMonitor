package com.example.earthquakemonitor.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.api.ApiStatusResponse
import com.example.earthquakemonitor.database.getDatabase
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _status = MutableLiveData<ApiStatusResponse>()
    val status: LiveData<ApiStatusResponse>
        get() = _status

    private val database = getDatabase(application)
    private val repository = MainRepository(database)

    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
    get() = _eqList

    init {
        reloadEartquakes(false)
    }

    fun reloadEartquakes(sortByMagnitude: Boolean) {
        viewModelScope.launch {
            try {
                _status.value = ApiStatusResponse.LOADING
                _eqList.value = repository.fetchEarthQuakes(sortByMagnitude)
                _status.value = ApiStatusResponse.DONE
            } catch (e: UnknownHostException) {
                _status.value = ApiStatusResponse.NOT_INTERNET_CONNECTION
                Log.d(TAG, "No internet conection", e)
            }
        }
    }
}