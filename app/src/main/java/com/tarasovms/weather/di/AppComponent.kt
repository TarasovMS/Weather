package com.tarasovms.weather.di

import com.tarasovms.weather.di.module.ApplicationModule
import com.tarasovms.weather.di.module.CommonViewModelModule
import com.tarasovms.weather.presentation.App
import com.tarasovms.weather.presentation.CommonViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CommonViewModelModule::class, ApplicationModule::class])
interface AppComponent {
    fun getApp(): App
    fun getViewModel(): CommonViewModel
}