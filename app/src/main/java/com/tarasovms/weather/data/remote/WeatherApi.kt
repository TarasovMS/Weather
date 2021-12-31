package com.tarasovms.weather.data.remote

import io.reactivex.Single
import retrofit2.http.*

interface WeatherApi {

    companion object{
         const val Key_API = "0cdb741ec14042a141b70eafae8f5777"
    }

    @GET("/data/2.5/weather?q=Samara&appid=${Key_API}")
    fun getWeatherList(): Single<List<WeatherResponse>>

    @GET("/data/2.5/weather?")
    fun getWeatherCity(@Query(value = "q", encoded = true) city: String?,
                       @Query("appid") key_API: String ): Single<WeatherResponse>

}
