package nl.vaya.mgdd.rjp.objects;

import java.util.ArrayList;

import nl.vaya.mgdd.rjp.GameDraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

public class Player {
	
	protected int _xPos;
	protected int _yPos;
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
	
	protected Point _prevPoint = new Point();
	
	protected ThrowingObject army = null;
	
	protected Context _context;
	
	public Player(Context context){
		_context = context;
		this._type = 0;
		this._name = "Unknow";
		createBodyImages();
	}
	
	public Player(Context context,String name){
		_context = context;
		this._type = 0;
		this._name = name;
		createBodyImages();
	}
	
	public Player(Context context,String name, int type){
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
		
		//swimming
		_swimming.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_swim1", "drawable", _context.getPackageName()), opts));
		_swimming.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_swim2", "drawable", _context.getPackageName()), opts));
		_swimming.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_swim3", "drawable", _context.getPackageName()), opts));
		
		
	}
	
	public void setPlayerPos(int x, int y, int winWidth, int winHeight, int tilesX, int tilesY, float touchX, float touchY, float basePointX, float basePointY){
			_prevPoint.x = this._xPos;
			_prevPoint.y = this._yPos;
			
			if(x == 0 && y == 0 && this.state != 2){
				this.state = 0;
				x = 0; y = 0;
			}else if(x > 5 || y > 5 || x < -5 || y < -5){
				this.state = 3;
			}else{
				this.state = 1;
			}
		
			this._xPos = this._xPos+x;
			this._yPos = this._yPos+y;

			this._screenTilesX = tilesX;
			this._screenTilesY = tilesY;
			
			if(_xPos < 0)
				_xPos = 0;
			if(_xPos > ((winWidth/_screenTilesX)*40))
				_xPos = ((winWidth/_screenTilesX)*40);
			
			if(_yPos < 0)
				_yPos = 0;
			if(_yPos > ((winHeight/_screenTilesY)*40)-(winHeight/_screenTilesY))
				_yPos = ((winHeight/_screenTilesY)*40)-(winHeight/_screenTilesY);
		
			this._angle = (float) Math.toDegrees( Math.atan2( basePointX-touchX, basePointY-touchY ) )+180;
	}
	
	
	public Point getScreenTiles(){
		return new Point(_screenTilesX, _screenTilesY);
	}
	
	public void setBack(){
		this._xPos = this._prevPoint.x;
		this._yPos = this._prevPoint.y;
		Log.i("log_tag", "HIT HIT HIT HIT");
	}
	
	public int getStartX(int winWidth){
		if(_xPos < winWidth/2)
			return 0;
		else
			return (_xPos-(winWidth/2))*-1;
	}
	
	public int getStartY(int winHeight){
		if(_yPos < winHeight/2)
			return 0;
		else
			return (_yPos-(winHeight/2))*-1;
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
			default:
				this.current = char_blue_1;
			break;
		}
		return this.current;
		
	}
	
	public int getScreenX(int startX, int winWidth){
		if(startX >= 0){
			//Log.i("log_tag", "Links scherm");
			return _xPos-startX;
		}else if(startX <= (((winWidth/_screenTilesX)*40)-winWidth)*-1){
			//Log.i("log_tag", "Rechts scherm");
			return _xPos+startX-(startX-((((winWidth/_screenTilesX)*40)-winWidth)*-1));
		}else{
			return winWidth/2;
		}
	}
	
	public int getScreenY(int startY, int winHeight){
		if(startY >= 0)
			return _yPos-startY;
		else if( startY <= (((winHeight/_screenTilesY)*40)-winHeight)*-1)
			return _yPos+startY-(startY-((((winHeight/_screenTilesY)*40)-winHeight)*-1));
		else
			return winHeight/2;
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
	
	public int getState(){
		return this.state;
	}
	
	public void addPickThrow(int x,int y, ArrayList<ThrowingObject> objects){
		if(army == null){
			for( int i = 0 ; i < objects.size() ; i++) {
			//for(ThrowingObject to:objects){
				if(objects.get(i).onPos(x,y)){
					Log.i("log_tag", "HIT ON OBJECT");
					army = objects.get(i);
					
					// Tell the server at which index the item has been fetched.
					GameDraw.getCommunicator().sendMessage("{\"type\" : \"player_got_obj\", \"index\" : " + i + "}");
				}
			}
		}else{
			army.MoveTo(x, y);
			Log.i("log_tag", "MOVE TO X Y");
			army = null;
			// Tell the server at which coord an item has been dropped.
			GameDraw.getCommunicator().sendMessage("{\"type\" : \"player_dropped_obj\", \"x\": " + x + ",\"y\": " + y + "}");
		}
	}
	
}
