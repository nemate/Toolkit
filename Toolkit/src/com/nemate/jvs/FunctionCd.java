package com.nemate.jvs;
import java.io.File;


public class FunctionCd extends JVSFunction {
	public FunctionCd(JVSReceiver jvs) {
		super(jvs);
		name = "cd";
		completeMsg = "";
	}
	
	
	
	public void onCall(String[] args) {
		if (args.length == 2) {
			if (!args[1].equals("..") && !args[1].equals(".")) {
	    		formatDir(args[1]);
	    	}
	    	else if (args[1].equals("..")) {
	    		if (jvsr.directory.length() > 1)
	    			backDir();
	    	} 
	    	else if (args[1].equals(".")) {
	    		completeMsg = "%dir=" + jvsr.directory;
	    	}
		} else {
			completeMsg = "cd: : No such file or directory";
		}
	}
	
	public void formatDir(String added) {
		String dir = jvsr.directory;
		if (added.equals("/")) {
			dir = "/";
		} else {
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
		}
		
		if (new File(dir).exists()) {
			jvsr.directory = dir;
			completeMsg = "%dir=" + jvsr.directory;
		} else {
			completeMsg = "cd: "+added+": No such file or directory";
		}
	}
	
	public void backDir() {
		String dir = jvsr.directory;
		int slash = dir.lastIndexOf("/");
		dir = dir.substring(0, slash);
		if (dir.equals(""))
			dir = "/";
		if (new File(dir).exists()) {
			jvsr.directory = dir;
			completeMsg = "%dir=" + jvsr.directory;
		} else {
			completeMsg = "cd: No such file or directory";
		}
	}

}
