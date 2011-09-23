package nl.vaya.mgdd.rjp;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class ActivitySwipeDetector implements View.OnTouchListener {

	static final String logTag = "ActivitySwipeDetector";
	private GameDraw drawView;
	static final int MIN_DISTANCE = 100;
	private float downX, downY, upX, upY;

	public ActivitySwipeDetector(GameDraw drawView){
		Log.i(logTag, "Initializing input detection.");
	    this.drawView = drawView;
	}

	public void onRightToLeftSwipe(){
	    Log.i(logTag, "RightToLeftSwipe!");
	    //drawView.setDirection(3);
	}

	public void onLeftToRightSwipe(){
	    Log.i(logTag, "LeftToRightSwipe!");
	    //drawView.setDirection(4);
	}

	public void onTopToBottomSwipe(){
	    Log.i(logTag, "onTopToBottomSwipe!");
	    //drawView.setDirection(2);
	}

	public void onBottomToTopSwipe(){
	    Log.i(logTag, "onBottomToTopSwipe!");
	    //drawView.setDirection(1);
	}

	public boolean onTouch(View v, MotionEvent event) {
	    switch(event.getAction()){
	        case MotionEvent.ACTION_DOWN: {
	            downX = event.getX();
	            downY = event.getY();
	            return true;
	        }
	        case MotionEvent.ACTION_UP: {
	        	//if(drawView.pauze()){
	        		//drawView.setPauze(false);
	        	//}
	            upX = event.getX();
	            upY = event.getY();

	            float deltaX = downX - upX;
	            float deltaY = downY - upY;

	            // swipe horizontal?
	            if(Math.abs(deltaX) > MIN_DISTANCE){
	                // left or right
	                if(deltaX < 0) { this.onLeftToRightSwipe(); return true; }
	                if(deltaX > 0) { this.onRightToLeftSwipe(); return true; }
	            } else { Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }

	            // swipe vertical?
	            if(Math.abs(deltaY) > MIN_DISTANCE){
	                // top or down
	                if(deltaY < 0) { this.onTopToBottomSwipe(); return true; }
	                if(deltaY > 0) { this.onBottomToTopSwipe(); return true; }
	            } else { Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }
	            
	            if(Math.abs(deltaX) < MIN_DISTANCE && Math.abs(deltaY) < MIN_DISTANCE){
	            	//drawView.setPauze(true);
	            	Log.i(logTag, "No movement but a tab.");
	            }
	            
	            return true;
	        }
	    }
	    return false;
	}

	}

