package com.beraldo.guitarimprov

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import android.animation.ValueAnimator



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
//    private val nextKeyText: TextView by lazy { activity!!.findViewById<TextView>(R.id.next_key) }
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
        model.nextKey.observe(this, Observer<String> { newValue ->
//            nextKeyText.text = newValue
        })

        model.currentSet.observe(this, Observer<String> { newValue ->
            currentSetText.text = newValue
        })

        model.currentInv.observe(this, Observer<String> { newValue ->
            currentInvText.text = newValue
        })

        playPauseButton.progress = 1f
        playPauseButton.setOnClickListener {
            model.onPlayPauseClick()
            startAnimation(playPauseButton)
        }

        model.beginRandom()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    private fun startAnimation(lottieAnimationView: LottieAnimationView) {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { valueAnimator ->
            lottieAnimationView.progress = valueAnimator.animatedValue as Float }

        if (lottieAnimationView.progress == 0f) {
            animator.start()
        } else {
            animator.reverse()
        }
    }

}