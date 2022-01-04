package com.tarasovms.weather.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.tarasovms.weather.data.remote.RemoteService
import com.tarasovms.weather.data.remote.WeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
 class CommonViewModel (application: Application): AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    var cityList = MutableLiveData<List<WeatherResponse>>()
    lateinit var remoteService: RemoteService
    val app = application as App

    fun getCityWeather(latLng: LatLng){
        remoteService = RemoteService(app.weatherApi)
        compositeDisposable.add( remoteService.getWeather(latLng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ cityList.value = it },{ })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}