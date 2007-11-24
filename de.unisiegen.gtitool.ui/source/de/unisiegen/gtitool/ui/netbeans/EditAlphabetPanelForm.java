/*
 * EditAlphabetPanel.java
 *
 * Created on 17. November 2007, 19:49
 */

package de.unisiegen.gtitool.ui.netbeans;

/**
 *
 * @author  benny
 */
public class EditAlphabetPanelForm extends javax.swing.JPanel {
    
    /** Creates new form EditAlphabetPanel */
    public EditAlphabetPanelForm() {
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

        jButtonAlphabetEdit = new javax.swing.JButton();
        styledAlphabetParserPanel1 = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();

        setLayout(new java.awt.GridBagLayout());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jButtonAlphabetEdit.setText(bundle.getString("PreferencesDialog.AlphabetEdit")); // NOI18N
        jButtonAlphabetEdit.setToolTipText(bundle.getString("PreferencesDialog.AlphabetEditToolTip")); // NOI18N
        jButtonAlphabetEdit.setEnabled(false);
        jButtonAlphabetEdit.setFocusPainted(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 20, 21);
        add(jButtonAlphabetEdit, gridBagConstraints);

        styledAlphabetParserPanel1.setPreferredSize(new java.awt.Dimension(300, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(30, 0, 30, 16);
        add(styledAlphabetParserPanel1, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variablendeklaration - nicht modifizieren//GEN-BEGIN:variables
    public javax.swing.JButton jButtonAlphabetEdit;
    private de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanel1;
    // Ende der Variablendeklaration//GEN-END:variables
    
}
