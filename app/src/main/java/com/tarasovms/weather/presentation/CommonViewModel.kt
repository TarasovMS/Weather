package com.tarasovms.weather.presentation

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.tarasovms.weather.data.remote.RemoteService
import com.tarasovms.weather.data.remote.WeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


open class CommonViewModel @Inject constructor(val application: App): AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    lateinit var remoteService: RemoteService
    open var cityList = MutableLiveData<List<WeatherResponse>>()

    open fun getCityWeather(latLng: LatLng){
        remoteService = RemoteService(application.weatherApi)
        compositeDisposable.add( remoteService.getWeather(latLng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cityList.value = it
                       },{ })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}