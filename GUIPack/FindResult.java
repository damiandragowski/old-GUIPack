/*
 * FindResult.java
 *
 * Created on 17 wrzesieñ 2003, 13:01
 */

package GUIPack;

import java.beans.*;
import java.util.*;

/**
 * bean for find results
 * @author  Damian Dragowski
 */
public class FindResult extends Object implements java.io.Serializable {
    
    private static final String PROP_SAMPLE_PROPERTY = "SampleProperty";
    
    private PropertyChangeSupport propertySupport;
    
    /** Holds value of property serverAddress. */
    private Address serverAddress;
    
    /** Holds value of property fileName. */
    private String fileName;
    
    /** Creates new FindResult */
    public FindResult() {
        propertySupport = new PropertyChangeSupport( this );
    }
    
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
    /** Getter for property addr.
     * @return Value of property addr.
     *
     */
    public Address getServerAddress() {
        return this.serverAddress;
    }
    
    /** Setter for property addr.
     * @param addr New value of property addr.
     *
     */
    public void setServerAddress(Address serverAddress) {
        this.serverAddress = serverAddress;
    }
    
    /** Getter for property fileName.
     * @return Value of property fileName.
     *
     */
    public String getFileName() {
        return this.fileName;
    }
    
    public String getFile()
    {
        String result = "temp";
        StringTokenizer tokens = new StringTokenizer(fileName, "\\");
        int len = tokens.countTokens();
        for ( int i = 0; i < len; i++)
            result = tokens.nextToken();
        return result;
    }
    
    /** Setter for property fileName.
     * @param fileName New value of property fileName.
     *
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}
