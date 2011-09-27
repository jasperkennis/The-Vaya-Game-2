package nl.vaya.mgdd.rjp.objects;

public class Player {
	
	protected float _xPos;
	protected float _yPos;
	
	protected int _type;
	protected String _name;
	
	public Player(){
		this._type = 0;
		this._name = "Unknow";
	}
	
	public Player(String name){
		this._type = 0;
		this._name = name;
	}
	
	public Player(String name, int type){
		this._type = type;
		this._name = name;
	}
	
	public void setPlayerPos(float x, float y){
		this._xPos = x;
		this._yPos = y;
	}
	
	public void getImage(){
		
	}
}
