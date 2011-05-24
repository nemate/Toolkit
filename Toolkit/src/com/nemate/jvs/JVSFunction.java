package com.nemate.jvs;

public class JVSFunction {
	public JVSFunction(JVSReceiver jvs, String functName, String msg) {
		name = functName;
		completeMsg = msg;
		jvsr = jvs;
	}
	
	public JVSFunction(JVSReceiver jvs) {
		jvsr = jvs;
	}
	
	public void onCall(String[] args) {
		//Do something
	}
	
	String name;
	String completeMsg;
	JVSReceiver jvsr;
}
