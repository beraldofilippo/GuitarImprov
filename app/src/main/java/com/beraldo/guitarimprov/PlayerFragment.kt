package com.beraldo.guitarimprov

import android.animation.ValueAnimator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView


/**
 * Created by Filippo Beraldo on 23/09/2018.
 * http://github.com/beraldofilippo
 */
class PlayerFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerFragment()
    }

    private val currentKeyText: TextView by lazy { activity!!.findViewById<TextView>(R.id.current_key) }
    private val currentSetText: TextView by lazy { activity!!.findViewById<TextView>(R.id.current_set) }
    private val currentInvText: TextView by lazy { activity!!.findViewById<TextView>(R.id.current_inv) }
    private val durationText: TextView by lazy { activity!!.findViewById<TextView>(R.id.duration) }

    private val minusDuration: ImageView by lazy { activity!!.findViewById<ImageView>(R.id.minus) }
    private val plusDuration: ImageView by lazy { activity!!.findViewById<ImageView>(R.id.plus) }

    private val playPauseButton: LottieAnimationView by lazy { activity!!.findViewById<LottieAnimationView>(R.id.play_pause) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val model = ViewModelProviders.of(this)
                .get(PlayerViewModel::class.java)

        model.currentKey.observe(this, Observer<String> { newValue ->
            currentKeyText.text = newValue
        })

        model.currentSet.observe(this, Observer<String> { newValue ->
            currentSetText.text = newValue
        })

        model.currentInv.observe(this, Observer<String> { newValue ->
            currentInvText.text = newValue
        })

        model.playingStatus.observe(this, Observer<Boolean> { playing ->
            startPlayPauseAnimation(playPauseButton, playing)
        })

        model.durationStatus.observe(this, Observer<Int> { newValue ->
            durationText.text = newValue.toString()
        })

        minusDuration.setOnClickListener {
            model.onMinusClick()
        }

        plusDuration.setOnClickListener {
            model.onPlusClick()
        }

        playPauseButton.setOnClickListener {
            model.onPlayPauseClick()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    private fun startPlayPauseAnimation(lottieAnimationView: LottieAnimationView, playing: Boolean?) {
        val animator = ValueAnimator.ofFloat(0f, 1f) // 0F Play is clickable, 1F pause is clickable
        animator.addUpdateListener { valueAnimator ->
            lottieAnimationView.progress = valueAnimator.animatedValue as Float
        }

        if (playing == true) {
            animator.start()
        } else {
            animator.reverse()
        }
    }
}