package com.beraldo.guitarimprov

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Filippo Beraldo on 23/09/2018.
 * http://github.com/beraldofilippo
 */
class PlayerFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerFragment()
    }

    val currentText : TextView by lazy { activity!!.findViewById<TextView>(R.id.current_text) }
    val nextText: TextView by lazy {  activity!!.findViewById<TextView>(R.id.next_text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val model = ViewModelProviders.of(this)
                .get(PlayerViewModel::class.java)

        model.current.observe(this, Observer<String> { newValue ->
            currentText.text = newValue
        })
        model.next.observe(this, Observer<String> { newValue ->
            nextText.text = newValue
        })

        model.beginRandom()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

}