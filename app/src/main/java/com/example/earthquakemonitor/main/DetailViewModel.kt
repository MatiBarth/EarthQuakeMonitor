package com.example.earthquakemonitor.main

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailViewModel : ViewModel() {

    fun dateFormat(date: Long): Any {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(date)
        return simpleDateFormat.format(date)
    }

}