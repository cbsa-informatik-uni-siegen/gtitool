package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.logic.MinimizeMachineDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;


/**
 * The {@link MinimizeMachineDialogForm}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class MinimizeMachineDialogForm extends JDialog implements GUIClass <MinimizeMachineDialog>
{
    
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2492103700111396933L;
   
    
    /**
     * The {@link MinimizeMachineDialog}.
     */
    private MinimizeMachineDialog logic ;
    
    /**
     * Allocates a new {@link MinimizeMachineDialogForm}.
     * 
     * @param logic The {@link MinimizeMachineDialog}.
     * @param parent The parent {@link JFrame}.
     */
    public MinimizeMachineDialogForm(MinimizeMachineDialog logic, JFrame parent) {
        super(parent, true);
        this.logic = logic ;
        initComponents();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see GUIClass#getLogic()
     */
    public final MinimizeMachineDialog getLogic ()
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

        jGTIToolBarMain = new de.unisiegen.gtitool.ui.swing.JGTIToolBar();
        jGTIToolBarButtonBeginStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        jGTIToolBarButtonPreviousStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        jGTIToolBarButtonNextStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        jGTIToolBarToggleButtonAutoStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton();
        jGTIToolBarButtonStop = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        jGTIToolBarButtonEndStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        jGTISplitPaneOutline = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTISplitPaneGraph = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTIScrollPaneOriginal = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTIScrollPaneConverted = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTIScrollPaneOutline = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableOutline = new de.unisiegen.gtitool.ui.swing.JGTITable();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonOk = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIPanelPrintAndAlgorithm = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIToggleButtonAlgorithm = new de.unisiegen.gtitool.ui.swing.JGTIToggleButton();
        jGTIButtonPrint = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        setTitle(bundle.getString("MinimizeMachineDialog.Title")); // NOI18N
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jGTIToolBarButtonBeginStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/begin.png"))); // NOI18N
        jGTIToolBarButtonBeginStep.setToolTipText(bundle.getString("MinimizeMachineDialog.BeginStep")); // NOI18N
        jGTIToolBarButtonBeginStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonBeginStepActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarButtonBeginStep);

        jGTIToolBarButtonPreviousStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/backward.png"))); // NOI18N
        jGTIToolBarButtonPreviousStep.setToolTipText(bundle.getString("MinimizeMachineDialog.PreviousStep")); // NOI18N
        jGTIToolBarButtonPreviousStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonPreviousStepActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarButtonPreviousStep);

        jGTIToolBarButtonNextStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/forward.png"))); // NOI18N
        jGTIToolBarButtonNextStep.setToolTipText(bundle.getString("MinimizeMachineDialog.NextStep")); // NOI18N
        jGTIToolBarButtonNextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonNextStepActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarButtonNextStep);

        jGTIToolBarToggleButtonAutoStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/autostep.png"))); // NOI18N
        jGTIToolBarToggleButtonAutoStep.setToolTipText(bundle.getString("MinimizeMachineDialog.AutoStep")); // NOI18N
        jGTIToolBarToggleButtonAutoStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarToggleButtonAutoStepActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarToggleButtonAutoStep);

        jGTIToolBarButtonStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/stop.png"))); // NOI18N
        jGTIToolBarButtonStop.setToolTipText(bundle.getString("MinimizeMachineDialog.Stop")); // NOI18N
        jGTIToolBarButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonStopActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarButtonStop);

        jGTIToolBarButtonEndStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/end.png"))); // NOI18N
        jGTIToolBarButtonEndStep.setToolTipText(bundle.getString("MinimizeMachineDialog.EndStep")); // NOI18N
        jGTIToolBarButtonEndStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonEndStepActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarButtonEndStep);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jGTIToolBarMain, gridBagConstraints);

        jGTISplitPaneOutline.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jGTISplitPaneOutline.setDividerLocation(600);
        jGTISplitPaneOutline.setResizeWeight(1.0);

        jGTISplitPaneGraph.setDividerLocation(250);
        jGTISplitPaneGraph.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jGTISplitPaneGraph.setResizeWeight(0.5);

        jGTIScrollPaneOriginal.setBorder(null);
        jGTISplitPaneGraph.setTopComponent(jGTIScrollPaneOriginal);

        jGTIScrollPaneConverted.setBorder(null);
        jGTISplitPaneGraph.setBottomComponent(jGTIScrollPaneConverted);

        jGTISplitPaneOutline.setLeftComponent(jGTISplitPaneGraph);

        jGTIScrollPaneOutline.setBorder(null);
        jGTIScrollPaneOutline.setMinimumSize(new java.awt.Dimension(200, 200));

        jGTITableOutline.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handleMouseClickedEvent(evt);
            }
        });
        jGTIScrollPaneOutline.setViewportView(jGTITableOutline);

        jGTISplitPaneOutline.setRightComponent(jGTIScrollPaneOutline);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jGTISplitPaneOutline, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MinimizeMachineDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("MinimizeMachineDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("MinimizeMachineDialog.CancelToolTip")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 16);
        getContentPane().add(jGTIButtonCancel, gridBagConstraints);

        jGTIButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MinimizeMachineDialog.OkMnemonic").charAt(0));
        jGTIButtonOk.setText(bundle.getString("MinimizeMachineDialog.Ok")); // NOI18N
        jGTIButtonOk.setToolTipText(bundle.getString("MinimizeMachineDialog.OkToolTip")); // NOI18N
        jGTIButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 5);
        getContentPane().add(jGTIButtonOk, gridBagConstraints);

        jGTIToggleButtonAlgorithm.setText(bundle.getString("TextWindow.ShowAlgorithm")); // NOI18N
        jGTIToggleButtonAlgorithm.setToolTipText(bundle.getString("TextWindow.ShowAlgorithmToolTip")); // NOI18N
        jGTIToggleButtonAlgorithm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToggleButtonAlgorithmItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jGTIPanelPrintAndAlgorithm.add(jGTIToggleButtonAlgorithm, gridBagConstraints);

        jGTIButtonPrint.setText(bundle.getString("PrintDialog.Print")); // NOI18N
        jGTIButtonPrint.setToolTipText(bundle.getString("PrintDialog.PrintToolTip")); // NOI18N
        jGTIButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonPrintActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelPrintAndAlgorithm.add(jGTIButtonPrint, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 5);
        getContentPane().add(jGTIPanelPrintAndAlgorithm, gridBagConstraints);

        setSize(new java.awt.Dimension(960, 600));
    }// </editor-fold>//GEN-END:initComponents

    private void handleMouseClickedEvent(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_handleMouseClickedEvent
        this.logic.highlightTransitions(this.jGTITableOutline.getSelectedRow ());
    }//GEN-LAST:event_handleMouseClickedEvent

    private void jGTIToolBarToggleButtonAutoStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarToggleButtonAutoStepActionPerformed
        this.logic.handleAutoStep();
    }//GEN-LAST:event_jGTIToolBarToggleButtonAutoStepActionPerformed

    private void jGTIToolBarButtonEndStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonEndStepActionPerformed
        this.logic.handleEndStep();
    }//GEN-LAST:event_jGTIToolBarButtonEndStepActionPerformed

    private void jGTIToolBarButtonBeginStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonBeginStepActionPerformed
        this.logic.handleBeginStep();
    }//GEN-LAST:event_jGTIToolBarButtonBeginStepActionPerformed

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

private void jGTIToggleButtonAlgorithmItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIToggleButtonAlgorithmItemStateChanged
      this.logic.handleAlgorithmWindowChanged(this.jGTIToggleButtonAlgorithm.isSelected());
}//GEN-LAST:event_jGTIToggleButtonAlgorithmItemStateChanged

private void jGTIButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonPrintActionPerformed
      this.logic.handlePrint();
}//GEN-LAST:event_jGTIButtonPrintActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonOk;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonPrint;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelPrintAndAlgorithm;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneConverted;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneOriginal;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneOutline;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneGraph;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneOutline;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableOutline;
    public de.unisiegen.gtitool.ui.swing.JGTIToggleButton jGTIToggleButtonAlgorithm;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonBeginStep;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonEndStep;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonNextStep;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonPreviousStep;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonStop;
    public de.unisiegen.gtitool.ui.swing.JGTIToolBar jGTIToolBarMain;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton jGTIToolBarToggleButtonAutoStep;
    // End of variables declaration//GEN-END:variables
    
}
