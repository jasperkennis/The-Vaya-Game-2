package nl.vaya.mgdd.rjp.objects;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class WinningObject {

	protected boolean puckedUp = false;
	protected int _xPos;
	protected int _yPos;
	protected Bitmap _suncreme;
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
	
	public WinningObject(Context c, int x, int y, float tileScaleX, float tileScaleY, int startX, int startY){
		_scaleX = tileScaleX;
		_scaleY = tileScaleY;
		_startX = startX;
		_startY = startY;
		SetPos(x,y);
		_context = c;
		createImages();
	}
	
	public void createImages(){
		//Set bitmap options
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		
		_suncreme = BitmapFactory.decodeResource(_context.getResources(),
				_context.getResources().getIdentifier("drawable/suncream", "drawable", _context.getPackageName()), opts);

	}
	
	public void MoveTo(int x, int y){
		int newX = (int) Math.ceil((x-this._startX)/(this.width*this._scaleX));
		int newY = (int) Math.ceil((y-this._startY)/(this.height*this._scaleY));
		this._xPos = (int) (x*(this.width*this._scaleX))+_startX;
		this._yPos = (int) (y*(this.width*this._scaleX))+_startY;
	}
	
	public void SetPos(int x, int y){
		this._xPos = (int)  (x*(this.width*this._scaleX));
		this._yPos = (int) (y*(this.height*this._scaleY));
	}
	
	public int getXPos(){
		return (int)(_xPos/((this.width*this._scaleX)));
	}
	
	public int getYPos(){
		return (int)(_yPos/((this.height*this._scaleY)));
	}
	
	public void SetState(int s){
		this.state = s;
	}
	
	public Bitmap getImage(){
		if(!this.puckedUp){
			this.current = _suncreme;
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
					this._xPos+_startX, 
					this._yPos+_startY + (this.height*tileScaleY), 
					this._xPos+_startX + (this.width*tileScaleX), 
					this._yPos+_startY + (this.height*tileScaleY)*2
				);
	}
	
	public boolean onPos(int x, int y){
		
		int objectX = (int) Math.floor((this._xPos)/(this.width*this._scaleX));
		int objectY = (int) Math.floor((this._yPos)/(this.height*this._scaleY));
		
		objectY++;
		
	if(x == objectX && y == objectY)
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
	
	public boolean isPicked(){
		return this.puckedUp;
	}
	
	public Boolean findTile(int tilePos){
		int y = (int)Math.floor(tilePos/40);
		int x = tilePos - (y*40);
		return onPos(x,y);
	}
	
}
