package com.nemate.jvs;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class FunctionWrite extends JVSFunction{

	public FunctionWrite(JVSReceiver jvs) {
		super(jvs);
		name = "write";
		completeMsg = "written";
	}
	
	public void onCall(String[] args) {
		if (args.length > 2) {
			try {
			    Writer out = new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8");
			    try {
			      out.write(args[2]);
			    }
			    finally {
			      out.close();
					completeMsg = "File Written";
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			completeMsg = "please enter a path and a string to write to it";
		}
	}

}
