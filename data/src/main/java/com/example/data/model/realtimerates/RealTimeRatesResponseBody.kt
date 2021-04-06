package com.example.data.model.realtimerates

data class RealTimeRatesResponseBody(
    val privacy: String,
    val quotes: Quotes,
    val source: String,
    val success: Boolean,
    val terms: String,
    val timestamp: Int
)