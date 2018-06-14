package com.mcristoni.autoinstrucional;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;

public class DrawingThread2 {

	private final HeroView mHeroView;
	private final EnemyView mEnemyView;
	private final TargetView mTargetView;
	private final int mFps;
	private final GameActivity.Callback Callback;
	private final GameActivity mGameActivity;
	private Thread thread = null;
	private Handler handler;
	private boolean running = false;
	private boolean stopped = false;

	public DrawingThread2(GameActivity activity, HeroView hero, EnemyView enemy, TargetView target, int fps, GameActivity.Callback callback){
		if (hero == null || enemy == null || target == null || fps <= 0) {
			throw new IllegalArgumentException();
		}
		mHeroView = hero;
		mEnemyView = enemy;
		mTargetView = target;
		mFps = fps;
		mGameActivity = activity;
		Callback = callback;
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
                        .setMessage("Você perdeu!\n\nDeseja reiniciar o jogo com as mesmas configurações?")
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
								if (Callback != null){
									Callback.onRetry(mGameActivity);
								}
                            }
                        })
						.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

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