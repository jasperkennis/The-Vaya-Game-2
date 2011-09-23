package nl.vaya.mgdd.rjp;

import nl.vaya.mgdd.rjp.layer.FloorLayer;
import android.content.Context;
import android.graphics.Canvas;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;



public class GameDraw extends View {
	
	protected FloorLayer floor;
	
	protected int _winWith;
	protected int _winHeight;
	
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
		this.setOnTouchListener(inputFetcher);
	}

	@Override
	protected void onDraw(Canvas canvas){
		
		floor.createFloor(canvas);
		
		//canvas.drawBitmap(floor.getBitmapTest(), new Matrix(), new Paint());
	}
	
	
}
