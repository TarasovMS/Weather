package com.tarasovms.weather.data.remote.parametries

data class WeatherBasic (
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
    )