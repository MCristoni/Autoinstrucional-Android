package com.mcristoni.autoinstrucional

import android.app.Activity
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.game_activity.*

class GameActivity : AppCompatActivity() {
    private lateinit var drawingThread : DrawingThread
    private lateinit var hero : HeroView
    private lateinit var enemy : EnemyView
    private lateinit var target : TargetView
    private var mHeroSize: Float = 0f
    private var mEnemySize: Float = 0f
    private var mTargetSize: Float = 0f
    private val fps = 50
    private var mMovingTarget: Boolean = false
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        getIntentExtras()
        setNewViews()
        addViews()
        setDrawingThread()
        frame_main.setOnClickListener(mClickScreenListener)
        frame_info_message.setOnClickListener(mClickScreenListener)
    }

    private fun getIntentExtras() {
        mHeroSize = intent.getFloatExtra(Constants.HERO_SIZE_VALUE, 0f)
        mEnemySize = intent.getFloatExtra(Constants.ENEMY_SIZE_VALUE, 0f)
        mTargetSize = intent.getFloatExtra(Constants.TARGET_SIZE_VALUE, 0f)
        mMovingTarget = intent.getBooleanExtra(Constants.TARGET_MOVING_VALUE, false);
    }

    private fun setNewViews() {
        hero = HeroView(this, mHeroSize,false)
        enemy = EnemyView(this, mEnemySize, false)
        target = TargetView(this, mTargetSize, false, mMovingTarget)
    }

    private fun addViews() {
        frame_main.addView(hero)
        frame_main.addView(enemy)
        frame_main.addView(target)
    }

    private fun setDrawingThread() {
        drawingThread = DrawingThread(this, hero, enemy, target, fps, Callback())
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
        enemy.mGameActivityClicked = true
        hero.mGameActivityClicked = true
        target.mGameActivityClicked = true

        if(clickCount == 0 || !drawingThread.isRunning){
            drawingThread.start()
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

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        hero.sensorManager.registerListener(hero ,hero.accelerometer, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        hero.sensorManager.unregisterListener(hero)
        super.onPause()
    }

    internal class Callback{
        fun onRetry(mGameActivity: GameActivity) {
            mGameActivity.setupNewGame()
            mGameActivity.onResume()
        }
    }
}
