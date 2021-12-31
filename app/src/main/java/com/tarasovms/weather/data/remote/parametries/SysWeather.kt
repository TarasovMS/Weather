package com.tarasovms.weather.data.remote.parametries

data class SysWeather (
    val type: Int,
    val id: Int,
    val message: Double,
    val country: String,
    val sunrise: Int,
    val sunset: Int
    )