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
        java.awt.GridBagConstraints gridBagConstraints;
        javax.swing.JLabel headerImageLabel;
        javax.swing.JLabel headerSubTitleLabel;
        javax.swing.JLabel headerTitleLabel;

        headerPanel = new javax.swing.JPanel();
        headerTitleLabel = new javax.swing.JLabel();
        headerSubTitleLabel = new javax.swing.JLabel();
        headerImageLabel = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();

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

        bodyPanel.setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.weighty = 10.0;
        getContentPane().add(bodyPanel, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-450)/2, (screenSize.height-300)/2, 450, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
// NOTICE Ihre Ereignisbehandlung hier einfügen:
    }//GEN-LAST:event_formWindowClosing
  
    // Variablendeklaration - nicht modifizieren//GEN-BEGIN:variables
    public javax.swing.JPanel bodyPanel;
    public javax.swing.JPanel headerPanel;
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
