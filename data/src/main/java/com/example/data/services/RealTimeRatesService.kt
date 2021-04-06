package com.example.data.services

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RealTimeRatesService {

    @GET("live")
    suspend fun getRealTimeRates(
        @Query("access_key") accessKey: String
    ): Response<JsonObject>
}