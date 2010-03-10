/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChooseNextActionDialogForm.java
 *
 * Created on 10.03.2010, 14:31:01
 */

package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.ChooseNextActionDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;

/**
 *
 * @author christian
 */
public class ChooseNextActionDialogForm extends javax.swing.JDialog implements GUIClass <ChooseNextActionDialog> {

    /** Creates new form ChooseNextActionDialogForm */
    public ChooseNextActionDialogForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jGTILabel1 = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIPanel1 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jGTIListActionList = new de.unisiegen.gtitool.ui.swing.JGTIList();
        jGTIButtonOk = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        setTitle(bundle.getString("ChooseNextActionDialog.Title")); // NOI18N
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jGTILabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabel1.setText(bundle.getString("ChooseNextActionDialog.Header")); // NOI18N
        jGTILabel1.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jGTILabel1, gridBagConstraints);

        jScrollPane1.setViewportView(jGTIListActionList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanel1.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jGTIPanel1, gridBagConstraints);

        jGTIButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("ChooseNextActionDialog.OkMnemonic").charAt(0));
        jGTIButtonOk.setText(bundle.getString("ChooseNextActionDialog.Ok")); // NOI18N
        jGTIButtonOk.setToolTipText(bundle.getString("ChooseNextActionDialog.OkToolTip")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jGTIButtonOk, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("ChooseNextActionDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("ChooseNextActionDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("ChooseNextActionDialog.CancelToolTip")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jGTIButtonCancel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChooseNextActionDialogForm dialog = new ChooseNextActionDialogForm(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonOk;
    private de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabel1;
    public de.unisiegen.gtitool.ui.swing.JGTIList jGTIListActionList;
    private de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private ChooseNextActionDialog logic;

    public ChooseNextActionDialog getLogic() {
        return this.logic;
    }

}
