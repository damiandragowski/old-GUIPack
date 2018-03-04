/*
 * DownloadThread.java
 *
 * Created on 17 wrzesieñ 2003, 15:49
 */

package GUIPack;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
/**
 * inherits from Thread
 * it sends the file througth network
 * @author  Damian Dr¹gowski
 */
public class DownloadThread extends Thread implements CliServCommon {
    
    /** Creates a new instance of DownloadThread */
    private PrintWriter out;
    private BufferedReader in;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket clientSocket;
    private MainFrame parent;
    private String fileName;
    private FileInputStream file;
    private int filesize;
    
    /**
     * Opens file
     * @params file name
     */
    private boolean openFile(String filename) 
    {
        try 
        {
            file = new FileInputStream(filename);
            filesize = file.available(); // wielkosc pliku
        }
        catch (FileNotFoundException ex)
        { 
            return false;
        }
        catch (IOException ex) 
        { 
            return false; 
        }
        return true;  
    }
    /** close file 
     */
    private void closeFile() 
    {
        try 
        {
            if (file != null)  
                file.close();
        }
        catch (IOException ex) 
        {
        }
    }
    
    /** another consturcotr
     */
    public DownloadThread(MainFrame parent, String fileName, Socket clientSocket, PrintWriter out, BufferedReader in ) throws IOException
    {
        this.in = in ;
        this.out = out;
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        this.parent = parent;
        this.fileName = fileName;
        this.clientSocket = clientSocket;
    }
    /**
     * method overrided from therad.run()
     * @params none
     */
    public void run() 
    {
        PrintWriter out;
        try
        {
            out = new PrintWriter(outputStream, true);
            if ( openFile(fileName) )
            {
                out.println(CliServCommon.BEGIN + " " + filesize);
                int chunks = (int)(filesize/8192);
                for (int i =0 ; i <= chunks; i++) 
                {
                    byte[] buffer;
                    if (filesize-i*8192 >= 8192) 
                        buffer = new byte[8192]; 
                    else 
                        buffer = new byte[filesize-i*8192];
                    int count = file.read(buffer);
                    outputStream.write(buffer);
                }
                if (file != null) closeFile(); // zamkniecie pliku
            }
            out.close();
            inputStream.close();
        }
        catch( Exception e)
        {
        }
    }
    
}
