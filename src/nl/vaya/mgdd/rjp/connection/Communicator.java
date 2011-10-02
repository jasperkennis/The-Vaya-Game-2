package nl.vaya.mgdd.rjp.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Communicator implements MessageResponder {
	
	protected String host = "ve.qtssrgkb.vesrv.com";
	protected int port = 1337;
	protected Socket socket;
	protected String log_tag = "game_server";
	protected BufferedReader receiver;
	protected PrintWriter sender;
	protected int linesPerTick = 5;
	
	public Communicator(){
		Log.i(log_tag, "Starting connection.");
		try {
	        socket = new Socket(host,port);
	        Log.i(log_tag, "Connected!");
	        
	        //Create an outgoing stream pointing at the socket:
	        OutputStream out = socket.getOutputStream();
	        sender = new PrintWriter(out);
	        
	        // Create a stream for incomming messages:
	        receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        
	        //	this.recieveMessages(this);
	        
		} catch (UnknownHostException e) {
			Log.i(log_tag, "Connection failed; unknow host.");
	        e.printStackTrace();
		} catch (IOException e) {
			Log.i(log_tag, "Connection failed. See stack for more data.");
	        e.printStackTrace();
		}
	}
	
	public void sendMessage(String message){
		sender.println(message);
	}
	
	public String[] recieveMessages(MessageResponder callack) {
		String[] _return = new String[this.linesPerTick];
		for(int i = 0; i < linesPerTick; i++){
			try {
				String incomming = receiver.readLine();
				if(incomming.length() > 0){
					callack.respond( incomming );
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return _return;
	}
	
	public void setEventListener(MessageResponder response){
		
	}

	/*
	 * This message gets called upon every found response.
	 * @see nl.vaya.mgdd.rjp.connection.MessageResponder#respond(java.lang.String)
	 */
	@Override
	public void respond(String message) {
		Log.i(log_tag, message);
	}
}
