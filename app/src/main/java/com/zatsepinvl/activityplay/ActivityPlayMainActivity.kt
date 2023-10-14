package com.zatsepinvl.activityplay

import android.graphics.BlendModeColorFilter
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.Shader
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.zatsepinvl.activityplay.android.color
import com.zatsepinvl.activityplay.android.resource.TileDrawable
import com.zatsepinvl.activityplay.color.ColoredView
import com.zatsepinvl.activityplay.databinding.ActivityMainBinding

class ActivityPlayMainActivity : AppCompatActivity(), ColoredView {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val view = binding.root
        setContentView(view)
        resetBackgroundColor()
    }

    override fun resetBackgroundColor() {
        changeBackgroundColor(applicationContext.color(R.color.colorPrimary))
    }

    override fun changeBackgroundColor(color: Int) {
        val backgroundDrawable = AppCompatResources.getDrawable(this, R.drawable.wallpaper)!!
        binding.mainLayout.background = TileDrawable(
            backgroundDrawable,
            Shader.TileMode.REPEAT
        )
        binding.mainLayout.background.apply {
            alpha = 10
            setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
        changeBarsColor(color)
    }

    override fun changeBarsColor(color: Int) {
        window.statusBarColor = color
        window.navigationBarColor = color
    }

}
