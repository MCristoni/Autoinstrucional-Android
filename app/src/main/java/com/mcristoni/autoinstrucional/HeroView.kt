package com.mcristoni.autoinstrucional

import android.content.Context.SENSOR_SERVICE
import android.content.res.Resources
import android.graphics.BitmapFactory.decodeResource
import android.graphics.Canvas
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.View


class HeroView(context: GameActivity, size: Float, gameActivityClicked: Boolean) : View(context), SensorEventListener {

    lateinit var hero : Sprite
    private val heroX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat() - (size/2)
    private val HeroY = (Resources.getSystem().displayMetrics.heightPixels*0.9).toFloat()-size
    val textSize = 15f
    var sensorManager: SensorManager
    var accelerometer: Sensor
    var mGameActivityClicked: Boolean = false
    var xAcceleration:Float = 0.toFloat()
    var yAcceleration:Float = 0.toFloat()
    private var mSize: Int

    init{
        mGameActivityClicked = gameActivityClicked
        hero = Sprite(heroX, HeroY, size, size, intArrayOf(255, 0, 0, 255))
        sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSize = size.toInt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas?.drawOval(hero.rect, hero.paint)
        val bmp = decodeResource(resources, R.drawable.rat_icon)
        val rect = Rect(0, 0, mSize*8, mSize*8)
        canvas?.drawBitmap(bmp, rect, hero.rect, hero.paint)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //do nothing
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER && mGameActivityClicked){
            xAcceleration = event.values[0]
            yAcceleration = event.values[1]
            updateSprite()
        }
    }

    private fun updateSprite() {
        hero.move()
        val velX = hero.dx - (xAcceleration/100)
        val velY = hero.dy + (yAcceleration/100)
        hero.setVelocity(velX, velY)
    }
}
