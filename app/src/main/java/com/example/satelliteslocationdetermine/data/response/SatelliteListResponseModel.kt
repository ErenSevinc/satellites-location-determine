package com.example.satelliteslocationdetermine.data.response

import kotlinx.serialization.Serializable

@Serializable
data class SatelliteListResponseModel(
    val id: Long,
    val active: Boolean,
    val name: String
)
