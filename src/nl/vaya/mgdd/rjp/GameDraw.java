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
	
	public GameDraw(Context context) {
		super(context);
		
		
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		
		_winWith = display.getWidth();
		_winHeight = display.getHeight();
		
		
		//create Layers
		floor = new FloorLayer(context, _winWith, _winHeight);
		
	}

	@Override
	protected void onDraw(Canvas canvas){
		
		floor.createFloor(canvas);
		
		//canvas.drawBitmap(floor.getBitmapTest(), new Matrix(), new Paint());
	}
	
	
}
