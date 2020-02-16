package com.zatsepinvl.activity.play

import android.graphics.PorterDuff
import android.graphics.Shader
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.zatsepinvl.activity.play.android.color
import com.zatsepinvl.activity.play.android.resource.TileDrawable
import com.zatsepinvl.activity.play.color.ColoredView
import kotlinx.android.synthetic.main.activity_main.*

class ActivityPlayMainActivity : AppCompatActivity(), ColoredView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        resetBackgroundColor()
    }

    override fun resetBackgroundColor() {
        changeBackgroundColor(applicationContext.color(R.color.colorPrimary))
    }

    override fun changeBackgroundColor(color: Int) {
        window.statusBarColor = color
        window.navigationBarColor = color
        val backgroundDrawable = this.getDrawable(R.drawable.wallpaper)!!
        mainLayout.background = TileDrawable(
            backgroundDrawable,
            Shader.TileMode.REPEAT
        )
        mainLayout.background.apply {
            alpha = 10
            setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }
}
