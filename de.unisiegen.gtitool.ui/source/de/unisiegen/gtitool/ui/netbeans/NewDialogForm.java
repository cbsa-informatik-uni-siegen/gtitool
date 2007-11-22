/*
 * FileWizard.java
 *
 * Created on 26. Juli 2006, 19:44
 */

package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.NewDialog;



/**
 *
 * @author Benjamin Mies
 * @version $Id$
 */
public class NewDialogForm extends javax.swing.JDialog {

  //
  // Constructor
  //
  
  /**
   * Allocates a new <code>FileWizard</code> instance.
   *
   * @param parent the parent frame.
   * @param modal <code>true</code> to display the wizard modal
   *              for the <code>parent</code>.
   */
  public NewDialogForm(java.awt.Frame parent, boolean modal) {
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
        javax.swing.JPanel buttonsPanel;
        javax.swing.JButton cancelButton;
        java.awt.GridBagConstraints gridBagConstraints;
        javax.swing.JLabel headerImageLabel;
        javax.swing.JPanel headerPanel;
        javax.swing.JLabel headerSubTitleLabel;
        javax.swing.JLabel headerTitleLabel;
        javax.swing.JPanel tabbedPanel;

        machines = new javax.swing.ButtonGroup();
        grammars = new javax.swing.ButtonGroup();
        headerPanel = new javax.swing.JPanel();
        headerTitleLabel = new javax.swing.JLabel();
        headerSubTitleLabel = new javax.swing.JLabel();
        headerImageLabel = new javax.swing.JLabel();
        tabbedPanel = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        machinesPanel = new javax.swing.JPanel();
        jLabelMachinesCaption = new javax.swing.JLabel();
        buttonMachinesPanel = new javax.swing.JPanel();
        jRadioButtonStackMachine = new javax.swing.JRadioButton();
        jRadioButtonNFA = new javax.swing.JRadioButton();
        jRadioButtonDFA = new javax.swing.JRadioButton();
        jRadioButtonEDFA = new javax.swing.JRadioButton();
        jPanelMachinesSpace = new javax.swing.JPanel();
        bodyPanelMachine = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        styledAlphabetParserPanel1 = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        grammarsPanel = new javax.swing.JPanel();
        jLabelGrammarsCaption = new javax.swing.JLabel();
        buttonGrammarsPanel = new javax.swing.JPanel();
        jRadioButtonRegularGrammar = new javax.swing.JRadioButton();
        jRadioButtonContextFreeGrammar = new javax.swing.JRadioButton();
        jRadioButtonContextSensitivGrammar = new javax.swing.JRadioButton();
        jPanelGrammarsSpace = new javax.swing.JPanel();
        bodyPanelGrammar = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        styledAlphabetParserPanel2 = new de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel();
        buttonsPanel = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setName("newDialog");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        headerPanel.setLayout(new java.awt.GridBagLayout());

        headerPanel.setBackground(new java.awt.Color(255, 255, 255));
        headerTitleLabel.setFont(new java.awt.Font("Dialog", 1, 24));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        headerTitleLabel.setText(bundle.getString("MainWindow.New")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        headerPanel.add(headerTitleLabel, gridBagConstraints);

        headerSubTitleLabel.setText(bundle.getString("NewDialog.NewSubtitle")); // NOI18N
        headerSubTitleLabel.setAlignmentY(0.0F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        headerPanel.add(headerSubTitleLabel, gridBagConstraints);

        headerImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/new24.png")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        headerPanel.add(headerImageLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(headerPanel, gridBagConstraints);

        tabbedPanel.setLayout(new java.awt.GridBagLayout());

        tabbedPane.setName("machines");
        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });
        tabbedPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabbedPaneKeyPressed(evt);
            }
        });

        machinesPanel.setLayout(new java.awt.GridBagLayout());

        machinesPanel.setPreferredSize(new java.awt.Dimension(329, 100));
        jLabelMachinesCaption.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMachinesCaption.setText(bundle.getString("NewDialog.ChooseMachine")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(25, 0, 15, 0);
        machinesPanel.add(jLabelMachinesCaption, gridBagConstraints);

        buttonMachinesPanel.setLayout(new java.awt.GridBagLayout());

        machines.add(jRadioButtonStackMachine);
        jRadioButtonStackMachine.setSelected(true);
        jRadioButtonStackMachine.setText(bundle.getString("NewDialog.Stackmachine")); // NOI18N
        jRadioButtonStackMachine.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonStackMachine.setEnabled(false);
        jRadioButtonStackMachine.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buttonMachinesPanel.add(jRadioButtonStackMachine, gridBagConstraints);

        machines.add(jRadioButtonNFA);
        jRadioButtonNFA.setText(bundle.getString("NewDialog.NFA")); // NOI18N
        jRadioButtonNFA.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonNFA.setEnabled(false);
        jRadioButtonNFA.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buttonMachinesPanel.add(jRadioButtonNFA, gridBagConstraints);

        machines.add(jRadioButtonDFA);
        jRadioButtonDFA.setSelected(true);
        jRadioButtonDFA.setText(bundle.getString("NewDialog.DFA")); // NOI18N
        jRadioButtonDFA.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonDFA.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buttonMachinesPanel.add(jRadioButtonDFA, gridBagConstraints);

        machines.add(jRadioButtonEDFA);
        jRadioButtonEDFA.setText(bundle.getString("NewDialog.ENFA")); // NOI18N
        jRadioButtonEDFA.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonEDFA.setEnabled(false);
        jRadioButtonEDFA.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buttonMachinesPanel.add(jRadioButtonEDFA, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        machinesPanel.add(buttonMachinesPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        machinesPanel.add(jPanelMachinesSpace, gridBagConstraints);

        bodyPanelMachine.setLayout(new java.awt.GridBagLayout());

        bodyPanelMachine.setMinimumSize(new java.awt.Dimension(261, 300));
        bodyPanelMachine.setPreferredSize(new java.awt.Dimension(261, 300));
        jLabel5.setText(bundle.getString("NewDialog.ChooseAlphabet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        bodyPanelMachine.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 30, 16);
        bodyPanelMachine.add(styledAlphabetParserPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        machinesPanel.add(bodyPanelMachine, gridBagConstraints);

        tabbedPane.addTab(bundle.getString("NewDialog.Machine"), machinesPanel); // NOI18N

        grammarsPanel.setLayout(new java.awt.GridBagLayout());

        jLabelGrammarsCaption.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGrammarsCaption.setText(bundle.getString("NewDialog.ChooseGrammar")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(25, 0, 15, 0);
        grammarsPanel.add(jLabelGrammarsCaption, gridBagConstraints);

        buttonGrammarsPanel.setLayout(new java.awt.GridBagLayout());

        grammars.add(jRadioButtonRegularGrammar);
        jRadioButtonRegularGrammar.setSelected(true);
        jRadioButtonRegularGrammar.setText(bundle.getString("NewDialog.regularGrammar")); // NOI18N
        jRadioButtonRegularGrammar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonRegularGrammar.setEnabled(false);
        jRadioButtonRegularGrammar.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buttonGrammarsPanel.add(jRadioButtonRegularGrammar, gridBagConstraints);

        grammars.add(jRadioButtonContextFreeGrammar);
        jRadioButtonContextFreeGrammar.setText(bundle.getString("NewDialog.contextFreeGrammar")); // NOI18N
        jRadioButtonContextFreeGrammar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonContextFreeGrammar.setEnabled(false);
        jRadioButtonContextFreeGrammar.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buttonGrammarsPanel.add(jRadioButtonContextFreeGrammar, gridBagConstraints);

        grammars.add(jRadioButtonContextSensitivGrammar);
        jRadioButtonContextSensitivGrammar.setText(bundle.getString("NewDialog.contextSensitivGrammar")); // NOI18N
        jRadioButtonContextSensitivGrammar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonContextSensitivGrammar.setEnabled(false);
        jRadioButtonContextSensitivGrammar.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buttonGrammarsPanel.add(jRadioButtonContextSensitivGrammar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        grammarsPanel.add(buttonGrammarsPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        grammarsPanel.add(jPanelGrammarsSpace, gridBagConstraints);

        bodyPanelGrammar.setLayout(new java.awt.GridBagLayout());

        bodyPanelGrammar.setMinimumSize(new java.awt.Dimension(261, 300));
        bodyPanelGrammar.setPreferredSize(new java.awt.Dimension(261, 300));
        jLabel4.setText(bundle.getString("NewDialog.ChooseAlphabet")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        bodyPanelGrammar.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 30, 16);
        bodyPanelGrammar.add(styledAlphabetParserPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        grammarsPanel.add(bodyPanelGrammar, gridBagConstraints);

        tabbedPane.addTab(bundle.getString("NewDialog.Grammar"), grammarsPanel); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 2.0;
        tabbedPanel.add(tabbedPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.weighty = 10.0;
        getContentPane().add(tabbedPanel, gridBagConstraints);

        buttonsPanel.setLayout(new java.awt.GridBagLayout());

        cancelButton.setText(bundle.getString("Cancel")); // NOI18N
        cancelButton.setLabel(bundle.getString("Cancel")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        buttonsPanel.add(cancelButton, gridBagConstraints);

        okButton.setMnemonic('O');
        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        buttonsPanel.add(okButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(buttonsPanel, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-515)/2, (screenSize.height-600)/2, 515, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void tabbedPaneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabbedPaneKeyPressed
// NOTICE Ihre Ereignisbehandlung hier einfügen:
    }//GEN-LAST:event_tabbedPaneKeyPressed

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
// NOTICE Ihre Ereignisbehandlung hier einfügen:
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
// NOTICE Ihre Ereignisbehandlung hier einfügen:
    }//GEN-LAST:event_formWindowClosing

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
// NOTICE Ihre Ereignisbehandlung hier einfügen:
      this.canceled = false;
      dispose();
    }//GEN-LAST:event_okButtonActionPerformed

  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
// NOTICE Ihre Ereignisbehandlung hier einfügen:
     this.canceled =  true;
      dispose();
  }//GEN-LAST:event_cancelButtonActionPerformed
  
    // Variablendeklaration - nicht modifizieren//GEN-BEGIN:variables
    public javax.swing.JPanel bodyPanelGrammar;
    public javax.swing.JPanel bodyPanelMachine;
    private javax.swing.JPanel buttonGrammarsPanel;
    private javax.swing.JPanel buttonMachinesPanel;
    public javax.swing.ButtonGroup grammars;
    private javax.swing.JPanel grammarsPanel;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelGrammarsCaption;
    private javax.swing.JLabel jLabelMachinesCaption;
    private javax.swing.JPanel jPanelGrammarsSpace;
    private javax.swing.JPanel jPanelMachinesSpace;
    public javax.swing.JRadioButton jRadioButtonContextFreeGrammar;
    public javax.swing.JRadioButton jRadioButtonContextSensitivGrammar;
    public javax.swing.JRadioButton jRadioButtonDFA;
    public javax.swing.JRadioButton jRadioButtonEDFA;
    public javax.swing.JRadioButton jRadioButtonNFA;
    public javax.swing.JRadioButton jRadioButtonRegularGrammar;
    public javax.swing.JRadioButton jRadioButtonStackMachine;
    public javax.swing.ButtonGroup machines;
    public javax.swing.JPanel machinesPanel;
    public javax.swing.JButton okButton;
    private de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanel1;
    private de.unisiegen.gtitool.ui.style.StyledAlphabetParserPanel styledAlphabetParserPanel2;
    public javax.swing.JTabbedPane tabbedPane;
    // Ende der Variablendeklaration//GEN-END:variables
    
    private NewDialog logic;
    
    private boolean canceled = true;
    
    public void setLogic( NewDialog pLogic){
        this.logic = pLogic;
    }
    
    public boolean isCanceled(){
        return this.canceled;
    }
}
