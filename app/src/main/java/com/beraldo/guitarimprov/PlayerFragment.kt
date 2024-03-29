package com.beraldo.guitarimprov

import android.animation.ValueAnimator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
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

    private val currentKeyText: TextView by lazy { requireActivity().findViewById(R.id.current_key) }
    private val currentSetText: TextView by lazy { requireActivity().findViewById(R.id.current_set) }
    private val currentInvText: TextView by lazy { requireActivity().findViewById(R.id.current_inv) }
    private val currentScaleText: TextView by lazy { requireActivity().findViewById(R.id.current_scale) }
    private val durationText: TextView by lazy { requireActivity().findViewById(R.id.duration) }

    private val minusDuration: ImageView by lazy { requireActivity().findViewById(R.id.minus) }
    private val plusDuration: ImageView by lazy { requireActivity().findViewById(R.id.plus) }
    private val info: ImageView by lazy { requireActivity().findViewById(R.id.info) }

    private val playPauseButton: LottieAnimationView by lazy { requireActivity().findViewById(R.id.play_pause) }

    private lateinit var mediaPlayer : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaPlayer = MediaPlayer.create(activity, R.raw.beep_short)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val model = ViewModelProviders.of(this)[PlayerViewModel::class.java]

        model.currentKey.observe(viewLifecycleOwner) { newValue ->
            currentKeyText.text = newValue

            playBeep()
        }

        model.currentSet.observe(viewLifecycleOwner) { newValue ->
            currentSetText.text = newValue
        }

        model.currentInv.observe(viewLifecycleOwner) { newValue ->
            currentInvText.text = newValue
        }

        model.currentScale.observe(viewLifecycleOwner) { newValue ->
            currentScaleText.text = newValue
        }

        model.playingStatus.observe(viewLifecycleOwner) { playing ->
            startPlayPauseAnimation(playPauseButton, playing)
        }

        model.durationStatus.observe(viewLifecycleOwner) { newValue ->
            durationText.text = getFormattedDuration(newValue)
        }

        minusDuration.setOnClickListener {
            model.onMinusClick()
        }

        plusDuration.setOnClickListener {
            model.onPlusClick()
        }

        playPauseButton.setOnClickListener {
            model.onPlayPauseClick()
        }

        info.setOnClickListener {
            showCredits()
        }
    }

    private fun getFormattedDuration(newValue: Int?) = newValue.toString().plus("s")

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

    private fun playBeep() {
        mediaPlayer.start()
    }

    private fun showCredits() {
        AlertDialog.Builder(activity as FullscreenActivity).create().apply {
            setTitle(getString(R.string.credits_title))
            setCancelable(false)
            setMessage(resources.getString(R.string.credits))
            setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)) {
                dialog, _ -> dialog.dismiss()
            }
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer.release()
    }
}