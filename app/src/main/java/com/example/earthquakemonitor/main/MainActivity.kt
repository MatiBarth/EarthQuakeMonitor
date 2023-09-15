package com.example.earthquakemonitor.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.earthQuakeRecycler.layoutManager = LinearLayoutManager(this)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val adapter = EarthquakeAdapter(this)
        binding.earthQuakeRecycler.adapter = adapter

        viewModel.earthquakeList.observe(this, Observer { eqList ->
            adapter.submitList(eqList)

            handleEmptyView(eqList)
        })

        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEmptyView(eqList: MutableList<Earthquake>) {
        if (eqList.isEmpty())
            binding.earthQuakeEmptyView.visibility = View.VISIBLE
        else
            binding.earthQuakeEmptyView.visibility = View.GONE
    }
}