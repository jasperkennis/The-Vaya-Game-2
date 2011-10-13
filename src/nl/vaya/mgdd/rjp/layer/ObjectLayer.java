package nl.vaya.mgdd.rjp.layer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import nl.vaya.mgdd.rjp.objects.Enemy;
import nl.vaya.mgdd.rjp.objects.GameObject;
import nl.vaya.mgdd.rjp.objects.Player;
import nl.vaya.mgdd.rjp.objects.ThrowingObject;
import nl.vaya.mgdd.rjp.objects.WinningObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class ObjectLayer {

	protected HashMap<String,Enemy> _enemies;
	protected ArrayList<GameObject> _objects;
	protected ArrayList<GameObject> _floorObjects;
	protected ArrayList<ThrowingObject> _throwingObjects;
	protected WinningObject _suncream = null;
	
	protected Player _you;
	protected int _startX;
	protected int _startY;
	
	protected float tileScaleX;
	protected float tileScaleY;
	
	protected int _winWidth;
	protected int _winHeight;
	
	protected int[] _objectPositionArray;
	protected ArrayList<Bitmap> _bitmapArray;
	protected ArrayList<int[]> _objectItemBuildArray;
	
	protected Bitmap palmboom;
	protected Bitmap parasol1;
	protected Bitmap parasol2;
	protected Bitmap steen1;
	protected Bitmap steen2;
	protected Bitmap handdoek;
	protected Bitmap building1;
	protected Bitmap building2;
	protected Bitmap building3;
	protected Bitmap stoep;
	
	protected int _numTilesWidth;
	protected int _numTilesHeight;
	
	protected int _onceAfterRun = 0;
	
	protected Context _context;
	
	protected boolean _playing = true;
	
	public ObjectLayer(Context context, int winWidth, int winHeight, int tilesW, int tilesH){
		_context = context;
		_objects = new ArrayList<GameObject>();
		_floorObjects = new ArrayList<GameObject>();
		_throwingObjects = new ArrayList<ThrowingObject>();
		
		_enemies = new HashMap<String,Enemy>();
		_winWidth = winWidth;
		_winHeight = winHeight;
		_you = new Player(context, "remi", 1, 20, 20);
		
		makeObjects(context);
	}
	
	
	public void makeObjects(Context context){
		
		_objectPositionArray = new int[] {
				/* Level one; normal level! */
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 3, 6, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 1, 3, 0, 0, 0, 0, 2, 0, 1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 1, 0, 2, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 4, 5, 0, 0, 4, 0, 0, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 3, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 1, 0, 0, 3, 0, 0, 0, 0, 0, 4, 2, 0, 4, 0, 2, 3, 0, 0, 5, 0, 5, 0, 2, 0, 0, 0, 3, 4, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 5, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 3, 0, 1, 0, 0, 0, 0, 2, 5, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 3, 0, 6, 0, 0, 0, 0, 0, 0, 6, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, 1, 1, 1, 3, 0, 0, 4, 5, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 1, 1, 0, 0, 3, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 2, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 10, 0, 8, 7, 0, 0, 9, 0, 8, 7, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 2, 0, 0, 0, 0, 8, 2, 0, 0, 0, 0, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 8, 5, 0, 0, 0, 0, 5, 8, 2, 10, 0, 0, 0, 0, 0, 1, 0, 3, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 2, 0, 1, 8, 0, 2, 0, 0, 2, 0, 8, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 4, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 1, 0, 1, 0, 3, 0, 0, 0, 0, 2, 10, 0, 8, 0, 0, 0, 0, 0, 0, 8, 9, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 8, 0, 0, 2, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 8, 8, 8, 8, 0, 2, 0, 0, 2, 0, 8, 8, 8, 8, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 9, 0, 8, 5, 0, 0, 0, 0, 5, 8, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 8, 8, 8, 8, 8, 8, 8, 8, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 7, 0, 0, 8, 9, 0, 10, 0, 8, 7, 0, 0, 0, 0, 0, 2, 4, 1, 0, 3, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 8, 0, 0, 0, 0, 8, 2, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 4, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 6, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 5, 3, 1, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 1, 3, 5, 0, 0, 2, 0, 4, 0, 6, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 5, 1, 0, 0, 0, 0, 0, 1, 1, 0, 3, 0, 0, 5, 0, 0, 2, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 3, 5, 0, 3, 0, 0, 1, 0, 0, 4, 0, 0, 0, 6, 1, 1, 0, 0, 0, 5, 4, 1, 3, 0, 1, 4, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 3, 4, 0, 2, 4, 0, 0, 0, 1, 1, 3, 2, 4, 0, 0, 0, 0, 0, 4, 0, 2, 0, 0, 1, 1, 1, 0, 3, 0, 0, 6, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
				/*0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 1, 1, 4, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 6, 0, 2, 0, 0, 0,
		        0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 4, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 6, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
				*//* Level two, race level!*/
				/*0, 0, 6, 0, 6, 0, 6, 6, 0, 0, 6, 6, 6, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 6, 0, 6, 0, 0, 0, 0, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        6, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 6, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 2, 2, 2, 0, 0, 2, 2, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 2, 0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        6, 0, 0, 2, 0, 0, 0, 0, 0, 0, 3, 6, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 6, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 2, 2, 2, 0, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 3, 6, 3, 6, 3, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 2, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 6, 0, 0, 2, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 3, 6, 3, 6, 3, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        6, 0, 0, 6, 0, 0, 2, 2, 2, 2, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 2, 2, 5, 2, 2, 0, 0, 2, 2, 0, 2, 4, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 2, 2, 0, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 6, 6, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 6, 6, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 3, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0*/
		};
		
		_objectItemBuildArray = new ArrayList<int[]>();
		_objectItemBuildArray.add(new int[]{0}); // 1 Handdoek Object 1
		_objectItemBuildArray.add(new int[]{0}); // 2 Palm Object 2
		_objectItemBuildArray.add(new int[]{0}); // 3 Parasol 1 Object 
		_objectItemBuildArray.add(new int[]{0}); // 4 Parasol 2 Object
		_objectItemBuildArray.add(new int[]{0}); // 5 Steen 1 Object
		_objectItemBuildArray.add(new int[]{0}); // 6 Steen 2 Object
		
		_objectItemBuildArray.add(new int[]{0,1,2,41,42}); // 7 Huis Object
		_objectItemBuildArray.add(new int[]{0}); // 8 Voetpad
		_objectItemBuildArray.add(new int[]{0,1,40,41}); // 9 Huis Object
		_objectItemBuildArray.add(new int[]{0,1,40,41}); // 10 Huis Object
		
		//Set bitmap options
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		
		//create game objects
		palmboom = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/palmboom", "drawable", context.getPackageName()), opts);
		parasol1 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/parasol", "drawable", context.getPackageName()), opts);
		parasol2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/parasol2", "drawable", context.getPackageName()), opts);
		steen1 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/steen1", "drawable", context.getPackageName()), opts);
		steen2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/steen2", "drawable", context.getPackageName()), opts);
		handdoek = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/handdoek", "drawable", context.getPackageName()), opts);
		building1 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/building_1", "drawable", context.getPackageName()), opts);
		building2 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/building_2", "drawable", context.getPackageName()), opts);
		building3 = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/building_3", "drawable", context.getPackageName()), opts);
		stoep = BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier("drawable/stoep1", "drawable", context.getPackageName()), opts);
		
		_bitmapArray = new ArrayList<Bitmap>();
		_bitmapArray.add(handdoek);
		_bitmapArray.add(palmboom);
		_bitmapArray.add(parasol1);
		_bitmapArray.add(parasol2);
		_bitmapArray.add(steen1);
		_bitmapArray.add(steen2);
		_bitmapArray.add(building3);
		_bitmapArray.add(stoep);
		_bitmapArray.add(building2);
		_bitmapArray.add(building1);
		
		
		for(int i = 0; i<_objectPositionArray.length; i++){
			if(_objectPositionArray[i] !=0){
				if(_objectPositionArray[i] == 7){
					_objects.add(new GameObject(_objectItemBuildArray.get(_objectPositionArray[i]-1),i,3,2,false,_bitmapArray.get(_objectPositionArray[i]-1)));
				}else if(_objectPositionArray[i] == 9){
					_objects.add(new GameObject(_objectItemBuildArray.get(_objectPositionArray[i]-1),i,2,2,false,_bitmapArray.get(_objectPositionArray[i]-1)));
				}else if(_objectPositionArray[i] == 10){
					_objects.add(new GameObject(_objectItemBuildArray.get(_objectPositionArray[i]-1),i,2,2,false,_bitmapArray.get(_objectPositionArray[i]-1)));
				}else if(_objectPositionArray[i] == 1){
					_floorObjects.add(new GameObject(_objectItemBuildArray.get(_objectPositionArray[i]-1),i,1,1,true,_bitmapArray.get(_objectPositionArray[i]-1)));
				}else if(_objectPositionArray[i] == 8){
					_floorObjects.add(new GameObject(_objectItemBuildArray.get(_objectPositionArray[i]-1),i,1,1,true,_bitmapArray.get(_objectPositionArray[i]-1)));
				}else{
					_objects.add(new GameObject(_objectItemBuildArray.get(_objectPositionArray[i]-1),i,1,1,false,_bitmapArray.get(_objectPositionArray[i]-1)));
				}
			}
		}
		
	}
	
	public void setTileScaleX(float xScale){
		this.tileScaleX = xScale;
	}
	
	public void setTileScaleY(float yScale){
		this.tileScaleY = yScale;
	}
	
	public void setStartX(int x, int y){
		if(x <= 0)
			this._startX = x;
		if(y <= 0)
			this._startY = y;
	}
	
	public void createObjects(Canvas canvas){

		for(GameObject o:_floorObjects){
			canvas.drawBitmap(o.getImage(), o.getStartRect(), o.getDestRect(tileScaleX,tileScaleY, _startX, _startY), null);
		}
		
		for(ThrowingObject o:_throwingObjects){
			canvas.drawBitmap(o.getImage(), o.getSrc(), o.getDest(tileScaleX,tileScaleY, _startX, _startY), null);
		}
		if(_suncream != null && !_suncream.isPicked())
		canvas.drawBitmap(_suncream.getImage(), _suncream.getSrc(), _suncream.getDest(tileScaleX,tileScaleY, _startX, _startY), null);
		
		
		Rect src = new Rect(0, 0, _you.getImage().getWidth(), _you.getImage().getHeight());
		RectF dest =  new RectF(_you.getScreenX(_startX, _winWidth)-((_you.getImage().getWidth()*tileScaleX)/2), _you.getScreenY(_startY, _winHeight)-((_you.getImage().getHeight()*tileScaleY)/2), _you.getScreenX(_startX, _winWidth) + ((_you.getImage().getWidth()*tileScaleX)/2), _you.getScreenY(_startY, _winHeight) + ((_you.getImage().getHeight()*tileScaleY)/2));
		
		canvas.save();
        canvas.rotate(180-this._you.getAngle(), _you.getScreenX(_startX, _winWidth), _you.getScreenY(_startY, _winHeight));
		canvas.drawBitmap(_you.getImage(), src, dest, null);
		canvas.restore();
		
		//draw enemys
		Collection<Enemy> c = _enemies.values();
		Iterator<Enemy> itr = c.iterator();
		
		while(itr.hasNext()){
			Enemy next_enemy = itr.next();
			canvas.save();
	        canvas.rotate(180-next_enemy.getAngle(), next_enemy.getScreenX(_startX), next_enemy.getScreenY(_startY));
			canvas.drawBitmap(next_enemy.getImage(), 
					new Rect(0, 0, next_enemy.getImage().getWidth(), next_enemy.getImage().getHeight()), 
					new RectF(next_enemy.getScreenX(_startX)-((next_enemy.getImage().getWidth()*tileScaleX)/2), next_enemy.getScreenY(_startY)-((next_enemy.getImage().getHeight()*tileScaleY)/2), next_enemy.getScreenX(_startX) + ((next_enemy.getImage().getWidth()*tileScaleX)/2), next_enemy.getScreenY(_startY) + ((next_enemy.getImage().getHeight()*tileScaleY)/2)), 
					null);
			canvas.restore();
		}
		
		for(GameObject o:_objects){
			canvas.drawBitmap(o.getImage(), o.getStartRect(), o.getDestRect(tileScaleX,tileScaleY, _startX, _startY), null);
		}
		
		if(this._you.checkWinnState() && _playing){
			_playing = false;
			AlertDialog alertDialog = new AlertDialog.Builder(_context).create();  
		    alertDialog.setTitle("GEFELICITEERD!");  
		    alertDialog.setMessage("Je hebt het spel gewonnen! Ga de uitdaging nogmaals aan.");  
		    alertDialog.setButton("Terug naar het hoofdscherm.", new DialogInterface.OnClickListener() {  
		      public void onClick(DialogInterface dialog, int which) { 
		        return;  
		    } });   
		    alertDialog.show();
		    
		    //TODO: ADD a action to the server that this player has won the game.
		}

		if(_onceAfterRun == 0){
			_throwingObjects.add(new ThrowingObject(_context,10,10, tileScaleX,tileScaleY, _startX, _startY));
			_throwingObjects.add(new ThrowingObject(_context,20,10, tileScaleX,tileScaleY, _startX, _startY));
			_throwingObjects.add(new ThrowingObject(_context,30,10, tileScaleX,tileScaleY, _startX, _startY));
			_throwingObjects.add(new ThrowingObject(_context,10,20, tileScaleX,tileScaleY, _startX, _startY));
			_throwingObjects.add(new ThrowingObject(_context,10,30, tileScaleX,tileScaleY, _startX, _startY));
			_throwingObjects.add(new ThrowingObject(_context,22,22, tileScaleX,tileScaleY, _startX, _startY));
			_throwingObjects.add(new ThrowingObject(_context,18,10, tileScaleX,tileScaleY, _startX, _startY));
			_throwingObjects.add(new ThrowingObject(_context,33,16, tileScaleX,tileScaleY, _startX, _startY));
			
			_suncream = new WinningObject(_context,20,20, tileScaleX,tileScaleY, _startX, _startY);
			
			AlertDialog alertDialog = new AlertDialog.Builder(_context).create();  
		    alertDialog.setTitle("Oei het is erg heet!");  
		    alertDialog.setMessage("Zoek de zonnenbrand en breng deze terug naar je spawn plek.");  
		    alertDialog.setButton("Oke", new DialogInterface.OnClickListener() {  
		      public void onClick(DialogInterface dialog, int which) {  
		        return;  
		    } });   
		    alertDialog.show();
		}
		_onceAfterRun = 1;
	}
	
	public ArrayList<GameObject> getObjects(){
		return _objects;
	}

	public Player getYou(){
		return _you;
	}
	
	public WinningObject getWinningObject(){
		return _suncream;
	}
	
	public HashMap<String,Enemy> getEnemy(){
		return _enemies;
	}
	
	public ArrayList<ThrowingObject> getThrowingObjects(){
		return _throwingObjects;
	}
	
	public void removeThrowable(int i){
		_throwingObjects.remove(i);
	}
	
	public void addThrowable(int x, int y){
		ThrowingObject _throwable = new ThrowingObject(_context, x, y, tileScaleX,tileScaleY, _startX, _startY);
		_throwingObjects.add(_throwable);
	}
	

	public void handleEnemies(JSONArray players, String playerId) {
		for(int i = 0; i < players.length() ; i++){
			JSONObject _player = players.optJSONObject(i);
			try{
				try {
					if(!_player.getString("player").equals(playerId)){
						createOrUpdateEnemy(_player);
					} else {
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} finally {
			}
		}
	}
	
	private void createOrUpdateEnemy(JSONObject player){
		try {
			if( _enemies.get( player.getString("player")) == null ){
				Enemy newEnemy = new Enemy(_context);
				newEnemy.setPlayerPos((int)(player.getInt("x")*tileScaleX), (int)(player.getInt("y")*tileScaleY), player.getInt("angle"), player.getInt("state"), _numTilesWidth, _numTilesHeight);
				_enemies.put(player.getString("player"), newEnemy);
			} else {
				_enemies.get( player.getString("player") ).setPlayerPos((int)(player.getInt("x")*tileScaleX), (int)(player.getInt("y")*tileScaleY), player.getInt("angle"), player.getInt("state"), _numTilesWidth, _numTilesHeight);
			}
		} catch (JSONException e) {
			Log.i("received_players", "Unable to read playerid.");
			e.printStackTrace();
		}
	}
}