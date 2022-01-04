package com.tarasovms.weather.data.remote

import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

class RemoteService(private val weatherApi: WeatherApi) {

    fun getWeather(latLng: LatLng): Single<List<WeatherResponse>>{
        return weatherApi.getWeatherList(latLng.latitude,latLng.longitude)
            .toObservable()
            .map { it.list }
            .flatMapIterable { it }
            .toList()
    }
}