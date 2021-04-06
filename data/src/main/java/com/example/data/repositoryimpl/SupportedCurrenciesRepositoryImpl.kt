package com.example.data.repositoryimpl

import com.example.data.ACCESS_KEY
import com.example.data.mappers.toCurrencies
import com.example.data.mappers.toQuotes
import com.example.data.services.SupportedCurrenciesService
import com.example.domain.Either
import com.example.domain.exceptions.GenericException
import com.example.domain.exceptions.NoNetworkingException
import com.example.domain.model.Currency
import com.example.domain.repositories.SupportedCurrenciesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class SupportedCurrenciesRepositoryImpl(
    private val service: SupportedCurrenciesService
): SupportedCurrenciesRepository {

    override suspend fun getSupportedCurrencies(): Either<List<Currency>, Exception> =
        withContext(Dispatchers.IO){
            try {
                val response = service.getSupportedCurrencies(
                    accessKey = ACCESS_KEY
                )
                when(response.code()){
                    200 ->{
                        val success = response.body()?.get("success")?.asBoolean == true
                        if (success){
                            Either.Success(response.body().toCurrencies())
                        }else{
                            Either.Failure(GenericException)
                        }
                    }
                    else -> Either.Failure(GenericException)
                }
            }catch (e: UnknownHostException){
                Either.Failure(NoNetworkingException)
            }
        }
}