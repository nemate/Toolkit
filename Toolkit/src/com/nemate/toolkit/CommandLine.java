package com.nemate.toolkit;

import java.io.PrintStream;
import java.util.Scanner;

public class CommandLine {
	
	public CommandLine(CommandLineInterface c) {
		out = System.out;
		inPrefix = ">";
		outPrefix = "~";
		cli = c;
	}
	
	public CommandLine(String inP, String outP, PrintStream o, CommandLineInterface c) {
		out = o;
		inPrefix = inP;
		outPrefix = outP;
		cli = c;
	}
	
	public void start() {
		on = true;
		Scanner s = new Scanner(System.in);
		String line;
		while (on) {
			out.print(inPrefix + " ");
			line = s.nextLine();
			out.print(outPrefix + " ");
			out.println(send(line));
		}
	}
	
	public String send(String line) {
		CommandTools tool = new CommandTools();
		return cli.onCall(tool.getSplit(line)[0], tool.getArguments(tool.getSplit(line)), tool.getFlags(tool.getSplit(line)));
	}

	public void stop() {
		on = false;
	}
	
	public String onCall(String inv, String[] args, String[] flags) {
		return null;
	}
	
	public boolean on = false;
	public PrintStream out;
	public String inPrefix = "";
	public String outPrefix = "";
	public CommandLineInterface cli;

}
