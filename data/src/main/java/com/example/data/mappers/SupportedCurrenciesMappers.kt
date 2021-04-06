package com.example.data.mappers

import com.example.domain.model.Currency
import com.google.gson.JsonObject


fun JsonObject?.toCurrencies(): List<Currency> {
    val currencies = mutableListOf<Currency>()
    val currenciesJson = this?.get("currencies")?.asJsonObject

    currenciesJson?.keySet()?.forEach { currencyCode ->
        val name = currenciesJson.get(currencyCode)?.asString
        name?.let { currencies.add(
            Currency(currencyCode, it)
        ) }
    }
    return currencies.toList()
}