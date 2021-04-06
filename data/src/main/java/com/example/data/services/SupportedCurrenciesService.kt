package com.example.data.services

import com.example.data.model.supportedcurrencies.SupportedCurrenciesResponseBody
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SupportedCurrenciesService {

    @GET("list")
    suspend fun getSupportedCurrencies(
        @Query("access_key") accessKey: String
    ): Response<JsonObject>
}