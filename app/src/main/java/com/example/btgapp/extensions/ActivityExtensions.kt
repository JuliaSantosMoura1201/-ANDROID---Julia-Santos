package com.example.btgapp.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.example.presentation.utils.SingleEvent

fun <T : Any> AppCompatActivity.observe(liveData: LiveData<T>, block: (T) -> Unit) {
    liveData.observe(this, {
        block(it)
    })
}

fun <T : Any> AppCompatActivity.observeSingleEvent(
    liveData: LiveData<SingleEvent<T>>?,
    observer: (T) -> Unit
) {
    liveData?.observe(this, { it ->
        it.getContentIfNotHandled()?.let {
            observer(it)
        }
    })
}