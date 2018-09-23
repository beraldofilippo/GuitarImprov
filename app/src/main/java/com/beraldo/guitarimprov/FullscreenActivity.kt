package com.beraldo.guitarimprov

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)

        supportFragmentManager.beginTransaction().add(R.id.fullscreen_content,
                PlayerFragment.newInstance(), "player_fragment")
                .commit()
    }
}
