package com.nemate.toolkit;

public class CommandTools {
	
	public String[] getSplit(String input) {
		String i2 = input.replaceAll("\\\\ ", "COMMAND_INTERFACE_TREFETHASAURUS_PACKAGE_SPACE_CHARACTER");
		String delims = "[ ]+";
 		String[] args = i2.split(delims);
 		for (int i = 0; i < args.length; i++) {
 			args[i] = args[i].replaceAll("COMMAND_INTERFACE_TREFETHASAURUS_PACKAGE_SPACE_CHARACTER", " ");
 		}
		return args;
	}
	
	public String[] getArguments(String[] args) {
		String[] returnArgs = new String[0];
		for (int i = 1; i < args.length; i++) {
			if (args[i].indexOf("-") != 0) {
				returnArgs = (String[]) t.expandArray(returnArgs);
				returnArgs[returnArgs.length-1] = args[i];
			}
		}
		return returnArgs;
	}
	
	public String[] getFlags(String[] args) {
		String[] returnArgs = new String[0];
		for (int i = 0; i < args.length; i++) {
			if (args[i].indexOf("-") == 0) {
				returnArgs = (String[]) t.expandArray(returnArgs);
				returnArgs[returnArgs.length-1] = args[i];
			}
		}
		return returnArgs;
	}
	
	public String[] trimFlags(String[] flags) {
		for (int i = 0; i < flags.length; i++) {
			flags[i] = flags[i].substring(1);
		}
		return flags;
	}
	
	public Toolkit t = new Toolkit();
}
