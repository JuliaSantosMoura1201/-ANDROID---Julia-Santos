package com.example.presentation.di

import com.example.presentation.utils.StringLoader
import com.example.presentation.viewmodels.CurrencyConverterViewModel
import com.example.presentation.viewmodels.MainActivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    single {
        StringLoader(androidContext())
    }

    viewModel {
        CurrencyConverterViewModel(
            realTimeRatesUseCase = get(),
            stringLoader = get()
        )
    }

    viewModel {
        MainActivityViewModel(
            supportedCurrenciesUseCase = get()
        )
    }
}