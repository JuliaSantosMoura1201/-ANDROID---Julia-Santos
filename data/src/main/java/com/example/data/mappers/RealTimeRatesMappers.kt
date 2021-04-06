package com.example.data.mappers

import com.example.domain.model.Quote
import com.google.gson.JsonObject

fun JsonObject?.toQuotes(): List<Quote> {
    val quotes = mutableListOf<Quote>()
    val quotesJson = this?.get("quotes")?.asJsonObject

    quotesJson?.keySet()?.forEach { quoteCode ->
        val value = quotesJson.get(quoteCode)?.asDouble
        value?.let { quotes.add(
            Quote(quoteCode, it)
        ) }
    }

    return quotes.toList()
}