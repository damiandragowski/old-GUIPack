/*
 * DownloadClient.java
 *
 * Created on 17 wrzesieñ 2003, 13:25
 */

package GUIPack;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import GUIPack.*;

/**
 * class which mane work is to get downloaded file
 * @author  Damian Dr¹gowski
 */
public class DownloadClient extends Thread implements CliServCommon {
    
    /** Creates a new instance of DownloadClient */
    FindResult findResult;
    MainFrame parent;
    Socket    socket;
    String Directory;
    FileOutputStream file;
    int filesize;
    String filename;
    
    /* close file
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
            parent.getLog().append("Blad przy zamykaniu pliku!");            
        }
    }
    
    /* open file
     * @params file name
     */
    private boolean openFile(String filename) 
    {
        try 
        {
            file = new FileOutputStream(filename);
        }
        catch (FileNotFoundException ex) 
        { 
            parent.getLog().append("Nieznaleziono pliku!");
            return false;
        }
        catch (IOException ex) 
        { 
            parent.getLog().append("Blad odczytu!");
            return false; 
        }
        return true;  
    }
    
    /* constructor for the class
     */
    public DownloadClient(MainFrame parent, FindResult findResult) 
    {
        this.findResult = findResult;
        this.parent = parent;
    }
    
    /* Main thread runing method
     */
    public void run() 
    {
        PrintWriter out = null;    // strumien wyjsciowy - dane wysylane na server 
        BufferedReader in = null;  // strumien wejsciowy - dane czytane z servera 
        String fromServer, fromUser;
        try {
            // zainicjowanie gniazdka 
            socket = new Socket(findResult.getServerAddress().getAddress() , findResult.getServerAddress().getPort());
            // pobranie strumienia wyjsciowego serwera
            out = new PrintWriter(socket.getOutputStream(), true);  
            // pobranie strumienia wejsciowego serwera
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            InputStream input = socket.getInputStream();
            fromServer = in.readLine();
            out.println(CliServCommon.DOWNLOAD+";"+findResult.getFileName());
            fromServer = in.readLine();
            StringTokenizer tokens = new StringTokenizer(fromServer, " ");
            fromServer = tokens.nextToken();
            if ( fromServer.compareTo(CliServCommon.BEGIN) == 0 )
            {
                try
                {
                    filesize = Integer.parseInt(tokens.nextToken());
                    parent.getLog().append("œci¹gnie pliku " + findResult.getFileName() + " rozpoczête\n"); 
                    DefaultListModel model = parent.getDownloadList();
                    model.addElement(findResult.getFileName());
                    Directory = parent.confDialog.getDirectory();
                    if ( openFile(Directory+"\\"+findResult.getFile()) )
                    {
                        int chunks = (int)(filesize/8192); // ilosc wysylanych kawalkow 8k
                        for (int i = 0 ; i <= chunks; i++) 
                        {
                            byte[] buffer;   // bufor do wylania - maksymalny rozmiar kawalka 8192
                            if (filesize < 8192) 
                                buffer = new byte[filesize-i*8192]; 
                            else
                                if (filesize-i*8192 >= 8192) 
                                    buffer = new byte[8192]; 
                                else 
                                    buffer = new byte[filesize-i*8192];
                            input.read(buffer);
                            file.write(buffer);  // zapis do pliku
                        }
                        parent.getLog().append("œci¹gnie pliku " + findResult.getFileName() + " zakoñczone\n");
                        closeFile();
                    }
                    else
                        parent.getLog().append("nie mozna zapisac pliku lokalnie\n");                    
                }
                catch( NumberFormatException e)
                {
                    parent.getLog().append("Z³y rozmiar pliku\n");                        
                }
            }
            else if ( fromServer.compareTo(CliServCommon.ERROR) == 0 )
            {
                parent.getLog().append("host " + findResult.getServerAddress().getAddress() + " nie posiada pliku\n");                
            }
            else
            {
                parent.getLog().append("host " + findResult.getServerAddress().getAddress() + " wywo³a³ nieznany b³¹d\n");                                
            }
            

            out.close();      // zamkniecie otwartych strumieni i gniazdka 
            in.close();
            socket.close();
        }
        catch (UnknownHostException e) {
            parent.getLog().append("Nieznany adres hosta" + findResult.getServerAddress().getAddress() +"\n");
        } 
        catch(SocketException e) { 
            parent.getLog().append("Jakis blad - SocketEx\n");            
        }        
        catch (IOException e) {
            parent.getLog().append("Blad operacji Wejscia/Wyjscia - I/O error\n");
        }
    }
}
