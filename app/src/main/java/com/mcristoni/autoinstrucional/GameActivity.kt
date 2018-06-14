package com.mcristoni.autoinstrucional

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.game_activity.*


class GameActivity : AppCompatActivity() {
    private lateinit var drawingThread : DrawingThread2
    private lateinit var hero : HeroView
    private lateinit var enemy : EnemyView
    private lateinit var target : TargetView
    private val fps = 30
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        setNewViews()
        addViews()
        setDrawingThread()
        frame_main.setOnClickListener(mClickScreenListener)
        frame_info_message.setOnClickListener(mClickScreenListener)

        val mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private fun setNewViews() {
        hero = HeroView(this, false)
        enemy = EnemyView(this, false)
        target = TargetView(this)
    }

    private fun addViews() {
        frame_main.addView(hero)
        frame_main.addView(enemy)
        frame_main.addView(target)
    }

    private fun setDrawingThread() {
        drawingThread = DrawingThread2(this, hero, enemy, target, fps, Callback())
    }

    fun setupNewGame(){
        frame_info_message.visibility = View.VISIBLE
        frame_main.visibility = View.GONE
        frame_main.removeAllViews()
        setNewViews()
        addViews()
        setDrawingThread()
    }

    private val mClickScreenListener : View.OnClickListener = View.OnClickListener {
        frame_main.visibility = View.VISIBLE
        frame_info_message.visibility = View.GONE
        enemy.mMainActivityClicked = true
        if(clickCount > 0 && !drawingThread.isRunning){
            drawingThread.start()
        }else{
            if(!drawingThread.isRunning){
                drawingThread.start()
            }
        }
        clickCount++
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    internal class Callback{
        fun onRetry(mGameActivity: GameActivity) {
            mGameActivity.setupNewGame()
        }

        fun onGameOver(){

        }
    }

}
