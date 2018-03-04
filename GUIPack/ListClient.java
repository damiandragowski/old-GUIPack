/*
 * ListClient.java
 *
 * Created on 17 wrzesieñ 2003, 00:05
 */

package GUIPack;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import GUIPack.*;

/** this class,
 * make aswer for queries
 * @author  Damian Dr¹gowski
 */
public class ListClient extends Thread implements CliServCommon {
    
    /** Creates a new instance of ListClient */
    private Socket socket;
    int port;
    MainFrame parent;    
    GUIPack.Address addr;
    
    public ListClient(MainFrame pp, GUIPack.Address address) {
        parent = pp;
        addr = address;
    }
    
    public void run() 
    {
        PrintWriter out = null;    // strumien wyjsciowy - dane wysylane na server 
        BufferedReader in = null;  // strumien wejsciowy - dane czytane z servera 
        String fromServer, fromUser;
        try {
            // zainicjowanie gniazdka 
            socket = new Socket(addr.getAddress() , addr.getPort());
            // pobranie strumienia wyjsciowego serwera
            out = new PrintWriter(socket.getOutputStream(), true);  
            // pobranie strumienia wejsciowego serwera
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            fromServer = in.readLine();
            out.println(CliServCommon.LIST);
            fromServer = in.readLine();
            StringTokenizer tokens = new StringTokenizer(fromServer, " ");
            if ( tokens.countTokens() == 2 )
            {
                String temp = tokens.nextToken();
                if ( temp.compareTo(CliServCommon.FOUND) == 0 )
                {
                    try
                    {
                        temp = tokens.nextToken();
                        int len = Integer.parseInt(temp);
                        Vector vect = parent.getResultData();
                        while ((fromServer = in.readLine()) != null) // petla odczytu z giazda
                        {
                            FindResult findResult = new FindResult();
                            findResult.setFileName(fromServer);
                            findResult.setServerAddress(addr);
                            vect.addElement((Object) findResult); 
                        }
                        parent.setRefresList();
                    }
                    catch( NumberFormatException e )
                    {
                        parent.getLog().append("Serever spowodowa³ b³¹d" + addr.getAddress());                        
                    }
                }
            }
            out.close();      // zamkniecie otwartych strumieni i gniazdka 
            in.close();
            socket.close();
        }
        catch (UnknownHostException e) {
            parent.getLog().append("Nieznany adres hosta" + addr.getAddress());
        } 
        catch (IOException e) {
            parent.getLog().append("Blad operacji Wejscia/Wyjscia - I/O error");
        }
    }
}
