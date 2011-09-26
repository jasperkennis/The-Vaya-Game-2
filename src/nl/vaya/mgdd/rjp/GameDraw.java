package nl.vaya.mgdd.rjp;

import nl.vaya.mgdd.rjp.layer.FloorLayer;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameDraw extends SurfaceView implements SurfaceHolder.Callback {

	protected FloorLayer floor;

	protected int _winWith;
	protected int _winHeight;

	protected long lastUpdate = 0;
	protected long sleepTime = 0;

	protected SurfaceHolder surfaceHolder;
	protected Context context;

	// Game engine
	protected GameEngine gEngine;

	// our Thread class which houses the game loop
	private GameLoop thread;

	protected ActivitySwipeDetector inputFetcher;

	private void InitView() {

		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

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

		thread = new GameLoop(holder, context, new Handler(), gEngine);
		setFocusable(true);
	}

	// class constructors
	public GameDraw(Context contextS, AttributeSet attrs, int defStyle) {
		super(contextS, attrs, defStyle);
		context = contextS;
		InitView();
	}

	public GameDraw(Context contextS, AttributeSet attrs) {
		super(contextS, attrs);
		context = contextS;
		InitView();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.i("ActivitySwipeDetector", "drawing");
		floor.createFloor(canvas);
		// canvas.drawBitmap(floor.getBitmapTest(), new Matrix(), new Paint());
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}
}
