package de.unisiegen.gtitool.ui.netbeans;

import java.awt.Frame;

import de.unisiegen.gtitool.ui.logic.AboutDialog;
import de.unisiegen.gtitool.ui.logic.NewStateNameDialog;

/**
 * The <code>NewStateNameDialogForm</code>.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class NewStateNameDialogForm extends javax.swing.JDialog {
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 7000984653787618329L;

    /**
     * The {@link NewStateNameDialog}.
     */
    private NewStateNameDialog logic ;
    
    /**
     * Creates new form <code>NewStateNameDialogForm</code>
     * 
     * @param pLogic The {@link AboutDialog}.
     * @param pParent The parent {@link Frame}.
     */
    public NewStateNameDialogForm(NewStateNameDialog pLogic, java.awt.Frame pParent) {
        super(pParent, true);
        this.logic = pLogic;
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

        jLabelRename = new javax.swing.JLabel();
        styledStateParserPanel = new de.unisiegen.gtitool.ui.style.StyledStateParserPanel();
        jGTIButtonOk = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        setTitle(bundle.getString("NewStateNameDialog.RenameTitle")); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabelRename.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRename.setText(bundle.getString("NewStateNameDialog.RenameText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jLabelRename, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        getContentPane().add(styledStateParserPanel, gridBagConstraints);

        jGTIButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("NewStateNameDialog.OkMnemonic").charAt(0));
        jGTIButtonOk.setText(bundle.getString("NewStateNameDialog.Ok")); // NOI18N
        jGTIButtonOk.setToolTipText(bundle.getString("NewStateNameDialog.OkToolTip")); // NOI18N
        jGTIButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonOkActionPerformed(evt);
                jGTIButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 5);
        getContentPane().add(jGTIButtonOk, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("NewStateNameDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("NewStateNameDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("NewStateNameDialog.CancelToolTip")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCanceljGTIButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 16);
        getContentPane().add(jGTIButtonCancel, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-250)/2, 400, 250);
    }// </editor-fold>//GEN-END:initComponents

    private void jGTIButtonCanceljGTIButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCanceljGTIButton1ActionPerformed
      this.logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCanceljGTIButton1ActionPerformed

    private void jGTIButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButton1ActionPerformed
      this.logic.handleOk();
    }//GEN-LAST:event_jGTIButton1ActionPerformed

    private void jGTIButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonOkActionPerformed
      this.logic.handleOk();
    }//GEN-LAST:event_jGTIButtonOkActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonOk;
    public javax.swing.JLabel jLabelRename;
    public de.unisiegen.gtitool.ui.style.StyledStateParserPanel styledStateParserPanel;
    // End of variables declaration//GEN-END:variables
    
}
