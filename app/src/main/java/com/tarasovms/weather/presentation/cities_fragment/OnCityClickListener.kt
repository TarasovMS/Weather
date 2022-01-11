package com.tarasovms.weather.presentation.cities_fragment

import com.tarasovms.weather.data.remote.WeatherResponse


interface OnCityClickListener {
    fun clickedCityItem(weatherResponse: WeatherResponse, position: Int)
}