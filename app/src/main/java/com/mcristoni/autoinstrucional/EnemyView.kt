package com.mcristoni.autoinstrucional

import android.app.Application
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
    val textOffset = 10
    val radius = 100f
    val enemy_MAX_VELOCITY = 50f
    val textSize = 30f
    val random = Random()
    lateinit var enemy : Sprite
    var enemyX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat()
    var enemyY = (Resources.getSystem().displayMetrics.heightPixels/ 2).toFloat()
    var mMainActivityClicked: Boolean = false

    constructor(context : Context, mainActivityClicked: Boolean) : super(context){
        init(mainActivityClicked)
    }

    private fun init(mainActivityClicked: Boolean) {
        mMainActivityClicked = mainActivityClicked
        val velX = (-50 + random.nextFloat() * enemy_MAX_VELOCITY)
        val velY = (-50 + random.nextFloat() * enemy_MAX_VELOCITY)

        enemy = Sprite(enemyX-50, enemyY-50, radius, radius, intArrayOf(255, 255, 0, 0))
        enemy.setVelocity(velX, velY)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawOval(enemy.rect, enemy.paint)
            canvas.drawText("Enemy", enemy.rect.centerX(), enemy.rect.centerY() + textOffset, enemy.paintName)
        }
        if (mMainActivityClicked){
            updateSprites()
        }
    }

    private fun updateSprites() {
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