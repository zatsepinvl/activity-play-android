package com.zatsepinvl.activity.play

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread {
            TimeUnit.SECONDS.sleep(3)
            runOnUiThread { helloWorldText.text = "3 seconds left..." }
        }.start()
    }
}
