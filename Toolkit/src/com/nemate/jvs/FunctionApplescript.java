package com.nemate.jvs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class FunctionApplescript extends JVSFunction{

	public FunctionApplescript(JVSReceiver jvs) {
		super(jvs);
		name = "applescript";
		completeMsg = "Applescript executed";
	}
	
	public void onCall(String[] args) {
		if (args.length > 1) {
			try {
				String[] cmd = { "osascript", "-e",	args[1] };
				Process p = Runtime.getRuntime().exec(cmd);
				BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String read = null;
				String msg = "";
				while((read = in.readLine()) != null) {
					msg += "%n"+read;
				}
				completeMsg = msg;
			} catch (IOException e) {
				e.printStackTrace();
				completeMsg = "Actionscript was not executed";
			}
		} else {
			completeMsg = "type some actionscript";
		}
	}

}
