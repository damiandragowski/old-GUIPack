/*
 * CellRender.java
 *
 * Created on 17 wrzesieñ 2003, 12:57
 */

package GUIPack;

import java.awt.*;
import javax.swing.*;
    /**
    *
    * @author  Damian Dr¹gowski
    * This class is to render cell in jList1 component
    *  is derived from JPanel
    */
class CellRender extends JPanel implements ListCellRenderer {
    public CellRender()
    {
        setLayout(new GridLayout(1, 0));
        label1 = new JLabel();
        label2 = new JLabel();
        label1.setOpaque(true);
        label2.setOpaque(true);
        add(label1);
        add(label2);            
    }
    public Component getListCellRendererComponent(
        JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus)
    {
        FindResult addr = ( FindResult ) value;
        if ( addr != null )
        {
            label1.setText( addr.getFileName() );
            label2.setText( addr.getServerAddress().getAddress() );
            label1.setBackground(isSelected ? Color.red : Color.white);
            label1.setForeground(isSelected ? Color.white : Color.black);
            label2.setBackground(isSelected ? Color.red : Color.white);
            label2.setForeground(isSelected ? Color.white : Color.black);
        }
        return this;
    }
    private JLabel label1, label2;        
}
