package nl.vaya.mgdd.rjp;

import nl.vaya.mgdd.rjp.connection.Communicator;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class Game extends Activity {
    /** Called when the activity is first created. */
	protected GameDraw gameDraw;
	protected Communicator communicator;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  
        
        setContentView(new GameDraw(this));
    }
}