package nl.vaya.mgdd.rjp;

import java.util.ArrayList;

import nl.vaya.mgdd.rjp.connection.Communicator;
import nl.vaya.mgdd.rjp.connection.MessageResponder;
import nl.vaya.mgdd.rjp.connection.SenderRunnable;
import nl.vaya.mgdd.rjp.connection.SenderThread;
import nl.vaya.mgdd.rjp.layer.FloorLayer;
import nl.vaya.mgdd.rjp.layer.ObjectLayer;
import nl.vaya.mgdd.rjp.objects.Enemy;
import nl.vaya.mgdd.rjp.objects.GameObject;
import nl.vaya.mgdd.rjp.objects.ThrowingObject;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class GameDraw extends View implements OnTouchListener, MessageResponder {

	protected FloorLayer floor;
	protected ObjectLayer objects;

	protected int _winWith;
	protected int _winHeight;

	protected Context context;

	protected float initialTouchXDisposition = 0;
	protected float initialTouchYDisposition = 0;
	protected int motionDetectionArea = 6;

	protected int _maxSpeed = 20;
	protected int _moveX = 0;
	protected int _moveY = 0;

	// protected float _angle = 0;
	protected float _touchX = 0;
	protected float _touchY = 0;

	protected int _startX = -240;
	protected int _startY = -40;
	
	protected int _downTime = 0;

	protected static Communicator communicator = null;
	protected Thread communicatorReceiveThread;
	protected SenderThread communicatorSendThread;
	protected String my_position_json;
	
	protected JSONObject incommingParser;
	
	protected String log_tag = "game_server";

	protected boolean gameReady = false; //false for server on
	
	protected String playerId;
	
	protected ArrayList<String> logText;
	
	protected int _once = 0; // 0 for server on

	public GameDraw(Context context) {
		super(context);
		
		setWillNotDraw(false);
		setOnTouchListener(this);
		setFocusable(true);
		setFocusableInTouchMode(true);

		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		_winWith = display.getWidth();
		_winHeight = display.getHeight();

		// Create Layers
		floor = new FloorLayer(context, _winWith, _winHeight);
		objects = new ObjectLayer(context, _winWith, _winHeight, floor.getNumTilesWidth(), floor.getNumTilesHeight());
		
		final GameDraw _self = this;
		if(_once == 0){
			
			logText = new ArrayList<String>();
			logText.add("Het spel is aan het laden...");
			/*
			 * Send position and orientation back to server, in
			 * a separate tread to prevent blocking the loop
			 */
			
			communicatorSendThread =  new SenderThread(new SenderRunnable() {
				@Override
				public void run(String my_position_json) {
			        GameDraw.getCommunicator().sendMessage(my_position_json);
			      }
			    });
			
			communicatorReceiveThread = new Thread(new Runnable(){
				@Override
				public void run(){
					GameDraw.getCommunicator().recieveMessages(_self);
				}
			});
			
			communicatorReceiveThread.start();
			
		}
		_once = 1;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (gameReady) {
			
			objects.getYou().setPlayerPos(_moveX, _moveY, _winWith, _winHeight,
					floor.getNumTilesWidth(), floor.getNumTilesHeight(),
					this._touchX, this._touchY, initialTouchXDisposition,
					initialTouchYDisposition);
			objects.getYou().giveSubGround(
					floor.getSubGround(objects.getYou().getXPos(), objects
							.getYou().getYPos()));
			
			int youPos = (int) ((int) Math.floor(objects.getYou().getXPos()
					/ (32 * floor.getTileScaleX())) + (Math.floor(objects
					.getYou().getYPos() / (32 * floor.getTileScaleY())) * 40));
			
			/*for(Enemy enemy:objects.getEnemy()){
				enemy.setPlayerPos(100, 200, 180.0f, 1, floor.getNumTilesWidth(), floor.getNumTilesHeight());
				if(enemy.checkCollision(youPos)){
					objects.getYou().setBack();
				}
			}*/

			// check collision
			for (GameObject o : objects.getObjects()) {
				if (o.findTile(youPos) && !o.canWalkTrough()) {
					objects.getYou().setBack();
				}
			}
			
			// check collision
			for (ThrowingObject to : objects.getThrowingObjects()) {
				if (to.findTile(youPos)) {
					objects.getYou().setBack();
					objects.getYou().fall();
				}
			}
			
			this._startX = objects.getYou().getStartX(_winWith);
			this._startY = objects.getYou().getStartY(_winHeight);
			floor.setStartX(this._startX, this._startY);
			objects.setStartX(this._startX, this._startY);
			canvas.drawColor(Color.BLACK);
			floor.createFloor(canvas);
			objects.setTileScaleX(floor.getTileScaleX());
			objects.setTileScaleY(floor.getTileScaleY());
			objects.createObjects(canvas);
			
			my_position_json = "{\"type\" : \"position_update\", \"position\" : {";
	    	my_position_json += "\"x\": " + objects.getYou().getXPos() + ",";
	    	my_position_json += "\"y\": " + objects.getYou().getYPos() + ",";
	    	my_position_json += "\"angle\": " + objects.getYou().getAngle() + ",";
	    	my_position_json += "\"state\": " + objects.getYou().getState() + "";
	    	my_position_json += "}}";
	    	
			communicatorSendThread.run(my_position_json); //uncomment for server on
			
		}else{
			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setStyle(Style.FILL);
			canvas.drawPaint(paint);

			paint.setColor(Color.WHITE);
			paint.setTextSize(12);
			int i = 0;
			for(String t:this.logText){
				canvas.drawText(t, 10, (25*(this.logText.size()-i)), paint);
				i++;
			}
			
		}
		
		invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {	
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			initialTouchXDisposition = event.getX(0);
			initialTouchYDisposition = event.getY(0);
			_downTime++;
			return true;
		case MotionEvent.ACTION_POINTER_2_DOWN:
			//pick item on location event.getX(1) en event.getY(1)
			objects.getYou().addPickThrow((int)event.getX(1),(int)event.getY(1), objects.getThrowingObjects());
			return true;
		case MotionEvent.ACTION_UP:
			if(_downTime < 5){
				objects.getYou().addPickThrow((int)event.getX(0),(int)event.getY(0), objects.getThrowingObjects());
			}
			_downTime = 0;
			initialTouchXDisposition = 0.0f;
			initialTouchYDisposition = 0.0f;
			this._moveX = 0;
			this._moveY = 0;
			return false;
		default:
			float x = ( event.getX() - initialTouchXDisposition );
			float y = ( event.getY() - initialTouchXDisposition );
			//x = (x > _maxSpeed) ? _maxSpeed : x;
			//y = (y > _maxSpeed) ? _maxSpeed : y;
			this._moveX = (int) Math.ceil(x / motionDetectionArea);
			this._moveY = (int) Math.ceil(y / motionDetectionArea);

			this._touchX = event.getX(0);
			this._touchY = event.getY(0);

			return true;
		}
	}

	/*
	 * This method gets called whenever a message is registered
	 * by the communicator
	 * @see nl.vaya.mgdd.rjp.connection.MessageResponder#respond()
	 */
	@Override
	public void respond(String message) {
		if(  message != null  ){
			try {
				
				incommingParser = new JSONObject(message);
				
				
				if(incommingParser.getString("type").equals("positions")){
					//Log.i(log_tag,incommingParser.getJSONArray("positions") + "");
					objects.handleEnemies(incommingParser.getJSONArray("positions"),playerId);
				}
				
				/*
				 *  Handle throwable object displacement
				 */
				if(incommingParser.getString("type").equals("player_dropped_obj")){
					objects.addThrowable(incommingParser.getInt("x"), incommingParser.getInt("y"));
					return;
				}
				
				if(incommingParser.getString("type").equals("player_got_obj")){
					objects.removeThrowable(incommingParser.getInt("index"));
					return;
				}
				
				// Handle directives
				if(incommingParser.getString("type").equals("directive")){
					if(incommingParser.getString("directive").equals("start")){
						Log.i("game_server", "Starting the game!" );
						gameReady = true;
						return;
					}
				}
				
				// Handle player id
				if(incommingParser.getString("type").equals("player_id")){
					playerId = incommingParser.getString("id");
					Log.i("game_server", "Player id has been set to: " + playerId  + "." );
					return;
				}
				
				// Handle messages
				if(incommingParser.getString("type").equals("message")){
					Log.i("game_server", incommingParser.getString("message") );
					this.logText.add(incommingParser.getString("message"));
					return;
				}
				
			} catch (JSONException e) {
				Log.i("game_server", "Incomming message not parsable json. It was:" );
				Log.i("game_server", message );
				Log.i(log_tag,e.getMessage());
			}
		}
	}
	
	public static Communicator getCommunicator(){
		if(communicator == null){
			Log.i("log_tag", "Creating Communicator");
			communicator = new Communicator();
		}
		return communicator;
	}
}