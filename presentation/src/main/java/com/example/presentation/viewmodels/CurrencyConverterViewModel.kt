package com.example.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.Either
import com.example.domain.exceptions.ConvertException
import com.example.domain.exceptions.NoNetworkingException
import com.example.domain.usecases.RealTimeRatesUseCase
import com.example.presentation.R
import com.example.presentation.utils.SingleEvent
import com.example.presentation.utils.StringLoader

class CurrencyConverterViewModel(
    private val realTimeRatesUseCase: RealTimeRatesUseCase,
    private val stringLoader: StringLoader
) : BaseViewModel() {

    private val _convertResult = MutableLiveData<String>()
    val convertResult = _convertResult as LiveData<String>

    private val _noNetwork = MutableLiveData<SingleEvent<Unit>>()
    val noNetwork = _noNetwork as LiveData<SingleEvent<Unit>>

    private val _error = MutableLiveData<SingleEvent<Unit>>()
    val error = _error as LiveData<SingleEvent<Unit>>

    val originalCurrency = MutableLiveData<String>()
    val desiredCurrency = MutableLiveData<String>()
    val currencyValue = MutableLiveData<String>()

    private var _originalCurrency: String = ""
    private var _desiredCurrency: String = ""

    fun confirmOriginalCurrency() {
        _originalCurrency = originalCurrency.value.orEmpty()
    }

    fun confirmDesiredCurrency() {
        _desiredCurrency = desiredCurrency.value.orEmpty()
    }

    fun convert() {
        launchSuspend {
            when (val result = realTimeRatesUseCase.execute(createRealTimeRatesUseCaseParams())) {
                is Either.Success -> _convertResult.postValue(
                    result.data.toString()
                )
                is Either.Failure -> when (result.cause) {
                    is NoNetworkingException -> _noNetwork.postValue(SingleEvent(Unit))
                    is ConvertException.InvalidValue -> _convertResult.postValue(stringLoader.get(R.string.txt_invalid_currency_value))
                    is ConvertException.EmptyOriginalCurrency -> _convertResult.postValue(
                        stringLoader.get(R.string.txt_empty_original_currency)
                    )
                    is ConvertException.EmptyDesiredCurrency -> _convertResult.postValue(
                        stringLoader.get(R.string.txt_empty_desired_currency)
                    )
                    is ConvertException.InvalidOriginalCurrency -> _convertResult.postValue(
                        stringLoader.get(R.string.txt_invalid_original_currency)
                    )
                    is ConvertException.InvalidDesiredCurrency -> _convertResult.postValue(
                        stringLoader.get(R.string.txt_invalid_desired_currency)
                    )
                    is ConvertException.OriginalCurrencyAndDesiredCurrencyAreTheSame -> _convertResult.postValue(
                        stringLoader.get(
                            R.string.txt_original_currency_and_desired_currency_can_not_be_the_same
                        )
                    )
                    else -> _error.postValue(SingleEvent(Unit))

                }
            }
        }
    }

    private fun createRealTimeRatesUseCaseParams() =
        RealTimeRatesUseCase.Params(
            originalCurrencyCode = _originalCurrency,
            desiredCurrencyCode = _desiredCurrency,
            value = currencyValue.value
        )
}