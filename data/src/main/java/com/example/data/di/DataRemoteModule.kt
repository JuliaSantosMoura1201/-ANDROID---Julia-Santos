package com.example.data.di

import com.example.data.Client.createWebService
import com.example.data.Client.provideHttpClient
import com.example.data.repositoryimpl.RealTimeRatesRepositoryImpl
import com.example.data.repositoryimpl.SupportedCurrenciesRepositoryImpl
import com.example.data.services.RealTimeRatesService
import com.example.data.services.SupportedCurrenciesService
import com.example.domain.repositories.RealTimeRatesRepository
import com.example.domain.repositories.SupportedCurrenciesRepository
import org.koin.dsl.module

val dataModule = module {

    factory {
        provideHttpClient()
    }

    single {
        createWebService<SupportedCurrenciesService>(
            okHttpClient = get()
        )
    }
    factory<SupportedCurrenciesRepository> {
        SupportedCurrenciesRepositoryImpl(
            service = get()
        )
    }

    single {
        createWebService<RealTimeRatesService>(
            okHttpClient = get()
        )
    }

    factory<RealTimeRatesRepository> {
        RealTimeRatesRepositoryImpl(
            service = get()
        )
    }
}