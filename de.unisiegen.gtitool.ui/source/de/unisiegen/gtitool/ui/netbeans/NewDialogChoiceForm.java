package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.NewDialogChoice;

/**
 * The new dialog choice form.
 *
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class NewDialogChoiceForm extends javax.swing.JPanel {
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -4830869368935186916L;

    /** Creates new form NewDialogChoiceForm */
    public NewDialogChoiceForm() {
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

        buttonGroupChoice = new javax.swing.ButtonGroup();
        jGTILabelCaption = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIPanelChoice = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIRadioButtonMachine = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIRadioButtonGrammar = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIPanelColumn0 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelButtons = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIButtonPrevious = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonNext = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setLayout(new java.awt.GridBagLayout());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jGTILabelCaption.setText(bundle.getString("NewDialog.ChoiceTitle")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        add(jGTILabelCaption, gridBagConstraints);

        buttonGroupChoice.add(jGTIRadioButtonMachine);
        jGTIRadioButtonMachine.setSelected(true);
        jGTIRadioButtonMachine.setText(bundle.getString("NewDialog.Machine")); // NOI18N
        jGTIRadioButtonMachine.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIRadioButtonMachineItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jGTIPanelChoice.add(jGTIRadioButtonMachine, gridBagConstraints);

        buttonGroupChoice.add(jGTIRadioButtonGrammar);
        jGTIRadioButtonGrammar.setText(bundle.getString("NewDialog.Grammar")); // NOI18N
        jGTIRadioButtonGrammar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIRadioButtonGrammarItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jGTIPanelChoice.add(jGTIRadioButtonGrammar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jGTIPanelChoice, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        add(jGTIPanelColumn0, gridBagConstraints);

        jGTIButtonPrevious.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("NewDialog.PreviousMnemonic").charAt(0));
        jGTIButtonPrevious.setText(bundle.getString("NewDialog.Previous")); // NOI18N
        jGTIButtonPrevious.setToolTipText(bundle.getString("NewDialog.PreviousToolTip")); // NOI18N
        jGTIButtonPrevious.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelButtons.add(jGTIButtonPrevious, gridBagConstraints);

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
        jGTIPanelButtons.add(jGTIButtonNext, gridBagConstraints);

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
        jGTIPanelButtons.add(jGTIButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        add(jGTIPanelButtons, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void jGTIRadioButtonGrammarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIRadioButtonGrammarItemStateChanged
        logic.handleGrammarItemStateChanged( evt ) ;
    }//GEN-LAST:event_jGTIRadioButtonGrammarItemStateChanged

    private void jGTIRadioButtonMachineItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIRadioButtonMachineItemStateChanged
        logic.handleMachineItemStateChanged( evt ) ;
    }//GEN-LAST:event_jGTIRadioButtonMachineItemStateChanged

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
        logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void jGTIButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonNextActionPerformed
        logic.handleNextNewDialogChoice();
    }//GEN-LAST:event_jGTIButtonNextActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.ButtonGroup buttonGroupChoice;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonNext;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonPrevious;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelCaption;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelButtons;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelChoice;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelColumn0;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonGrammar;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonMachine;
    // End of variables declaration//GEN-END:variables
    
    private NewDialogChoice logic;
    
    public void setLogic ( NewDialogChoice logic ){
      this.logic = logic;
    }
}
