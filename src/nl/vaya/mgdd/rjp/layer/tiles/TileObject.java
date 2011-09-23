package nl.vaya.mgdd.rjp.layer.tiles;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

public class TileObject {

	static final String logTag = "log_tag";
	
	protected ArrayList<Bitmap> _sprites;
	protected int _activeSprite = 0;
	
	protected float _tileHeight = 32;
	protected float _tileWidth = 32;
	
	protected int _winWidth;
	protected int _winHeight;
	
	/**
	 * Accepts an array of sprites and stores them in the array list of sprites
	 * @param sprites
	 * @return void
	 */
	public TileObject(Bitmap[] sprites) {
		_sprites = new ArrayList<Bitmap>();
		
		for (Bitmap sprite : sprites){
			addSprite(sprite);
		}
	}
	
	
	
	/**
	 * Accepts a single sprite and stores it in the array list of sprites.
	 * @param sprite
	 * @return void
	 */
	public TileObject(Bitmap sprite) {
		
		_sprites = new ArrayList<Bitmap>();
		
		addSprite(sprite);
	}
	
	
	
	public void addSprite(Bitmap sprite){
		_sprites.add(sprite);
	}
	
	
	public Bitmap getTile(){
		return _sprites.get(_activeSprite);
	}
	
	
	
	/**
	 * Sets the active sprite index according to the currently active sprite and the number of sprites
	 * @return void
	 */
	public void update(){
		// If there is only one sprite, no action is needed
		if(_sprites.size() < 2){
			return;
		}
		
		// If the current active sprite isn't the last one, add up
		if( ( _activeSprite + 1 ) < _sprites.size()){
			_activeSprite++;
			return;
		}
		
		// Otherwise set the index to 0 
		_activeSprite = 0;
	}
	
	public void setTileSize(float w, float h, int winW, int winH){
		_winWidth = winW;
		_winHeight = winH;
		_tileWidth = _winWidth/w;
		_tileHeight = _winHeight/h;
		Log.i(logTag, "Size is w="+_tileWidth+" h="+_tileHeight);
	}
	
	public float getScaleX(){
		
		return _tileWidth/this.getTile().getWidth();
	}
	
	public float getScaleY(){
		return _tileHeight/this.getTile().getHeight();
	}
	
	public Point getPosition(float x, float y){
		float yPos = y*_tileHeight;
		float xPos = x*_tileWidth;
		Point position = new Point((int)xPos,(int)yPos);
		Log.i(logTag, "Position is x="+xPos+" y="+yPos);
		return position;
	}
}
