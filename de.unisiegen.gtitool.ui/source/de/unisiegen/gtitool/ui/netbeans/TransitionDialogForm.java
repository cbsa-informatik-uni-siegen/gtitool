package de.unisiegen.gtitool.ui.netbeans;

import java.awt.Frame;

import de.unisiegen.gtitool.ui.logic.TransitionDialog;

/**
 * The transition dialog.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class TransitionDialogForm extends javax.swing.JDialog {
    
   /**
    * The serial version uid.
    */
    private static final long serialVersionUID = 8316002721568381761L;
    
    /**
     * The {@link TransitionDialog}.
     */
    private TransitionDialog logic;
    
    /**
     * Creates new form <code>TransitionDialog</code>.
     * 
     * @param pLogic The {@link TransitionDialog}.
     * @param pParent The parent {@link Frame}.
     */
    public TransitionDialogForm(TransitionDialog pLogic, java.awt.Frame pParent) {
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

        jPanel1 = new javax.swing.JPanel();
        JLabelHeadline = new javax.swing.JLabel();
        jLabelAlphabet = new javax.swing.JLabel();
        jLabelChangeOverSet = new javax.swing.JLabel();
        jPanelList = new javax.swing.JPanel();
        jScrollPaneAlphabet = new javax.swing.JScrollPane();
        jListAlphabet = new javax.swing.JList();
        jPanelChangeOverButtons = new javax.swing.JPanel();
        jButtonMoveLeft = new javax.swing.JButton();
        jButtonMoveRight = new javax.swing.JButton();
        jScrollChangeOverSet = new javax.swing.JScrollPane();
        jListChangeOverSet = new javax.swing.JList();
        JLabelSubHeadline1 = new javax.swing.JLabel();
        styledAlphabetParserPanel = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        jPanelButtons = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        JLabelHeadline.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        JLabelHeadline.setText(bundle.getString("TransitionDialog.Header")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 10, 16);
        jPanel1.add(JLabelHeadline, gridBagConstraints);

        jLabelAlphabet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAlphabet.setText(bundle.getString("TransitionDialog.Alphabet")); // NOI18N
        jLabelAlphabet.setPreferredSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 10, 26);
        jPanel1.add(jLabelAlphabet, gridBagConstraints);

        jLabelChangeOverSet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelChangeOverSet.setText(bundle.getString("TransitionDialog.TransitionSet")); // NOI18N
        jLabelChangeOverSet.setPreferredSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 26, 10, 16);
        jPanel1.add(jLabelChangeOverSet, gridBagConstraints);

        jPanelList.setLayout(new java.awt.GridBagLayout());

        jScrollPaneAlphabet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPaneAlphabet.setPreferredSize(new java.awt.Dimension(100, 130));
        jListAlphabet.setFocusable(false);
        jListAlphabet.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                handleValueChanged(evt);
            }
        });

        jScrollPaneAlphabet.setViewportView(jListAlphabet);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelList.add(jScrollPaneAlphabet, gridBagConstraints);

        jPanelChangeOverButtons.setLayout(new java.awt.GridBagLayout());

        jButtonMoveLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/moveLeft.png")));
        jButtonMoveLeft.setEnabled(false);
        jButtonMoveLeft.setFocusable(false);
        jButtonMoveLeft.setMaximumSize(new java.awt.Dimension(32, 32));
        jButtonMoveLeft.setMinimumSize(new java.awt.Dimension(32, 32));
        jButtonMoveLeft.setPreferredSize(new java.awt.Dimension(32, 32));
        jButtonMoveLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleActionPerformedMoveLeft(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelChangeOverButtons.add(jButtonMoveLeft, gridBagConstraints);

        jButtonMoveRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/moveRight.png")));
        jButtonMoveRight.setEnabled(false);
        jButtonMoveRight.setFocusable(false);
        jButtonMoveRight.setMaximumSize(new java.awt.Dimension(32, 32));
        jButtonMoveRight.setMinimumSize(new java.awt.Dimension(32, 32));
        jButtonMoveRight.setPreferredSize(new java.awt.Dimension(32, 32));
        jButtonMoveRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleActionPerformedMoveRight(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelChangeOverButtons.add(jButtonMoveRight, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jPanelList.add(jPanelChangeOverButtons, gridBagConstraints);

        jScrollChangeOverSet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollChangeOverSet.setPreferredSize(new java.awt.Dimension(100, 130));
        jListChangeOverSet.setFocusable(false);
        jListChangeOverSet.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                handleValueChanged(evt);
            }
        });

        jScrollChangeOverSet.setViewportView(jListChangeOverSet);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelList.add(jScrollChangeOverSet, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 16);
        jPanel1.add(jPanelList, gridBagConstraints);

        JLabelSubHeadline1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabelSubHeadline1.setText(bundle.getString("TransitionDialog.ResultingTransitionSet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 16, 10, 16);
        jPanel1.add(JLabelSubHeadline1, gridBagConstraints);

        styledAlphabetParserPanel.setEditable(false);
        styledAlphabetParserPanel.setMinimumSize(new java.awt.Dimension(39, 80));
        styledAlphabetParserPanel.setPreferredSize(new java.awt.Dimension(24, 80));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 16);
        jPanel1.add(styledAlphabetParserPanel, gridBagConstraints);

        jPanelButtons.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanelButtons.add(jButtonOk, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanelButtons.add(jButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 16, 16, 16);
        jPanel1.add(jPanelButtons, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-450)/2, 400, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void handleValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_handleValueChanged
        this.logic.handleFocusGained ( evt );
    }//GEN-LAST:event_handleValueChanged

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabelHeadline;
    private javax.swing.JLabel JLabelSubHeadline1;
    private javax.swing.JButton jButtonCancel;
    public javax.swing.JButton jButtonMoveLeft;
    public javax.swing.JButton jButtonMoveRight;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelAlphabet;
    private javax.swing.JLabel jLabelChangeOverSet;
    public javax.swing.JList jListAlphabet;
    public javax.swing.JList jListChangeOverSet;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelChangeOverButtons;
    private javax.swing.JPanel jPanelList;
    private javax.swing.JScrollPane jScrollChangeOverSet;
    private javax.swing.JScrollPane jScrollPaneAlphabet;
    public de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanel;
    // End of variables declaration//GEN-END:variables
 
}
