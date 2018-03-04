/*
 * CliServCommon.java
 *
 * Created on 16 wrzesieñ 2003, 19:03
 */

package GUIPack;

/**
 * interface for common things
 * @author  Damian Dr¹gowski
 */
public interface CliServCommon {
    
    /* deafult port number
     */
    int portNum = 4422;
    
    /* commands for server and client
     */
    static final String SEARCH = "SEARCH";
    static final String LIST = "LIST";
    static final String FOUND = "FOUND";
    static final String DOWNLOAD = "DOWNLOAD";
    static final String CHAT = "CHAT";
    static final String ERROR = "ERROR";
    static final String BEGIN = "BEGIN";
    static final String RESPONSE = "RESPOSNE";
}
