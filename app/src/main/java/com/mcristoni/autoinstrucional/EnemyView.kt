package com.mcristoni.autoinstrucional

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.view.View
import java.util.*

class EnemyView(context: Context, size: Float, mainActivityClicked: Boolean) : View(context) {
    val textOffset = 10
    val enemy_MAX_VELOCITY = 50f
    val textSize = 15f
    val random = Random()
    lateinit var enemy : Sprite
    var enemyX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat()
    var enemyY = (Resources.getSystem().displayMetrics.heightPixels/ 2).toFloat()
    var mMainActivityClicked: Boolean = false

    init {
        mMainActivityClicked = mainActivityClicked
        val velX = (-50 + random.nextFloat() * enemy_MAX_VELOCITY)
        val velY = (-50 + random.nextFloat() * enemy_MAX_VELOCITY)

        enemy = Sprite(enemyX-50, enemyY-50, size, size, intArrayOf(255, 255, 0, 0))
        enemy.setVelocity(velX, velY)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawOval(enemy.rect, enemy.paint)
//            canvas.drawText("Enemy", enemy.rect.centerX(), enemy.rect.centerY() + textOffset, enemy.paintName)
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