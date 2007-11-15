/*
 * TransitionDialog.java
 *
 * Created on 9. November 2007, 14:18
 */

package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.TransitionDialog;

/**
 * @author Benjamin Mies
 * @version $Id: NewDialog.java 119 2007-11-10 12:07:30Z fehler 
 */
public class TransitionDialogForm extends javax.swing.JDialog {
    
    /** Creates new form TransitionDialog */
    public TransitionDialogForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        jPanel1 = new javax.swing.JPanel();
        JLabelHeadline = new javax.swing.JLabel();
        jLabelAlphabet = new javax.swing.JLabel();
        jLabelChangeOverSet = new javax.swing.JLabel();
        jScrollPaneAlphabet = new javax.swing.JScrollPane();
        jListAlphabet = new javax.swing.JList();
        jButtonMoveLeft = new javax.swing.JButton();
        jScrollChangeOverSet = new javax.swing.JScrollPane();
        jListChangeOverSet = new javax.swing.JList();
        jButtonMoveRight = new javax.swing.JButton();
        JLabelSubHeadline1 = new javax.swing.JLabel();
        jTextPane1 = new javax.swing.JTextPane();
        jLabelErrorMessage = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        JLabelHeadline.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabelHeadline.setText("von \"\" nach \"\"");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(JLabelHeadline, gridBagConstraints);

        jLabelAlphabet.setText("Eingabealphabet");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        jPanel1.add(jLabelAlphabet, gridBagConstraints);

        jLabelChangeOverSet.setText("\u00dcbergangsmenge");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        jPanel1.add(jLabelChangeOverSet, gridBagConstraints);

        jListAlphabet.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Eintrag 1", "Eintrag 2", "Eintrag 3", "Eintrag 4", "Eintrag 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListAlphabet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                handleFocusGained(evt);
            }
        });

        jScrollPaneAlphabet.setViewportView(jListAlphabet);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        jPanel1.add(jScrollPaneAlphabet, gridBagConstraints);

        jButtonMoveLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/moveLeft.png")));
        jButtonMoveLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleActionPerformedMoveLeft(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 40, 10);
        jPanel1.add(jButtonMoveLeft, gridBagConstraints);

        jListChangeOverSet.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Eintrag 1", "Eintrag 2", "Eintrag 3", "Eintrag 4", "Eintrag 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListChangeOverSet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                handleFocusGained(evt);
            }
        });

        jScrollChangeOverSet.setViewportView(jListChangeOverSet);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        jPanel1.add(jScrollChangeOverSet, gridBagConstraints);

        jButtonMoveRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/moveRight.png")));
        jButtonMoveRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleActionPerformedMoveRight(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(40, 10, 10, 10);
        jPanel1.add(jButtonMoveRight, gridBagConstraints);

        JLabelSubHeadline1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabelSubHeadline1.setText("Entstehender \u00dcbergang");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(JLabelSubHeadline1, gridBagConstraints);

        jTextPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextPane1.setMinimumSize(new java.awt.Dimension(2, 63));
        jTextPane1.setPreferredSize(new java.awt.Dimension(2, 63));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 10);
        jPanel1.add(jTextPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(jLabelErrorMessage, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jButtonOk.setText("Ok");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleActionPerformedOk(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        jPanel2.add(jButtonOk, gridBagConstraints);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jButtonCancel.setText(bundle.getString("Cancel")); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleActionPerformedCancel(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        jPanel2.add(jButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jPanel2, gridBagConstraints);

        getContentPane().add(jPanel1, new java.awt.GridBagConstraints());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void handleFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_handleFocusGained
        this.logic.handleFocusGained ( evt );
    }//GEN-LAST:event_handleFocusGained

    private void handleActionPerformedMoveLeft(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleActionPerformedMoveLeft
        this.logic.handleActionPerformedMoveLeft();
    }//GEN-LAST:event_handleActionPerformedMoveLeft

    private void handleActionPerformedMoveRight(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleActionPerformedMoveRight
        this.logic.handleActionPerformedMoveRight();
    }//GEN-LAST:event_handleActionPerformedMoveRight

    private void handleActionPerformedCancel(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleActionPerformedCancel
        this.logic.handleActionPerformedCancel();
    }//GEN-LAST:event_handleActionPerformedCancel

    private void handleActionPerformedOk(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleActionPerformedOk
        this.logic.handleActionPerformedOk();
    }//GEN-LAST:event_handleActionPerformedOk
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransitionDialogForm(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }
    
    // Variablendeklaration - nicht modifizieren//GEN-BEGIN:variables
    private javax.swing.JLabel JLabelHeadline;
    private javax.swing.JLabel JLabelSubHeadline1;
    private javax.swing.JButton jButtonCancel;
    public javax.swing.JButton jButtonMoveLeft;
    public javax.swing.JButton jButtonMoveRight;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelAlphabet;
    private javax.swing.JLabel jLabelChangeOverSet;
    public javax.swing.JLabel jLabelErrorMessage;
    public javax.swing.JList jListAlphabet;
    public javax.swing.JList jListChangeOverSet;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollChangeOverSet;
    private javax.swing.JScrollPane jScrollPaneAlphabet;
    public javax.swing.JTextPane jTextPane1;
    // Ende der Variablendeklaration//GEN-END:variables
   
    private TransitionDialog logic;
    
    public void setLogic( TransitionDialog pLogic){
      this.logic = pLogic;
  }
    
    
}
