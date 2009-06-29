package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.logic.SaveDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;

/**
 * The {@link SaveDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class SaveDialogForm extends JDialog implements GUIClass <SaveDialog>
{
  
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2312132994091880833L;


  /**
   * The {@link SaveDialog}.
   */
  private SaveDialog logic;


  /**
   * Allocates a new {@link SaveDialogForm}.
   * 
   * @param logic The {@link SaveDialog}.
   * @param parent The parent {@link JFrame}.
   */
  public SaveDialogForm ( SaveDialog logic, JFrame parent )
  {
    super ( parent, true );
    this.logic = logic ;
    initComponents ();
    this.jGTIFileChooser.setSaveDialog ( this.logic );
  }


  /**
   * Allocates a new {@link SaveDialogForm}.
   * 
   * @param logic The {@link SaveDialog}.
   * @param parent The parent {@link JFrame}.
   */
  public SaveDialogForm ( SaveDialog logic, JDialog parent )
  {
    super ( parent, true );
    this.logic = logic ;
    initComponents ();
    this.jGTIFileChooser.setSaveDialog ( this.logic );
  }
  
  /**
   * {@inheritDoc}
   * 
   * @see GUIClass#getLogic()
   */
  public final SaveDialog getLogic ()
  {
    return this.logic;
  }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        this.jGTIFileChooser = new de.unisiegen.gtitool.ui.swing.JGTIFileChooser();
        this.jGTIPanelButtons = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        this.jGTIButtonSave = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        this.jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        setTitle(bundle.getString("SaveDialog.Title")); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        this.jGTIFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        this.jGTIFileChooser.setMultiSelectionEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(this.jGTIFileChooser, gridBagConstraints);

        this.jGTIButtonSave.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("SaveDialog.SaveMnemonic").charAt(0));
        this.jGTIButtonSave.setText(bundle.getString("SaveDialog.Save")); // NOI18N
        this.jGTIButtonSave.setToolTipText(bundle.getString("SaveDialog.SaveToolTip")); // NOI18N
        this.jGTIButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonSaveActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        this.jGTIPanelButtons.add(this.jGTIButtonSave, gridBagConstraints);

        this.jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("SaveDialog.CancelMnemonic").charAt(0));
        this.jGTIButtonCancel.setText(bundle.getString("SaveDialog.Cancel")); // NOI18N
        this.jGTIButtonCancel.setToolTipText(bundle.getString("SaveDialog.CancelToolTip")); // NOI18N
        this.jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        this.jGTIPanelButtons.add(this.jGTIButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        getContentPane().add(this.jGTIPanelButtons, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-560)/2, (screenSize.height-352)/2, 560, 352);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
      this.logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void jGTIButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonSaveActionPerformed
      this.logic.handleSave();
    }//GEN-LAST:event_jGTIButtonSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonSave;
    public de.unisiegen.gtitool.ui.swing.JGTIFileChooser jGTIFileChooser;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelButtons;
    // End of variables declaration//GEN-END:variables
    
}
