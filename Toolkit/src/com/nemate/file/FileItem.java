package com.nemate.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileItem extends File{
	
	//call method things//

	public FileItem(File parent, String child) {
		super(parent, child);
	}
	
    public FileItem(String string) {
		super(string);
	}
    
    public FileItem(String parent, String child) {
    	super(parent, child);
    }
    
    public FileItem(URI u) {
    	super(u);
    }
    
    
	//methods//

    public boolean create() {
    	try {
    		String path = this.getAbsolutePath();
        	String delims = "[.]+";
     		String[] file = path.split(delims);
     		if (file.length > 1) {
     			return createNewFile();
     		} else {
     			return createNewDirectory();
     		}
    	} catch (IOException e) {
    		e.printStackTrace();
    		return false;
    	}
    }

	public boolean createNewFile() throws IOException {
    	String path = this.getAbsolutePath();
    	String delims = "[/]+";
 		String[] dirs = path.split(delims);
 		String curPath = "";
 		File f;
 		for (int i = 1; i < dirs.length-1; i++) {
 			curPath +=  "/" + dirs[i];
 				f = new File(curPath);
 			if (!f.exists()) {
 				if (!f.mkdir())
 					return false;
 			}
 		}
 		f = new File(path);
 		return f.createNewFile();
     }
	
    public boolean createNewDirectory() throws IOException {
    	return mkdir();
    }

    public boolean move(String path) {
    	return renameTo(new File(path));
    }
    
    public boolean move(File f) {
    	return renameTo(f);
    }

    public boolean copy(String path) {
    	try {
			return copyDirectory(this, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public boolean copySafe(String path) {
    	try {
			return safeCopyDirectory(this, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public boolean copy(File f) {
    	try {
			return copyDirectory(this, f);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public boolean copySafe(File f) {
    	try {
			return safeCopyDirectory(this, f);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }
	
	public boolean delete() {
		if (isDirectory()) {
			return deleteFolder(new File(this.getAbsolutePath()));
		} else {
			File f = new File(this.getAbsolutePath());
			return f.delete();
		}
	}
	
	private boolean deleteFolder(File directory) {

		  if (directory == null)
		    return false;
		  if (!directory.exists())
		    return true;
		  if (!directory.isDirectory())
		    return false;

		  String[] list = directory.list();

		  if (list != null) {
		    for (int i = 0; i < list.length; i++) {
		      File entry = new File(directory, list[i]);

		      if (entry.isDirectory())
		      {
		        if (!deleteFolder(entry))
		          return false;
		      }
		      else
		      {
		        if (!entry.delete())
		          return false;
		      }
		    }
		  }

		  return directory.delete();
	}
	
	public boolean copyDirectory(File srcPath, File dstPath) throws IOException{
		if (srcPath.isDirectory())
		{
			if (!dstPath.exists())
			{
				if (!dstPath.mkdir())
					return false;
			}

			String files[] = srcPath.list();
			for(int i = 0; i < files.length; i++)
			{
				if (!copyDirectory(new File(srcPath, files[i]), new File(dstPath, files[i])))
					return false;
			}
		}
		else
		{
			if(!srcPath.exists())
			{
				return false;
			}
			else
			{
				InputStream in = new FileInputStream(srcPath);
		        OutputStream out = new FileOutputStream(dstPath);
    
				// Transfer bytes from in to out
		        byte[] buf = new byte[1024];
				int len;
		        while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
		        out.close();
			}
		}
		return true;
	}
	
	public boolean safeCopyDirectory(File srcPath, File dstPath) throws IOException{
		if (dstPath.exists()) {
			return false;
		}
		if (srcPath.isDirectory())
		{
			if (!dstPath.exists())
			{
				if (!dstPath.mkdir())
					return false;
			}

			String files[] = srcPath.list();
			for(int i = 0; i < files.length; i++)
			{
				if (!copyDirectory(new File(srcPath, files[i]), new File(dstPath, files[i])))
					return false;
			}
		}
		else
		{
			if(!srcPath.exists())
			{
				return false;
			}
			else
			{
				InputStream in = new FileInputStream(srcPath);
		        OutputStream out = new FileOutputStream(dstPath);
    
				// Transfer bytes from in to out
		        byte[] buf = new byte[1024];
				int len;
		        while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
		        out.close();
			}
		}
		return true;
	}
	
	public boolean zip() {
		try {
			File inFolder=new File(getAbsolutePath());
			File outFolder=new File(getAbsolutePath()+".zip");
			ZipOutputStream out = new ZipOutputStream(new 
			BufferedOutputStream(new FileOutputStream(outFolder)));
			BufferedInputStream in = null;
			byte[] data    = new byte[1024];
			String files[] = inFolder.list();
			for (int i=0; i<files.length; i++) {
				in = new BufferedInputStream(new FileInputStream
				(inFolder.getPath() + "/" + files[i]), 1024);                  
				out.putNextEntry(new ZipEntry(files[i])); 
				int count;
				while((count = in.read(data,0,1024)) != -1)
				{
					out.write(data, 0, count);
				}
				out.closeEntry();
			}
			out.flush();
			out.close();
	      }
	      catch(Exception e) {
	          e.printStackTrace();
	  		return false;
	      } 
		return true;
	}
	
	public boolean unzip() {
		  ZipFile zipFile;
			try {
				zipFile = new ZipFile(getAbsolutePath());

			  File jiniHomeParentDir = new File(getAbsolutePath().substring(0, getAbsolutePath().lastIndexOf("/")));
			  Enumeration<?> files = zipFile.entries();
			    File f = null;
			    FileOutputStream fos = null;
			    
			    while (files.hasMoreElements()) {
			      try {
			        ZipEntry entry = (ZipEntry) files.nextElement();
			        if (!(entry.getName().contains("__MACOSX"))) {
			        InputStream eis = zipFile.getInputStream(entry);
			        byte[] buffer = new byte[1024];
			        int bytesRead = 0;
			  
			        f = new File(jiniHomeParentDir.getAbsolutePath() + File.separator + entry.getName());
			        
			        if (entry.isDirectory()) {
			          f.mkdirs();
			          continue;
			        } else {
			          f.getParentFile().mkdirs();
			          f.createNewFile();
			        }
			        
			        fos = new FileOutputStream(f);
			  
			        while ((bytesRead = eis.read(buffer)) != -1) {
			          fos.write(buffer, 0, bytesRead);
			        }
			        }
			      } catch (IOException e) {
			        e.printStackTrace();
					return false;
			      } finally {
			        if (fos != null) {
			          try {
			            fos.close();
			          } catch (IOException e) {
			            // ignore
			          }
			        }
			      }
			    }
			} catch (IOException e1) {
				e1.printStackTrace();
				return false;
			}
		return true;
	}
	
	//variables//
	
	private static final long serialVersionUID = 1L;

	

}
