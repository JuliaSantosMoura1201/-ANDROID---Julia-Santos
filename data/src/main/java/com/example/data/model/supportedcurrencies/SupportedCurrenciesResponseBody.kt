package com.example.data.model.supportedcurrencies

data class SupportedCurrenciesResponseBody(
    val currencies: Currencies,
    val privacy: String,
    val success: Boolean,
    val terms: String
)