package com.tarasovms.weather.presentation

import android.app.Application
import android.util.Log
import com.tarasovms.weather.data.remote.WeatherApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    lateinit var weatherApi: WeatherApi

    override fun onCreate() {
        super.onCreate()
        configureRetrofit()
    }

    private fun configureRetrofit(){
        val httpLogginInterceptor = HttpLoggingInterceptor()
        httpLogginInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLogginInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        weatherApi = retrofit.create(WeatherApi::class.java)
//        weatherApi.getWeatherList()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    it.map {
//                        Log.d("OkHttp", it.cityName)
//                    }
//                },
//                {
//                    //onError
//                    Log.d("OkHttp", "ERROR")
//                }
//            )





    }
}