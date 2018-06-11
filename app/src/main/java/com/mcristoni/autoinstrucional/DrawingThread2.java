package com.mcristoni.autoinstrucional;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class DrawingThread2 {

	private final HeroView mHeroView;
	private final EnemyView mEnemyView;
	private final TargetView mTargetView;
	private final int mFps;
	private final MainActivity.mCallback mCallback;
	private final MainActivity mMainActivity;
	private Thread thread = null;
	private Handler handler;
	private boolean running = false;
	private boolean stopped = false;

	public DrawingThread2(MainActivity activity, HeroView hero, EnemyView enemy, TargetView target, int fps, MainActivity.mCallback callback){
		if (hero == null || enemy == null || target == null || fps <= 0) {
			throw new IllegalArgumentException();
		}
		mHeroView = hero;
		mEnemyView = enemy;
		mTargetView = target;
		mFps = fps;
		mMainActivity = activity;
		mCallback = callback;
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
			running = false;
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
			running = true;
			while (running) {
				// sleep for a short time between frames of animation
				try {
					Thread.sleep(1000 / mFps);
				} catch (InterruptedException ie) {
					running = false;
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

			if (enemyRect.intersects(heroRect.left, heroRect.top, heroRect.right, heroRect.bottom)
                    && running){
				new AlertDialog.Builder(mTargetView.getContext())
                        .setTitle("Você perdeu!")
                        .setMessage("Para iniciar um novo jogo, pressione em qualquer lugar")
                        .setCancelable(true)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
								if (mCallback != null){
									mCallback.onLose(mMainActivity);
								}
                            }
                        })
                        .show();
				stop();
			}
			mTargetView.invalidate();
			mEnemyView.invalidate();
			mHeroView.invalidate();
		}
	}
}