package com.tarasovms.weather.di.module

import com.tarasovms.weather.presentation.App
import com.tarasovms.weather.presentation.CommonViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommonViewModelModule {
    @Provides
    @Singleton
    fun provideCommonViewModel(application: App): CommonViewModel {
        return CommonViewModel(application)
    }
}