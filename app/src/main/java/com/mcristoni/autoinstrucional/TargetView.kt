package com.mcristoni.autoinstrucional

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class TargetView(context: Context, size: Float) : View(context) {
    val targetX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat()
    val targetY = 75f
    lateinit var target : Sprite
    val textSize = 30f

    init{
        target = Sprite(targetX-50, targetY, size, size, intArrayOf(255, 21, 104, 15))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(targetX, targetY, 50f, target.paint)
    }
}