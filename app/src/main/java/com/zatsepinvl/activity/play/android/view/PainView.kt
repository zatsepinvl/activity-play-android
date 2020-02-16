package com.zatsepinvl.activity.play.android.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.R.color.colorPrimary
import com.zatsepinvl.activity.play.android.color

const val DEFAULT_PAINT_STROKE_WIDTH = 3f

class PainView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var path = Path()
    private val paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND

        val attributes = context.theme.obtainStyledAttributes(
            attrs, R.styleable.PaintView, 0, 0
        )
        try {
            paint.color = attributes.getColor(
                R.styleable.PaintView_paintColor,
                context.color(colorPrimary)
            )
            paint.strokeWidth = attributes.getFloat(
                R.styleable.PaintView_paintStrokeWidth,
                DEFAULT_PAINT_STROKE_WIDTH
            )
        } finally {
            attributes.recycle()
        }
    }

    var paintColor: Int = resources.getColor(colorPrimary)
        set(value) {
            paint.color = value
            field = value
        }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawPath(path, paint)
    }

    fun clear() {
        path = Path()
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //  if (SingleGameTimer.isTimerWorking()) {
        // Get the coordinates of the touch event.
        val eventX = event.x
        val eventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Set a new starting point
                path.moveTo(eventX, eventY)
                return true
            }
            MotionEvent.ACTION_MOVE ->
                // Connect the points
                path.lineTo(eventX, eventY)
            else -> return false
        }

        // Makes our view repaint and call onDraw
        invalidate()
        return true
        //   } else return false;
    }

}