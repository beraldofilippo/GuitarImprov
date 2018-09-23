package com.beraldo.guitarimprov

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler


/**
 * Created by Filippo Beraldo on 23/09/2018.
 * http://github.com/beraldofilippo
 */
class PlayerViewModel : ViewModel() {
    val handler = Handler()

    val current: MutableLiveData<String> = MutableLiveData()
    val next: MutableLiveData<String> = MutableLiveData()

    fun beginRandom() {
        val runnableCode = object : Runnable {
            override fun run() {
                when (next.value) {
                    null -> current.postValue(getRandomKeyValue())
                    else -> current.postValue(next.value)
                }
                next.postValue(getRandomKeyValue())

                handler.postDelayed(this, 2000)
            }
        }
        handler.post(runnableCode)
    }

fun getRandomKeyValue() = Math.random().toString()
}