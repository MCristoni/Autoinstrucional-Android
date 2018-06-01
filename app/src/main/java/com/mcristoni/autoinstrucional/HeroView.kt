package com.mcristoni.autoinstrucional

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

class HeroView : View {
    constructor(context : Context) : super(context)
    constructor(context : Context, attrs : AttributeSet) : super(context, attrs)
    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr)
    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int, defStyleRes : Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint =  Paint()
        val heroX = (width/ 2).toFloat()
        val HeroY = (height).toFloat()-75
        val textOffset = 10
        val textSize = 30f
        if (canvas != null) {
            paint.setARGB(255, 0, 0, 255)
            paint.style = Paint.Style.FILL_AND_STROKE
            canvas.drawCircle(heroX, HeroY, 50f, paint)

            paint.setARGB(255, 255, 255, 255)
            paint.textSize = textSize
            paint.textAlign = Paint.Align.CENTER
            paint.typeface = Typeface.DEFAULT_BOLD
            canvas.drawText("Hero", heroX, HeroY+textOffset, paint)
        }
    }
}