package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.logic.StateConfigDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;

/**
 * The {@link StateConfigDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class StateConfigDialogForm extends JDialog implements GUIClass<StateConfigDialog>
{
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 7000984653787618329L;

    /**
     * The {@link StateConfigDialog}.
     */
    private StateConfigDialog logic ;
    
    /**
     * Allocates a new {@link StateConfigDialogForm}.
     * 
     * @param logic The {@link StateConfigDialog}.
     * @param parent The parent {@link JFrame}.
     */
    public StateConfigDialogForm(StateConfigDialog logic, JFrame parent) {
        super(parent, true);
        this.logic = logic;
        initComponents();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see GUIClass#getLogic()
     */
    public final StateConfigDialog getLogic ()
    {
      return this.logic;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jGTILabelRename = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledStateParserPanel = new de.unisiegen.gtitool.ui.style.StyledStateParserPanel();
        jGTIPanelState = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTICheckBoxStartState = new de.unisiegen.gtitool.ui.swing.JGTICheckBox();
        jGTICheckBoxFinalState = new de.unisiegen.gtitool.ui.swing.JGTICheckBox();
        jGTIPanelButtons = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIButtonOk = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        setTitle(bundle.getString("NewStateNameDialog.RenameTitle")); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jGTILabelRename.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelRename.setText(bundle.getString("NewStateNameDialog.RenameText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jGTILabelRename, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        getContentPane().add(styledStateParserPanel, gridBagConstraints);

        jGTICheckBoxStartState.setText(bundle.getString("NewStateNameDialog.StartState")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelState.add(jGTICheckBoxStartState, gridBagConstraints);

        jGTICheckBoxFinalState.setText(bundle.getString("NewStateNameDialog.FinalState")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jGTIPanelState.add(jGTICheckBoxFinalState, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(jGTIPanelState, gridBagConstraints);

        jGTIButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("NewStateNameDialog.OkMnemonic").charAt(0));
        jGTIButtonOk.setText(bundle.getString("NewStateNameDialog.Ok")); // NOI18N
        jGTIButtonOk.setToolTipText(bundle.getString("NewStateNameDialog.OkToolTip")); // NOI18N
        jGTIButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonOkActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelButtons.add(jGTIButtonOk, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("NewStateNameDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("NewStateNameDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("NewStateNameDialog.CancelToolTip")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCanceljGTIButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
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
        getContentPane().add(jGTIPanelButtons, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-250)/2, 400, 250);
    }// </editor-fold>//GEN-END:initComponents

    private void jGTIButtonCanceljGTIButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCanceljGTIButton1ActionPerformed
      this.logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCanceljGTIButton1ActionPerformed

    private void jGTIButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonOkActionPerformed
      this.logic.handleOk();
    }//GEN-LAST:event_jGTIButtonOkActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonOk;
    public de.unisiegen.gtitool.ui.swing.JGTICheckBox jGTICheckBoxFinalState;
    public de.unisiegen.gtitool.ui.swing.JGTICheckBox jGTICheckBoxStartState;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelRename;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelButtons;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelState;
    public de.unisiegen.gtitool.ui.style.StyledStateParserPanel styledStateParserPanel;
    // End of variables declaration//GEN-END:variables
    
}
