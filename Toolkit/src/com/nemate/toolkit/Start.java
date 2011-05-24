package com.nemate.toolkit;

import com.nemate.file.TextFile;

public class Start {
	
	public static void main(String[] args) {
		Toolkit t = new Toolkit();
		TextFile tf = new TextFile("/Users/hardcorebadger/.ssh/id_rsa.pub");
		t.p(tf.read());
	}

}
