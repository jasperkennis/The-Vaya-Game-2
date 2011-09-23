package nl.vaya.mgdd.rjp;

import nl.vaya.mgdd.rjp.layer.FloorLayer;
import android.content.Context;
import android.graphics.Canvas;
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
	
	SurfaceHolder surfaceHolder;
    Context context;
    
    //our Thread class which houses the game loop
    private GameLoop thread;
	
	protected ActivitySwipeDetector inputFetcher;
	
	public GameDraw(Context context) {
		super(context);
		
		
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		
		_winWith = display.getWidth();
		_winHeight = display.getHeight();
		
		
		// Create Layers
		floor = new FloorLayer(context, _winWith, _winHeight);
		
		// Create input fetcher, used to detect both movement and tapping.
		inputFetcher = new ActivitySwipeDetector(this);
		//this.setOnTouchListener(inputFetcher);
		this.setOnClickListener(inputFetcher);
		
	}

	@Override
	protected void onDraw(Canvas canvas){
		Log.i("ActivitySwipeDetector","drawing");
		floor.createFloor(canvas);
		Log.i("ActivitySwipeDetector",inputFetcher.getDownDisplacementX() + "");
		//canvas.drawBitmap(floor.getBitmapTest(), new Matrix(), new Paint());
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
