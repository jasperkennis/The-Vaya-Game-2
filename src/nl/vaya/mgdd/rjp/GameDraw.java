package nl.vaya.mgdd.rjp;

import java.util.ArrayList;
import java.util.Random;

import nl.vaya.mgdd.rjp.connection.Communicator;
import nl.vaya.mgdd.rjp.connection.MessageResponder;
import nl.vaya.mgdd.rjp.connection.SenderRunnable;
import nl.vaya.mgdd.rjp.connection.SenderThread;
import nl.vaya.mgdd.rjp.layer.FloorLayer;
import nl.vaya.mgdd.rjp.layer.ObjectLayer;
import nl.vaya.mgdd.rjp.objects.GameObject;
import nl.vaya.mgdd.rjp.objects.ThrowingObject;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class GameDraw extends View implements OnTouchListener, MessageResponder {

	protected boolean _firstDraw = true;

	protected FloorLayer floor;
	protected ObjectLayer objects;

	protected ArrayList<Point> _spawnPoints;

	protected int _winWith;
	protected int _winHeight;

	protected Context context;

	protected float initialTouchXDisposition = 0;
	protected float initialTouchYDisposition = 0;
	protected int motionDetectionArea = 5;

	protected int _maxSpeed = 20;
	protected int _moveX = 0;
	protected int _moveY = 0;

	protected float _angle = 0;
	protected float _touchX = 0;
	protected float _touchY = 0;

	protected int _startX = 0;
	protected int _startY = 0;

	protected int _downTime = 0;

	protected static Communicator communicator = null;
	protected Thread communicatorReceiveThread;
	protected SenderThread communicatorSendThread;
	protected Thread fpsThread;
	protected String my_position_json;

	protected JSONObject incommingParser;

	protected String log_tag = "game_server";

	protected boolean gameReady = false; // false for server on
	protected boolean drawing = true;
	protected long now = 0;
	protected long lastDraw = 0;
	protected int fps = 30;
	protected int sampleTime = 0;
	protected boolean draw = false;

	protected String playerId;

	protected ArrayList<String> logText;

	protected int _once = 0; // 0 for server on

	protected Canvas _canvas = null;

	protected GameCycle _cycle;

	// protected GameThread _gameThread;

	public GameDraw(Context context) {

		super(context);

		// Create spawnpoints
		_spawnPoints = new ArrayList<Point>();
		_spawnPoints.add(new Point(32, 35));
		_spawnPoints.add(new Point(4, 36));
		_spawnPoints.add(new Point(31, 3));
		_spawnPoints.add(new Point(5, 5));

		setWillNotDraw(false);
		setOnTouchListener(this);
		setFocusable(true);
		setFocusableInTouchMode(true);

		sampleTime = 1000 / fps;

		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		_winWith = display.getWidth();
		_winHeight = display.getHeight();

		// Create Layers
		floor = new FloorLayer(context, _winWith, _winHeight);
		objects = new ObjectLayer(context, _winWith, _winHeight,
				floor.getNumTilesWidth(), floor.getNumTilesHeight());

		final GameDraw _self = this;
		if (_once == 0) {

			logText = new ArrayList<String>();
			logText.add("Het spel is aan het laden...");
			/*
			 * Send position and orientation back to server, in a separate tread
			 * to prevent blocking the loop
			 */

			communicatorSendThread = new SenderThread(new SenderRunnable() {
				@Override
				public void run(String my_position_json) {
					GameDraw.getCommunicator().sendMessage(my_position_json);
				}
			});

			communicatorReceiveThread = new Thread(new Runnable() {
				@Override
				public void run() {
					GameDraw.getCommunicator().recieveMessages(_self);
				}
			});

			communicatorReceiveThread.start();

		}
		_once = 1;
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {

		/**
		 * Unfortunately, due to dependency on canvas, fps can only be
		 * initialized here
		 */
		/*
		 * if(_canvas == null){ _canvas = canvas; drawing = true; lastDraw =
		 * System.currentTimeMillis(); _cycle = new GameCycle();
		 * _cycle.doInBackground(this); }
		 */

		if (gameReady) {
			my_position_json = "{\"type\" : \"position_update\", \"position\" : {";
			my_position_json += "\"x\": "
					+ (int) (objects.getYou().getXPos() / floor.getTileScaleX())
					+ ",";
			my_position_json += "\"y\": "
					+ (int) (objects.getYou().getYPos() / floor.getTileScaleY())
					+ ",";
			my_position_json += "\"angle\": " + objects.getYou().getAngle()
					+ ",";
			my_position_json += "\"state\": " + objects.getYou().getState()
					+ "";
			my_position_json += "}}";

			communicatorSendThread.run(my_position_json); // uncomment for
															// server on

			if (_firstDraw) {
				Random randomGenerator = new Random();
				int spanInt = randomGenerator.nextInt(_spawnPoints.size());
				objects.getYou().setSpanPoint(_spawnPoints.get(spanInt));
				floor.setSpanPoint(_spawnPoints.get(spanInt));
			}

			if ((getNow() - getLastDraw()) > sampleTime) {
				//Log.i(log_tag, "writing");

				objects.getYou().setPlayerPos(_moveX, _moveY, _winWith,
						_winHeight, floor.getNumTilesWidth(),
						floor.getNumTilesHeight(), this._touchX, this._touchY,
						initialTouchXDisposition, initialTouchYDisposition);
			}

			objects.getYou().giveSubGround(
					floor.getSubGround(objects.getYou().getXPos(), objects
							.getYou().getYPos()));

			int youPos = (int) ((int) Math.floor(objects.getYou().getXPos()
					/ (32 * floor.getTileScaleX())) + (Math.floor(objects
					.getYou().getYPos() / (32 * floor.getTileScaleY())) * 40));

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

			// check collision
			if (objects.getWinningObject() != null
					&& !objects.getWinningObject().isPicked()
					&& objects.getWinningObject().findTile(youPos)) {
				objects.getYou()
						.pickUpWinningObject(objects.getWinningObject());
			}

			this._startX = objects.getYou().getStartX(_winWith);
			this._startY = objects.getYou().getStartY(_winHeight);
			floor.setStartX(this._startX, this._startY);
			objects.setStartX(this._startX, this._startY);
			canvas.drawColor(Color.BLACK);
			floor.createFloor(canvas);
			objects.setTileScaleX(floor.getTileScaleX());
			objects.setTileScaleY(floor.getTileScaleY());

			if (_firstDraw) {
				objects.getYou()
						.setPlayerPos(
								(int) (objects.getYou().getSpanPoint().x * (32 * floor
										.getTileScaleX())),
								(int) (objects.getYou().getSpanPoint().y * (32 * floor
										.getTileScaleY())), _winWith,
								_winHeight, floor.getNumTilesWidth(),
								floor.getNumTilesHeight(), this._touchX,
								this._touchY, initialTouchXDisposition,
								initialTouchYDisposition);
			}

			_firstDraw = false;

			objects.createObjects(canvas);
		} else {
			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setStyle(Style.FILL);
			canvas.drawPaint(paint);

			paint.setColor(Color.WHITE);
			paint.setTextSize(12);
			int i = 0;
			for (String t : this.logText) {
				canvas.drawText(t, 10, (25 * (this.logText.size() - i)), paint);
				i++;
			}

		}

		/*
		 * if(!drawing || draw){ // Ensure a drawing cycle before an fps has
		 * been established. Log.i("draw log","Completing draw.");
		 * setDraw(false); invalidate(); setLastDrawnToNow();
		 * Log.i("draw log","Draw complete."); }
		 */
		invalidate();
			//}
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			initialTouchXDisposition = event.getX();
			initialTouchYDisposition = event.getY();
			_downTime++;
			return true;
		case MotionEvent.ACTION_POINTER_2_DOWN:
			// pick item on location event.getX(1) en event.getY(1)
			objects.getYou().addPickThrow((int) event.getX(1),
					(int) event.getY(1), objects.getThrowingObjects());
			return true;
		case MotionEvent.ACTION_UP:
			if (_downTime < 5) {
				objects.getYou().addPickThrow((int) event.getX(),
						(int) event.getY(), objects.getThrowingObjects());
			}
			_downTime = 0;
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

			this._touchX = event.getX();
			this._touchY = event.getY();

			return true;
		}
	}

	/*
	 * This method gets called whenever a message is registered by the
	 * communicator
	 * 
	 * @see nl.vaya.mgdd.rjp.connection.MessageResponder#respond()
	 */
	@Override
	public void respond(String message) {
		if (message != null) {
			try {

				incommingParser = new JSONObject(message);

				if (incommingParser.getString("type").equals("positions")) {
					objects.handleEnemies(
							incommingParser.getJSONArray("positions"), playerId);
				}

				/*
				 * Handle throwable object displacement
				 */
				if (incommingParser.getString("type").equals(
						"player_dropped_obj")) {
					objects.addThrowable(incommingParser.getInt("x"),
							incommingParser.getInt("y"));
					return;
				}

				if (incommingParser.getString("type").equals("player_got_obj")) {
					objects.removeThrowable(incommingParser.getInt("index"));
					return;
				}

				/*
				 * Handle winning object grab or drop
				 */
				if (incommingParser.getString("type").equals(
						"player_dropped_win_obj")) {
					objects.getWinningObject().dropped();
					return;
				}

				if (incommingParser.getString("type").equals(
						"player_got_win_obj")) {
					objects.getWinningObject().picked();
					return;
				}

				/*
				 * Handle losing signal
				 */
				if (incommingParser.getString("type").equals("you_lose")) {
					objects.setPlaying(false);
					AlertDialog alertDialog = new AlertDialog.Builder(
							objects.getContext()).create();
					alertDialog.setTitle("Helaas!");
					alertDialog
							.setMessage("Je hebt het spel verloren! Ga de uitdaging nogmaals aan.");
					alertDialog.setButton("Terug naar het hoofdscherm.",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							});
					alertDialog.show();
				}

				// Handle directives
				if (incommingParser.getString("type").equals("directive")) {
					if (incommingParser.getString("directive").equals("start")) {
						Log.i("game_server", "Starting the game!");
						gameReady = true;
						return;
					}
				}

				// Handle player id
				if (incommingParser.getString("type").equals("player_id")) {
					playerId = incommingParser.getString("id");
					Log.i("game_server", "Player id has been set to: "
							+ playerId + ".");
					return;
				}

				// Handle messages
				if (incommingParser.getString("type").equals("message")) {
					this.logText.add(incommingParser.getString("message"));
					return;
				}

			} catch (JSONException e) {
				Log.i("game_server",
						"Incomming message not parsable json. It was:");
				Log.i("game_server", message);
				Log.i(log_tag, e.getMessage());
			}
		}
	}

	public static Communicator getCommunicator() {
		if (communicator == null) {
			communicator = new Communicator();
		}
		return communicator;
	}

	public Canvas getCanvas() {
		return _canvas;
	}

	private void setLastDraw(long n) {
		lastDraw = n;
	}

	private void setLastDrawnToNow() {
		setLastDraw(System.currentTimeMillis());
	}

	private long getLastDraw() {
		return lastDraw;
	}

	private long getNow() {
		return System.currentTimeMillis();
	}

	private void setDraw(boolean v) {
		draw = v;
	}

	private class GameCycle extends AsyncTask<FTPController, Object, Object> {

		protected Object doInBackground(FTPController... callBacks) {
			callBacks[0].ftpCycle();
			return null;
		}

	}
}