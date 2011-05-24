package com.nemate.remote;

import com.nemate.jvs.JVSClient;

public class Server {
	
	public Server(String i, int po) {
		ip = i;
		port = po;
		jvsclient = new JVSClient(i, po);
	}
	
	public Server(String i, String u, String p) {
		ip = i;
		username = u;
		password = p;
		ftpclient = new FTPClient(i,u,p);
	}
	
	public Server(String i, String u, String p, int po) {
		ip = i;
		username = u;
		password = p;
		port = po;
		jvsclient = new JVSClient(i, po);
		ftpclient = new FTPClient(i,u,p);
	}
	
	public boolean connect() {
		if (ftpclient == null && jvsclient == null)
			return false;
		if (ftpclient == null && jvsclient != null)
			return connectJVS();
		if (ftpclient != null && jvsclient == null)
			return connectFTP();
		return jvsclient.connect() && ftpclient.connect();
	}
	
	public boolean connectJVS() {
		if (jvsclient == null)
			return false;
		return jvsclient.connect();
	}
	
	public boolean connectFTP() {
		if (ftpclient == null)
			return false;
		return ftpclient.connect();
	}
	
	public String exec(String command) {
		if (jvsclient == null)
			return null;
		return jvsclient.exec(command);
	}
	
	public boolean download(String remoteFile, String localFile) {
		if (ftpclient == null)
			return false;
		return ftpclient.download(remoteFile, localFile);
	}
	
	public boolean upload(String localFile, String remoteFile) {
		if (ftpclient == null)
			return false;
		return ftpclient.upload(localFile, remoteFile);
	}
	
		
	public String ip;
	public String password;
	public String username;
	public int port;
	public JVSClient jvsclient = null;
	public FTPClient ftpclient = null;
}
