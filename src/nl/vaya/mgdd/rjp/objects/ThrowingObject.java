package nl.vaya.mgdd.rjp.objects;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class ThrowingObject {

	protected boolean puckedUp = false;
	protected int _xPos;
	protected int _yPos;
	protected ArrayList<Bitmap> _banaanAn;
	protected Bitmap _banaan;
	protected Context _context;
	
	protected int state = 0;
	protected int loopState = 0;
	protected Bitmap current;
	
	protected int width = 32;
	protected int height = 32;
	
	protected float _scaleX = 0;
	protected float _scaleY = 0;
	
	protected int _startX = 0;
	protected int _startY = 0;
	
	public ThrowingObject(Context c, int x, int y){
		//_xPos = x;
		//_yPos = y;
		SetPos(8,8);
		_context = c;
		createImages();
	}
	
	public void createImages(){
		//Set bitmap options
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		
		//Create bitmaps (objects)
		_banaanAn = new ArrayList<Bitmap>();
		_banaanAn.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/banaan1", "drawable", _context.getPackageName()), opts));
		_banaanAn.add(BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/banaan2", "drawable", _context.getPackageName()), opts));
		_banaan = BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/banaan3", "drawable", _context.getPackageName()), opts);
	}
	
	public void MoveTo(int x, int y){
		x--;
		y++;
		int newX = (int) Math.ceil((x-this._startX)/(this.width*this._scaleX));
		int newY = (int) Math.ceil((y-this._startY)/(this.height*this._scaleY));
		this._xPos = (int) (newX*(this.width*this._scaleX));
		this._yPos = (int) (newY*(this.width*this._scaleX));
		Log.i("log_tag", "Set banaan to pos x:"+newX+" y:"+newY );
		/*
		while((newX*(this._scaleX*this.width) != this._xPos) || (newY*(this._scaleY*this.height) != this._yPos)){
			if(newX*(this._scaleX*this.width) > this._xPos){
				this._xPos++;
			}else if(newX*(this._scaleX*this.width) < this._xPos){
				this._xPos--;
			}
			
			if(newY*(this._scaleY*this.height) > this._yPos){
				this._yPos++;
			}else if(newY*(this._scaleY*this.height) < this._yPos){
				this._yPos--;
			}
		}*/
	}
	
	public void SetPos(int x, int y){
		this._xPos = (int) (x*(this._scaleX*this.width));
		this._yPos = (int) (y*(this._scaleY*this.height));
	}
	
	public void SetState(int s){
		this.state = s;
	}
	
	public Bitmap getImage(){
		if(!this.puckedUp){
			loopState ++;
			switch(this.state){
				case 0:
					this.current = _banaan;
				break;
				case 1:
					if(loopState >= _banaanAn.size())
						loopState = 0;
					this.current = _banaanAn.get(loopState);
				break;
				default:
					this.current = _banaan;
				break;
			}
		}
		return this.current;
	}
	
	public Rect getSrc(){
		return new Rect(0,0,this.width,this.height);
	}
	
	public RectF getDest(float tileScaleX, float tileScaleY, int startX, int startY){
		this._scaleX = tileScaleX;
		this._scaleY = tileScaleY;
		this._startX = startX;
		this._startY = startY;
		return new RectF(
					this._xPos + startX, 
					this._yPos + startY - ((this.height*tileScaleY)/2), 
					this._xPos + startX + (this.width*tileScaleX), 
					this._yPos + startY + ((this.height*tileScaleY)/2)
				);
	}
	
	public boolean onPos(int x, int y){
		int touchX = (int) Math.floor((x-_startX)/(this.width*this._scaleX));
		int touchY = (int) Math.floor((y-_startY)/(this.height*this._scaleY));
		
		int objectX = (int) Math.floor((this._xPos)/(this.width*this._scaleX));
		int objectY = (int) Math.floor((this._yPos)/(this.height*this._scaleY));
		
		Log.i("log_tag", "touch x:"+touchX+" & y:"+touchY+" en object x:"+objectX+" y:"+objectY);
		
	if(touchX == objectX && touchY == objectY)
			return true;
		else
			return false;
	}
	
	public void picked(){
		this.puckedUp = true;
	}
	
	public void dropped(){
		this.puckedUp = false;
	}
	
}
