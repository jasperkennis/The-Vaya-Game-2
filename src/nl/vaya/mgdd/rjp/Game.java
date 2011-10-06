package nl.vaya.mgdd.rjp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Game extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  
        
        setContentView(R.layout.main);
        
        View nextscreenButton = findViewById(R.id.nextscreenButton);
        nextscreenButton.setOnClickListener(this);
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
    			finish();
    			return true;
    	}
    	return false;
    }
    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.nextscreenButton:
				Intent i = new Intent(this, GameActivity.class);
				startActivity(i);
				finish();
			break;
		}
	}
}