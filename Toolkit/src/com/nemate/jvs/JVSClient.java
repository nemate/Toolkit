package com.nemate.jvs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class JVSClient {
	public JVSClient(String hostName, int socketPort) {
		host = hostName;
		port = socketPort;
	}
	
	public boolean connect(){
		try{
		    socket = new Socket(host, port);
		    out = new PrintWriter(socket.getOutputStream(), true);
		    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    return true;
		} catch (UnknownHostException e) {
		    e.printStackTrace();
		    return false;
		} catch  (IOException e) {
		    e.printStackTrace();
		    return false;
		}
	}
	
	public String exec(String command) {
		String response = "";
	    out.println(command);
	    try{
	 		response = decodeLines(in.readLine());
	 		if (response.contains("%dir=")) {
	 			directory = response.substring(6);
	 			return "";
	 		} else {
	 			return response;
	 		}
	 	 } catch (IOException e){
	 		 e.printStackTrace();
	 	 }
		   
		return response;
	}
	
	public String encodeLines(String msg) {
		String delims = "[\n]+";
 		String[] split = msg.split(delims);	 	          
 		if (split.length < 1){
	 	    return msg;
	 	} else {
	 		for (int i = 0; i < split.length; i++) {
	 			if (!split[i].equals("")) {
	 				msg += "%n" + split[i];
	 			}
	 		}
	 		return msg;
	 	}	
	}
	
	public String decodeLines(String msg) {
		String delims = "[%]+[n]+";
 		String[] split = msg.split(delims);	 	          
 		if (split.length < 1){
	 	    return msg;
	 	} else {
	 		msg = "";
	 		for (int i = 0; i < split.length; i++) {
	 			if (!split[i].equals("")) {
	 				msg += "\n" + split[i];
	 			}
	 		}
	 		return msg;
	 	}	
	}
	
	String host = "";
	String directory = "/";
	int port = 0000;
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
}
