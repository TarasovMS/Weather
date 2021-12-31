package com.tarasovms.weather.presentation.cities_fragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tarasovms.weather.data.remote.WeatherApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CitiesViewModel(application: Application): AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    var name = MutableLiveData<String>()
    var nameCity = MutableLiveData<String>()
    var tempory = MutableLiveData<Double>()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchWeatherCity(weatherApi: WeatherApi?){
        weatherApi?.let {
//            compositeDisposable.add(weatherApi.getWeatherList()
            compositeDisposable.add(weatherApi.getWeatherCity(name.value,WeatherApi.Key_API)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        //OnSuccess
//                    nameCity.value = it.name
                        nameCity.value = it.cityName
                    tempory.value = it.main.temp
//                        it.map {
////                            nameCity.value = it.cityName
//                            nameCity.value = it.toString()
//                        }
                    },
                    {
                        //onError
                        nameCity.value = "it.cityName"
                        tempory.value = 0.0
                    }
                ))
        }
    }
}