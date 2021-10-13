package com.example.bottlerocket.di

import android.app.Application
import com.example.bottlerocket.di.modules.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CityApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CityApplication)
            modules(module)
        }
    }
}