package com.mcristoni.autoinstrucional
import android.os.Handler
import android.os.Looper
import android.widget.Toast
class DrawingThread(hero:HeroView, enemy:EnemyView, target:TargetView, fps:Int) {
    private val mHeroView:HeroView
    private val mEnemyView:EnemyView
    private val mTargetView:TargetView
    private var mFps:Int = 0
    private lateinit var thread:Thread
    private val handler:Handler
    private var running = false

    init{
        if (fps <= 0) {
            throw IllegalArgumentException()
        }
        mHeroView = hero
        mEnemyView = enemy
        mTargetView = target
        mFps = fps
        this.handler = Handler(Looper.getMainLooper())
    }

    fun isRunning():Boolean {
        return thread.isInterrupted
    }

    fun start() {
        if (thread.state == Thread.State.NEW) {
            thread = Thread(MainRunner())
            thread.start()
        }
    }

    private fun stop() {
        if (thread.isInterrupted) {
            running = false
            try {
                thread.join()
            }
            catch (ie:InterruptedException) {
                ie.printStackTrace()
            }
            thread.interrupt()
        }
    }

    private inner class MainRunner:Runnable {
        override fun run() {
            running = true
            while (running) {
                // sleep for a short time between frames of animation
                try {
                    Thread.sleep((1000 / mFps).toLong())
                }
                catch (ie:InterruptedException) {
                    running = false
                }
                // post a message that will cause the view to redraw
                handler.post(Updater())
            }
        }
    }
    private inner class Updater:Runnable {
        override fun run() {
            val enemyRect = mEnemyView.enemy.rect
            val heroRect = mHeroView.hero.rect
            //RectF targetRect = ((EnemyView) mEnemyView).enemy.rect;
            if (enemyRect.intersects(heroRect.left, heroRect.top, heroRect.right, heroRect.bottom)) {
                Toast.makeText(mHeroView.getContext(), "OPA", Toast.LENGTH_SHORT).show()
                stop()
            }
            mTargetView.invalidate()
            mEnemyView.invalidate()
            mHeroView.invalidate()
        }
    }
}