package de.unisiegen.gtitool.ui.netbeans;


import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.logic.ConfirmDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;


/**
 * The {@link ConfirmDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings ({ "all" })
public class ConfirmDialogForm extends JDialog implements GUIClass <ConfirmDialog>
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5215251607776791331L;


  /**
   * The {@link ConfirmDialog}.
   */
  private ConfirmDialog logic;


  /**
   * Allocates a new {@link ConfirmDialogForm}.
   * 
   * @param logic The {@link ConfirmDialog}.
   * @param parent The parent {@link JFrame}.
   */
  public ConfirmDialogForm ( ConfirmDialog logic, JFrame parent )
  {
    super ( parent, true );
    this.logic = logic;
    initComponents ();
  }


  /**
   * Allocates a new {@link ConfirmDialogForm}.
   * 
   * @param logic The {@link ConfirmDialog}.
   * @param parent The parent {@link JDialog}.
   */
  public ConfirmDialogForm ( ConfirmDialog logic, JDialog parent )
  {
    super ( parent, true );
    this.logic = logic;
    initComponents ();
  }

  /**
   * {@inheritDoc}
   * 
   * @see GUIClass#getLogic()
   */
  public final ConfirmDialog getLogic ()
  {
    return this.logic;
  }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jGTIScrollPaneInfo = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITextAreaInfo = new de.unisiegen.gtitool.ui.swing.JGTITextArea();
        jGTIButtonYes = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonYesToAll = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonNo = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonNoToAll = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jGTIScrollPaneInfo.setBorder(null);

        jGTITextAreaInfo.setFocusable(false);
        jGTITextAreaInfo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jGTITextAreaInfo.setOpaque(false);
        jGTIScrollPaneInfo.setViewportView(jGTITextAreaInfo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jGTIScrollPaneInfo, gridBagConstraints);

        jGTIButtonYes.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("ConfirmDialog.YesMnemonic").charAt(0));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        jGTIButtonYes.setText(bundle.getString("ConfirmDialog.Yes")); // NOI18N
        jGTIButtonYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonYesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 5);
        getContentPane().add(jGTIButtonYes, gridBagConstraints);

        jGTIButtonYesToAll.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("ConfirmDialog.YesToAllMnemonic").charAt(0));
        jGTIButtonYesToAll.setText(bundle.getString("ConfirmDialog.YesToAll")); // NOI18N
        jGTIButtonYesToAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonYesToAllActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 5);
        getContentPane().add(jGTIButtonYesToAll, gridBagConstraints);

        jGTIButtonNo.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("ConfirmDialog.NoMnemonic").charAt(0));
        jGTIButtonNo.setText(bundle.getString("ConfirmDialog.No")); // NOI18N
        jGTIButtonNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonNoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 5);
        getContentPane().add(jGTIButtonNo, gridBagConstraints);

        jGTIButtonNoToAll.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("ConfirmDialog.NoToAllMnemonic").charAt(0));
        jGTIButtonNoToAll.setText(bundle.getString("ConfirmDialog.NoToAll")); // NOI18N
        jGTIButtonNoToAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonNoToAllActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 5);
        getContentPane().add(jGTIButtonNoToAll, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("ConfirmDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("ConfirmDialog.Cancel")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 16);
        getContentPane().add(jGTIButtonCancel, gridBagConstraints);

        setSize(new java.awt.Dimension(600, 250));
    }// </editor-fold>//GEN-END:initComponents

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
      this.logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void jGTIButtonNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonNoActionPerformed
      this.logic.handleNotConfirm();
    }//GEN-LAST:event_jGTIButtonNoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing

    private void jGTIButtonYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonYesActionPerformed
      this.logic.handleConfirm();
    }//GEN-LAST:event_jGTIButtonYesActionPerformed

private void jGTIButtonYesToAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonYesToAllActionPerformed
      this.logic.handleConfirmAll();
}//GEN-LAST:event_jGTIButtonYesToAllActionPerformed

private void jGTIButtonNoToAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonNoToAllActionPerformed
      this.logic.handleNotConfirmAll();
}//GEN-LAST:event_jGTIButtonNoToAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonNo;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonNoToAll;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonYes;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonYesToAll;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneInfo;
    public de.unisiegen.gtitool.ui.swing.JGTITextArea jGTITextAreaInfo;
    // End of variables declaration//GEN-END:variables

}
