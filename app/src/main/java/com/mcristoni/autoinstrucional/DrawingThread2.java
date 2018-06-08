package com.mcristoni.autoinstrucional;

import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DrawingThread2 {

	private final HeroView mHeroView;
	private final EnemyView mEnemyView;
	private final TargetView mTargetView;
	private final int mFps;
	private Thread thread = null;
	private Handler handler;
	private boolean isRunning = false;
	private boolean stopped = false;

	public DrawingThread2(HeroView hero, EnemyView enemy, TargetView target, int fps){
		if (hero == null || enemy == null || target == null || fps <= 0) {
			throw new IllegalArgumentException();
		}
		mHeroView = hero;
		mEnemyView = enemy;
		mTargetView = target;
		mFps = fps;
		this.handler = new Handler(Looper.getMainLooper());
	}

	public boolean isRunning() {
		return thread != null;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(new MainRunner());
			thread.start();
		}
	}

	private void stop() {
		if (thread != null) {
			isRunning = false;
			try {
				thread.join();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			thread = null;
		}
	}

	private class MainRunner implements Runnable {
		public void run() {
			isRunning = true;
			while (isRunning) {
				// sleep for a short time between frames of animation
				try {
					Thread.sleep(1000 / mFps);
				} catch (InterruptedException ie) {
					isRunning = false;
				}

				// post a message that will cause the view to redraw
				handler.post(new Updater());
			}
		}
	}

	private class Updater implements Runnable {
		public void run() {
            RectF enemyRect = mEnemyView.enemy.rect;
            RectF heroRect = mHeroView.hero.rect;
            RectF targetRect = mTargetView.target.rect;

			if (enemyRect.intersects(heroRect.left, heroRect.top, heroRect.right, heroRect.bottom)){
				Toast.makeText(mHeroView.getContext(), "OPA", Toast.LENGTH_SHORT).show();
				stopped = true;
				stop();
			}
			mTargetView.invalidate();
			mEnemyView.invalidate();
			mHeroView.invalidate();
		}
	}
}