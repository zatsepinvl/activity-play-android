package com.zatsepinvl.activityplay

import android.graphics.PorterDuff
import android.graphics.Shader
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zatsepinvl.activityplay.android.color
import com.zatsepinvl.activityplay.android.resource.TileDrawable
import com.zatsepinvl.activityplay.color.ColoredView
import kotlinx.android.synthetic.main.activity_main.*

class ActivityPlayMainActivity : AppCompatActivity(), ColoredView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        resetBackgroundColor()
        val db = Firebase.firestore
        db.collection("rooms")
            .document("1")
            .get().addOnSuccessListener {
                println(it.data)
            }
    }

    override fun resetBackgroundColor() {
        changeBackgroundColor(applicationContext.color(R.color.colorPrimary))
    }

    override fun changeBackgroundColor(color: Int) {
        val backgroundDrawable = ContextCompat.getDrawable(this, R.drawable.wallpaper)!!
        mainLayout.background = TileDrawable(
            backgroundDrawable,
            Shader.TileMode.REPEAT
        )
        mainLayout.background.apply {
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
