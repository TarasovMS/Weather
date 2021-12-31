package com.tarasovms.weather.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tarasovms.weather.data.remote.parametries.*

data class WeatherResponse (

    @SerializedName("coord")
    @Expose
    val coordinates: CityCoordinates,

    @SerializedName("weather")
    @Expose
    val weather: List<WeatherBasic>,

    @SerializedName("base")
    @Expose
    val base: String,

    @SerializedName("main")
    @Expose
    val main: MainWeather,

    @SerializedName("visibility")
    @Expose
    val visibility: Int,

    @SerializedName("wind")
    @Expose
    val wind: WindWeather,

    @SerializedName("clouds")
    @Expose
    val clouds: Clouds,

    @SerializedName("dt")
    @Expose
    val dt: Int,

    @SerializedName("sys")
    @Expose
    val sys: SysWeather,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val cityName: String,

    @SerializedName("cod")
    @Expose
    val cod: Int
    )