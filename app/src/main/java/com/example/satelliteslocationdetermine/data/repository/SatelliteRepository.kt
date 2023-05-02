package com.example.satelliteslocationdetermine.data.repository

import com.example.satelliteslocationdetermine.data.dao.SatelliteDetailDao
import com.example.satelliteslocationdetermine.data.response.SatelliteDetailResponseModel
import com.example.satelliteslocationdetermine.domain.database.SatelliteDatabase
import javax.inject.Inject

class SatelliteRepository @Inject constructor(private val dao: SatelliteDetailDao) {

    suspend fun addSatellite(item: SatelliteDetailResponseModel) {
        dao.addSatellite(item)
    }

    suspend fun getSatelliteDetail(satelliteId: Long): SatelliteDetailResponseModel? {
       return dao.getSatelliteDetail(satelliteId)
    }

}