package com.example.satelliteslocationdetermine.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.satelliteslocationdetermine.data.UIState
import com.example.satelliteslocationdetermine.data.response.SatelliteDetailResponseModel

@Dao
interface SatelliteDetailDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSatellite(item: SatelliteDetailResponseModel)

    @Query("SELECT * FROM satellite_detail WHERE id == :id")
    suspend fun getSatelliteDetail(id: Long): SatelliteDetailResponseModel?
}