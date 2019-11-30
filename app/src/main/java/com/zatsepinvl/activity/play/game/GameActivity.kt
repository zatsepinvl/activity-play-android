package com.zatsepinvl.activity.play.game

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zatsepinvl.activity.play.R

class GameActivity : AppCompatActivity() {

    private val model by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportFragmentManager.beginTransaction()
            .add(R.id.gameMainLayout, StartGameFragment())
            .commit()

        model.isPlayingRound.observe(this, Observer<Boolean> { isPlaying ->
            if (isPlaying) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.gameMainLayout, GameFrameFragment())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.gameMainLayout, FinishGameFragment())
                    .commit()
            }
        })
    }

}
