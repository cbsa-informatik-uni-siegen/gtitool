package de.unisiegen.gtitool.ui.netbeans;

import java.awt.Frame;

import de.unisiegen.gtitool.ui.logic.ConvertMachineDialog;


/**
 * The {@link ConvertMachineDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class ConvertMachineDialogForm extends javax.swing.JDialog {
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 8264731535784921404L;
    
    /**
     * The {@link ConvertMachineDialog}.
     */
    private ConvertMachineDialog logic ;
    
    /**
     * Creates new form {@link ConvertMachineDialog}.
     * 
     * @param logic The {@link ConvertMachineDialog}.
     * @param parent The parent {@link Frame}.
     */
    public ConvertMachineDialogForm(ConvertMachineDialog logic, Frame parent) {
        super(parent, true);
        this.logic = logic ;
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

        jGTIToolBarMain = new de.unisiegen.gtitool.ui.swing.JGTIToolBar();
        jGTIToolBarButtonPreviousStep = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonNextStep = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonAutoStep = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonStop = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTISplitPaneGraph = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTIScrollPaneOriginal = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTIScrollPaneConverted = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonOk = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        setTitle(bundle.getString("ConvertMachineDialog.Title")); // NOI18N
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jGTIToolBarButtonPreviousStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/word-backward.png")));
        jGTIToolBarButtonPreviousStep.setToolTipText(bundle.getString("ConvertMachineDialog.PreviousStep")); // NOI18N
        jGTIToolBarButtonPreviousStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonPreviousStepActionPerformed(evt);
            }
        });

        jGTIToolBarMain.add(jGTIToolBarButtonPreviousStep);

        jGTIToolBarButtonNextStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/word-forward.png")));
        jGTIToolBarButtonNextStep.setToolTipText(bundle.getString("ConvertMachineDialog.NextStep")); // NOI18N
        jGTIToolBarButtonNextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonNextStepActionPerformed(evt);
            }
        });

        jGTIToolBarMain.add(jGTIToolBarButtonNextStep);

        jGTIToolBarButtonAutoStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/word-autostep.png")));
        jGTIToolBarButtonAutoStep.setToolTipText(bundle.getString("ConvertMachineDialog.AutoStep")); // NOI18N
        jGTIToolBarButtonAutoStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonAutoStepActionPerformed(evt);
            }
        });

        jGTIToolBarMain.add(jGTIToolBarButtonAutoStep);

        jGTIToolBarButtonStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/word-stop.png")));
        jGTIToolBarButtonStop.setToolTipText(bundle.getString("ConvertMachineDialog.Stop")); // NOI18N
        jGTIToolBarButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonStopActionPerformed(evt);
            }
        });

        jGTIToolBarMain.add(jGTIToolBarButtonStop);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jGTIToolBarMain, gridBagConstraints);

        jGTISplitPaneGraph.setDividerLocation(250);
        jGTISplitPaneGraph.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jGTISplitPaneGraph.setResizeWeight(0.5);
        jGTISplitPaneGraph.setTopComponent(jGTIScrollPaneOriginal);

        jGTISplitPaneGraph.setBottomComponent(jGTIScrollPaneConverted);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jGTISplitPaneGraph, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("ConvertMachineDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("ConvertMachineDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("ConvertMachineDialog.CancelToolTip")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 16);
        getContentPane().add(jGTIButtonCancel, gridBagConstraints);

        jGTIButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("ConvertMachineDialog.OkMnemonic").charAt(0));
        jGTIButtonOk.setText(bundle.getString("ConvertMachineDialog.Ok")); // NOI18N
        jGTIButtonOk.setToolTipText(bundle.getString("ConvertMachineDialog.OkToolTip")); // NOI18N
        jGTIButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonOkActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 5);
        getContentPane().add(jGTIButtonOk, gridBagConstraints);

        setSize(new java.awt.Dimension(960, 600));
    }// </editor-fold>//GEN-END:initComponents

    private void jGTIToolBarButtonAutoStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonAutoStepActionPerformed
        this.logic.handleAutoStep();
    }//GEN-LAST:event_jGTIToolBarButtonAutoStepActionPerformed

    private void jGTIToolBarButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonStopActionPerformed
        this.logic.handleStop();
    }//GEN-LAST:event_jGTIToolBarButtonStopActionPerformed

    private void jGTIToolBarButtonPreviousStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonPreviousStepActionPerformed
        this.logic.handlePreviousStep();
    }//GEN-LAST:event_jGTIToolBarButtonPreviousStepActionPerformed

    private void jGTIToolBarButtonNextStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonNextStepActionPerformed
        this.logic.handleNextStep();
    }//GEN-LAST:event_jGTIToolBarButtonNextStepActionPerformed

    private void jGTIButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonOkActionPerformed
      this.logic.handleOk();
    }//GEN-LAST:event_jGTIButtonOkActionPerformed

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
      this.logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonOk;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneConverted;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneOriginal;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneGraph;
    public de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonAutoStep;
    public de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonNextStep;
    public de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonPreviousStep;
    public de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonStop;
    public de.unisiegen.gtitool.ui.swing.JGTIToolBar jGTIToolBarMain;
    // End of variables declaration//GEN-END:variables
    
}
