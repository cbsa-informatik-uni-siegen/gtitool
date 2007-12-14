/*
 * NewDialogAlphabetForm.java
 *
 * Created on 14. Dezember 2007, 12:26
 */

package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.NewDialogAlphabet;

/**
 *
 * @author  benny
 */
public class NewDialogAlphabetForm extends javax.swing.JPanel {
    
    /** Creates new form NewDialogAlphabetForm */
    public NewDialogAlphabetForm() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Erzeugter Quelltext ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonsPanel = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonPrevious = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        styledAlphabetParserPanel = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();

        setLayout(new java.awt.GridBagLayout());

        buttonsPanel.setLayout(new java.awt.GridBagLayout());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jButtonCancel.setText(bundle.getString("Cancel")); // NOI18N
        jButtonCancel.setLabel(bundle.getString("Cancel")); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        buttonsPanel.add(jButtonCancel, gridBagConstraints);

        jButtonPrevious.setMnemonic('O');
        jButtonPrevious.setLabel("Zur\u00fcck");
        jButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        buttonsPanel.add(jButtonPrevious, gridBagConstraints);

        jButtonNext.setMnemonic('O');
        jButtonNext.setLabel("Fertig");
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNexthandleNextButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        buttonsPanel.add(jButtonNext, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(buttonsPanel, gridBagConstraints);

        styledAlphabetParserPanel.setPreferredSize(new java.awt.Dimension(300, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 16, 16);
        add(styledAlphabetParserPanel, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNexthandleNextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNexthandleNextButtonActionPerformed
        logic.handleFinish();
    }//GEN-LAST:event_jButtonNexthandleNextButtonActionPerformed

    private void jButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousActionPerformed
        logic.handlePrevious();
    }//GEN-LAST:event_jButtonPreviousActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        logic.handleCancel();
    }//GEN-LAST:event_jButtonCancelActionPerformed
    
    
    // Variablendeklaration - nicht modifizieren//GEN-BEGIN:variables
    public javax.swing.JPanel buttonsPanel;
    public javax.swing.JButton jButtonCancel;
    public javax.swing.JButton jButtonNext;
    public javax.swing.JButton jButtonPrevious;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanel;
    // Ende der Variablendeklaration//GEN-END:variables
    
    private NewDialogAlphabet logic;
    
    public void setLogic( NewDialogAlphabet pLogic ){
        this.logic = pLogic;
    }
}
