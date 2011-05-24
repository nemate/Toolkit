package com.nemate.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.nemate.toolkit.Toolkit;

public class SocketTools {
	
	public SocketTools(String ip, int port) {
		this.ip = ip;
		this.port = port;
		isClient = true;
	}
	
	public SocketTools(int port) {
		this.port = port;
		isClient = false;
	}
	
	public boolean connect() {
		if (isClient) {
			try{
			    socket = new Socket(ip, port);
			    out = new PrintWriter(socket.getOutputStream(), true);
			    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    return true;
			} catch (UnknownHostException e) {
			    e.printStackTrace();
			} catch  (IOException e) {
			    e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean start() {
		if (!isClient) {
			 try{
				 servSocket = new ServerSocket(port); 
			    } catch (Exception e) {
				      System.out.println("Could not listen on port " + port);
			    	e.printStackTrace();
			    }

			    try{
			      socket = servSocket.accept();
			    } catch (Exception e) {
				      System.out.println("Client acception failed on port " + port);
			    	e.printStackTrace();
			    }

			    try{
			      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			      out = new PrintWriter(socket.getOutputStream(), true);
			      return true;
			    } catch (Exception e) {
				      System.out.println("Client acception failed on port " + port);
			    	e.printStackTrace();
			    }
		}
		return false;
	}
	
	public void send(String msg) {
		msg = msg.replaceAll("\n", "COMMAND_INTERFACE_TREFETHASAURUS_PACKAGE_ENTER_CHARACTER");
		msg = msg.replaceAll("\\\\n", "COMMAND_INTERFACE_TREFETHASAURUS_PACKAGE_ENTER_CHARACTER");
		out.println(msg);
	}
	
	
	public String receive() {
		try {
			String input = in.readLine();
			input = input.replaceAll("COMMAND_INTERFACE_TREFETHASAURUS_PACKAGE_ENTER_CHARACTER", "\n");
			return input;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Socket socket;
	public ServerSocket servSocket;
	public String ip;
	public int port;
	public boolean isClient;
	public PrintWriter out = null;
	public BufferedReader in = null;
	public Toolkit t = new Toolkit();
}
