package nl.vaya.mgdd.rjp.objects;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

public class Enemy {
	
	protected int _xPos = 0;
	protected int _yPos = 0;
	protected float _angle = 0;
	
	protected int _screenTilesX;
	protected int _screenTilesY;
	
	protected int _type;
	protected String _name;
	
	
	protected int state = 0;
	protected int loopState = 0;
	protected Bitmap current;
	protected Bitmap char_blue_1;
	protected ArrayList<Bitmap> _walking = new ArrayList<Bitmap>();
	protected ArrayList<Bitmap> _swimming = new ArrayList<Bitmap>();
	protected ArrayList<Bitmap> _running = new ArrayList<Bitmap>();
	protected ArrayList<Bitmap> _crashing = new ArrayList<Bitmap>();
	
	protected Point _prevPoint = new Point();
	
	protected Context _context;
	
	public Enemy(Context context){
		_context = context;
		this._type = 0;
		this._name = "Unknow";
		createBodyImages();
	}
	
	public Enemy(Context context,String name){
		_context = context;
		this._type = 0;
		this._name = name;
		createBodyImages();
	}
	
	public Enemy(Context context,String name, int type){
		_context = context;
		this._type = type;
		this._name = name;
		createBodyImages();
	}
	
	protected void createBodyImages(){
		//Set bitmap options
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		
		//Create bitmaps (tiles)
		//still
		char_blue_1 = BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue", "drawable", _context.getPackageName()), opts);
		
		//walking
		_walking.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_loop1", "drawable", _context.getPackageName()), opts));
		_walking.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_loop2", "drawable", _context.getPackageName()), opts));
		
		//running
		_running.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_run1", "drawable", _context.getPackageName()), opts));
		_running.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_run2", "drawable", _context.getPackageName()), opts));
		
		//running
		_crashing.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_val1", "drawable", _context.getPackageName()), opts));
		_crashing.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_val2", "drawable", _context.getPackageName()), opts));
		
		//swimming
		_swimming.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_swim1", "drawable", _context.getPackageName()), opts));
		_swimming.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_swim2", "drawable", _context.getPackageName()), opts));
		_swimming.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_swim3", "drawable", _context.getPackageName()), opts));
		
		
	}
	
	public void setPlayerPos(int x, int y, float angle, int s, int tilesX, int tilesY){
		
		_prevPoint.x = this._xPos;
		_prevPoint.y = this._yPos;

		this.state = s;
		
		this._screenTilesX = tilesX;
		this._screenTilesY = tilesY;

		this._xPos = x;
		this._yPos = y;
	
		this._angle = angle;
		
}
	
	public boolean checkCollision(int youPos){
		return true;
	}
	
	public Point getScreenTiles(){
		return new Point(_screenTilesX, _screenTilesY);
	}
	
	public void setBack(){
		this._xPos = this._prevPoint.x;
		this._yPos = this._prevPoint.y;
		Log.i("log_tag", "HIT HIT HIT HIT");
	}
	
	public float getAngle(){
		return _angle;
	}
	
	public Bitmap getImage(){
		loopState ++;
		switch(this.state){
			case 1:
				if(loopState >= _walking.size())
					loopState = 0;
				this.current = _walking.get(loopState);
			break;
			case 2:
				if(loopState >= _swimming.size())
					loopState = 0;
				this.current = _swimming.get(loopState);
			break;
			case 3:
				if(loopState >= _running.size())
					loopState = 0;
				this.current = _running.get(loopState);
			break;
			case 4:
				if(loopState >= _crashing.size())
					loopState = 0;
				this.current = _crashing.get(loopState);
			break;
			default:
				this.current = char_blue_1;
			break;
		}
		return this.current;
		
	}
	
	public int getScreenX(int startX){
		return _xPos+startX;
	}
	
	public int getScreenY(int startY){
		return _yPos+startY;
	}
	
	public void giveSubGround(int floorState){
		if(floorState == 13){
			this.state = 2; //swimming
		}
	}
	
	public int getXPos(){
		return this._xPos;
	}
	
	public int getYPos(){
		return this._yPos;
	}
	
}
