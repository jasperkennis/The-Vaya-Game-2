package nl.vaya.mgdd.rjp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GameActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  
        
        setContentView(new GameDraw(this));
    }
    
    public void onRestoreInstanceState(){
    	Log.i("log_tag", "restore");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.exit:
    			Intent intent = new Intent(this, FinActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			//finish();
    			startActivity(intent);
    			finish();
    			return true;
    	}
    	return false;
    }
}