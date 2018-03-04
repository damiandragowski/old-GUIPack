/*
 * SearchThread.java
 *
 * Created on 17 wrzesieñ 2003, 17:53
 */

package GUIPack;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import GUIPack.*;
/**
 * This class is for makeing query for servers from the list
 * @author  Damian Dr¹gowski
 */
public class SearchThread extends Thread implements CliServCommon {

    private MainFrame parent;
    private String fileName;
    int depth;
    Address address;
    /** Creates a new instance of SearchThread 
     *  @params fileName = fileName to query string, parent = parent frame, depth = depth with which have to be searched, addr = address for server where we make query
     *  
     */
    public SearchThread(String fileName, MainFrame parent, int depth, Address addr) 
    {
        this.parent = parent;
        this.fileName = fileName;
        this.depth = depth;
        this.address = addr;
    }
    /**
     * nothing gets and nothing return
     * overrideed from Thread->run()
     */
    public void run() 
    {
        Vector servers = parent.getServers();
        for ( int i = 0; i < servers.size(); i++)
        {
            try
            {
                PrintWriter out = null;
                BufferedReader in = null;
                Address addr = ( Address ) servers.get(i);
                Socket socket = new Socket(addr.getAddress(), addr.getPort());
                // output stream
                out = new PrintWriter(socket.getOutputStream(), true);  
                // input stream
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String cmd = CliServCommon.SEARCH+";"+fileName+";"+depth+";"+address.getAddress()+";"+address.getPort();
                out.println(cmd);
                out.close();
                in.close();
                socket.close();
            }
            catch ( Exception e)
            {
            }
        }
    }
    
}
