package nl.vaya.mgdd.rjp;

import nl.vaya.mgdd.rjp.layer.FloorLayer;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

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

	// our Thread class which houses the game loop
	private GameLoop thread;

	protected ActivitySwipeDetector inputFetcher;

	private void InitView(Context contexts) {

		gEngine = new GameEngine();
		gEngine.Init(context.getResources());

		// for the draw to work
		this.setWillNotDraw(false);

		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		_winWith = display.getWidth();
		_winHeight = display.getHeight();

		// Create Layers
		floor = new FloorLayer(context, _winWith, _winHeight);

		// Create input fetcher, used to detect both movement and tapping.
		inputFetcher = new ActivitySwipeDetector(this);
		// this.setOnTouchListener(inputFetcher);
		this.setOnClickListener(inputFetcher);

		//thread = new GameLoop(holder, context, new Handler(), gEngine);
		setFocusable(true);
		
		handler = new Handler();
		
		thread = new GameLoop(contexts, handler, gEngine);
		thread.start();
	}

	public GameDraw(Context contextS) {
		super(contextS);
		context = contextS;
		InitView(contextS);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.i("ActivitySwipeDetector", "drawing");
		floor.createFloor(canvas);
		// canvas.drawBitmap(floor.getBitmapTest(), new Matrix(), new Paint());
	}	
}