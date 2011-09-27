package nl.vaya.mgdd.rjp.layer;

import java.util.ArrayList;

import nl.vaya.mgdd.rjp.objects.Player;
import android.content.Context;
import android.graphics.Canvas;

public class ObjectLayer {

	protected ArrayList<Player> _enemys;
	protected Player _you;
	
	public ObjectLayer(Context context, int winWith, int winHeight){
		_you = new Player("remi", 1);
	}
	
	public void createObjects(Canvas canvas){
		
	}
}
