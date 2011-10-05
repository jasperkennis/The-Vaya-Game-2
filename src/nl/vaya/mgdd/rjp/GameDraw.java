package nl.vaya.mgdd.rjp;

import nl.vaya.mgdd.rjp.connection.Communicator;
import nl.vaya.mgdd.rjp.connection.MessageResponder;
import nl.vaya.mgdd.rjp.connection.SenderRunnable;
import nl.vaya.mgdd.rjp.connection.SenderThread;
import nl.vaya.mgdd.rjp.layer.FloorLayer;
import nl.vaya.mgdd.rjp.layer.ObjectLayer;
import nl.vaya.mgdd.rjp.objects.Enemy;
import nl.vaya.mgdd.rjp.objects.GameObject;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
	protected int motionDetectionArea = 3;

	protected int _moveX = 0;
	protected int _moveY = 0;

	// protected float _angle = 0;
	protected float _touchX = 0;
	protected float _touchY = 0;

	protected int _startX = -240;
	protected int _startY = -40;

	protected static Communicator communicator = null;
	protected Thread communicatorReceiveThread;
	protected SenderThread communicatorSendThread;
	protected String my_position_json;
	
	protected JSONObject incommingParser;
	
	protected String log_tag = "game_server";

	protected boolean gameReady = false;
	
	protected String playerId;
	
	

	public GameDraw(Context context) {
		super(context);

		createCommunicator();
		
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
		objects = new ObjectLayer(context, _winWith, _winHeight);
		
		final GameDraw _self = this;
		
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

	@Override
	protected void onDraw(Canvas canvas) {
		
		if (gameReady) {
			
			my_position_json = "{\"type\" : \"position_update\", \"position\" : {";
	    	my_position_json += "\"x\": " + objects.getYou().getXPos() + ",";
	    	my_position_json += "\"y\": " + objects.getYou().getYPos() + ",";
	    	my_position_json += "\"angle\": " + objects.getYou().getAngle() + "";
	    	my_position_json += "}}";
	    	
			communicatorSendThread.run(my_position_json);
			
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
			
			for(Enemy enemy:objects.getEnemy()){
				enemy.setPlayerPos(100, 200, 180.0f, 1, floor.getNumTilesWidth(), floor.getNumTilesHeight());
				if(enemy.checkCollision(youPos)){
					objects.getYou().setBack();
				}
			}

			// check collision
			for (GameObject o : objects.getObjects()) {
				if (o.findTile(youPos) && !o.canWalkTrough()) {
					objects.getYou().setBack();
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
		}
		
		invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {	
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			initialTouchXDisposition = event.getX(0);
			initialTouchYDisposition = event.getY(0);
			return true;
		case MotionEvent.ACTION_POINTER_2_DOWN:
			//pick item on location event.getX(1) en event.getY(1)
			objects.getYou().addPickThrow((int)event.getX(1),(int)event.getY(1), objects.getThrowingObjects());
			return true;
		case MotionEvent.ACTION_UP:
			initialTouchXDisposition = 0.0f;
			initialTouchYDisposition = 0.0f;
			this._moveX = 0;
			this._moveY = 0;
			return false;
		default:
			float x = event.getX() - initialTouchXDisposition;
			float y = event.getY() - initialTouchYDisposition;
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
		try {
			Log.i("game_server", message );
			
			incommingParser = new JSONObject(message);
			
			// Handle directives
			if(incommingParser.getString("type").equals("directive")){
				if(incommingParser.getString("directive").equals("start")){
					Log.i("game_server", "Starting the game!" );
					gameReady = true;
				}
			}
			
			// Handle player id
			if(incommingParser.getString("type").equals("player_id")){
				playerId = incommingParser.getString("id");
				Log.i("game_server", "Player id has been set to: " + playerId  + "." );
			}
			
			// Handle messages
			if(incommingParser.getString("type").equals("message")){
				Log.i("game_server", incommingParser.getString("message") );
			}
		} catch (JSONException e) {
			Log.i("game_server", "Incomming message not parsable json. It was:" );
			Log.i("game_server", message );
		}
	}
	
	private static void createCommunicator(){
		//Log.i(log_tag, "Constructing communicator now");
		communicator = new Communicator();
		//Log.i(log_tag, "Past construction, beginning game object inizialization.");
	}
	
	
	static Communicator getCommunicator(){
		if(communicator.equals(null)){
			createCommunicator();
			return communicator;
		}
		return communicator;
	}
}