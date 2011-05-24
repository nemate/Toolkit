package com.nemate.jvs;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;


public class FunctionRead extends JVSFunction {

	public FunctionRead(JVSReceiver jvs) {
		super(jvs);
		name = "read";
		completeMsg = "reader";
	}
	
	public void onCall(String[] args) {
		if (args.length > 1) {
			int count = lineCount(args[1]);
			for (int i = 1; i <= count; i++) {
				msg += "%n" + readLine(i, args[1]);
			}
			completeMsg = msg;
		} else {
			completeMsg = "command incorrect";
		}
	}
	
	public int lineCount(String path){

		int linecount = 0;
		try {

			BufferedReader in = new BufferedReader(new FileReader(path));
			LineNumberReader ln = new LineNumberReader(in);
			  
		      int count = 0;
		  
		        while (ln.readLine() != null){
		          count++;
		        }
		        linecount = count;
		} catch (IOException e){
	    	 e.printStackTrace();
		}
		return linecount;
	
	}
	
	public String readLine(int linenum, String path){
		String lineStr = "End";

		try {
		BufferedReader in = new BufferedReader(new FileReader(path));

		        for (int i = 1; i <= linenum; i++)
		
		        {
		        	lineStr = null;
		        lineStr = in.readLine();
		
		
		        }
		        in.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return lineStr;
	}
	
	String line;
	String msg = "";

}
