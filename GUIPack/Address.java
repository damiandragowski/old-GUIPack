/*
 * Address.java
 *
 * Created on 16 wrzesieñ 2003, 00:46
 */

package GUIPack;

import java.beans.*;
import java.net.*;
import java.io.*;
/**
 * class is very helpfull for puting data into vector
 * @author  Damian Dr¹gowksi
 */
public class Address extends Object implements java.io.Serializable {
    
    private static final String PROP_SAMPLE_PROPERTY = "SampleProperty";
    
    private PropertyChangeSupport propertySupport;
    
    /** Holds value of property address. */
    private String address;
    
    /** Holds value of property port. */
    private int port;
    
    /** Holds value of property name. */
    private String name;
    
    /** Creates new Address */
    public Address() {
        propertySupport = new PropertyChangeSupport( this );
    }
    
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
    /** Getter for property address.
     * @return Value of property address.
     *
     */
    public String getAddress() {
        return address;
    }
    
    /** Setter for property address.
     * @param address New value of property address.
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /** Getter for property port.
     * @return Value of property port.
     *
     */
    public int getPort() {
        return this.port;
    }
    
    /** Setter for property port.
     * @param port New value of property port.
     *
     */
    public void setPort(int port) {
        this.port = port;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     *
     */
    public String getName() {
        return this.name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     *
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * method which finds out is the server is alive ?
     * @return true when is alive ,false in other case
     * @params none
     */
    public final boolean isAlive() {
      try {
            Socket socket = new Socket(address, port);
            socket.close();
            return true;
      }
      catch (UnknownHostException e) {
            return false;
      }
      catch (IOException e) {
            return false;
      }        
    }
    
}
