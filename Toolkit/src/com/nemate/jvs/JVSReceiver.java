package com.nemate.jvs;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;


public class JVSReceiver {
	
	public JVSReceiver(int Socketport) {
		port = Socketport;
		functs[0] = new FunctionCd(this);
	}
	
	public JVSReceiver(int Socketport, JVSFunction[] functions) {
		port = Socketport;
		functs = functions;
	}
	
	public void start() {
		 try{
		      server = new ServerSocket(port); 
		    } catch (Exception e) {
			      System.out.println("Could not listen on port " + port);
		    	e.printStackTrace();
		    }

		    try{
		      client = server.accept();
		    } catch (Exception e) {
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
		    try {
		    	loop();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		    
	}
	
	public void loop() {
	    while (true) {
	    	line = getInput();
		    if (line != null) {
		    	String[] args = getArgs(line);
		    	if (args.length > 0 && !runFunctions(args)) {
			    	out.println(exec(line));
		    	}
		    	if (args.length <= 0) {
		    		out.println("Please enter a command");
		    	}
		    } else {
		        restart();
		    }
	    }
	    
	}
	
	public String exec(String command) {
		String message = "";
		try {
			Process p;
			if (directory != "") {
				p = Runtime.getRuntime().exec(command, new String[0], new File(directory));
			} else {
				p = Runtime.getRuntime().exec(command, new String[0], new File("/"));
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String read = null;
			while((read = in.readLine()) != null) {
				message += "%n"+read;
			}
			in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			read = null;
			while((read = in.readLine()) != null) {
				message += "%n"+read;
			}
			message += "%n";
		} catch (Exception e) {
			System.out.println("Command failed");
			message = "Unknown command%n";
		}
		return message;
	}
	
	public void restart() {
		 try{
			 client = server.accept();
   		     in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		     out = new PrintWriter(client.getOutputStream(), true);
			 directory = "";
     	  } catch (Exception e) {
     		 e.printStackTrace();
     	  }
	}

	public String getInput() {
		try {
			return in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String[] getArgs(String input) {
		String delims = "[ ]+";
 		String[] args = input.split(delims);
 		for (int i = 0; i < args.length; i++) {
 			args[i] = args[i].replaceAll("%_", " ");
 			args[i] = args[i].replaceAll("%n", "\n");
 		}
 		return args;
	}
	
	public void formatDir(String added) {
		String dir = directory;
		if (dir != "" && dir != "/") {
			if (added.charAt(added.length()-1) == '/') {
				added = added.substring(0, added.length()-1);
			}
			if (added.charAt(0) != '/') {
				added = "/" + added;
			}
			if (dir.charAt(dir.length()-1) == '/') {
				dir = dir.substring(0, dir.length()-2);
			}
			if (dir.charAt(0) != '/') {
				dir = "/" + dir;
			}
			dir += added;
		} else {
			dir = added;
			if (dir.charAt(dir.length()-1) == '/') {
				dir = dir.substring(0, dir.length()-1);
			}
			if (dir.charAt(0) != '/') {
				dir = "/" + dir;
			}
		}
		
		if (new File(dir).exists())
			directory = dir;
	}
	
	public void backDir() {
		String dir = directory;
		int slash = dir.lastIndexOf("/");
		dir = dir.substring(0, slash);
		if (dir.equals(""))
			dir = "/";
		if (new File(dir).exists())
			directory = dir;
	}
	
	public boolean runFunctions(String[] args) {
		for (int i = 0; i < functs.length; i++) {
			if (args[0].equals(functs[i].name)) {
				functs[i].onCall(args);
				out.println(functs[i].completeMsg);
				return true;
			}
		}
		return false;
	}
	
	public void addFunction(JVSFunction funct) {
		functs = expand();
		functs[functs.length-1] = funct;
	}
	
	public JVSFunction[] expand() {
	    int length = Array.getLength(functs);
	    int newLength = length + 1;
	    Object newArray = Array.newInstance(functs.getClass().getComponentType(), newLength);
	    System.arraycopy(functs, 0, newArray, 0, length);
	    return (JVSFunction[])newArray;
	}
	
	ServerSocket server = null;
	Socket client = null;
	BufferedReader in = null;
	PrintWriter out = null;
	String line;
	String directory = "";
	JVSFunction[] functs = new JVSFunction[1];
	int port;

}
