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

    companion object {
        private const val DURATION_MIN = 5
        private const val DURATION_MAX = 600
        private const val DURATION_DELTA_VALUE = 1
    }

    private val keys = ArrayList<String>().apply {
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

    private val sets = ArrayList<String>().apply {
        add("Set 1")
        add("Set 2")
        add("Set 3")
        add("Set 4")
    }

    private val inversions = ArrayList<String>().apply {
        add("1st Inv")
        add("2nd Inv")
        add("Fundamental")
    }

    private val scale = ArrayList<String>().apply {
        add("Maj")
        add("Min")
    }

    val handler = Handler()

    private var playing = false

    private var duration = 10

    val currentKey: MutableLiveData<String> = MutableLiveData()
    val currentSet: MutableLiveData<String> = MutableLiveData()
    val currentInv: MutableLiveData<String> = MutableLiveData()
    val currentScale: MutableLiveData<String> = MutableLiveData()

    val playingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val durationStatus: MutableLiveData<Int> = MutableLiveData()

    init { // Set initial state
        playingStatus.postValue(playing)
        durationStatus.postValue(duration)
    }

    private fun beginRandom() {
        val runnableCode = object : Runnable {
            override fun run() {
                val randomKeyValue = getRandomKeyValue()

                currentKey.postValue(randomKeyValue.key)
                currentSet.postValue(randomKeyValue.set)
                currentInv.postValue(randomKeyValue.inversion)
                currentScale.postValue(randomKeyValue.scale)

                handler.postDelayed(this, duration * 1000L)
            }
        }
        handler.post(runnableCode)
    }

    fun getRandomKeyValue() = RandomValueSet(
            key = keys.shuffled().take(1)[0],
            set = sets.shuffled().take(1)[0],
            inversion = inversions.shuffled().take(1)[0],
            scale = scale.shuffled().take(1)[0])

    fun onPlayPauseClick() {
        playing = !playing
        if (!playing) {
            handler.removeCallbacksAndMessages(null)
        } else {
            beginRandom()
        }
        playingStatus.postValue(playing)
    }

    fun onPlusClick() {
        durationStatus.postValue(increaseDuration())
    }

    fun onMinusClick() {
        durationStatus.postValue(decreaseDuration())
    }

    private fun increaseDuration(): Int {
        if (duration + DURATION_DELTA_VALUE > DURATION_MAX) {
            duration = DURATION_MAX
        }
        else {
            duration += DURATION_DELTA_VALUE
        }
        return duration
    }

    private fun decreaseDuration(): Int {
        if (duration - DURATION_DELTA_VALUE < DURATION_MIN) {
            duration = DURATION_MIN
        }
        else {
            duration -= DURATION_DELTA_VALUE
        }
        return duration
    }

    data class RandomValueSet(var key: String, var set: String, var inversion: String, var scale: String)
}