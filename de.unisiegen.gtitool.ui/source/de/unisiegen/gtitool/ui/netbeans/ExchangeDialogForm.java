package de.unisiegen.gtitool.ui.netbeans;

import java.awt.Frame;

import de.unisiegen.gtitool.ui.logic.AboutDialog;
import de.unisiegen.gtitool.ui.logic.ExchangeDialog;

/**
 * The {@link AboutDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class ExchangeDialogForm extends javax.swing.JDialog {
    
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 4187569542433797162L;


  /**
   * The {@link ExchangeDialog}.
   */
  private ExchangeDialog logic;


  /**
   * Creates new form {@link ExchangeDialog}.
   * 
   * @param logic The {@link ExchangeDialog}.
   * @param parent The parent {@link Frame}.
   */
  public ExchangeDialogForm ( ExchangeDialog logic, java.awt.Frame parent )
  {
    super ( parent, true );
    this.logic = logic;
    initComponents ();
  }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupPreferences = new javax.swing.ButtonGroup();
        jScrollPaneStatus = new javax.swing.JScrollPane();
        jTextPaneStatus = new javax.swing.JTextPane();
        jPanelChoice = new javax.swing.JPanel();
        jRadioButtonReceive = new javax.swing.JRadioButton();
        jRadioButtonSend = new javax.swing.JRadioButton();
        jPanelPreferences = new javax.swing.JPanel();
        jLabelPort = new javax.swing.JLabel();
        jGTITextFieldPort = new de.unisiegen.gtitool.ui.swing.JGTITextField();
        jLabelHost = new javax.swing.JLabel();
        jGTITextFieldHost = new de.unisiegen.gtitool.ui.swing.JGTITextField();
        jPanelButtons = new javax.swing.JPanel();
        jGTIButtonExecute = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonClose = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        setTitle(bundle.getString("ExchangeDialog.Title")); // NOI18N
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jScrollPaneStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextPaneStatus.setBorder(null);
        jTextPaneStatus.setEditable(false);
        jTextPaneStatus.setFocusable(false);
        jScrollPaneStatus.setViewportView(jTextPaneStatus);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jScrollPaneStatus, gridBagConstraints);

        jPanelChoice.setLayout(new java.awt.GridBagLayout());

        buttonGroupPreferences.add(jRadioButtonReceive);
        jRadioButtonReceive.setSelected(true);
        jRadioButtonReceive.setText(bundle.getString("ExchangeDialog.Receive")); // NOI18N
        jRadioButtonReceive.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonReceive.setFocusPainted(false);
        jRadioButtonReceive.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonReceiveItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanelChoice.add(jRadioButtonReceive, gridBagConstraints);

        buttonGroupPreferences.add(jRadioButtonSend);
        jRadioButtonSend.setText(bundle.getString("ExchangeDialog.Send")); // NOI18N
        jRadioButtonSend.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonSend.setFocusPainted(false);
        jRadioButtonSend.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonSendItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanelChoice.add(jRadioButtonSend, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 5);
        getContentPane().add(jPanelChoice, gridBagConstraints);

        jPanelPreferences.setLayout(new java.awt.GridBagLayout());

        jLabelPort.setText(bundle.getString("ExchangeDialog.Port")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        jPanelPreferences.add(jLabelPort, gridBagConstraints);

        jGTITextFieldPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jGTITextFieldPortKeyReleased(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        jPanelPreferences.add(jGTITextFieldPort, gridBagConstraints);

        jLabelHost.setText(bundle.getString("ExchangeDialog.Host")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jPanelPreferences.add(jLabelHost, gridBagConstraints);

        jGTITextFieldHost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jGTITextFieldHostKeyReleased(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanelPreferences.add(jGTITextFieldHost, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 16);
        getContentPane().add(jPanelPreferences, gridBagConstraints);

        jPanelButtons.setLayout(new java.awt.GridBagLayout());

        jGTIButtonExecute.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("ExchangeDialog.ExecuteMnemonic").charAt(0));
        jGTIButtonExecute.setText(bundle.getString("ExchangeDialog.Execute")); // NOI18N
        jGTIButtonExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonExecuteActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanelButtons.add(jGTIButtonExecute, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("ExchangeDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("ExchangeDialog.Cancel")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanelButtons.add(jGTIButtonCancel, gridBagConstraints);

        jGTIButtonClose.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("ExchangeDialog.Close").charAt(0));
        jGTIButtonClose.setText(bundle.getString("ExchangeDialog.Close")); // NOI18N
        jGTIButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCloseActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanelButtons.add(jGTIButtonClose, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        getContentPane().add(jPanelButtons, gridBagConstraints);

        setBounds(0, 0, 400, 250);
    }// </editor-fold>//GEN-END:initComponents

    private void jGTITextFieldPortKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jGTITextFieldPortKeyReleased
      this.logic.handlePortChanged();
    }//GEN-LAST:event_jGTITextFieldPortKeyReleased

    private void jGTITextFieldHostKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jGTITextFieldHostKeyReleased
      this.logic.handleHostChanged();
    }//GEN-LAST:event_jGTITextFieldHostKeyReleased

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
      this.logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void jGTIButtonExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonExecuteActionPerformed
      this.logic.handleExecute();
    }//GEN-LAST:event_jGTIButtonExecuteActionPerformed

    private void jRadioButtonSendItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonSendItemStateChanged
      this.logic.handleItemStateChanged(evt);
    }//GEN-LAST:event_jRadioButtonSendItemStateChanged

    private void jRadioButtonReceiveItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonReceiveItemStateChanged
      this.logic.handleItemStateChanged(evt);
    }//GEN-LAST:event_jRadioButtonReceiveItemStateChanged

    private void jGTIButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCloseActionPerformed
      this.logic.handleClose();
    }//GEN-LAST:event_jGTIButtonCloseActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleClose();
    }//GEN-LAST:event_formWindowClosing
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.ButtonGroup buttonGroupPreferences;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonClose;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonExecute;
    public de.unisiegen.gtitool.ui.swing.JGTITextField jGTITextFieldHost;
    public de.unisiegen.gtitool.ui.swing.JGTITextField jGTITextFieldPort;
    public javax.swing.JLabel jLabelHost;
    public javax.swing.JLabel jLabelPort;
    public javax.swing.JPanel jPanelButtons;
    public javax.swing.JPanel jPanelChoice;
    public javax.swing.JPanel jPanelPreferences;
    public javax.swing.JRadioButton jRadioButtonReceive;
    public javax.swing.JRadioButton jRadioButtonSend;
    public javax.swing.JScrollPane jScrollPaneStatus;
    public javax.swing.JTextPane jTextPaneStatus;
    // End of variables declaration//GEN-END:variables
}
