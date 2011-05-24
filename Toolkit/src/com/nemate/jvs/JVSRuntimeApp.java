package com.nemate.jvs;
import java.util.Scanner;


public class JVSRuntimeApp {
	
	public JVSRuntimeApp(String strHost, int intPort) {
		host = strHost;
		port = intPort;
	}
	
	public void start() {
		JVSClient client = new JVSClient(host, port);
		client.connect();
		Scanner input = new Scanner(System.in);
		while (true) {
			String delims = "[/]+";
	 		String[] ss = client.directory.split(delims);
	 		if (ss.length == 0) {
	 			ss = new String[1];
	 			ss[0] = "";
	 		}
			System.out.print("jvs " + host + ":" + port + " ~" + ss[ss.length-1] + " ");
			String line = input.nextLine();
			if (line.equals("exit")) {
				System.exit(0);
			} else {
				System.out.println(client.exec(line));
			}
		}
	}

	String host;
	int port;

}
