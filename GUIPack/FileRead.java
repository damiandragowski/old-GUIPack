/*
 * FileRead.java
 *
 * Created on 16 wrzesieñ 2003, 18:35
 */

package GUIPack;
import java.io.*;
import java.util.*;


/**
 * class which handle Addresses
 * @author  Damian Dragowski
 */
public class FileRead {
    
    /** Creates a new instance of FileRead */
    BufferedReader input;
    public FileRead(BufferedReader input) {
        this.input = input;
    }
    /**
     * This is method which returns us all servers in vector structure
     * @throws IOException
     */
    public Vector getAddress() throws IOException
    {
        String s = "" ;
        Vector result = new Vector();
        while ( ( s = input.readLine() ) != null )
        {
            StringTokenizer tokens = new StringTokenizer( s, ":");
            int length = tokens.countTokens();
            if ( length == 3 )
            {
                Address addr = new Address();
                addr.setAddress( tokens.nextToken() );
                addr.setName( tokens.nextToken() );
                addr.setPort( Integer.parseInt( tokens.nextToken() ) );
                result.addElement((Object) addr);
            }
        }
        return result;
    }
    
}
