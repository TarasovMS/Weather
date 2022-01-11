package com.tarasovms.weather.di.module

import com.tarasovms.weather.presentation.App
import com.tarasovms.weather.presentation.CommonViewModel
import com.tarasovms.weather.presentation.map_fragment.MapViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapViewModelModule {
    @Provides
    @Singleton
    fun provideMapViewModel(application: App): MapViewModel {
        return MapViewModel(application)
    }
}