package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JPanel;

import de.unisiegen.gtitool.ui.logic.NewDialogGrammarChoice;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;

/**
 * The new dialog choice form.
 *
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings ({ "all" })
public class NewDialogGrammarChoiceForm extends JPanel implements
    GUIClass < NewDialogGrammarChoice >
{

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 5573593212620914155L;

    /**
     * The {@link NewDialogGrammarChoice}.
     */
    private NewDialogGrammarChoice logic;
    
    /**
     * Allocates a new {@link NewDialogGrammarChoiceForm}.
     */
    public NewDialogGrammarChoiceForm(NewDialogGrammarChoice logic) {
        this.logic = logic;
        initComponents();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see GUIClass#getLogic()
     */
    public final NewDialogGrammarChoice getLogic ()
    {
      return this.logic;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupChoice = new javax.swing.ButtonGroup();
        jGTILabelCaption = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIPanelChoice = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIRadioButtonRegularGrammar = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIRadioButtonContextFreeGrammar = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIPanelColumn0 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelButtons = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIButtonPrevious = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonNext = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setLayout(new java.awt.GridBagLayout());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        jGTILabelCaption.setText(bundle.getString("NewDialog.ChooseGrammar")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 5, 16);
        add(jGTILabelCaption, gridBagConstraints);

        buttonGroupChoice.add(jGTIRadioButtonRegularGrammar);
        jGTIRadioButtonRegularGrammar.setSelected(true);
        jGTIRadioButtonRegularGrammar.setText(bundle.getString("NewDialog.RG")); // NOI18N
        jGTIRadioButtonRegularGrammar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIRadioButtonRegularGrammarItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jGTIPanelChoice.add(jGTIRadioButtonRegularGrammar, gridBagConstraints);

        buttonGroupChoice.add(jGTIRadioButtonContextFreeGrammar);
        jGTIRadioButtonContextFreeGrammar.setText(bundle.getString("NewDialog.CFG")); // NOI18N
        jGTIRadioButtonContextFreeGrammar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIRadioButtonContextFreeGrammarItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jGTIPanelChoice.add(jGTIRadioButtonContextFreeGrammar, gridBagConstraints);

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

        jGTIButtonPrevious.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("NewDialog.PreviousMnemonic").charAt(0));
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
        jGTIPanelButtons.add(jGTIButtonPrevious, gridBagConstraints);

        jGTIButtonNext.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("NewDialog.NextMnemonic").charAt(0));
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

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("NewDialog.CancelMnemonic").charAt(0));
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

    private void jGTIRadioButtonContextFreeGrammarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIRadioButtonContextFreeGrammarItemStateChanged
        logic.handleContextFreeItemStateChanged ( evt );
    }//GEN-LAST:event_jGTIRadioButtonContextFreeGrammarItemStateChanged

    private void jGTIRadioButtonRegularGrammarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIRadioButtonRegularGrammarItemStateChanged
        logic.handleRegularGrammarItemStateChanged ( evt );
}//GEN-LAST:event_jGTIRadioButtonRegularGrammarItemStateChanged

    private void jGTIButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonPreviousActionPerformed
        logic.handlePreviousGrammarChoice();
    }//GEN-LAST:event_jGTIButtonPreviousActionPerformed

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
        logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void jGTIButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonNextActionPerformed
        logic.handleNextGrammarChoice();
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
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonContextFreeGrammar;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonRegularGrammar;
    // End of variables declaration//GEN-END:variables
    
}
