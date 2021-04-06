package com.example.domain.repositories

import com.example.domain.Either
import com.example.domain.model.Currency
import java.util.*

interface SupportedCurrenciesRepository {

    suspend fun getSupportedCurrencies(): Either<List<Currency>, Exception>
}