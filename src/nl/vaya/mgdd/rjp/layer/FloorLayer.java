package nl.vaya.mgdd.rjp.layer;

import java.util.ArrayList;

import nl.vaya.mgdd.rjp.layer.tiles.TileObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class FloorLayer {
	
	//Create a log tag
	static final String logTag = "log_tag";
	
	protected int[] _tilePositionArray;
	
	protected int _numTilesWidth = 8; //15
	protected int _numTilesHeight = 5; //10
	
	protected Point _spanPos;
	
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
	
	protected Bitmap floorTile_water2;
	protected Bitmap floorTile_zandrechtonder2;
	protected Bitmap floorTile_zandrechtboven2;
	protected Bitmap floorTile_hoekrechtsonder2;
	protected Bitmap floorTile_hoeklinksonder2;
	protected Bitmap floorTile_hoekrechtsboven2;
	protected Bitmap floorTile_hoeklinksboven2;
	protected Bitmap floorTile_binnenhoeklinksboven2;
	protected Bitmap floorTile_binnenhoekrechtsboven2;
	protected Bitmap floorTile_binnenhoeklinksonder2;
	protected Bitmap floorTile_binnenhoekrechtsonder2;
	protected Bitmap floorTile_zandrechtrechts2;
	protected Bitmap floorTile_zandrechtlinks2;
	
	protected Bitmap floorTile_roadhoeklinksboven1;
	protected Bitmap floorTile_roadhoeklinksboven2;
	protected Bitmap floorTile_roadhoeklinksboven3;
	protected Bitmap floorTile_roadhoeklinksboven4;
	protected Bitmap floorTile_roadhoeklinksonder1;
	protected Bitmap floorTile_roadhoeklinksonder2;
	protected Bitmap floorTile_roadhoeklinksonder3;
	protected Bitmap floorTile_roadhoeklinksonder4;
	protected Bitmap floorTile_roadhoekrechtsboven1;
	protected Bitmap floorTile_roadhoekrechtsboven2;
	protected Bitmap floorTile_roadhoekrechtsboven3;
	protected Bitmap floorTile_roadhoekrechtsboven4;
	protected Bitmap floorTile_roadhoekrechtsonder1;
	protected Bitmap floorTile_roadhoekrechtsonder2;
	protected Bitmap floorTile_roadhoekrechtsonder3;
	protected Bitmap floorTile_roadhoekrechtsonder4;
	protected Bitmap floorTile_roadliggend1;
	protected Bitmap floorTile_roadliggend2;
	protected Bitmap floorTile_roadliggend3;
	protected Bitmap floorTile_roadliggend4;
	protected Bitmap floorTile_roadstaand1;
	protected Bitmap floorTile_roadstaand2;
	protected Bitmap floorTile_roadstaand3;
	protected Bitmap floorTile_roadstaand4;
	
	protected ArrayList<Bitmap> spanPoint;
	protected int currentLoop = 0;
	
	protected int _winWidth;
	protected int _winHeight;
	
	protected int _startX;
	protected int _startY;
	
	protected float tileScaleX;
	protected float tileScaleY;
	
	public int _updateDelay = 0;
	
	protected TileObject[] _tiles;
	
	public FloorLayer(Context context, int winWith, int winHeight){
		
		//set Win size
		_winWidth = winWith;
		_winHeight = winHeight;
		
		//Set bitmap options
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		
		//Spanpoint image
		spanPoint = new ArrayList<Bitmap>();
		spanPoint.add(BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/blue_klein", "drawable", context.getPackageName()), opts));
		spanPoint.add(BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/blue_middel", "drawable", context.getPackageName()), opts));
		spanPoint.add(BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/blue_groot", "drawable", context.getPackageName()), opts));
		
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
		
		floorTile_water2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/water2", "drawable", context.getPackageName()), opts);
		floorTile_zandrechtonder2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/zandrechtonder2", "drawable", context.getPackageName()), opts);
		floorTile_zandrechtboven2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/zandrechtboven2", "drawable", context.getPackageName()), opts);
		floorTile_hoekrechtsonder2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/hoekrechtsonder2", "drawable", context.getPackageName()), opts);
		floorTile_hoeklinksonder2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/hoeklinksonder2", "drawable", context.getPackageName()), opts);
		floorTile_hoekrechtsboven2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/hoekrechtsboven2", "drawable", context.getPackageName()), opts);
		floorTile_hoeklinksboven2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/hoeklinksboven2", "drawable", context.getPackageName()), opts);
		floorTile_binnenhoeklinksboven2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/binnenhoeklinksboven2", "drawable", context.getPackageName()), opts);
		floorTile_binnenhoekrechtsboven2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/binnenhoekrechtsboven2", "drawable", context.getPackageName()), opts);
		floorTile_binnenhoeklinksonder2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/binnenhoeklinksonder2", "drawable", context.getPackageName()), opts);
		floorTile_binnenhoekrechtsonder2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/binnenhoekrechtsonder2", "drawable", context.getPackageName()), opts);
		floorTile_zandrechtrechts2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/zandrechtrechts2", "drawable", context.getPackageName()), opts);
		floorTile_zandrechtlinks2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/zandrechtlinks2", "drawable", context.getPackageName()), opts);
		
		//Weg hokjes inladen
		floorTile_roadhoeklinksboven1 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoeklinksboven1", "drawable", context.getPackageName()), opts);
		floorTile_roadhoeklinksboven2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoeklinksboven2", "drawable", context.getPackageName()), opts);
		floorTile_roadhoeklinksboven3 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoeklinksboven3", "drawable", context.getPackageName()), opts);
		floorTile_roadhoeklinksboven4 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoeklinksboven4", "drawable", context.getPackageName()), opts);
		
		floorTile_roadhoeklinksonder1 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoeklinksonder1", "drawable", context.getPackageName()), opts);
		floorTile_roadhoeklinksonder2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoeklinksonder2", "drawable", context.getPackageName()), opts);
		floorTile_roadhoeklinksonder3 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoeklinksonder3", "drawable", context.getPackageName()), opts);
		floorTile_roadhoeklinksonder4 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoeklinksonder4", "drawable", context.getPackageName()), opts);
		
		floorTile_roadhoekrechtsboven1 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoekrechtsboven1", "drawable", context.getPackageName()), opts);
		floorTile_roadhoekrechtsboven2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoekrechtsboven2", "drawable", context.getPackageName()), opts);
		floorTile_roadhoekrechtsboven3 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoekrechtsboven3", "drawable", context.getPackageName()), opts);
		floorTile_roadhoekrechtsboven4 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoekrechtsboven4", "drawable", context.getPackageName()), opts);
		
		floorTile_roadhoekrechtsonder1 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoekrechtsonder1", "drawable", context.getPackageName()), opts);
		floorTile_roadhoekrechtsonder2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoekrechtsonder2", "drawable", context.getPackageName()), opts);
		floorTile_roadhoekrechtsonder3 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoekrechtsonder3", "drawable", context.getPackageName()), opts);
		floorTile_roadhoekrechtsonder4 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadhoekrechtsonder4", "drawable", context.getPackageName()), opts);
		
		floorTile_roadliggend1 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadliggend1", "drawable", context.getPackageName()), opts);
		floorTile_roadliggend2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadliggend2", "drawable", context.getPackageName()), opts);
		floorTile_roadliggend3 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadliggend3", "drawable", context.getPackageName()), opts);
		floorTile_roadliggend4 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadliggend4", "drawable", context.getPackageName()), opts);
		
		floorTile_roadstaand1 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadstaand1", "drawable", context.getPackageName()), opts);
		floorTile_roadstaand2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadstaand2", "drawable", context.getPackageName()), opts);
		floorTile_roadstaand3 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadstaand3", "drawable", context.getPackageName()), opts);
		floorTile_roadstaand4 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/roadstaand4", "drawable", context.getPackageName()), opts);
		
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
		        13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13,
		        13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 8, 13, 13, 13, 13,
		        13, 13, 6, 3, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13, 13,
		        13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 11, 5, 13, 13, 13,
		        13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 41, 42, 33, 34, 33, 34, 33, 34, 33, 34, 33, 34, 33, 34, 33, 34, 49, 50, 14, 14, 14, 14, 14, 14, 2, 5, 13, 13,
		        13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 45, 46, 37, 38, 37, 38, 37, 38, 37, 38, 37, 38, 37, 38, 37, 38, 53, 54, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 13, 6, 9, 9, 3, 14, 14, 14, 14, 14, 31, 32, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 31, 32, 14, 14, 14, 14, 14, 1, 9, 8, 13, 13,
		        13, 13, 13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 35, 36, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 35, 36, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 31, 32, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 31, 32, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13,
		        13, 13, 13, 13, 7, 11, 4, 14, 14, 14, 14, 14, 35, 36, 14, 14, 14, 14, 14, 1, 9, 9, 3, 14, 14, 14, 14, 14, 35, 36, 14, 14, 14, 14, 14, 2, 5, 13, 13, 13,
		        13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 31, 32, 14, 14, 14, 14, 1, 8, 13, 13, 6, 3, 14, 14, 14, 14, 31, 32, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13,
		        13, 13, 7, 11, 4, 14, 14, 14, 14, 14, 14, 14, 35, 36, 14, 14, 14, 14, 10, 13, 7, 5, 13, 12, 14, 14, 14, 14, 35, 36, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13,
		        13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 31, 32, 14, 14, 14, 14, 10, 13, 6, 8, 13, 12, 14, 14, 14, 14, 31, 32, 14, 14, 14, 14, 1, 9, 8, 13, 13, 13,
		        13, 13, 6, 9, 9, 3, 14, 14, 14, 14, 14, 14, 35, 36, 14, 14, 14, 14, 2, 5, 13, 13, 7, 4, 14, 14, 14, 14, 35, 36, 14, 14, 14, 1, 8, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 6, 9, 9, 3, 14, 14, 14, 31, 32, 14, 14, 14, 14, 14, 2, 11, 11, 4, 14, 14, 14, 14, 14, 31, 32, 14, 14, 14, 10, 13, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 13, 13, 13, 12, 14, 14, 14, 35, 36, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 35, 36, 14, 14, 14, 2, 11, 11, 11, 5, 13, 13,
		        13, 13, 13, 7, 11, 5, 13, 13, 12, 14, 14, 14, 31, 32, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 31, 32, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 13, 12, 14, 2, 11, 11, 4, 14, 14, 14, 35, 36, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 35, 36, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 39, 40, 33, 34, 33, 34, 33, 34, 33, 34, 33, 34, 33, 34, 33, 34, 47, 48, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 13, 6, 9, 3, 14, 14, 14, 14, 14, 14, 43, 44, 37, 38, 37, 38, 37, 38, 37, 38, 37, 38, 37, 38, 37, 38, 51, 52, 14, 14, 14, 14, 1, 9, 9, 8, 13, 13,
		        13, 13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 7, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 11, 5, 13, 13, 13,
		        13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 2, 5, 13, 13,
		        13, 13, 13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 7, 11, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 12, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 9, 3, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 13, 13,
		        13, 13, 12, 14, 14, 1, 9, 9, 3, 14, 14, 14, 14, 1, 9, 3, 14, 14, 14, 14, 10, 13, 6, 9, 9, 9, 9, 3, 14, 14, 14, 14, 14, 14, 1, 9, 9, 8, 13, 13,
		        13, 13, 12, 14, 14, 10, 13, 13, 6, 9, 3, 14, 14, 10, 13, 6, 9, 9, 9, 3, 10, 13, 13, 13, 13, 13, 13, 12, 14, 14, 14, 1, 9, 9, 8, 13, 13, 13, 13, 13,
		        13, 13, 6, 9, 9, 8, 13, 13, 13, 13, 6, 9, 9, 8, 13, 13, 13, 13, 13, 6, 8, 13, 13, 13, 13, 13, 13, 6, 9, 9, 9, 8, 13, 13, 13, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,
		        13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13
		};	
		
		_tiles = new TileObject[55];
		_tiles[0] = new TileObject(floorTile_sand);
		_tiles[14] = new TileObject(floorTile_sand);
		_tiles[13] = new TileObject(new Bitmap[]{floorTile_water, floorTile_water2});
		_tiles[11] = new TileObject(new Bitmap[]{floorTile_zandrechtonder, floorTile_zandrechtonder2});
		_tiles[9] = new TileObject(new Bitmap[]{floorTile_zandrechtboven, floorTile_zandrechtboven2});
		_tiles[7] = new TileObject(new Bitmap[]{floorTile_hoekrechtsonder, floorTile_hoekrechtsonder2});
		_tiles[5] = new TileObject(new Bitmap[]{floorTile_hoeklinksonder, floorTile_hoeklinksonder2});
		_tiles[6] = new TileObject(new Bitmap[]{floorTile_hoekrechtsboven, floorTile_hoekrechtsboven2});
		_tiles[8] = new TileObject(new Bitmap[]{floorTile_hoeklinksboven, floorTile_hoeklinksboven2});
		_tiles[1] = new TileObject(new Bitmap[]{floorTile_binnenhoeklinksboven, floorTile_binnenhoeklinksboven2});
		_tiles[3] = new TileObject(new Bitmap[]{floorTile_binnenhoekrechtsboven, floorTile_binnenhoekrechtsboven2});
		_tiles[2] = new TileObject(new Bitmap[]{floorTile_binnenhoeklinksonder, floorTile_binnenhoeklinksonder2});
		_tiles[4] = new TileObject(new Bitmap[]{floorTile_binnenhoekrechtsonder, floorTile_binnenhoekrechtsonder2});
		_tiles[12] = new TileObject(new Bitmap[]{floorTile_zandrechtrechts, floorTile_zandrechtrechts2});
		_tiles[10] = new TileObject(new Bitmap[]{floorTile_zandrechtlinks, floorTile_zandrechtlinks2});
		
		//Extra fix tiles
		_tiles[15] = new TileObject(floorTile_sand);
		_tiles[16] = new TileObject(floorTile_sand);
		
		_tiles[30] = new TileObject(floorTile_sand); //14
		_tiles[29] = new TileObject(new Bitmap[]{floorTile_water, floorTile_water2}); //13
		_tiles[27] = new TileObject(new Bitmap[]{floorTile_zandrechtonder, floorTile_zandrechtonder2}); //11
		_tiles[25] = new TileObject(new Bitmap[]{floorTile_zandrechtboven, floorTile_zandrechtboven2}); //9
		_tiles[23] = new TileObject(new Bitmap[]{floorTile_hoekrechtsonder, floorTile_hoekrechtsonder2}); //7
		_tiles[21] = new TileObject(new Bitmap[]{floorTile_hoeklinksonder, floorTile_hoeklinksonder2}); // 5
		_tiles[22] = new TileObject(new Bitmap[]{floorTile_hoekrechtsboven, floorTile_hoekrechtsboven2}); //6
		_tiles[24] = new TileObject(new Bitmap[]{floorTile_hoeklinksboven, floorTile_hoeklinksboven2}); //8
		_tiles[17] = new TileObject(new Bitmap[]{floorTile_binnenhoeklinksboven, floorTile_binnenhoeklinksboven2}); //1
		_tiles[19] = new TileObject(new Bitmap[]{floorTile_binnenhoekrechtsboven, floorTile_binnenhoekrechtsboven2}); //3
		_tiles[18] = new TileObject(new Bitmap[]{floorTile_binnenhoeklinksonder, floorTile_binnenhoeklinksonder2}); //2
		_tiles[20] = new TileObject(new Bitmap[]{floorTile_binnenhoekrechtsonder, floorTile_binnenhoekrechtsonder2}); //4
		_tiles[28] = new TileObject(new Bitmap[]{floorTile_zandrechtrechts, floorTile_zandrechtrechts2}); //12
		_tiles[26] = new TileObject(new Bitmap[]{floorTile_zandrechtlinks, floorTile_zandrechtlinks2}); //10
		
		
		//WEG hokjes
		_tiles[47] = new TileObject(floorTile_roadhoeklinksboven1);
		_tiles[51] = new TileObject(floorTile_roadhoeklinksboven2);
		_tiles[52] = new TileObject(floorTile_roadhoeklinksboven3);
		_tiles[48] = new TileObject(floorTile_roadhoeklinksboven4);
		_tiles[53] = new TileObject(floorTile_roadhoeklinksonder1);
		_tiles[54] = new TileObject(floorTile_roadhoeklinksonder2);
		_tiles[50] = new TileObject(floorTile_roadhoeklinksonder3);
		_tiles[49] = new TileObject(floorTile_roadhoeklinksonder4);
		_tiles[40] = new TileObject(floorTile_roadhoekrechtsboven1);
		_tiles[39] = new TileObject(floorTile_roadhoekrechtsboven2);
		_tiles[44] = new TileObject(floorTile_roadhoekrechtsboven4);
		_tiles[43] = new TileObject(floorTile_roadhoekrechtsboven3);
		_tiles[46] = new TileObject(floorTile_roadhoekrechtsonder1);
		_tiles[42] = new TileObject(floorTile_roadhoekrechtsonder2);
		_tiles[41] = new TileObject(floorTile_roadhoekrechtsonder3);
		_tiles[45] = new TileObject(floorTile_roadhoekrechtsonder4);
		_tiles[31] = new TileObject(floorTile_roadliggend1);
		_tiles[35] = new TileObject(floorTile_roadliggend2);
		_tiles[36] = new TileObject(floorTile_roadliggend3);
		_tiles[32] = new TileObject(floorTile_roadliggend4);
		_tiles[33] = new TileObject(floorTile_roadstaand1);
		_tiles[34] = new TileObject(floorTile_roadstaand2);
		_tiles[37] = new TileObject(floorTile_roadstaand3);
		_tiles[38] = new TileObject(floorTile_roadstaand4);
		
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
	
	public int getSubGround(int x, int y){
		int xHokje = (int)(x/(this.tileScaleX*32));
		int yHokje = (int)(y/(this.tileScaleY*32));
		//Log.i("log_tag", "subground = x "+xHokje+" y "+yHokje);
		if(xHokje > (_winWidth/_numTilesWidth)*40 || yHokje > (_winHeight/_numTilesHeight)*40){
			xHokje = 0;
			yHokje = 0;
		}
		return _tilePositionArray[(yHokje*40)+xHokje];
	}
	
	public void createFloor(Canvas canvas){
		_updateDelay++;
		if(_updateDelay == 5){
			for(int i = 0; i < _tiles.length; i++){
				_tiles[i].update();
			}
			_updateDelay = 0;
		}
		
		int test = 0;

			int leftSquares = (int)((_startX * -1)/(_winWidth/_numTilesWidth));
			int topSquares = (int)((_startY * -1)/(_winHeight/_numTilesHeight));
			
			for(int ix = leftSquares; ix<leftSquares+_numTilesWidth+1; ix++){
				for(int iy = topSquares; iy<topSquares+_numTilesHeight+1; iy++){
					int i = ix+(iy*40);
					float y = (int)(i/40);
					float x = (int)i-(y*40);
				
				TileObject tile = _tiles[_tilePositionArray[i]];
				
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
				
				//draw SpanPoint
				currentLoop++;
				if(currentLoop >= spanPoint.size())
					currentLoop = 0;
				
				Rect s = new Rect(0, 0, 32, 32);
				RectF d =  new RectF(_spanPos.x*(_winWidth/_numTilesWidth)+_startX, _spanPos.y*(_winHeight/_numTilesHeight)+_startY, _spanPos.x*(_winWidth/_numTilesWidth)+ _startX + (_winWidth/_numTilesWidth), _spanPos.y*(_winHeight/_numTilesHeight)+_startY + (_winHeight/_numTilesHeight));
				canvas.drawBitmap(spanPoint.get(currentLoop), s, d, null);
				//Log.i("log_tag", "current spanitem to draw: "+currentLoop+" x:"+_spanPos.x*(_winWidth/_numTilesWidth)+" y:"+_spanPos.y*(_winHeight/_numTilesHeight));
		}

	}
	
	public void setSpanPoint(Point p){
		this._spanPos = p;
	}
}
