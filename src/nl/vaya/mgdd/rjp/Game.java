package nl.vaya.mgdd.rjp;

import android.app.Activity;
import android.os.Bundle;

public class Game extends Activity {
    /** Called when the activity is first created. */
	protected GameDraw gameDraw;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        gameDraw = new GameDraw(this);
        setContentView(gameDraw);
    }
}