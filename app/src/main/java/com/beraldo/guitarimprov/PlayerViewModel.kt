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
                val number = Math.random().toString()

                current.postValue(number)
                next.postValue(number)

                handler.postDelayed(this, 2000)
            }
        }
        handler.post(runnableCode)
    }
}