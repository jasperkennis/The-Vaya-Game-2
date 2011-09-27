package nl.vaya.mgdd.rjp.layer;

import nl.vaya.mgdd.rjp.layer.tiles.TileObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

public class FloorLayer {
	
	//Create a log tag
	static final String logTag = "log_tag";
	
	protected int[] _tilePositionArray;
	
	protected int _numTilesWidth = 8; //15
	protected int _numTilesHeight = 5; //10
	
	protected Bitmap floorTile_sand;
	protected Bitmap floorTile_water;
	protected Bitmap floorTile_zandrechtonder;
	protected Bitmap floorTile_zandrechtboven;
	protected Bitmap floorTile_hoekrechtsonder;
	protected Bitmap floorTile_hoeklinksonder;
	protected Bitmap floorTile_hoekrechtsboven;
	protected Bitmap floorTile_hoeklinksboven;
	protected Bitmap floorTile_binnenhoeklinksboven;
	protected Bitmap floorTile_binnenhoekrechtsboven;
	protected Bitmap floorTile_binnenhoeklinksonder;
	protected Bitmap floorTile_binnenhoekrechtsonder;
	protected Bitmap floorTile_zandrechtrechts;
	protected Bitmap floorTile_zandrechtlinks;
	
	protected int _winWidth;
	protected int _winHeight;
	
	protected int _startX;
	protected int _startY;
	
	protected float tileScaleX;
	protected float tileScaleY;
	
	public FloorLayer(Context context, int winWith, int winHeight){
		
		//set Win size
		_winWidth = winWith;
		_winHeight = winHeight;
		
		//Set bitmap options
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		//Create bitmaps (tiles)
		floorTile_sand = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/sand", "drawable", context.getPackageName()), opts);
		floorTile_water = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/water", "drawable", context.getPackageName()), opts);
		floorTile_zandrechtonder = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/zandrechtonder", "drawable", context.getPackageName()), opts);
		floorTile_zandrechtboven = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/zandrechtboven", "drawable", context.getPackageName()), opts);
		floorTile_hoekrechtsonder = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/hoekrechtsonder", "drawable", context.getPackageName()), opts);
		floorTile_hoeklinksonder = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/hoeklinksonder", "drawable", context.getPackageName()), opts);
		floorTile_hoekrechtsboven = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/hoekrechtsboven", "drawable", context.getPackageName()), opts);
		floorTile_hoeklinksboven = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/hoeklinksboven", "drawable", context.getPackageName()), opts);
		floorTile_binnenhoeklinksboven = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/binnenhoeklinksboven", "drawable", context.getPackageName()), opts);
		floorTile_binnenhoekrechtsboven = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/binnenhoekrechtsboven", "drawable", context.getPackageName()), opts);
		floorTile_binnenhoeklinksonder = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/binnenhoeklinksonder", "drawable", context.getPackageName()), opts);
		floorTile_binnenhoekrechtsonder = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/binnenhoekrechtsonder", "drawable", context.getPackageName()), opts);
		floorTile_zandrechtrechts = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/zandrechtrechts", "drawable", context.getPackageName()), opts);
		floorTile_zandrechtlinks = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/zandrechtlinks", "drawable", context.getPackageName()), opts);
		
		_tilePositionArray = new int[] {
				13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 7, 11, 11, 11, 5, 13, 13, 13, 13, 13, 13, 13,
		        13, 13, 7, 11, 11, 11, 5, 13, 7, 11, 11, 5, 13, 13, 13, 13, 7, 11, 11, 11, 11, 11, 5, 13, 13, 13, 13, 7, 4, 14, 14, 14, 10, 13, 13, 7, 11, 5, 13, 13,
		        13, 13, 12, 14, 14, 14, 2, 11, 4, 14, 14, 10, 13, 13, 13, 7, 4, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13, 12, 14, 14, 14, 14, 2, 11, 11, 4, 14, 10, 13, 13,
		        13, 13, 6, 3, 14, 14, 14, 14, 14, 14, 14, 2, 11, 5, 13, 12, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13, 6, 3, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 11, 4, 14, 14, 14, 14, 14, 14, 2, 5, 13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 11, 11, 11, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 7, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 9, 8, 13, 13,
		        13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 9, 9, 3, 14, 14, 14, 10, 13, 13, 13, 13,
		        13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 1, 9, 9, 9, 9, 3, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 8, 13, 13, 12, 14, 14, 1, 8, 13, 13, 13, 13,
		        13, 13, 6, 3, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 8, 13, 13, 7, 4, 14, 14, 10, 13, 13, 13, 13, 13,
		        13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 1, 8, 13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 7, 4, 14, 14, 14, 2, 11, 5, 13, 13, 13,
		        13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 10, 13, 13, 7, 11, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 7, 4, 14, 14, 14, 14, 14, 14, 2, 5, 13, 13,
		        13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 2, 11, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 13, 6, 9, 9, 3, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 9, 8, 13, 13,
		        13, 13, 13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13,
		        13, 13, 13, 13, 7, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 5, 13, 13, 13,
		        13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13,
		        13, 13, 7, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13,
		        13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 9, 9, 3, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 9, 8, 13, 13, 13,
		        13, 13, 6, 9, 9, 3, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 8, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 6, 9, 9, 3, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 5, 13, 6, 3, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 5, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 11, 11, 11, 5, 13, 13,
		        13, 13, 13, 7, 11, 5, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 13, 12, 14, 2, 11, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 13, 6, 9, 3, 14, 14, 14, 14, 14, 1, 9, 9, 3, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 9, 9, 3, 14, 14, 1, 9, 9, 8, 13, 13,
		        13, 13, 13, 13, 13, 12, 14, 14, 14, 14, 1, 8, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 9, 8, 13, 13, 12, 14, 14, 10, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 7, 4, 14, 14, 14, 14, 10, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 7, 11, 4, 14, 14, 2, 11, 5, 13, 13, 13,
		        13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 10, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 7, 4, 14, 14, 14, 14, 14, 14, 2, 5, 13, 13,
		        13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 2, 11, 11, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 7, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 9, 3, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 12, 14, 14, 1, 9, 9, 3, 14, 14, 14, 14, 1, 9, 3, 14, 14, 14, 14, 10, 13, 6, 9, 9, 9, 9, 3, 14, 14, 14, 14, 14, 14, 1, 9, 9, 8, 13, 13,
		        13, 13, 12, 14, 14, 10, 13, 13, 6, 9, 3, 14, 14, 10, 13, 6, 9, 9, 9, 3, 10, 13, 13, 13, 13, 13, 13, 12, 14, 14, 14, 1, 9, 9, 8, 13, 13, 13, 13, 13,
		        13, 13, 6, 9, 9, 8, 13, 13, 13, 13, 6, 9, 9, 8, 13, 13, 13, 13, 13, 6, 8, 13, 13, 13, 13, 13, 13, 6, 9, 9, 9, 8, 13, 13, 13, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13
		};
		
	}
	
