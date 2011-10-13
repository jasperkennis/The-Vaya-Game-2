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
	
	//Set the drop position of the object
	protected int _dropX = 0;
	protected int _dropY = 0;
	
	protected WinningObject _winningObject = null;
	
	protected int _winWidth;
	protected int _winHeight;
	
	protected int _startX;
	protected int _startY;
	
	protected int _screenTilesX;
	protected int _screenTilesY;
	
	protected int _type;
	protected String _name;
	
	protected Point _spanPos;
	
	protected int state = 0;
	protected int loopState = 0;
	protected int plusState = 0;
	protected Bitmap current;
	protected Bitmap char_blue_1;
	protected Bitmap char_blue_Suncream;
	protected ArrayList<Bitmap> _walking = new ArrayList<Bitmap>();
	protected ArrayList<Bitmap> _swimming = new ArrayList<Bitmap>();
	protected ArrayList<Bitmap> _running = new ArrayList<Bitmap>();
	protected ArrayList<Bitmap> _crashing = new ArrayList<Bitmap>();
	
	protected ArrayList<Bitmap> _walkingSuncream = new ArrayList<Bitmap>();
	protected ArrayList<Bitmap> _swimmingSuncream = new ArrayList<Bitmap>();
	protected ArrayList<Bitmap> _runningSuncream = new ArrayList<Bitmap>();
	
	
	protected Point _prevPoint = new Point();
	
	protected ThrowingObject army = null;
	
	protected Context _context;
	
	protected int buzzTime = 0;
	
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
	
	public Player(Context context,String name, int type, int x, int y){
		_context = context;
		this._type = type;
		this._name = name;
		createBodyImages();
		_xPos = x;
		_yPos = y;
	}
	
	protected void createBodyImages(){
		//Set bitmap options
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		
		//Create bitmaps (tiles)
		//still
		char_blue_1 = BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue", "drawable", _context.getPackageName()), opts);
		
		char_blue_Suncream = BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_suncream", "drawable", _context.getPackageName()), opts);		
		
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
		
		//crash
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
		
		
		//walking with suncream
		_walkingSuncream.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_loop1_suncream", "drawable", _context.getPackageName()), opts));
		_walkingSuncream.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_loop2_suncream", "drawable", _context.getPackageName()), opts));
		
		//running with suncream
		_runningSuncream.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_run1_suncream", "drawable", _context.getPackageName()), opts));
		_runningSuncream.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_run2_suncream", "drawable", _context.getPackageName()), opts));
		
		//swimming with suncream
		_swimmingSuncream.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_swim1_suncream", "drawable", _context.getPackageName()), opts));
		_swimmingSuncream.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_swim2_suncream", "drawable", _context.getPackageName()), opts));
		_swimmingSuncream.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_swim3_suncream", "drawable", _context.getPackageName()), opts));
		
	}
	
	public void setPlayerPos(int x, int y, int winWidth, int winHeight, int tilesX, int tilesY, float touchX, float touchY, float basePointX, float basePointY){
		
		if(buzzTime == 0){
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
		}else{
			buzzTime--;
			Log.i("log_tag", "buzzy left frames :"+buzzTime);
		}
	}
	
	
	public Point getScreenTiles(){
		return new Point(_screenTilesX, _screenTilesY);
	}
	
	public void setBack(){
		if(_xPos < _prevPoint.x )
			_dropX = 1;
		else if(_xPos > _prevPoint.x )
			_dropX = -1;
		else
			_dropX = 0;
		
		if(_yPos < _prevPoint.y )
			_dropY = 1;
		else if(_yPos > _prevPoint.y )
			_dropY = -1;
		else
			_dropY = 0;
		
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
		switch(this.state+this.plusState){
			case 1:
				if(loopState >= _walking.size())
					loopState = 0;
				this.current = _walking.get(loopState);
			break;
			case 11:
				if(loopState >= _walkingSuncream.size())
					loopState = 0;
				this.current = _walkingSuncream.get(loopState);
			break;
			case 2:
				if(loopState >= _swimming.size())
					loopState = 0;
				this.current = _swimming.get(loopState);
			break;
			case 12:
				if(loopState >= _swimmingSuncream.size())
					loopState = 0;
				this.current = _swimmingSuncream.get(loopState);
			break;
			case 3:
				if(loopState >= _running.size())
					loopState = 0;
				this.current = _running.get(loopState);
			break;
			case 13:
				if(loopState >= _runningSuncream.size())
					loopState = 0;
				this.current = _runningSuncream.get(loopState);
			break;
			case 4:
				if(loopState >= _crashing.size())
					loopState = 0;
				this.current = _crashing.get(loopState);
			break;
			case 10:
				this.current = char_blue_Suncream;
			break;
			default:
				this.current = char_blue_1;
			break;
		}
		return this.current;
		
	}
	
	public int getScreenX(int startX, int winWidth){
		this._startX = startX;
		this._winWidth = winWidth;
		if(startX >= 0){
			return _xPos-startX;
		}else if(startX <= (((winWidth/_screenTilesX)*40)-winWidth)*-1){
			return _xPos+startX-(startX-((((winWidth/_screenTilesX)*40)-winWidth)*-1));
		}else{
			return winWidth/2;
		}
	}
	
	public int getScreenY(int startY, int winHeight){
		this._startY = startY;
		this._winHeight = winHeight;
		if(startY >= 0)
			return _yPos-startY;
		else if( startY <= (((winHeight/_screenTilesY)*40)-winHeight)*-1)
			return _yPos+startY-(startY-((((winHeight/_screenTilesY)*40)-winHeight)*-1));
		else
			return winHeight/2;
	}
	
	public void fall(){
		if(_winningObject != null){
			this.DropWinningObject();
		}
		buzzTime = 20;
		this.state = 4;
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
		return this.state+this.plusState;
	}
	
	public void pickUpWinningObject(WinningObject object){
		plusState = 10;
		_winningObject = object;
		_winningObject.picked();
	}
	
	public void DropWinningObject(){
		plusState = 0;
		int mapx = (int) Math.floor(((this._xPos-_startX)/((_winWidth/_screenTilesX))));
		int mapy = (int) Math.floor(((this._yPos-_startY)/((_winHeight/_screenTilesY))));
		_winningObject.dropped();
		Log.i("log_tag", "winning object dropped on x:"+_winningObject._xPos+" y:"+_winningObject._yPos);
		_winningObject = null;
	}
	
	public boolean checkWinnState(){
		int mapx = (int) Math.floor(((this._xPos)/((_winWidth/_screenTilesX))));
		int mapy = (int) Math.floor(((this._yPos)/((_winHeight/_screenTilesY))));
		Log.i("log_tag", "x1:"+mapx+" x2:"+this.getSpanPoint().x+" y1:"+mapy+" y2:"+this.getSpanPoint().y);
		if(this._winningObject != null && mapx == this.getSpanPoint().x && mapy == this.getSpanPoint().y){
			return true;
		}
		return false;
	}
	
	public void setSpanPoint(Point p){
		this._spanPos = p;
	}
	
	public Point getSpanPoint(){
		return this._spanPos;
	}
	
	public void addPickThrow(int x,int y, ArrayList<ThrowingObject> objects){
		
		int mapx = (int) Math.floor(((x-_startX)/((_winWidth/_screenTilesX))));
		int mapy = (int) Math.floor(((y-_startY)/((_winHeight/_screenTilesY))));
		
		Log.i("log_tag", "hit on pos x:"+mapx+" y:"+mapy);
		
		if(army == null){
			for( int i = 0 ; i < objects.size() ; i++) {
			//for(ThrowingObject to:objects){
				if(objects.get(i).onPos(mapx,mapy)){
					Log.i("log_tag", "HIT ON OBJECT");
					army = objects.get(i);
					objects.remove(i);
					GameDraw.getCommunicator().sendMessage("{\"type\" : \"player_got_obj\", \"index\" : " + i + "}");
				}
			}
		}else{
			army.SetPos(mapx, mapy);
			Log.i("log_tag", "MOVE TO X Y");
			objects.add(army);
			GameDraw.getCommunicator().sendMessage("{\"type\" : \"player_dropped_obj\", \"x\": " + army.getXPos() + ",\"y\": " + army.getYPos() + "}");
			Log.i("log_tag", "NEW THROW POS x: "+army.getXPos()+" y: "+army.getYPos());
			army = null;
		}
	}
	
}
