package com.example.earthquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.earthQuakeRecycler.layoutManager = LinearLayoutManager(this)

        val earthQuakeList: MutableList<Earthquake> = mutableListOf<Earthquake>()
        earthQuakeList.add(Earthquake("1", "Buenos Aires", 1.3, 273846152L, -102.4756, 28.47365))
        earthQuakeList.add(Earthquake("2", "Lima", 2.9, 273846152L, -102.4756, 28.47365))
        earthQuakeList.add(
            Earthquake(
                "3",
                "Ciudad de Mexico",
                6.3,
                273846152L,
                -102.4756,
                28.47365
            )
        )
        earthQuakeList.add(Earthquake("4", "Bogotá", 6.8, 273846152L, -102.4756, 28.47365))
        earthQuakeList.add(Earthquake("5", "Caracas", 3.5, 273846152L, -102.4756, 28.47365))
        earthQuakeList.add(Earthquake("6", "Madrid", 7.5, 273846152L, -102.4756, 28.47365))
        earthQuakeList.add(Earthquake("7", "Montevideo", 5.1, 273846152L, -102.4756, 28.47365))

        val adapter = EarthquakeAdapter()
        binding.earthQuakeRecycler.adapter = adapter
        adapter.submitList(earthQuakeList)
    }
}