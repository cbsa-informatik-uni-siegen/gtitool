/*
 * TextForm.java
 *
 * Created on 24. März 2009, 14:38
 */

package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.logic.TextWindow;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;
import de.unisiegen.gtitool.ui.utils.AlgorithmDocument;

/**
 *
 * @author  simon
 */
@SuppressWarnings("all")
public class TextForm extends javax.swing.JDialog implements GUIClass < TextWindow >{

    private TextWindow logic;
    
    /** Creates new form TextForm */
    public TextForm(JDialog parent, TextWindow logic, boolean modal, String title) {
        super(parent,title, modal);
        this.logic = logic;
        initComponents();
        this.jGTITextPaneAlgorithm.setDocument(new AlgorithmDocument());
        this.jGTITextPaneAlgorithm.setText(this.logic.getText());
        pack();
    }
    
    /** Creates new form TextForm */
    public TextForm(JFrame parent, TextWindow logic, boolean modal, String title) {
      super(parent,title, modal);
      this.logic = logic;
      initComponents();
      this.jGTITextPaneAlgorithm.setDocument(new AlgorithmDocument());
      this.jGTITextPaneAlgorithm.setText(this.logic.getText());
      pack();
  }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPaneAlgorithm = new javax.swing.JScrollPane();
        jGTITextPaneAlgorithm = new de.unisiegen.gtitool.ui.swing.JGTITextPane();
        jGTIButtonSave = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonPrint = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(220, 200));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jGTITextPaneAlgorithm.setEditable(false);
        jScrollPaneAlgorithm.setViewportView(jGTITextPaneAlgorithm);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPaneAlgorithm, gridBagConstraints);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        jGTIButtonSave.setText(bundle.getString("TextWindow.Save")); // NOI18N
        jGTIButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonSaveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jGTIButtonSave, gridBagConstraints);

        jGTIButtonPrint.setText(bundle.getString("TextWindow.Print")); // NOI18N
        jGTIButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonPrintActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jGTIButtonPrint, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jGTIButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonSaveActionPerformed
    this.logic.handleSave();
}//GEN-LAST:event_jGTIButtonSaveActionPerformed

private void jGTIButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonPrintActionPerformed
    this.logic.handlePrint();
}//GEN-LAST:event_jGTIButtonPrintActionPerformed

private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    this.logic.handleGUIClosed();
}//GEN-LAST:event_formWindowClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonPrint;
    private de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonSave;
    public de.unisiegen.gtitool.ui.swing.JGTITextPane jGTITextPaneAlgorithm;
    private javax.swing.JScrollPane jScrollPaneAlgorithm;
    // End of variables declaration//GEN-END:variables

    /**
    * TODO
    *
    * @return
    * @see de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass#getLogic()
    */
    public TextWindow getLogic ()
    {
    return this.logic;
    }
}
