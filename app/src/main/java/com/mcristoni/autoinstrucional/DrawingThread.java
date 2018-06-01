package com.mcristoni.autoinstrucional;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

public class DrawingThread{

	private final View mView;
	private final int mFps;
	private Thread thread = null;
	private Handler handler = null;
	private boolean isRunning = false;

	public DrawingThread (View view, int fps){
		if (view == null || fps <= 0) {
			throw new IllegalArgumentException();
		}
		mView = view;
		mFps = fps;
		this.handler = new Handler(Looper.getMainLooper());
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


	public void stop() {
		if (thread != null) {
			isRunning = false;
			try {
				thread.join();
			} catch (InterruptedException ie) {
				// empty
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
			mView.invalidate();
		}
	}
}
