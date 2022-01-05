package com.tarasovms.weather.data.remote

import io.reactivex.Single
import retrofit2.http.*

interface WeatherApi {

    companion object{
         const val Key_API = "9bc26e2bc40cc50f6243dec639d76e70"
    }

    /* Запрос погоды для списка городов в радиусе */
    @GET("/data/2.5/find?cnt=21&appid=${Key_API}&units=metric&lang=ru")
    fun getWeatherList(@Query("lat") lat: Double,
                       @Query("lon") long: Double): Single<WeatherMassiveCity>
}

