	public int getNumTilesWidth(){
		return _numTilesWidth;
	}
	public int getNumTilesHeight(){
		return _numTilesHeight;
	}
	
	public float getTileScaleX(){
		return tileScaleX;
	}
	public float getTileScaleY(){
		return tileScaleY;
	}
	
	public void setStartX(int x, int y){
		if(x <= 0 && x > (((_winWidth/_numTilesWidth)*40)-_winWidth)*-1)
			this._startX = x;
		if(y <= 0 && y > (((_winHeight/_numTilesHeight)*40)-_winHeight)*-1)
			this._startY = y;
	}
	
	public void createFloor(Canvas canvas){
		
			int test = 0;

			int leftSquares = (int)((_startX * -1)/(_winWidth/_numTilesWidth));
			int topSquares = (int)((_startY * -1)/(_winHeight/_numTilesHeight));
			
			for(int ix = leftSquares; ix<leftSquares+_numTilesWidth+1; ix++){
				for(int iy = topSquares; iy<topSquares+_numTilesHeight+1; iy++){
					int i = ix+(iy*40);
					//Log.i(logTag, "Hier "+i);
			//for(int i = 0; i< 72; i++){
			//for(int i = 0; i< _tilePositionArray.length; i++){
				float y = (int)(i/40);
				float x = (int)i-(y*40);
				
				TileObject tile;
				switch(_tilePositionArray[i]){
					case 14:
						tile = new TileObject(floorTile_sand);
					break;
					case 13:
						tile = new TileObject(floorTile_water);
					break;
					case 11:
						tile = new TileObject(floorTile_zandrechtonder);
					break;
					case 9:
						tile = new TileObject(floorTile_zandrechtboven);
					break;
					case 7:
						tile = new TileObject(floorTile_hoekrechtsonder);
					break;
					case 5:
						tile = new TileObject(floorTile_hoeklinksonder);
					break;
					case 6:
						tile = new TileObject(floorTile_hoekrechtsboven);
					break;
					case 8:
						tile = new TileObject(floorTile_hoeklinksboven);
					break;
					case 1:
						tile = new TileObject(floorTile_binnenhoeklinksboven);
					break;
					case 3:
						tile = new TileObject(floorTile_binnenhoekrechtsboven);
					break;
					case 2:
						tile = new TileObject(floorTile_binnenhoeklinksonder);
					break;
					case 4:
						tile = new TileObject(floorTile_binnenhoekrechtsonder);
					break;
					case 12:
						tile = new TileObject(floorTile_zandrechtrechts);
					break;
					case 10:
						tile = new TileObject(floorTile_zandrechtlinks);
					break;
					default:
						tile = new TileObject(floorTile_sand);	
					break;
				}
				
				
				
				
				tile.setTileSize(_numTilesWidth, _numTilesHeight, _winWidth, _winHeight);
				Point pos = tile.getPosition(x,y);
				
				pos.set(pos.x+_startX, pos.y+_startY);
				
				if(pos.x+(tile.getTile().getWidth()*tile.getScaleX()) < 0 || pos.y+(tile.getTile().getHeight()*tile.getScaleY()) < 0){
					return;
					
				}else if(pos.x > _winWidth || pos.y > _winHeight){
					return;
				}else{
					test++;
					Rect src = new Rect(0, 0, tile.getTile().getWidth(), tile.getTile().getHeight());
					RectF dest =  new RectF(pos.x, pos.y, pos.x + (tile.getTile().getWidth()*tile.getScaleX()), pos.y + (tile.getTile().getHeight()*tile.getScaleY()));
					this.tileScaleX = tile.getScaleX();
					this.tileScaleY = tile.getScaleY();
					
					canvas.drawBitmap(tile.getTile(), src, dest, null);
				}
				
			}
		}

	}
}
