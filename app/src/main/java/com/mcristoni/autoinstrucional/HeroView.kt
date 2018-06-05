package com.mcristoni.autoinstrucional

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

class HeroView : View {
    val heroX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat()
    val HeroY = (Resources.getSystem().displayMetrics.heightPixels).toFloat()
    lateinit var hero : Sprite
    val radius = 100f
    val textOffset = 10f
    val textSize = 30f

    constructor(context : Context) : super(context){
        init()
    }

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs){
        init()
    }

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr){
        init()
    }

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int, defStyleRes : Int) : super(context, attrs, defStyleAttr, defStyleRes){
        init()
    }

    private fun init(){
        hero = Sprite(heroX-50, HeroY, radius, radius, intArrayOf(255, 0, 0, 255))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawOval(hero.rect, hero.paint)
            canvas.drawText("Hero", hero.rect.centerX(), hero.rect.centerY()+textOffset, hero.paintName)
        }
    }
}