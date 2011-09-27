package nl.vaya.mgdd.rjp.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Communicator {
	
	protected String host = "0.0.0.0";
	protected int port = 1337;
	protected Socket socket;
	
	public Communicator(){
		Log.i("log_tag", "Starting connection.");
		try {
			Log.i("log_tag", "Connecting");
	        Socket s = new Socket(host,port);
	       
	        //outgoing stream redirect to socket
	        OutputStream out = s.getOutputStream();
	       
	        PrintWriter output = new PrintWriter(out);
	        Log.i("log_tag", "Hello Android!");
	        output.println("Hello Android!");
	        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
	       
	        //read line(s)
	        String st = input.readLine();
	        Log.i("log_tag", st);
	        //Close connection
	        s.close();
	       
		} catch (UnknownHostException e) {
			Log.i("log_tag", "Fail 1");
	        // TODO Auto-generated catch block
	        e.printStackTrace();
		} catch (IOException e) {
			Log.i("log_tag", "Fail 2");
	        // TODO Auto-generated catch block
	        e.printStackTrace();
		}
	}
}
