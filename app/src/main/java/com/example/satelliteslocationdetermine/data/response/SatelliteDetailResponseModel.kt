package com.example.satelliteslocationdetermine.data.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "satellite_detail")
data class SatelliteDetailResponseModel(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    @ColumnInfo(name = "cost_per_launch")
    val costPerLaunch: Long,
    @ColumnInfo(name = "first_flight")
    val firstFlight: String,
    @ColumnInfo(name = "height")
    val height: Long,
    @ColumnInfo(name = "mass")
    val mass: Long
)
