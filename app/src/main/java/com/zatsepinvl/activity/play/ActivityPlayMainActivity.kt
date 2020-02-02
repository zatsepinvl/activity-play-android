package com.zatsepinvl.activity.play

import android.graphics.PorterDuff
import android.graphics.Shader
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zatsepinvl.activity.play.android.TileDrawable
import com.zatsepinvl.activity.play.color.ColoredView
import kotlinx.android.synthetic.main.activity_main.*

class ActivityPlayMainActivity : AppCompatActivity(), ColoredView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resetBackgroundColor()
    }

    override fun resetBackgroundColor() {
        changeBackgroundColor(resources.getColor(R.color.colorPrimary))
    }

    override fun changeBackgroundColor(color: Int) {
        val backgroundDrawable = this.getDrawable(R.drawable.wallpaper)!!
        mainLayout.background = TileDrawable(backgroundDrawable, Shader.TileMode.REPEAT)
        mainLayout.background.apply {
            alpha = 10
            setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }
}
