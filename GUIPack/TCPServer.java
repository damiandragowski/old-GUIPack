/*
 * UDPServer.java
 *
 * Created on 16 wrzesieñ 2003, 21:10
 */

package GUIPack;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
/**
 *
 * @author  Damian Dr¹gowski
 */
public class TCPServer extends Thread implements CliServCommon {
    
    private ServerSocket socket;
    int port;
    MainFrame parent;
    
    /** Creates a new instance of UDPServer */
    public TCPServer(MainFrame pp) {
        port = portNum;
        parent = pp;
        try
        {
            initServer();
        }
        catch(IOException err) 
        { 
            JOptionPane.showMessageDialog( null, "Blad tworzena servera TCP", "Blad", JOptionPane.ERROR_MESSAGE);                                                          
        }
    }
    /** Creates a new instance of UDPServer specify port
     *  @parmas pp = parent frame, port = port on which theserver will work.
     */    
    public TCPServer(int port, MainFrame pp) {
        this.port = port;
        parent = pp;
        try
        {
            initServer();
        }
        catch(IOException err) 
        { 
            JOptionPane.showMessageDialog( null, "Blad tworzena servera TCP", "Blad", JOptionPane.ERROR_MESSAGE);                                                          
        }
    }
    /**
     * initialize server 
     * @throws IOException
     * @params nothing
     */
    private void initServer() throws IOException
    {
        socket = new ServerSocket(port);
    }
    /**
     *  The method overrided from Thread->run(). Start when Thread.strat(); called
     *  @params none
     *  @return none
     *
     */
    synchronized public void run() 
    {
           Socket clientSocket = null;  // gniazdko klienta        
           PrintWriter out;
           BufferedReader in;
           parent.getLog().append("Serwer uruchomiony na " + port + "\n" );
           Vector threads = new Vector();
           boolean close = false;
           while (true) 
           {
                close = false;
                try {
                    clientSocket = socket.accept(); // wlaczenie nasluchu i ew. nawiazanie polaczenia
                } 
                catch (IOException e) 
                {
                    System.out.println("Blad !!!");
                    continue;
                }
                try
                {
                    // get output stream
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    // get input stream
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out.println("WELCOME: SERVER VER. 1.0");
                    String cmd = in.readLine();
                    if ( cmd.compareTo(CliServCommon.LIST) == 0 )
                    {
                        Vector vect = parent.getFileList();
                        String found = CliServCommon.FOUND + " " + vect.size();
                        out.println(found);
                        for ( int i = 0; i < vect.size(); i++)
                            out.println((String) vect.get(i));
                        close = true;
                    }
                    else if ( cmd.compareTo(CliServCommon.CHAT) == 0 )
                    {
                    }
                    else
                    {
                        StringTokenizer tokens = new StringTokenizer(cmd, ";");
                        int len = tokens.countTokens();
                        cmd = tokens.nextToken();
                        if ( cmd.compareTo(CliServCommon.DOWNLOAD) == 0 )
                        {
                            try
                            {
                                DownloadThread thread = new DownloadThread(parent, tokens.nextToken(), clientSocket, out, in);
                                threads.add((Object)thread);
                                thread.start();
                            }
                            catch ( IOException e )
                            {
                                out.println(CliServCommon.ERROR);
                            }
                        } 
                        else  if ( cmd.compareTo(CliServCommon.SEARCH ) == 0 ) 
                        {
                            try
                            {
                                SearchServer serv = new SearchServer(parent, tokens);
                                serv.start();
                            }
                            catch (NoSuchElementException e)
                            {
                            }
                        }
                        else  if ( cmd.compareTo(CliServCommon.RESPONSE ) == 0 ) 
                        {
                            close = true;
                            try
                            {
                                String Addr = tokens.nextToken();
                                int port = Integer.parseInt(tokens.nextToken());
                                Address addr = new Address();
                                addr.setAddress(Addr);
                                addr.setPort(port);
                                Vector vect = parent.getResultData();
                                String str = "";
                                while ( (str=in.readLine()) != null )
                                {
                                    FindResult res = new FindResult();
                                    res.setServerAddress(addr);
                                    res.setFileName(str);
                                    vect.addElement((Object) res);
                                }
                                parent.setRefresList();
                            }
                            catch ( Exception e)
                            {}
                        }
                        else
                        {
                            out.println(CliServCommon.ERROR);
                        }
                    }
                    if ( close )
                    {
                        clientSocket.close();
                        out.close(); // close all
                        in.close();
                    }
                }
                catch ( Exception e )
                {
                }
         }    
    }           
}
