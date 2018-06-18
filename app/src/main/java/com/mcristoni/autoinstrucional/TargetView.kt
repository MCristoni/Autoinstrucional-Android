package com.mcristoni.autoinstrucional

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class TargetView(context: Context, size: Float) : View(context) {
    private val targetX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat() - (size/2)
    private val targetY = 50f + (size/2)
    lateinit var target : Sprite
    val textSize = 30f

    init{
        target = Sprite(targetX, targetY, size, size, intArrayOf(255, 21, 104, 15))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawOval(target.rect, target.paint)
    }
}