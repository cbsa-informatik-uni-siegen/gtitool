package de.unisiegen.gtitool.ui.netbeans;


import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.logic.InfoDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;


/**
 * The {@link InfoDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id: InfoDialogForm.java 980 2008-06-11 23:38:13Z fehler $
 */
@SuppressWarnings ({"all"} )
public class InfoDialogForm extends JDialog implements GUIClass <InfoDialog>
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4350027197157442282L;


  /**
   * The {@link InfoDialog}.
   */
  private InfoDialog logic;


  /**
   * Allocates a new {@link InfoDialogForm}.
   * 
   * @param logic The {@link InfoDialog}.
   * @param parent The parent {@link JFrame}.
   */
  public InfoDialogForm ( InfoDialog logic, JFrame parent )
  {
    super ( parent, true );
    this.logic = logic;
    initComponents ();
  }


  /**
   * Allocates a new {@link InfoDialogForm}.
   * 
   * @param logic The {@link InfoDialog}.
   * @param parent The parent {@link JFrame}.
   */
  public InfoDialogForm ( InfoDialog logic, JDialog parent )
  {
    super ( parent, true );
    this.logic = logic;
    initComponents ();
  }

  /**
   * {@inheritDoc}
   * 
   * @see GUIClass#getLogic()
   */
  public final InfoDialog getLogic ()
  {
    return this.logic;
  }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        this.jGTIScrollPaneInfo = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        this.jGTITextAreaInfo = new de.unisiegen.gtitool.ui.swing.JGTITextArea();
        this.jGTIButtonClose = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        this.jGTIScrollPaneInfo.setBorder(null);
        this.jGTITextAreaInfo.setFocusable(false);
        this.jGTITextAreaInfo.setFont(new java.awt.Font("Dialog", 1, 12));
        this.jGTITextAreaInfo.setOpaque(false);
        this.jGTIScrollPaneInfo.setViewportView(this.jGTITextAreaInfo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(this.jGTIScrollPaneInfo, gridBagConstraints);

        this.jGTIButtonClose.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("InfoDialog.CloseMnemonic").charAt(0));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        this.jGTIButtonClose.setText(bundle.getString("InfoDialog.Close")); // NOI18N
        this.jGTIButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCloseActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        getContentPane().add(this.jGTIButtonClose, gridBagConstraints);

        setSize(new java.awt.Dimension(240, 150));
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleClose();
    }//GEN-LAST:event_formWindowClosing

    private void jGTIButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCloseActionPerformed
      this.logic.handleClose();
    }//GEN-LAST:event_jGTIButtonCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonClose;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneInfo;
    public de.unisiegen.gtitool.ui.swing.JGTITextArea jGTITextAreaInfo;
    // End of variables declaration//GEN-END:variables

}
