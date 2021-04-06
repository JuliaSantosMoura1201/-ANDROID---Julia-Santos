package com.example.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.Either
import com.example.domain.exceptions.NoNetworkingException
import com.example.domain.model.Currency
import com.example.domain.usecases.SupportedCurrenciesUseCase
import com.example.presentation.utils.SingleEvent

class MainActivityViewModel(
    private val supportedCurrenciesUseCase: SupportedCurrenciesUseCase
): BaseViewModel(){

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies = _currencies as LiveData<List<Currency>>

    private val _goToCurrencyConverterActivity = MutableLiveData<SingleEvent<Unit>>()
    val goToCurrencyConverterActivity = _goToCurrencyConverterActivity as LiveData<SingleEvent<Unit>>

    private val _noNetwork = MutableLiveData<SingleEvent<Unit>>()
    val noNetwork = _noNetwork as LiveData<SingleEvent<Unit>>

    private val _error = MutableLiveData<SingleEvent<Unit>>()
    val error = _error as LiveData<SingleEvent<Unit>>

    init {
        getCurrencies()
    }

    private fun getCurrencies(){
        launchSuspend {
            when(val result = supportedCurrenciesUseCase.execute()){
                is Either.Success -> _currencies.postValue(result.data)
                is Either.Failure -> when(result.cause){
                    is NoNetworkingException -> _noNetwork.postValue(SingleEvent(Unit))
                    else -> _error.postValue(SingleEvent(Unit))
                }
            }
        }
    }

    fun goToCurrencyConverterActivity(){
        _goToCurrencyConverterActivity.postValue(SingleEvent(Unit))
    }

}