package com.tarasovms.weather.data.remote

import io.reactivex.Single
import retrofit2.http.*

interface Weather {
    @GET("")
    fun getCitiesList(): Single<WeatherList>
}