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

	protected long lastUpdate = 0;
	protected long sleepTime = 0;

	//protected SurfaceHolder surfaceHolder;
	protected Context context;
	protected Handler handler;

	// Game engine
	protected GameEngine gEngine;

	
	protected OnTouchListener inputHandler;

	private void InitView(Context contexts) {
		
		// for the draw to work
		this.setWillNotDraw(false);

		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		_winWith = display.getWidth();
		_winHeight = display.getHeight();

		// Create Layers
		floor = new FloorLayer(context, _winWith, _winHeight);

		// Create input fetcher, used to detect both movement and tapping.
		//inputFetcher = new ActivitySwipeDetector(this);
		// this.setOnTouchListener(inputFetcher);
		//this.setOnClickListener(inputFetcher);

		setFocusable(true);
		
		inputHandler = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				Log.i("pointmove", x + "");
				return false;
			}
		};

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