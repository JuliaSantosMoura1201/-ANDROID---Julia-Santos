package com.example.domain.exceptions

sealed class ConvertException: Exception(){
    object InvalidValue: ConvertException()
    object EmptyOriginalCurrency: ConvertException()
    object EmptyDesiredCurrency: ConvertException()
    object InvalidOriginalCurrency: ConvertException()
    object InvalidDesiredCurrency: ConvertException()
    object OriginalCurrencyAndDesiredCurrencyAreTheSame: ConvertException()
}