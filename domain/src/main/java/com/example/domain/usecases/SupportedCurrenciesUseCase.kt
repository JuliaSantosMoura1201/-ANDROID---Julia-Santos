package com.example.domain.usecases

import com.example.domain.Either
import com.example.domain.model.Currency
import com.example.domain.repositories.SupportedCurrenciesRepository

class SupportedCurrenciesUseCase(
    private val repository: SupportedCurrenciesRepository
) {

    suspend fun execute(): Either<List<Currency>, Exception> =
        repository.getSupportedCurrencies()
}