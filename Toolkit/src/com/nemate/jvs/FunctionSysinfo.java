package com.nemate.jvs;

public class FunctionSysinfo extends JVSFunction{

	public FunctionSysinfo(JVSReceiver jvs) {
		super(jvs);
		name = "sysinfo";
		completeMsg = "Unable to read system info";
	}
	
	public void onCall(String[] args) {
		if (args.length == 1) {
			completeMsg = "OS: " + System.getProperty("os.name");
		}
	}

}
