package nl.vaya.mgdd.rjp.objects;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;


public class GameObject {
	
	protected Boolean _walkTrough;
	protected int[] _squares;
	protected int _start;
	protected int _squaresWidth;
	protected int _squaresHeight;
	protected Bitmap _image;
	
	public GameObject(int[] a, int s, int w, int h, Boolean walkTrough, Bitmap i){
		_squares = new int[a.length];
		for(int b = 0; b<a.length; b++){
			_squares[b] = a[b]+s;
		}
		_start = s;
		_walkTrough = walkTrough;
		_squaresWidth = w;
		_squaresHeight = h;
		_image = i;
	}

	public Boolean findTile(int tilePos){
		for(int i = 0; i<_squares.length; i++){
			if(tilePos == _squares[i])
				return true;
		}
		return false;
	}
	
	public Rect getStartRect(){
		Rect src = new Rect(0, 0, _squaresWidth*32, _squaresHeight*32);
		return src;
	}
	
	public RectF getDestRect(float scaleX,float scaleY, int startX, int startY){
		if(startX<-3200)
			startX = -3200;
		if(startY<-3200)
			startY = -3200;
		RectF dest =  new RectF(getMostLeft()*32*scaleX+startX, getMostTop()*32*scaleY+startY, (getMostLeft()*32*scaleX)+(_squaresWidth*32*scaleX)+startX, getMostTop()*32*scaleY+(_squaresHeight*32*scaleY)+startY);
		return dest;
	}
	
	protected int getMostLeft(){
		int mostLeft = 40;
		for(int i = 0; i<_squares.length; i++){
			int x = _squares[i];
			int y = 0;
			if(x>40){
				y = (int)Math.floor(x/40);
				x = x-(40*y);
			}
			if(x < mostLeft)
				mostLeft = x;
		}
		return mostLeft;
	}
	
	protected int getMostRight(){
		int mostRight = 0;
		for(int i = 0; i<_squares.length; i++){
			int x = _squares[i];
			int y = 0;
			if(x>40){
				y = (int)Math.floor(x/40);
				x = x-(40*y);
			}
			if(x > mostRight)
				mostRight = x;
		}
		return mostRight;
	}
	
	protected int getMostTop(){
		int mostTop = 40;
		for(int i = 0; i<_squares.length; i++){
			int x = _squares[i];
			int y = 0;
			y = (int)Math.floor(x/40);
			if(y < mostTop)
				mostTop = y;
		}
		return mostTop;
	}
	
	protected int getMostBottom(){
		int mostBottom = 0;
		for(int i = 0; i<_squares.length; i++){
			int x = _squares[i];
			int y = 0;
			y = (int)Math.floor(x/40);
			if(y < mostBottom)
				mostBottom = y;
		}
		return mostBottom;
	}
	
	public Boolean canWalkTrough(){
		return _walkTrough;
	}
	
	public Bitmap getImage(){
		return _image;
	}
	
	public Boolean isInScreen(int screenLeft, int screenTop, int screenRight, int screenBottom ){
		if(getMostTop() < screenBottom && getMostLeft() < screenRight && getMostRight() > screenLeft && getMostBottom() > screenTop)
			return true;
		else
			return false;
	}
	
}
