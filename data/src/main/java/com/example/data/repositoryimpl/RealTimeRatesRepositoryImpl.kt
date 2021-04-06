package com.example.data.repositoryimpl

import com.example.data.ACCESS_KEY
import com.example.data.mappers.toQuotes
import com.example.data.services.RealTimeRatesService
import com.example.domain.Either
import com.example.domain.exceptions.GenericException
import com.example.domain.exceptions.NoNetworkingException
import com.example.domain.model.Quote
import com.example.domain.repositories.RealTimeRatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class RealTimeRatesRepositoryImpl(
    private val service: RealTimeRatesService
): RealTimeRatesRepository {

    override suspend fun getRealTimeRates(): Either<List<Quote>, Exception> =
        withContext(Dispatchers.IO){
            try {
                val response = service.getRealTimeRates(
                    accessKey = ACCESS_KEY
                )
                when(response.code()){
                    200 -> {
                        val success = response.body()?.get("success")?.asBoolean == true
                        if (success){
                            Either.Success(response.body().toQuotes())
                        }else{
                            Either.Failure(GenericException)
                        }
                    }
                    else ->  Either.Failure(GenericException)
                }
            }catch (e: UnknownHostException){
                Either.Failure(NoNetworkingException)
            }
        }
}