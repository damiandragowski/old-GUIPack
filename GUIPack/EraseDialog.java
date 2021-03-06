/*
 * EraseDialog.java
 *
 * Created on 16 wrzesie� 2003, 20:22
 */

package GUIPack;

import java.util.*;
/**
 * Dialog class, erase from list servers which are selected
 * @author  Damian Dr�gowski
 */
public class EraseDialog extends javax.swing.JDialog {
    
    /** Creates new form EraseDialog */
    public EraseDialog(java.awt.Frame parent, boolean modal, Vector sharedFiles) {
        super(parent, modal);
        this.sharedFiles = sharedFiles;        
        initComponents();
        setSize(430, 340);
        jList1.setListData(sharedFiles);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setTitle("Usuniecie dzielonego pliku");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 400, 260);

        jButton1.setText("usun zaznoczony");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton1);
        jButton1.setBounds(20, 270, 160, 26);

        jButton2.setText("Wyjd\u017a");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton2);
        jButton2.setBounds(210, 270, 160, 26);

        pack();
    }//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Add your handling code here:
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Add your handling code here:
        int [] arr = jList1.getSelectedIndices();
        for ( int i = 0; i < arr.length; i++)
        {
            sharedFiles.remove(arr[i]-i);
        }
        jList1.setListData(sharedFiles);
        jScrollPane1.revalidate();
        jScrollPane1.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private Vector sharedFiles;    

}
