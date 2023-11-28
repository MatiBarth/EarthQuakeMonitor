package com.example.earthquakemonitor.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.earthquakemonitor.Earthquake

@Dao
interface EarthQuakeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Earthquake>)

    @Query("Select * From earthquakes")
    fun getEarthquakes(): MutableList<Earthquake>

    @Query("Select * From earthquakes ORDER BY magnitude ASC")
    fun getEarthquakesByMagnitude(): MutableList<Earthquake>

    @Delete
    fun deleteEarthquake(vararg eq: Earthquake)
}
