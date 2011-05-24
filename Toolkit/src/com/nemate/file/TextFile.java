package com.nemate.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

public class TextFile {
	
	public TextFile(String p) {
		path = p;
	}
		
	///////////////////////////////////////////////////////////////READ////////////////////////////////////////////////////////////

	
	
	public String read() {
		try {
			FileReader reader = new FileReader(path);
		    BufferedReader in = new BufferedReader(reader);
		    String read = "";
		    String line;
		    while ((line = in.readLine()) != null) {
		    	read += line + "\n";
		    }
		    in.close();
			return read;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String readLine(int line) {
		try {
			FileReader reader = new FileReader(path);
		    BufferedReader in = new BufferedReader(reader);
		    String lineR;
		    int count = 0;
		    while ((lineR = in.readLine()) != null) {
		    	count++;
		    	if (count == line) {
				    in.close();
		    		return lineR;
		    	}
		    }
		    in.close();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int lineCount() {
		try {
			FileReader reader = new FileReader(path);
		    BufferedReader in = new BufferedReader(reader);
		    int count = 0;
		    while (in.readLine() != null) {
		    	count++;
		    }
		    in.close();
			return count;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public TextIndex findFirst(String find) {
		try {
			FileReader reader = new FileReader(path);
		    BufferedReader in = new BufferedReader(reader);
		    String lineR;
		    int count = 0;
		    while ((lineR = in.readLine()) != null) {
		    	count++;
		    	if (lineR.contains(find)) {
				    in.close();
		    		return new TextIndex(count, lineR.indexOf(find));
		    	}
		    }
		    in.close();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TextIndex[] find(String find) {
		try {
			FileReader reader = new FileReader(path);
		    BufferedReader in = new BufferedReader(reader);
		    String lineR;
		    TextIndex[] ti = new TextIndex[0];
		    int count = 0;
		    int index = 0;
		    while ((lineR = in.readLine()) != null) {
		    	count++;
		    	if (lineR.contains(find)) {
				    ti = expand(ti);
				    ti[index] = new TextIndex(count, lineR.indexOf(find));
				    index++;
		    	}
		    }
		    in.close();
			return ti;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TextIndex findLast(String find) {
		try {
			FileReader reader = new FileReader(path);
		    BufferedReader in = new BufferedReader(reader);
		    String lineR;
		    TextIndex ti = null;
		    int count = 0;
		    while ((lineR = in.readLine()) != null) {
		    	count++;
		    	if (lineR.contains(find)) {
		    		ti = new TextIndex(count, lineR.indexOf(find));
		    	}
		    }
		    in.close();
			return ti;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	///////////////////////////////////////////////////////////////WRITE///////////////////////////////////////////////////////////
	
	
	public boolean write(String content) {
		try {
			FileWriter fstream = new FileWriter(path);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(content);
		    out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean replaceAll(String old, String rep) {
		try {
			String read = read();
			read = read.replaceAll(old, rep);
			FileWriter fstream = new FileWriter(path);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(read);
		    out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addLine(String line, int lineNum) {
		try {
			FileReader reader = new FileReader(path);
		    BufferedReader in = new BufferedReader(reader);
		    String read = "";
		    String lineR;
		    int count = 0;
		    while ((lineR = in.readLine()) != null) {
		    	count++;
		    	read += lineR + "\n";
		    	if (count == lineNum)
		    		read += line + "\n";
		    }
		    in.close();
			
			FileWriter fstream = new FileWriter(path);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(read);
		    out.close();
		    
		    return true;
		    
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean newLine(String content) {
		return addLine(content, lineCount());
	}
	
	public boolean newTopLine(String content) {
	    
	    try {
			FileReader reader = new FileReader(path);
		    BufferedReader in = new BufferedReader(reader);
		    String read = content + "\n";
		    String line;
		    while ((line = in.readLine()) != null) {
		    	read += line + "\n";
		    }
		    in.close();
			
			FileWriter fstream = new FileWriter(path);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(read);
		    out.close();
		    
		    return true;
		    
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean replaceLine(int line, String content) {
		try {
			FileReader reader = new FileReader(path);
		    BufferedReader in = new BufferedReader(reader);
		    String read = "";
		    String lineR;
		    int count = 0;
		    while ((lineR = in.readLine()) != null) {
		    	count++;
		    	if (count == line)
		    		read += content + "\n";
		    	else
			    	read += lineR + "\n";
		    }
		    in.close();
			
			FileWriter fstream = new FileWriter(path);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(read);
		    out.close();
		    
		    return true;
		    
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteLine(int line) {
		try {
			FileReader reader = new FileReader(path);
		    BufferedReader in = new BufferedReader(reader);
		    String read = "";
		    String lineR;
		    int count = 0;
		    while ((lineR = in.readLine()) != null) {
		    	count++;
		    	if (count != line)
			    	read += lineR + "\n";
		    }
		    in.close();
			
			FileWriter fstream = new FileWriter(path);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(read);
		    out.close();
		    
		    return true;
		    
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private TextIndex[] expand(TextIndex[] ti) {
	    int length = Array.getLength(ti);
	    int newLength = length + 1; // 50% more
	    Object newArray = Array.newInstance(ti.getClass().getComponentType(), newLength);
	    System.arraycopy(ti, 0, newArray, 0, length);
	    return (TextIndex[])newArray;
	}
	
	public String path;
}
