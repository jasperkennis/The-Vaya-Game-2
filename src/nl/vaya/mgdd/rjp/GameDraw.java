package nl.vaya.mgdd.rjp;

import nl.vaya.mgdd.rjp.layer.FloorLayer;
import nl.vaya.mgdd.rjp.layer.ObjectLayer;
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
	protected ObjectLayer objects;

	protected int _winWith;
	protected int _winHeight;
	
	protected Context context;
	
	protected float initialTouchXDisposition = 0;
	protected float initialTouchYDisposition = 0;
	protected int motionDetectionArea = 7;
	
	protected int _moveX = 0;
	protected int _moveY = 0;
	
	//protected float _angle = 0;
	protected float _touchX = 0;
	protected float _touchY = 0;
	
	protected int _startX = -240;
	protected int _startY = -40;

	
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
		objects = new ObjectLayer(context, _winWith, _winHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		objects.getYou().setPlayerPos(_moveX, _moveY, _winWith, _winHeight, floor.getNumTilesWidth(), floor.getNumTilesHeight(), this._touchX, this._touchY, initialTouchXDisposition, initialTouchYDisposition);
		objects.getYou().giveSubGround(floor.getSubGround(objects.getYou().getXPos(), objects.getYou().getYPos()));
		
		this._startX = objects.getYou().getStartX(_winWith);
		this._startY = objects.getYou().getStartY(_winHeight);
		floor.setStartX(this._startX, this._startY);
		objects.setStartX(this._startX, this._startY);
		canvas.drawColor(Color.BLACK);
		floor.createFloor(canvas);
		objects.setTileScaleX(floor.getTileScaleX());
		objects.setTileScaleY(floor.getTileScaleY());
		objects.createObjects(canvas);
		invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			initialTouchXDisposition = event.getX();
			initialTouchYDisposition = event.getY();
			return true;
		case MotionEvent.ACTION_UP:
			initialTouchXDisposition = 0.0f;
			initialTouchYDisposition = 0.0f;
			this._moveX = 0;
			this._moveY = 0;
			return false;
		default:
			float x = event.getX() - initialTouchXDisposition;
			float y = event.getY() - initialTouchYDisposition;
			this._moveX = (int)Math.ceil(x/motionDetectionArea);
			this._moveY = (int)Math.ceil(y/motionDetectionArea);
			
			this._touchX = event.getX();
			this._touchY = event.getY();

			return true;
		}
	}	
}