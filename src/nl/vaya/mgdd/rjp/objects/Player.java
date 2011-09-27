package nl.vaya.mgdd.rjp.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Player {
	
	protected int _xPos;
	protected int _yPos;
	
	protected int _screenTilesX;
	protected int _screenTilesY;
	
	protected int _type;
	protected String _name;
	
	protected Bitmap char_blue_1;
	protected Bitmap char_blue_2;
	protected Bitmap char_blue_3;
	
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
		char_blue_1 = BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue", "drawable", _context.getPackageName()), opts);
		char_blue_2 = BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_loop1", "drawable", _context.getPackageName()), opts);
		char_blue_3 = BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/char_blue_loop1", "drawable", _context.getPackageName()), opts);
	}
	
	public void setPlayerPos(int x, int y, int winWidth, int winHeight, int tilesX, int tilesY){
		
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
		
		Log.i("log_tag", "Player pos = x:"+_xPos+" y:"+_yPos);
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
	
	public Bitmap getImage(){
		return char_blue_1;
	}
	
	public int getScreenX(int startX, int winWidth){
		if(startX >= 0){
			Log.i("log_tag", "Links scherm");
			return _xPos-startX;
		}else if(startX <= (((winWidth/_screenTilesX)*40)-winWidth)*-1){
			Log.i("log_tag", "Rechts scherm");
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
	
}
