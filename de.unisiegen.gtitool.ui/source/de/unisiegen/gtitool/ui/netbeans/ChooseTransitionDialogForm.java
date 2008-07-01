package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.logic.ChooseTransitionDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;

/**
 * The choose {@link Transition} dialog.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class ChooseTransitionDialogForm extends JDialog implements GUIClass <ChooseTransitionDialog>
{
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -7313308327163039456L;

    
    /**
     * The {@link ChooseTransitionDialog}.
     */
    private ChooseTransitionDialog logic;


    /**
     * Allocates a new {@link ChooseTransitionDialogForm}.
     * 
     * @param logic The {@link ChooseTransitionDialog}.
     * @param parent The parent {@link JFrame}.
     */
    public ChooseTransitionDialogForm(ChooseTransitionDialog logic, JFrame parent) {
        super(parent, true);
        this.logic = logic;
        initComponents();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see GUIClass#getLogic()
     */
    public final ChooseTransitionDialog getLogic ()
    {
      return this.logic;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jGTILabelHeader = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIPanelTransitions = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIButtonOk = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        setTitle(bundle.getString("ChooseTransitionDialog.Title")); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jGTILabelHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelHeader.setText(bundle.getString("ChooseTransitionDialog.Header")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jGTILabelHeader, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(jGTIPanelTransitions, gridBagConstraints);

        jGTIButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("ChooseTransitionDialog.OkMnemonic").charAt(0));
        jGTIButtonOk.setText(bundle.getString("ChooseTransitionDialog.Ok")); // NOI18N
        jGTIButtonOk.setToolTipText(bundle.getString("ChooseTransitionDialog.OkToolTip")); // NOI18N
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
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        getContentPane().add(jGTIButtonOk, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-300)/2, 400, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void jGTIButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonOkActionPerformed
        this.logic.handleOk();
    }//GEN-LAST:event_jGTIButtonOkActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonOk;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelHeader;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelTransitions;
    // End of variables declaration//GEN-END:variables
 
}
