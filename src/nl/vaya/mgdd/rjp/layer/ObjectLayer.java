package nl.vaya.mgdd.rjp.layer;

import java.util.ArrayList;

import nl.vaya.mgdd.rjp.objects.GameObject;
import nl.vaya.mgdd.rjp.objects.Player;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class ObjectLayer {

	protected ArrayList<Player> _enemys;
	protected ArrayList<GameObject> _objects;
	
	protected Player _you;
	protected int _startX;
	protected int _startY;
	
	protected float tileScaleX;
	protected float tileScaleY;
	
	protected int _winWidth;
	protected int _winHeight;
	
	protected int[] _objectPositionArray;
	protected Bitmap[] _bitmapArray;
	protected ArrayList<int[]> _objectItemBuildArray;
	
	protected Bitmap palmboom;
	protected Bitmap parasol1;
	protected Bitmap parasol2;
	protected Bitmap steen1;
	protected Bitmap steen2;
	protected Bitmap handdoek;
	protected Bitmap building1;
	
	public ObjectLayer(Context context, int winWidth, int winHeight){
		_objects = new ArrayList<GameObject>();
		_winWidth = winWidth;
		_winHeight = winHeight;
		_you = new Player(context, "remi", 1);
		makeObjects(context);
	}
	
	
	public void makeObjects(Context context){
		
		_objectPositionArray = new int[] {
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,1,0,0,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,1,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,1,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,1,0,0,0,5,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
		};
		
		_objectItemBuildArray = new ArrayList<int[]>();
		_objectItemBuildArray.add(new int[]{1}); // Null Object
		_objectItemBuildArray.add(new int[]{1}); // Palm Object
		_objectItemBuildArray.add(new int[]{1}); // Handdoek Object
		_objectItemBuildArray.add(new int[]{1}); // Parasol 1 Object 
		_objectItemBuildArray.add(new int[]{1}); // Parasol 2 Object
		_objectItemBuildArray.add(new int[]{1}); // Steen 1 Object
		_objectItemBuildArray.add(new int[]{1}); // Steen 2 Object
		_objectItemBuildArray.add(new int[]{1,2,3,42,43}); // Huis Object
		
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
				context.getResources().getIdentifier("drawable/building1", "drawable", context.getPackageName()), opts);
		
		
		for(int i = 0; i<_objectPositionArray.length; i++){
			if(_objectPositionArray[i] !=0){
				if(_objectPositionArray[i] == 1)
				_objects.add(new GameObject(_objectItemBuildArray.get(_objectPositionArray[i]),i,1,1,false,palmboom));
				if(_objectPositionArray[i] == 2)
					_objects.add(new GameObject(_objectItemBuildArray.get(_objectPositionArray[i]),i,1,1,false,parasol1));
				if(_objectPositionArray[i] == 7)
					_objects.add(new GameObject(_objectItemBuildArray.get(_objectPositionArray[i]),i,3,2,false,building1));
				
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
		
		Rect src = new Rect(0, 0, _you.getImage().getWidth(), _you.getImage().getHeight());
		RectF dest =  new RectF(_you.getScreenX(_startX, _winWidth)-((_you.getImage().getWidth()*tileScaleX)/2), _you.getScreenY(_startY, _winHeight)-((_you.getImage().getHeight()*tileScaleY)/2), _you.getScreenX(_startX, _winWidth) + ((_you.getImage().getWidth()*tileScaleX)/2), _you.getScreenY(_startY, _winHeight) + ((_you.getImage().getHeight()*tileScaleY)/2));
		
		canvas.save();
        canvas.rotate(180-this._you.getAngle(), _you.getScreenX(_startX, _winWidth), _you.getScreenY(_startY, _winHeight));
		canvas.drawBitmap(_you.getImage(), src, dest, null);
		canvas.restore();
		
		//int test = 0;
		
		for(GameObject o:_objects){
			//test++;
			//if(o.isInScreen((int) Math.floor((_startX*-1/(32*tileScaleX))-1), (int) Math.floor((_startY*-1/(32*tileScaleY))-1), (int) Math.floor((_startX*-1/(32*tileScaleX))+_you.getScreenTiles().x+2), (int) Math.floor((_startY*-1/(32*tileScaleY))+_you.getScreenTiles().y+2))){
			canvas.drawBitmap(o.getImage(), o.getStartRect(), o.getDestRect(tileScaleX,tileScaleY, _startX, _startY), null);
			//		Log.i("log_tag", "test "+test);
			//}
		}
	}
	
	public ArrayList<GameObject> getObjects(){
		return _objects;
	}
	
	public Player getYou(){
		return _you;
	}
}
