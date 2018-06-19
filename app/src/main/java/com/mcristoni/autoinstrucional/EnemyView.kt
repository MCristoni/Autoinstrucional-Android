package com.mcristoni.autoinstrucional

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.decodeResource
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import java.util.*

class EnemyView(context: Context, size: Float, gameActivityClicked: Boolean) : View(context) {
    val enemy_MAX_VELOCITY = 50f
    val textSize = 15f
    val random = Random()
    lateinit var enemy : Sprite
    var enemyX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat() - (size/2)
    var enemyY = (Resources.getSystem().displayMetrics.heightPixels/ 2).toFloat() - (size/2)
    var mGameActivityClicked: Boolean = false
    private val mSize: Int

    init {
        mGameActivityClicked = gameActivityClicked
        val velX = (-50 + random.nextFloat() * enemy_MAX_VELOCITY)
        val velY = (-50 + random.nextFloat() * enemy_MAX_VELOCITY)

        enemy = Sprite(enemyX, enemyY, size, size, intArrayOf(255, 255, 0, 0))
        enemy.setVelocity(velX, velY)
        mSize = size.toInt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas?.drawOval(enemy.rect, enemy.paint)
        val bmp = decodeResource(resources, R.drawable.cat_icon)
        val rect = Rect(0, 0, mSize*8, mSize*8)
        canvas?.drawBitmap(bmp, rect, enemy.rect, enemy.paint)
        if (mGameActivityClicked){
            updateSprites()
        }
    }

    private fun updateSprites() {
        enemy.move()
        if (enemy.rect.left < 0 || enemy.rect.right >= width) {
            enemy.dx = -enemy.dx
        }
        if (enemy.rect.top < 0 || enemy.rect.bottom >= height){
            enemy.dy = -enemy.dy
        }
    }
}