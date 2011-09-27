package nl.vaya.mgdd.rjp;

import nl.vaya.mgdd.rjp.layer.FloorLayer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;


public class GameDraw extends View implements OnTouchListener {

	protected FloorLayer floor;

	protected int _winWith;
	protected int _winHeight;
	
	protected Context context;
	
	protected float initialTouchXDisposition = 0;
	protected float initialTouchYDisposition = 0;
	protected int motionDetectionArea = 25;

	
	public GameDraw(Context context) {
		super(context);

		setWillNotDraw(false);
		setOnTouchListener(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		_winWith = display.getWidth();
		_winHeight = display.getHeight();

		// Create Layers
		floor = new FloorLayer(context, _winWith, _winHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		floor.createFloor(canvas);
		invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			Log.i("pointmove", "start detection");
			initialTouchXDisposition = event.getX();
			initialTouchYDisposition = event.getY();
			return true;
		case MotionEvent.ACTION_CANCEL:
			initialTouchXDisposition = 0.0f;
			initialTouchYDisposition = 0.0f;
			return false;
		default:
			float x = event.getX() - initialTouchXDisposition;
			float y = event.getY() - initialTouchYDisposition;
			Log.i("pointmove", "x: " + Math.ceil(x/motionDetectionArea) + ", y: " + Math.ceil(y/motionDetectionArea));
			return true;
		}
	}	
}