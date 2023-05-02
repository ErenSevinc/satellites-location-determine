package com.example.satelliteslocationdetermine.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.satelliteslocationdetermine.data.dao.SatelliteDetailDao
import com.example.satelliteslocationdetermine.data.response.SatelliteDetailResponseModel

@Database(entities = [SatelliteDetailResponseModel::class], version = 1, exportSchema = false)
abstract class SatelliteDatabase: RoomDatabase() {

    abstract fun getSatelliteDetailDao(): SatelliteDetailDao
}