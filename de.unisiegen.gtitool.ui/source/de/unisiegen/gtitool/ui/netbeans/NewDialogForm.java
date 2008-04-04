package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.NewDialog;


/**
 * The new dialog form.
 *
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class NewDialogForm extends javax.swing.JDialog {

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5230055486510640010L;

  //
  // Constructor
  //
  
  /**
   * Allocates a new {@link NewDialogForm} instance.
   *
   * @param parent the parent frame.
   * @param modal true to display the wizard modal
   *              for the parent.
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
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jGTIPanelHeader = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTILabelHeaderTitle = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelHeaderSubTitle = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelHeaderImage = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIPanelBody = new de.unisiegen.gtitool.ui.swing.JGTIPanel();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        setTitle(bundle.getString("NewDialog.Title")); // NOI18N
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jGTIPanelHeader.setBackground(new java.awt.Color(255, 255, 255));
        jGTILabelHeaderTitle.setText(bundle.getString("MainWindow.New")); // NOI18N
        jGTILabelHeaderTitle.setFont(new java.awt.Font("Dialog", 1, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 5);
        jGTIPanelHeader.add(jGTILabelHeaderTitle, gridBagConstraints);

        jGTILabelHeaderSubTitle.setText(bundle.getString("NewDialog.NewSubtitle")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 5);
        jGTIPanelHeader.add(jGTILabelHeaderSubTitle, gridBagConstraints);

        jGTILabelHeaderImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/new24.png")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(16, 5, 16, 16);
        jGTIPanelHeader.add(jGTILabelHeaderImage, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(jGTIPanelHeader, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        getContentPane().add(jGTIPanelBody, gridBagConstraints);

        setSize(new java.awt.Dimension(560, 350));
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelHeaderImage;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelHeaderSubTitle;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelHeaderTitle;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelBody;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelHeader;
    // End of variables declaration//GEN-END:variables
    
    private NewDialog logic;
    
    private boolean canceled = true;
    
    public void setLogic( NewDialog pLogic){
        this.logic = pLogic;
    }
    
    public boolean isCanceled(){
        return this.canceled;
    }
}
