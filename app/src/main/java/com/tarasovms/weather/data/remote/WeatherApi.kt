package com.tarasovms.weather.data.remote

import com.tarasovms.weather.base.Constants
import io.reactivex.Single
import retrofit2.http.*

interface WeatherApi {

    /* Запрос погоды для списка городов в радиусе */
    @GET("/data/2.5/find?cnt=20")
    fun getWeatherList(@Query("lat") lat: Double,
                       @Query("lon") long: Double,
                       @Query("appid") api_key: String = Constants.WEATHER_API_KEY,
                       @Query("units") units: String = Constants.WEATHER_UNIT_METRIC,
                       @Query("lang") lang: String = Constants.WEATHER_LANGUAGE): Single<WeatherMassiveCity>
}

















