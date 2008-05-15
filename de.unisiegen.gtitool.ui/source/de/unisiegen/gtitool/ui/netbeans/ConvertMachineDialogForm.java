package de.unisiegen.gtitool.ui.netbeans;

import java.awt.Frame;

import de.unisiegen.gtitool.ui.logic.ConvertMachineDialog;


/**
 * The {@link ConvertMachineDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class ConvertMachineDialogForm extends javax.swing.JDialog {
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 8264731535784921404L;
    
    /**
     * The {@link ConvertMachineDialog}.
     */
    private ConvertMachineDialog logic ;
    
    /**
     * Creates new form {@link ConvertMachineDialog}.
     * 
     * @param logic The {@link ConvertMachineDialog}.
     * @param parent The parent {@link Frame}.
     */
    public ConvertMachineDialogForm(ConvertMachineDialog logic, Frame parent) {
        super(parent, true);
        this.logic = logic ;
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

        jGTIScrollPaneOriginal = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTIScrollPaneConverted = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonOk = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        setTitle(bundle.getString("ConvertMachineDialog.Title")); // NOI18N
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jGTIScrollPaneOriginal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(jGTIScrollPaneConverted, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("ConvertMachineDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("ConvertMachineDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("ConvertMachineDialog.CancelToolTip")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 16);
        getContentPane().add(jGTIButtonCancel, gridBagConstraints);

        jGTIButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("ConvertMachineDialog.OkMnemonic").charAt(0));
        jGTIButtonOk.setText(bundle.getString("ConvertMachineDialog.Ok")); // NOI18N
        jGTIButtonOk.setToolTipText(bundle.getString("ConvertMachineDialog.OkToolTip")); // NOI18N
        jGTIButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonOkActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 5);
        getContentPane().add(jGTIButtonOk, gridBagConstraints);

        setSize(new java.awt.Dimension(640, 400));
    }// </editor-fold>//GEN-END:initComponents

    private void jGTIButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonOkActionPerformed
      this.logic.handleOk();
    }//GEN-LAST:event_jGTIButtonOkActionPerformed

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
      this.logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonOk;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneConverted;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneOriginal;
    // End of variables declaration//GEN-END:variables
    
}
