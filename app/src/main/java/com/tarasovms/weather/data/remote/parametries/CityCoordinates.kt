package com.tarasovms.weather.data.remote.parametries

import com.google.gson.annotations.SerializedName

data class CityCoordinates (
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double
    )
