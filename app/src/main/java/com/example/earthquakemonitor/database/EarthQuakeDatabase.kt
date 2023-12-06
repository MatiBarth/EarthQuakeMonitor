package com.example.earthquakemonitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.earthquakemonitor.Earthquake

@Database(entities = [Earthquake::class], version = 1)
abstract class EarthQuakeDatabase: RoomDatabase(){
    abstract val eqDao: EarthQuakeDAO
}

private lateinit var INSTANCE: EarthQuakeDatabase

fun getDatabase(context: Context): EarthQuakeDatabase{
    synchronized(EarthQuakeDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
            context.applicationContext,
            EarthQuakeDatabase::class.java,
            "earthquake_db").build()
        }
        return INSTANCE
    }
}