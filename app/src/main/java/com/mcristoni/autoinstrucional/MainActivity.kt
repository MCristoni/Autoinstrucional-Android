package com.mcristoni.autoinstrucional

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var drawingThread : DrawingThread2
    private lateinit var hero : HeroView
    private lateinit var enemy : EnemyView
    private lateinit var target : TargetView
    private val fps = 24
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNewViews()
        addViews()
        setDrawingThread()
        frame_main.setOnClickListener(mClickScreenListener)

    }

    private fun setDrawingThread() {
        drawingThread = DrawingThread2(hero, enemy, target, fps)
    }

    private fun addViews() {
        frame_main.addView(hero)
        frame_main.addView(enemy)
        frame_main.addView(target)
    }

    private fun setNewViews() {
        hero = HeroView(this)
        enemy = EnemyView(this)
        target = TargetView(this)
    }

    private val mClickScreenListener : View.OnClickListener = View.OnClickListener {
        if(clickCount > 0 && !drawingThread.isRunning){
            frame_main.removeAllViews()
            setNewViews()
            addViews()
            setDrawingThread()
            drawingThread.start()
        }else{
            if(!drawingThread.isRunning()){
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
}
