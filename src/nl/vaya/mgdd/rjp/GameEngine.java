package nl.vaya.mgdd.rjp;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class GameEngine {

	//Create a log tag
	static final String logTag = "log_tag";
	
	protected GameDraw _gameDraw;
	protected Canvas _canvas;
	
	public GameEngine(GameDraw gameDraw){
		_gameDraw = gameDraw;
	}
	
	void Update(){
		Log.i(logTag, "Update game Engine");
		_gameDraw.invalidate();
		//this.Clear(_canvas);
		//this.Draw(_canvas);
	}
	
	void Draw(Canvas c){

	}
	
	void Clear(Canvas c){
		//_canvas.drawColor(Color.BLACK);
		//Canvas c = null;
	}
	
	public void setCanvas(Canvas c){
		_canvas = c;
	}
	
	void Init(Resources r){}
}
