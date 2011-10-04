package nl.vaya.mgdd.rjp.connection;

public class SenderThread extends Thread {
	protected SenderRunnable _runnable;
	public SenderThread(SenderRunnable runnable){
		_runnable = runnable;
	}
	
	public void run(String my_position_json){
		_runnable.run(my_position_json);
	}
}
