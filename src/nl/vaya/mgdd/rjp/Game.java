package nl.vaya.mgdd.rjp;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.AttributeSet;

public class Game extends Activity {
    /** Called when the activity is first created. */
	protected GameDraw gameDraw;
	protected AttributeSet attributeSet;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XmlPullParser parser = resources.getXml(myResouce);
        attributeSet = new AttributeSet();
        
        gameDraw = new GameDraw(this);
        setContentView(gameDraw);
    }
}