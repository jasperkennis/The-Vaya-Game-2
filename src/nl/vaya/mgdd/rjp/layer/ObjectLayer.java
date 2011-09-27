package nl.vaya.mgdd.rjp.layer;

import java.util.ArrayList;

import nl.vaya.mgdd.rjp.objects.Player;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class ObjectLayer {

	protected ArrayList<Player> _enemys;
	protected Player _you;
	protected int _startX;
	protected int _startY;
	
	protected float tileScaleX;
	protected float tileScaleY;
	
	protected int _winWidth;
	protected int _winHeight;
	
	public ObjectLayer(Context context, int winWidth, int winHeight){
		_winWidth = winWidth;
		_winHeight = winHeight;
		_you = new Player(context, "remi", 1);
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
	
		canvas.drawBitmap(_you.getImage(), src, dest, null);
	}
	
	public Player getYou(){
		return _you;
	}
}
