package com.tarasovms.weather.di.module

import android.content.Context
import com.tarasovms.weather.presentation.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideApplication(): App {
        return context.applicationContext as App
    }
}