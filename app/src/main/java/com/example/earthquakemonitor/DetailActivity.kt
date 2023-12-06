package com.example.earthquakemonitor

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.earthquakemonitor.databinding.ActivityDetailBinding
import com.example.earthquakemonitor.main.DetailViewModel
import com.example.earthquakemonitor.main.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EARTHQUAKE_PLACE_KEY = "earthquake_place"
        const val EARTHQUAKE_MAGNITUDE_KEY = "earthquake_magnitude"
        const val EARTHQUAKE_LONGITUDE_KEY = "earthquake_longitude"
        const val EARTHQUAKE_LATITUDE_KEY = "earthquake_latitude"
        const val EARTHQUAKE_TIME_KEY = "earthquake_time"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailViewModel = DetailViewModel()

        val place = intent.getStringExtra(EARTHQUAKE_PLACE_KEY)
        val magnitude = String.format("%.2f",intent.getDoubleExtra(EARTHQUAKE_MAGNITUDE_KEY, 0.0))
        val time = intent.getLongExtra(EARTHQUAKE_TIME_KEY, 0)
        val longitude = intent.getDoubleExtra(EARTHQUAKE_LONGITUDE_KEY, 0.0)
        val latitude = intent.getDoubleExtra(EARTHQUAKE_LATITUDE_KEY, 0.0)

        binding.tvMagnitude.text = magnitude
        binding.tvLatitudeEdit.text = latitude.toString()
        binding.tvLongitudeEdit.text = longitude.toString()
        binding.tvLocation.text = place.toString()
        binding.tvDate.text = detailViewModel.dateFormat(time).toString()

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }


}