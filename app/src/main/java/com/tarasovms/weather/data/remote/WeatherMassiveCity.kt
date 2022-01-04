package com.tarasovms.weather.data.remote

data class WeatherMassiveCity(
    val cod: String,
    val count: Int,
    val list: List<WeatherResponse>,
    val message: String
)