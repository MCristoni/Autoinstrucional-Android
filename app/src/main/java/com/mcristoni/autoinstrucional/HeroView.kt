package com.mcristoni.autoinstrucional

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.res.Resources
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.View
import android.widget.Toast


class HeroView(context: Context, size: Float, mainActivityClicked: Boolean) : View(context), SensorEventListener {

    lateinit var hero : Sprite
    val heroX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat()
    val HeroY = (Resources.getSystem().displayMetrics.heightPixels).toFloat()-size
    val textOffset = 10f
    val textSize = 15f
    lateinit var sensorManager: SensorManager
    lateinit var accelerometer: Sensor
    private var lastUpdate : Long = 0
    var mMainActivityClicked: Boolean = false

    init{
        mMainActivityClicked = mainActivityClicked
        hero = Sprite(heroX-50, HeroY, size, size, intArrayOf(255, 0, 0, 255))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawOval(hero.rect, hero.paint)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //do nothing
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val actualTime = System.currentTimeMillis()
                if (actualTime - lastUpdate > 500) {
                    lastUpdate = actualTime
                }
            }
        }
    }
}
