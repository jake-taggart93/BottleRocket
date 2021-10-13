package com.example.bottlerocket.di.modules

import com.example.bottlerocket.data.local.CityRoom
import com.example.bottlerocket.data.remote.CityApi
import com.example.bottlerocket.repository.IRepository
import com.example.bottlerocket.repository.Repository
import com.example.bottlerocket.viewmodel.CityViewModel
import com.example.bottlerocket.viewmodel.provider.CityViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val module = module {

    factory {
        CityRoom.getInstance(androidContext()).getDao()
    }
    single(named("IO")) {
        CoroutineScope(Job() + Dispatchers.IO)
    }
    single(named("Main")) {
        CoroutineScope(Job() + Dispatchers.Main)
    }
    factory<CityApi> {
        CityApi.initRetrofit()
    }
    factory<IRepository> {
        Repository(
            get(),
            get(),
            get(named("IO"))
        )
    }
    single{
        CityViewModelProvider(
            get(),
            get(named("IO"))
        ).create(CityViewModel::class.java)
    }
}