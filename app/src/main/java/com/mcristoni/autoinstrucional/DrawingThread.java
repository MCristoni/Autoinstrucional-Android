package com.mcristoni.autoinstrucional;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;

public class DrawingThread {

	private final HeroView mHeroView;
	private final EnemyView mEnemyView;
	private final TargetView mTargetView;
	private final int mFps;
	private final GameActivity.Callback Callback;
	private final GameActivity mGameActivity;
	private final RectF targetRect;
	private final RectF enemyRect;
	private final RectF heroRect;
	private Thread thread = null;
	private Handler handler;
	private boolean running = false;
	private boolean dialogShown = false;
	private Updater updater;

	public DrawingThread(GameActivity activity, HeroView hero, EnemyView enemy, TargetView target, int fps, GameActivity.Callback callback){
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
		enemyRect = mEnemyView.enemy.rect;
		heroRect = mHeroView.hero.rect;
		targetRect = mTargetView.target.rect;
	}

	public boolean isRunning() {
		return thread != null;
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
				if (updater == null){
					updater = new Updater();
				}
				handler.post(updater);
			}
		}
	}

	private class Updater implements Runnable {
		public void run() {
			//Verifica condições de perda
			//1- Bolinha vermelha tocar na bolinha azul
			//2- Bolinha azul tocar em alguma das paredes
			if ((RectF.intersects(heroRect, enemyRect)
					|| heroRect.left < 0 || heroRect.right >= mHeroView.getWidth()
					|| heroRect.bottom >= mHeroView.getHeight() || heroRect.top <= 0)  && running && !dialogShown){
				buildDialog(R.string.lose_message);
			}
			//Verifica condições de vitória
			//1- Bolinha azul tocar na bolinha verde
			else if (RectF.intersects(heroRect, targetRect) && running && !dialogShown){
				buildDialog(R.string.win_message);
			}

			//Redesenha as views
			mTargetView.invalidate();
			mEnemyView.invalidate();
			mHeroView.invalidate();
		}

		//Método responsável por buildar um dialog informando mensagem de vitória ou derrota por parâmetro
		private void buildDialog(int msgId){
			dialogShown = true;
			new AlertDialog.Builder(mTargetView.getContext())
					.setMessage(msgId)
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
							mGameActivity.onBackPressed();
						}
					})
					.show();
			mEnemyView.enemy.stopMoving();
			mHeroView.hero.stopMoving();
			stop();
		}
	}
}