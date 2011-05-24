package com.nemate.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FTPClient {
	
	public FTPClient(String i, String u, String p) {
		ip = i;
		username = u;
		password = p;
	}
	
	public boolean connect() {
		StringBuffer sb = new StringBuffer( "ftp://" );
        // check for authentication else assume its anonymous access.
        if (username != null && password != null)
        {
           sb.append( username );
           sb.append( ':' );
           sb.append( password );
           sb.append( '@' );
        }
        sb.append( ip );
        sb.append( '/' );
        conn = sb.toString();
        /*
         * type ==> a=ASCII mode, i=image (binary) mode, d= file directory
         * listing
         */
        try
        {
           URL url = new URL(conn + "test.test" + ";type=i");
           URLConnection urlc = url.openConnection();
           
           return urlc.getInputStream() != null;
        }
        catch (FileNotFoundException e) {
       	 	return true;
        }
        catch (MalformedURLException e) {
       	 e.printStackTrace();
       	 return false;
        }
        catch (IOException e) {
       	 return false;
        }
     
	}
	
	public boolean download(String sFile, String lFile) {
		
		 BufferedInputStream bis = null;
         BufferedOutputStream bos = null;
         try
         {
            URL url = new URL(conn + sFile + ";type=i");
            URLConnection urlc = url.openConnection();

            bis = new BufferedInputStream( urlc.getInputStream() );
            bos = new BufferedOutputStream( new FileOutputStream(lFile));

            int i;
            while ((i = bis.read()) != -1)
            {
               bos.write( i );
            }
         }
         catch (FileNotFoundException e) {
        	 e.printStackTrace();
        	 return false;
         }
         catch (MalformedURLException e) {
        	 e.printStackTrace();
        	 return false;
         }
         catch (IOException e) {
        	 e.printStackTrace();
        	 return false;
         }
         finally
         {
            if (bis != null)
               try
               {
                  bis.close();
               }
               catch (IOException ioe)
               {
                  ioe.printStackTrace();
               }
            if (bos != null)
               try
               {
                  bos.close();
               }
               catch (IOException ioe)
               {
                  ioe.printStackTrace();
               }
         } 
      
		return true;
	}
	
	public boolean upload(String lFile, String sFile) {
		
		BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try
        {
           URL url = new URL( conn + sFile + ";type=i" );
           URLConnection urlc = url.openConnection();

           bos = new BufferedOutputStream( urlc.getOutputStream() );
           bis = new BufferedInputStream( new FileInputStream( lFile ) );

           int i;
           // read byte by byte until end of stream
           while ((i = bis.read()) != -1)
           {
              bos.write( i );
           }
        }
        catch (FileNotFoundException e) {
       	 e.printStackTrace();
       	 return false;
        }
        catch (MalformedURLException e) {
       	 e.printStackTrace();
       	 return false;
        }
        catch (IOException e) {
       	 e.printStackTrace();
       	 return false;
        }
        finally
        {
           if (bis != null)
              try
              {
                 bis.close();
              }
              catch (IOException ioe)
              {
                 ioe.printStackTrace();
              }
           if (bos != null)
              try
              {
                 bos.close();
              }
              catch (IOException ioe)
              {
                 ioe.printStackTrace();
              }
        }
        
        
		return true;
	}

	public String conn;
	public String ip;
	public String username;
	public String password;
}
