package com.mcristoni.autoinstrucional

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.Window
import java.util.*
import kotlin.math.log

class EnemyView : View {
    val paint =  Paint()
    val textOffset = 10
    val radius = 100f
    val enemy_MAX_VELOCITY = 30f
    val textSize = 30f
    lateinit var enemy : Sprite
    var enemyX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat()
    var enemyY = (Resources.getSystem().displayMetrics.heightPixels/ 2).toFloat()

    constructor(context : Context) : super(context){
        init()
    }

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr){
        init()
    }

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int, defStyleRes : Int) : super(context, attrs, defStyleAttr, defStyleRes){
        init()
    }

    private fun init() {
        enemy = Sprite(enemyX-50, enemyY-50, radius, radius, intArrayOf(255, 255, 0, 0))
        enemy.setVelocity(
                ((Math.random() - .5) * 2 * enemy_MAX_VELOCITY).toFloat(),
                ((Math.random() - .5) * 2 * enemy_MAX_VELOCITY).toFloat()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        width
        if (canvas != null) {
            canvas.drawOval(enemy.rect, enemy.paint)
        }
        updateSprites()
    }

    private fun updateSprites() {
        enemy.move()
        val random = Random()
        enemy.move()

        // handle enemy bouncing off edges
        if (enemy.rect.left < 0 || enemy.rect.right >= width) {
            enemy.dx = -enemy.dx
            //enemy.dy = enemy.dy + (-50 + random.nextFloat() * (50 - -50))
        }
        if (enemy.rect.top < 0 || enemy.rect.bottom >= height){
            enemy.dy = -enemy.dy
            //enemy.dx = enemy.dx + (-15 + random.nextFloat() * (50 - -15))
        }
    }
}