package com.example.domain.repositories

import com.example.domain.Either
import com.example.domain.model.Quote

interface RealTimeRatesRepository {

    suspend fun getRealTimeRates(): Either<List<Quote>, Exception>
}