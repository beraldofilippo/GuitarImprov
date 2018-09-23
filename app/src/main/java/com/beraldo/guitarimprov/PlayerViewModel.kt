package com.beraldo.guitarimprov

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import java.util.*


/**
 * Created by Filippo Beraldo on 23/09/2018.
 * http://github.com/beraldofilippo
 */
class PlayerViewModel : ViewModel() {

    val keys = ArrayList<String>().apply {
        add("C")
        add("C#")
        add("Cb")
        add("D")
        add("D#")
        add("Db")
        add("E")
        add("Eb")
        add("F")
        add("F#")
        add("G")
        add("G#")
        add("Gb")
        add("A")
        add("A#")
        add("Ab")
        add("B")
        add("Bb")
    }

    val sets = ArrayList<String>().apply {
        add("Set 1")
        add("Set 2")
        add("Set 3")
        add("Set 4")
    }

    val inversions = ArrayList<String>().apply {
        add("1st Inv")
        add("2nd Inv")
        add("Fundamental")
    }

    val handler = Handler()

    var playing = true

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

    fun getRandomKeyValue() = keys.shuffled().take(1)[0]

    fun onPlayPauseClick() {
        playing = if (playing) {
            handler.removeCallbacksAndMessages(null)
            false
        } else {
            beginRandom()
            true
        }
    }
}