/*
 * SearchServer.java
 *
 * Created on 17 wrzesieñ 2003, 18:46
 */

package GUIPack;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import GUIPack.*;
/**
 *
 * @author  Damian Dr¹gowski
 * Class which inherits from Thread and implements CliServCommon
 * it is run when server have to answer to client
 */
public class SearchServer extends Thread implements CliServCommon {
    
    private String  address;
    private MainFrame parent;
    private String fileName;
    private int depth;
    private int port;
    /** Creates a new instance of SearchServer 
     * @param     parent frame, StringTokenizer tokens with next cmd
     * @return    void
     * @exception NoSuchElementException
     */

    public SearchServer(MainFrame parent, StringTokenizer tokens) throws NoSuchElementException
    {
        this.parent = parent;
        fileName = tokens.nextToken();
        try
        {
            depth = Integer.parseInt(tokens.nextToken());
            depth--;
        }
        catch ( NumberFormatException e)
        {
            depth = 0;
        }
        address = tokens.nextToken();
        try
        {
            port = Integer.parseInt(tokens.nextToken());
        }
        catch ( NumberFormatException e )
        {
            throw new NoSuchElementException();
        }
    }

    /**
     *  Find out if the string one contains string two
     *  @params String one, and String two
     */
    private boolean ispart(String one, String two)
    {
        int len = two.length();
        int length = one.length();
        boolean response = false;
        for ( int i = 0; i < length; i++)
        {
            if ( one.regionMatches(true, i, two, 0, len) )
            {
                response = true;
                break;
            }
        }
        return response;
    }

    /**
     *  This method is overrided from Thread->run(), and implements anserw server
     *  no params needed. it is runing when Thread calls start() method
     *  
     */
    public void run() 
    {
        boolean ispart = false;
        Vector files = parent.getSharedFiles();
        Vector newVect = new Vector();
        for ( int i = 0; i < files.size(); i++)
        {
            String str = (String)files.get(i);
            if ( ispart( str, fileName) )
            {
                ispart = true;
                newVect.addElement((Object) str);
            }
        }
        if ( ispart )
        {
            try
            {
                Socket socket = new Socket(address, port);
                BufferedReader in=null;
                PrintWriter out=null;
                out = new PrintWriter(socket.getOutputStream(), true);
                // pobranie strumienia wejsciowego klienta
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String buffer = in.readLine();
                out.println(CliServCommon.RESPONSE+";"+parent.getIP()+";"+parent.getPort());
                for ( int i = 0; i < newVect.size(); i++)
                {
                    String str = (String)newVect.get(i);
                    out.println(str);
                }
                out.close();
                in.close();
                socket.close();
            }
            catch ( Exception e )
            {
            }
        } // if ( ispart )
        // start threads
        if ( depth > 0 )
        {
            Vector servers = parent.getServers();
            for ( int i = 0; i < servers.size(); i++)
            {
                Address addr = new Address();
                addr.setAddress(address);
                addr.setPort(port);
                SearchThread thread = new SearchThread(fileName, parent, depth, addr);
                thread.start();            
            }
        }
    }// run()
    
}
