package nl.vaya.mgdd.rjp;

import nl.vaya.mgdd.rjp.layer.FloorLayer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;


public class GameDraw extends View {

	protected FloorLayer floor;

	protected int _winWith;
	protected int _winHeight;
	
	protected Context context;

	
	protected OnTouchListener inputHandler;
	
	protected float initialTouchXDisposition = 0;
	protected float initialTouchYDisposition = 0;
	protected int motionDetectionArea = 25;

	private void InitView(Context contexts) {
		
		this.setWillNotDraw(false);

		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		_winWith = display.getWidth();
		_winHeight = display.getHeight();

		// Create Layers
		floor = new FloorLayer(context, _winWith, _winHeight);

		setFocusable(true);
		
		inputHandler = new OnTouchListener() {

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
		};
		this.setOnTouchListener(inputHandler);
	}

	public GameDraw(Context contextS) {
		super(contextS);
		context = contextS;
		InitView(contextS);
		
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
        
		Log.i("log_tag", "drawing");
		floor.moveFloor(-1, -1);
		floor.createFloor(canvas);
		invalidate();
	}	
}