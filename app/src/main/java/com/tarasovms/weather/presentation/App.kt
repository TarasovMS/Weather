package com.tarasovms.weather.presentation

import android.app.Application
import com.tarasovms.weather.base.Constants
import com.tarasovms.weather.data.remote.WeatherApi
import com.tarasovms.weather.di.AppComponent
import com.tarasovms.weather.di.DaggerAppComponent
import com.tarasovms.weather.di.module.ApplicationModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class App @Inject constructor(): Application() {

    lateinit var weatherApi: WeatherApi
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        configureRetrofit()
        initDagger()
    }

    private fun configureRetrofit(){
        val httpLogginInterceptor = HttpLoggingInterceptor()
        httpLogginInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLogginInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.WEATHER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    private fun initDagger(){
        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}