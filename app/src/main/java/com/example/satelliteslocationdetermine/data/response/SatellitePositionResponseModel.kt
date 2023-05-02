package com.example.satelliteslocationdetermine.data.response

import kotlinx.serialization.Serializable

@Serializable
data class SatellitePositionResponseModel(
    val list: MutableList<Positions>
)

@Serializable
data class Positions(
    var id: String? = null,
    val positions: MutableList<Position>
)

@Serializable
data class Position(
    val posX: Double? = null,
    val posY: Double? = null
)
