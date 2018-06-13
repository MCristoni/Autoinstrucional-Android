package com.mcristoni.autoinstrucional

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.AttributeSet
import android.view.View
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager



class HeroView : View, SensorEventListener {

    val heroX = (Resources.getSystem().displayMetrics.widthPixels/ 2).toFloat()
    val HeroY = (Resources.getSystem().displayMetrics.heightPixels).toFloat()
    lateinit var hero : Sprite
    val radius = 100f
    val textOffset = 10f
    val textSize = 30f
    lateinit var sensorManager: SensorManager
    lateinit var accelerometer: Sensor?
    private var lastUpdate : Long = 0

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

        sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
        for (sensor in sensorManager.getSensorList(Sensor.TYPE_ALL)) {
            println(sensor.name)
        }

        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        lastUpdate = System.currentTimeMillis()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawOval(hero.rect, hero.paint)
            canvas.drawText("Hero", hero.rect.centerX(), hero.rect.centerY()+textOffset, hero.paintName)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //do nothing
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event.sensor.type === Sensor.TYPE_ACCELEROMETER) {
            val actualTime = System.currentTimeMillis()
            if (actualTime - lastUpdate > 500) {
                lastUpdate = actualTime

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                xView.setText(x.toString())
                yView.setText(y.toString())
                zView.setText(z.toString())

                lowPassX = lowPass(x, lowPassX)
                lowPassY = lowPass(y, lowPassY)
                lowPassZ = lowPass(z, lowPassZ)
                xLowPassView.setText(String.valueOf(lowPassX))
                yLowPassView.setText(String.valueOf(lowPassY))
                zLowPassView.setText(String.valueOf(lowPassZ))

                highPassX = highPass(x, lastX, highPassX)
                highPassY = highPass(y, lastY, highPassY)
                highPassZ = highPass(z, lastZ, highPassZ)
                xHighPassView.setText(String.valueOf(highPassX))
                yHighPassView.setText(String.valueOf(highPassY))
                zHighPassView.setText(String.valueOf(highPassZ))

                calibX = zeroX - x
                calibY = zeroY - y
                calibZ = zeroZ - z
                xCalibView.setText(String.valueOf(calibX))
                yCalibView.setText(String.valueOf(calibY))
                zCalibView.setText(String.valueOf(calibZ))

                lastX = x
                lastY = y
                lastZ = z
            }
        }
    }
}
