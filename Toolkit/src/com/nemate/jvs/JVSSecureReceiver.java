package com.nemate.jvs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;



public class JVSSecureReceiver extends JVSReceiver {

	public JVSSecureReceiver(int Socketport, String pass) {
		super(Socketport);
		password = pass;
	}

	public void start() {
		 try{
		      server = new ServerSocket(port); 
		    } catch (IOException e) {
			      System.out.println("Could not listen on port " + port);
		    	e.printStackTrace();
		    }

		    try{
		      client = server.accept();
		    } catch (IOException e) {
			      System.out.println("Client acception failed on port " + port);
		    	e.printStackTrace();
		    }

		    try{
		      in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		      out = new PrintWriter(client.getOutputStream(), true);
		    } catch (Exception e) {
			      System.out.println("Client acception failed on port " + port);
		    	e.printStackTrace();
		    }
		    
		    while (true) {
		    	if (auth()){
					out.println("Welcome to JVS Secure Connection");
			    	loop();
			    }
		    }
		    
	}
	
	public boolean auth() {
		out.println("password: ");
		try {
			in.readLine();
			String read = in.readLine();
			if (read.equals(password)) {
				return true;
			}
			out.println("password incorrect: " + read + ". Press return to try again.");
			return false;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void restart() {
		 try{
			 client = server.accept();
  		     in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		     out = new PrintWriter(client.getOutputStream(), true);
			 directory = "";
			 while (true) {
			    	if (auth()){
						out.println("Welcome to JVS Secure Connection");
				    	loop();
				    }
			    }
    	  } catch (Exception e) {
    		 e.printStackTrace();
    	  }
	}

	String password = "";

}
