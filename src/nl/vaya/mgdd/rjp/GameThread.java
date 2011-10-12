package nl.vaya.mgdd.rjp;

import android.graphics.Paint;
import android.util.Log;

public class GameThread extends Thread {
	/** Are we running ? */
	private boolean mRun;

	/** Used to figure out elapsed time between frames */
	private long mLastTime;

	/** Variables for the counter */
	private int frameSamplesCollected = 0;
	private int frameSampleTime = 0;
	private int fps = 0;

	/** Handle to the surface manager object we interact with */
	private GameDraw mView;

	/** How to display the text */
	private Paint textPaint;
	
	protected String log_tag = "Game thread";

	public GameThread(GameDraw view) {
		mView = view;
	}

	/**
	 * The actual game loop!
	 */
	@Override
	public void run() {
		while (mRun) {
			try {
				synchronized (mView.getCanvas()) {
					// updatePhysics();
					// doDraw();
				}
			} finally {
				Log.i(log_tag, "Failed to sync canvas.");
			}
		}
	}

	private void doDraw() {
		mView.draw(mView.getCanvas());
	}

	/**
	 * So we can stop/pauze the game loop
	 */
	public void setRunning(boolean b) {
		mRun = b;
	}

	/**
	 * Figures the gamestate based on the passage of realtime. Called at the
	 * start of draw(). Only calculates the FPS for now.
	 */
	private void updatePhysics() {
		long now = System.currentTimeMillis();

		if (mLastTime != 0) {

			// Time difference between now and last time we were here
			int time = (int) (now - mLastTime);
			frameSampleTime += time;
			frameSamplesCollected++;

			// After 10 frames
			if (frameSamplesCollected == 10) {

				// Update the fps variable
				fps = (int) (10000 / frameSampleTime);

				// Reset the sampletime + frames collected
				frameSampleTime = 0;
				frameSamplesCollected = 0;
			}
		}

		mLastTime = now;
	}
}