package com.example.domain.di

import com.example.domain.usecases.RealTimeRatesUseCase
import com.example.domain.usecases.SupportedCurrenciesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        SupportedCurrenciesUseCase(
            repository = get()
        )
    }
    factory {
        RealTimeRatesUseCase(
            repository = get()
        )
    }
}