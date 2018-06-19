package com.mcristoni.autoinstrucional

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.*
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View

class TargetView(context: Context, size: Float, gameActivityClicked: Boolean, movingTarget: Boolean) : View(context) {
    private val targetX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat() - (size/2)
    private val targetY = 50f + (size/2)
    lateinit var target : Sprite
    var mGameActivityClicked: Boolean
    private var mMovingTarget: Boolean
    private var mSize: Int

    init{
        target = Sprite(targetX, targetY, size, size, intArrayOf(255, 21, 104, 15))
        mMovingTarget = movingTarget
        if (mMovingTarget){
            target.setVelocity(20f, 0f)
        }
        mGameActivityClicked = gameActivityClicked
        mSize = size.toInt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//      canvas?.drawOval(target.rect, target.paint)
        val bmp = decodeResource(resources, R.drawable.cheese_icon)
        val rect = Rect(0, 0, mSize*8, mSize*8)
        canvas?.drawBitmap(bmp, rect, target.rect, target.paint)

        if (mGameActivityClicked && mMovingTarget){
            updateSprite()
        }
    }

    private fun updateSprite() {
        target.move()
        if (target.rect.left < 0 || target.rect.right >= width) {
            target.dx = -target.dx
        }
    }
}