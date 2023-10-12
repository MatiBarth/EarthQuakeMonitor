package com.example.earthquakemonitor.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earthquakemonitor.DetailActivity
import com.example.earthquakemonitor.DetailActivity.Companion.EARTHQUAKE_LATITUDE_KEY
import com.example.earthquakemonitor.DetailActivity.Companion.EARTHQUAKE_LONGITUDE_KEY
import com.example.earthquakemonitor.DetailActivity.Companion.EARTHQUAKE_MAGNITUDE_KEY
import com.example.earthquakemonitor.DetailActivity.Companion.EARTHQUAKE_PLACE_KEY
import com.example.earthquakemonitor.DetailActivity.Companion.EARTHQUAKE_TIME_KEY
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.api.ApiStatusResponse
import com.example.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.earthQuakeRecycler.layoutManager = LinearLayoutManager(this)
        val viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(application)
        ).get(MainViewModel::class.java)

        val adapter = EarthquakeAdapter(this)
        binding.earthQuakeRecycler.adapter = adapter

        viewModel.earthquakeList.observe(this, Observer { eqList ->
            adapter.submitList(eqList)

            handleEmptyView(eqList)
        })

        // Rueda de progreso
        viewModel.status.observe(
            this,
            Observer { apiStatusResponse ->
                if (apiStatusResponse == ApiStatusResponse.LOADING) {
                    binding.loadingWheel.visibility = View.VISIBLE
                } else if (apiStatusResponse == ApiStatusResponse.DONE) {
                    binding.loadingWheel.visibility = View.GONE
                } else if (apiStatusResponse == ApiStatusResponse.NOT_INTERNET_CONNECTION) {
                    binding.loadingWheel.visibility = View.GONE
                } else if (apiStatusResponse == ApiStatusResponse.ERROR) {
                    binding.loadingWheel.visibility = View.GONE
                }
            })

        adapter.onItemClickListener = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(EARTHQUAKE_PLACE_KEY, it.place)
            intent.putExtra(EARTHQUAKE_MAGNITUDE_KEY, it.magnitude)
            intent.putExtra(EARTHQUAKE_LONGITUDE_KEY, it.longitude)
            intent.putExtra(EARTHQUAKE_LATITUDE_KEY, it.latitude)
            intent.putExtra(EARTHQUAKE_TIME_KEY, it.time)
            startActivity(intent)
        }
    }

    private fun handleEmptyView(eqList: MutableList<Earthquake>) {
        if (eqList.isEmpty())
            binding.earthQuakeEmptyView.visibility = View.VISIBLE
        else
            binding.earthQuakeEmptyView.visibility = View.GONE
    }
}