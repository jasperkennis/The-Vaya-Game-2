package nl.vaya.mgdd.rjp;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class ActivitySwipeDetector implements View.OnClickListener {

	static final String logTag = "ActivitySwipeDetector";
	private GameDraw drawView;
	static final int MIN_DISTANCE = 100;
	private float downX, downY, upX, upY;

	public ActivitySwipeDetector(GameDraw drawView){
	    this.drawView = drawView;
	}
	
	@Override
	public void onClick(View v) {
		Log.i(logTag, "Starting registration of touch.");
	}
	
	public float getDownDisplacementX(){
		return this.downX;
	}
}