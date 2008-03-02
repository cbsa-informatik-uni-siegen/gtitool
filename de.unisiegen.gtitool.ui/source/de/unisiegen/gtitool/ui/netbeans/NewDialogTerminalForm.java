package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.NewDialogTerminal;

/**
 * The new dialog alphabet form.
 *
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id: NewDialogAlphabetForm.java 563 2008-02-14 22:24:33Z fehler $
 */
@SuppressWarnings({ "all" })
public class NewDialogTerminalForm extends javax.swing.JPanel {
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -8070356231677722624L;

    /** Creates new form NewDialogAlphabetForm */
    public NewDialogTerminalForm() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        terminalPanelForm = new de.unisiegen.gtitool.ui.netbeans.TerminalPanelForm();
        jPanelButtons = new javax.swing.JPanel();
        jGTIButtonPrevious = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonFinished = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(terminalPanelForm, gridBagConstraints);

        jPanelButtons.setLayout(new java.awt.GridBagLayout());

        jGTIButtonPrevious.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("NewDialog.PreviousMnemonic").charAt(0));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jGTIButtonPrevious.setText(bundle.getString("NewDialog.Previous")); // NOI18N
        jGTIButtonPrevious.setToolTipText(bundle.getString("NewDialog.PreviousToolTip")); // NOI18N
        jGTIButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonPreviousActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanelButtons.add(jGTIButtonPrevious, gridBagConstraints);

        jGTIButtonFinished.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("NewDialog.FinishedMnemonic").charAt(0));
        jGTIButtonFinished.setText(bundle.getString("NewDialog.Finished")); // NOI18N
        jGTIButtonFinished.setToolTipText(bundle.getString("NewDialog.FinishedToolTip")); // NOI18N
        jGTIButtonFinished.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonFinishedActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanelButtons.add(jGTIButtonFinished, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("NewDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("NewDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("NewDialog.CancelToolTip")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanelButtons.add(jGTIButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        add(jPanelButtons, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
        logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void jGTIButtonFinishedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonFinishedActionPerformed
        logic.handleFinish();
    }//GEN-LAST:event_jGTIButtonFinishedActionPerformed

    private void jGTIButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonPreviousActionPerformed
        logic.handlePrevious();
    }//GEN-LAST:event_jGTIButtonPreviousActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonFinished;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonPrevious;
    public javax.swing.JPanel jPanelButtons;
    public de.unisiegen.gtitool.ui.netbeans.TerminalPanelForm terminalPanelForm;
    // End of variables declaration//GEN-END:variables
    
    private NewDialogTerminal logic;
    
    public void setLogic( NewDialogTerminal pLogic ){
        this.logic = pLogic;
    }
}
