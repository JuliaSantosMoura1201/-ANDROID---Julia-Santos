package com.example.domain.usecases

import com.example.domain.Either
import com.example.domain.exceptions.ConvertException.*
import com.example.domain.exceptions.GenericException
import com.example.domain.model.Quote
import com.example.domain.repositories.RealTimeRatesRepository

class RealTimeRatesUseCase(
    private val repository: RealTimeRatesRepository
) {

    suspend fun execute(params: Params?): Either<Double, Exception> {
        if (params == null) throw  IllegalArgumentException()
        return when {
            params.value == null -> Either.Failure(InvalidValue)
            params.originalCurrencyCode.isEmpty() -> Either.Failure(EmptyOriginalCurrency)
            params.desiredCurrencyCode.isEmpty() -> Either.Failure(EmptyDesiredCurrency)
            params.originalCurrencyCode == params.desiredCurrencyCode -> Either.Failure(
                OriginalCurrencyAndDesiredCurrencyAreTheSame
            )
            else -> getRealTimeRates(params)
        }
    }

    private suspend fun getRealTimeRates(params: Params) =
        when (val result = repository.getRealTimeRates()) {
            is Either.Success -> convertCurrency(result.data, params)
            else -> Either.Failure(GenericException)
        }

    private fun convertCurrency(quotes: List<Quote>, params: Params): Either<Double, Exception> {
        val originalCurrencyQuote = quotes.find { it.code == "$USD${params.originalCurrencyCode}" }?.value
        val desiredCurrencyCode = quotes.find { it.code == "$USD${params.desiredCurrencyCode}"}?.value
        var currencyValue = 0.0

        try {
            params.value?.let {
                currencyValue = it.toDouble()
            } ?: Either.Failure(GenericException)
        } catch (e: Exception) {
            return Either.Failure(e)
        }

        originalCurrencyQuote?.let { originalQuote ->
            desiredCurrencyCode?.let { desiredQuote ->
                val convertedValue = originalQuote / desiredQuote
                return Either.Success(convertedValue * currencyValue)
            } ?: return Either.Failure(InvalidDesiredCurrency)
        } ?: return Either.Failure(InvalidOriginalCurrency)
    }

    data class Params(
        val originalCurrencyCode: String,
        val desiredCurrencyCode: String,
        val value: String?
    )

    private companion object{
        const val USD = "USD"
    }
}