package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.NewDialogMachineChoice;

/**
 * The new dialog machine choice form.
 *
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class NewDialogMachineChoiceForm extends javax.swing.JPanel {
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -1303990739783698131L;

    /** Creates new form NewDialogMachineChoiceForm */
    public NewDialogMachineChoiceForm() {
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

        buttonGroup = new javax.swing.ButtonGroup();
        jLabelMachinesCaption = new javax.swing.JLabel();
        jPanelMachines = new javax.swing.JPanel();
        jRadioButtonDFA = new javax.swing.JRadioButton();
        jRadioButtonNFA = new javax.swing.JRadioButton();
        jRadioButtonEDFA = new javax.swing.JRadioButton();
        jRadioButtonStackMachine = new javax.swing.JRadioButton();
        jPanelMachinesSpace = new javax.swing.JPanel();
        jPanelButtons = new javax.swing.JPanel();
        jGTIButtonPrevious = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonNext = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setLayout(new java.awt.GridBagLayout());

        jLabelMachinesCaption.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jLabelMachinesCaption.setText(bundle.getString("NewDialog.ChooseMachine")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        add(jLabelMachinesCaption, gridBagConstraints);

        jPanelMachines.setLayout(new java.awt.GridBagLayout());

        buttonGroup.add(jRadioButtonDFA);
        jRadioButtonDFA.setSelected(true);
        jRadioButtonDFA.setText(bundle.getString("NewDialog.DFA")); // NOI18N
        jRadioButtonDFA.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonDFA.setFocusPainted(false);
        jRadioButtonDFA.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButtonDFA.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleDFAItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanelMachines.add(jRadioButtonDFA, gridBagConstraints);

        buttonGroup.add(jRadioButtonNFA);
        jRadioButtonNFA.setText(bundle.getString("NewDialog.NFA")); // NOI18N
        jRadioButtonNFA.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonNFA.setFocusPainted(false);
        jRadioButtonNFA.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButtonNFA.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleNFAItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanelMachines.add(jRadioButtonNFA, gridBagConstraints);

        buttonGroup.add(jRadioButtonEDFA);
        jRadioButtonEDFA.setText(bundle.getString("NewDialog.ENFA")); // NOI18N
        jRadioButtonEDFA.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonEDFA.setFocusPainted(false);
        jRadioButtonEDFA.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButtonEDFA.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleEDFAItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanelMachines.add(jRadioButtonEDFA, gridBagConstraints);

        buttonGroup.add(jRadioButtonStackMachine);
        jRadioButtonStackMachine.setText(bundle.getString("NewDialog.PDA")); // NOI18N
        jRadioButtonStackMachine.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonStackMachine.setFocusPainted(false);
        jRadioButtonStackMachine.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButtonStackMachine.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                handleStackMachineItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanelMachines.add(jRadioButtonStackMachine, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jPanelMachines, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jPanelMachinesSpace, gridBagConstraints);

        jPanelButtons.setLayout(new java.awt.GridBagLayout());

        jGTIButtonPrevious.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("NewDialog.PreviousMnemonic").charAt(0));
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

        jGTIButtonNext.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("NewDialog.NextMnemonic").charAt(0));
        jGTIButtonNext.setText(bundle.getString("NewDialog.Next")); // NOI18N
        jGTIButtonNext.setToolTipText(bundle.getString("NewDialog.NextToolTip")); // NOI18N
        jGTIButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonNextActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanelButtons.add(jGTIButtonNext, gridBagConstraints);

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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        add(jPanelButtons, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
        logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void jGTIButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonNextActionPerformed
        logic.handleNextMachineChoice();
    }//GEN-LAST:event_jGTIButtonNextActionPerformed

    private void jGTIButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonPreviousActionPerformed
        logic.handlePreviousMachineChoice();
    }//GEN-LAST:event_jGTIButtonPreviousActionPerformed

    private void handleStackMachineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleStackMachineItemStateChanged
        logic.handleStackMachineItemStateChanged(evt);
    }//GEN-LAST:event_handleStackMachineItemStateChanged

    private void handleEDFAItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleEDFAItemStateChanged
        logic.handleEDFAItemStateChanged(evt);
    }//GEN-LAST:event_handleEDFAItemStateChanged

    private void handleNFAItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleNFAItemStateChanged
        logic.handleNFAItemStateChanged(evt);
    }//GEN-LAST:event_handleNFAItemStateChanged

    private void handleDFAItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_handleDFAItemStateChanged
        logic.handleDFAItemStateChanged(evt);
    }//GEN-LAST:event_handleDFAItemStateChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.ButtonGroup buttonGroup;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonNext;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonPrevious;
    public javax.swing.JLabel jLabelMachinesCaption;
    public javax.swing.JPanel jPanelButtons;
    public javax.swing.JPanel jPanelMachines;
    public javax.swing.JPanel jPanelMachinesSpace;
    public javax.swing.JRadioButton jRadioButtonDFA;
    public javax.swing.JRadioButton jRadioButtonEDFA;
    public javax.swing.JRadioButton jRadioButtonNFA;
    public javax.swing.JRadioButton jRadioButtonStackMachine;
    // End of variables declaration//GEN-END:variables
    
    private NewDialogMachineChoice logic;
    
    public void setLogic( NewDialogMachineChoice pLogic){
      this.logic = pLogic;
  }
}
