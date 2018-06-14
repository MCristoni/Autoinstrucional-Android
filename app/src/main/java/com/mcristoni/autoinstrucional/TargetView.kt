package com.mcristoni.autoinstrucional

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class TargetView : View {
    val targetX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat()
    val targetY = 75f
    lateinit var target : Sprite
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
        target = Sprite(targetX-50, targetY, radius, radius, intArrayOf(255, 21, 104, 15))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawCircle(targetX, targetY, 50f, target.paint)
            canvas.drawText("Target", targetX, targetY+textOffset, target.paintName)
        }
    }
}