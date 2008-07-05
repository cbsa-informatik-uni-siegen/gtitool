/*
 * PrintDialog.java
 *
 * Created on June 25, 2008, 4:44 PM
 */

package de.unisiegen.gtitool.ui.netbeans;

import java.awt.Frame;

import de.unisiegen.gtitool.ui.logic.PrintDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;

/**
 * The {@link PrintDialogForm}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
@SuppressWarnings("all")
public class PrintDialogForm extends javax.swing.JDialog implements GUIClass < PrintDialog > {

  
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -4448708879986070934L;
  
  
    /**
     * The {@link PrintDialog}.
     */
    private PrintDialog logic;
  
  
    /** 
     * Creates new form PrintDialog
     * 
     * @param logic The {@link PrintDialog}. 
     * @param parent The parent {@link Frame}.
     **/
    public PrintDialogForm ( PrintDialog logic, Frame parent )
    {
      super ( parent, true );
      this.logic = logic;
      initComponents ();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass#getLogic()
     */
    public PrintDialog getLogic ()
    {
      return this.logic;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupMachinePanel = new javax.swing.ButtonGroup();
        buttonGroupPageFormat = new javax.swing.ButtonGroup();
        buttonGroupConvertMachine = new javax.swing.ButtonGroup();
        buttonGroupMininizeMachine = new javax.swing.ButtonGroup();
        buttonGroupReachableStates = new javax.swing.ButtonGroup();
        jGTIPanelPrinter = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTILabelPrinter = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIComboBoxPrinter = new de.unisiegen.gtitool.ui.swing.JGTIComboBox();
        jGTIPanelMachine = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIRadioButtonMachineGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIRadioButtonMachineTable = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIRadioButtonMachinePDATable = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIPanelMachineSpace = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelConvertMachine = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIRadioButtonConvertMachineOriginalGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIRadioButtonConvertMachineConvertedGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIRadioButtonConvertMachineTable = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIPanelConvertMachineSpace = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelMinimizeMachine = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIRadioButtonMinimizeMachineOriginalGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIRadioButtonMinimizeMachineMinimizedGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIRadioButtonMinimizeMachineTable = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIPanelMinimizeMachineSpace = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelReachableStates = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIRadioButtonReachableStatesGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIRadioButtonReachableStatesTable = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIPanelMachineSpace1 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelColumn0 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelColumn1 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelPageSetup = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelFormat = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTILabelPortraitIcon = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIRadioButtonPortrait = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTIRadioLandscape = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton();
        jGTILabelLandscapeIcon = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIPanelMargins = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jLabelMarginLeft = new javax.swing.JLabel();
        jSpinnerMarginLeft = new javax.swing.JSpinner();
        jLabelMarginRight = new javax.swing.JLabel();
        jSpinnerMarginRight = new javax.swing.JSpinner();
        jLabelMarginTop = new javax.swing.JLabel();
        jSpinnerMarginTop = new javax.swing.JSpinner();
        jLabelMarginBottom = new javax.swing.JLabel();
        jSpinnerMarginBottom = new javax.swing.JSpinner();
        jGTIPanelButton = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIButtonPrint = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        setTitle(bundle.getString("PrintDialog.Print")); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jGTIPanelPrinter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jGTILabelPrinter.setText(bundle.getString("PrintDialog.Printer")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jGTIPanelPrinter.add(jGTILabelPrinter, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jGTIPanelPrinter.add(jGTIComboBoxPrinter, gridBagConstraints);

        buttonGroupMachinePanel.add(jGTIRadioButtonMachineGraph);
        jGTIRadioButtonMachineGraph.setSelected(true);
        jGTIRadioButtonMachineGraph.setText(bundle.getString("PrintDialog.PrintMachine")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelMachine.add(jGTIRadioButtonMachineGraph, gridBagConstraints);

        buttonGroupMachinePanel.add(jGTIRadioButtonMachineTable);
        jGTIRadioButtonMachineTable.setText(bundle.getString("PrintDialog.PrintTable")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jGTIPanelMachine.add(jGTIRadioButtonMachineTable, gridBagConstraints);

        buttonGroupMachinePanel.add(jGTIRadioButtonMachinePDATable);
        jGTIRadioButtonMachinePDATable.setText(bundle.getString("PrintDialog.PrintPrintPDATable")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jGTIPanelMachine.add(jGTIRadioButtonMachinePDATable, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jGTIPanelMachine.add(jGTIPanelMachineSpace, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jGTIPanelPrinter.add(jGTIPanelMachine, gridBagConstraints);

        buttonGroupConvertMachine.add(jGTIRadioButtonConvertMachineOriginalGraph);
        jGTIRadioButtonConvertMachineOriginalGraph.setSelected(true);
        jGTIRadioButtonConvertMachineOriginalGraph.setText(bundle.getString("PrintDialog.ConvertMachineOriginalGraph")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelConvertMachine.add(jGTIRadioButtonConvertMachineOriginalGraph, gridBagConstraints);

        buttonGroupConvertMachine.add(jGTIRadioButtonConvertMachineConvertedGraph);
        jGTIRadioButtonConvertMachineConvertedGraph.setText(bundle.getString("PrintDialog.ConvertMachineConvertedGraph")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jGTIPanelConvertMachine.add(jGTIRadioButtonConvertMachineConvertedGraph, gridBagConstraints);

        buttonGroupConvertMachine.add(jGTIRadioButtonConvertMachineTable);
        jGTIRadioButtonConvertMachineTable.setText(bundle.getString("PrintDialog.ConvertMachineTable")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jGTIPanelConvertMachine.add(jGTIRadioButtonConvertMachineTable, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jGTIPanelConvertMachine.add(jGTIPanelConvertMachineSpace, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jGTIPanelPrinter.add(jGTIPanelConvertMachine, gridBagConstraints);

        buttonGroupMininizeMachine.add(jGTIRadioButtonMinimizeMachineOriginalGraph);
        jGTIRadioButtonMinimizeMachineOriginalGraph.setSelected(true);
        jGTIRadioButtonMinimizeMachineOriginalGraph.setText(bundle.getString("PrintDialog.MinimizeMachineOriginalGraph")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelMinimizeMachine.add(jGTIRadioButtonMinimizeMachineOriginalGraph, gridBagConstraints);

        buttonGroupMininizeMachine.add(jGTIRadioButtonMinimizeMachineMinimizedGraph);
        jGTIRadioButtonMinimizeMachineMinimizedGraph.setText(bundle.getString("PrintDialog.MinimizeMachineMinimizedGraph")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jGTIPanelMinimizeMachine.add(jGTIRadioButtonMinimizeMachineMinimizedGraph, gridBagConstraints);

        buttonGroupMininizeMachine.add(jGTIRadioButtonMinimizeMachineTable);
        jGTIRadioButtonMinimizeMachineTable.setText(bundle.getString("PrintDialog.MinimizeMachineTable")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jGTIPanelMinimizeMachine.add(jGTIRadioButtonMinimizeMachineTable, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jGTIPanelMinimizeMachine.add(jGTIPanelMinimizeMachineSpace, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jGTIPanelPrinter.add(jGTIPanelMinimizeMachine, gridBagConstraints);

        buttonGroupReachableStates.add(jGTIRadioButtonReachableStatesGraph);
        jGTIRadioButtonReachableStatesGraph.setSelected(true);
        jGTIRadioButtonReachableStatesGraph.setText(bundle.getString("PrintDialog.PrintMachine")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelReachableStates.add(jGTIRadioButtonReachableStatesGraph, gridBagConstraints);

        buttonGroupReachableStates.add(jGTIRadioButtonReachableStatesTable);
        jGTIRadioButtonReachableStatesTable.setText(bundle.getString("PrintDialog.PrintTable")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jGTIPanelReachableStates.add(jGTIRadioButtonReachableStatesTable, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jGTIPanelReachableStates.add(jGTIPanelMachineSpace1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jGTIPanelPrinter.add(jGTIPanelReachableStates, gridBagConstraints);

        jGTIPanelColumn0.setMinimumSize(new java.awt.Dimension(100, 5));
        jGTIPanelColumn0.setPreferredSize(new java.awt.Dimension(100, 5));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 100;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelPrinter.add(jGTIPanelColumn0, gridBagConstraints);

        jGTIPanelColumn1.setMinimumSize(new java.awt.Dimension(200, 5));
        jGTIPanelColumn1.setPreferredSize(new java.awt.Dimension(200, 5));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 100;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelPrinter.add(jGTIPanelColumn1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jGTIPanelPrinter, gridBagConstraints);

        jGTIPanelPageSetup.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jGTILabelPortraitIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/large/portrait.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        jGTIPanelFormat.add(jGTILabelPortraitIcon, gridBagConstraints);

        buttonGroupPageFormat.add(jGTIRadioButtonPortrait);
        jGTIRadioButtonPortrait.setSelected(true);
        jGTIRadioButtonPortrait.setText(bundle.getString("PrintDialog.Portrait")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        jGTIPanelFormat.add(jGTIRadioButtonPortrait, gridBagConstraints);

        buttonGroupPageFormat.add(jGTIRadioLandscape);
        jGTIRadioLandscape.setText(bundle.getString("PrintDialog.Landscape")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jGTIPanelFormat.add(jGTIRadioLandscape, gridBagConstraints);

        jGTILabelLandscapeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/large/landscape.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        jGTIPanelFormat.add(jGTILabelLandscapeIcon, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelPageSetup.add(jGTIPanelFormat, gridBagConstraints);

        jLabelMarginLeft.setText(bundle.getString("PrintDialog.MarginLeft")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jGTIPanelMargins.add(jLabelMarginLeft, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelMargins.add(jSpinnerMarginLeft, gridBagConstraints);

        jLabelMarginRight.setText(bundle.getString("PrintDialog.MarginRight")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelMargins.add(jLabelMarginRight, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jGTIPanelMargins.add(jSpinnerMarginRight, gridBagConstraints);

        jLabelMarginTop.setText(bundle.getString("PrintDialog.MarginTop")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jGTIPanelMargins.add(jLabelMarginTop, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelMargins.add(jSpinnerMarginTop, gridBagConstraints);

        jLabelMarginBottom.setText(bundle.getString("PrintDialog.MarginBottom")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelMargins.add(jLabelMarginBottom, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jGTIPanelMargins.add(jSpinnerMarginBottom, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelPageSetup.add(jGTIPanelMargins, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        getContentPane().add(jGTIPanelPageSetup, gridBagConstraints);

        jGTIButtonPrint.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PrintDialog.ButtonPrintMnemonic").charAt(0));
        jGTIButtonPrint.setText(bundle.getString("PrintDialog.ButtonPrint")); // NOI18N
        jGTIButtonPrint.setToolTipText(bundle.getString("PrintDialog.ButtonPrintToolTip")); // NOI18N
        jGTIButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonPrintActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jGTIPanelButton.add(jGTIButtonPrint, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PrintDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("PrintDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("PrintDialog.CancelToolTip")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jGTIPanelButton.add(jGTIButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        getContentPane().add(jGTIPanelButton, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
  this.logic.handleCancel();
}//GEN-LAST:event_formWindowClosing

private void jGTIButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonPrintActionPerformed
  this.logic.handlePrint();
}//GEN-LAST:event_jGTIButtonPrintActionPerformed

private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
  this.logic.handleCancel();
}//GEN-LAST:event_jGTIButtonCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.ButtonGroup buttonGroupConvertMachine;
    public javax.swing.ButtonGroup buttonGroupMachinePanel;
    public javax.swing.ButtonGroup buttonGroupMininizeMachine;
    public javax.swing.ButtonGroup buttonGroupPageFormat;
    public javax.swing.ButtonGroup buttonGroupReachableStates;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonPrint;
    public de.unisiegen.gtitool.ui.swing.JGTIComboBox jGTIComboBoxPrinter;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelLandscapeIcon;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelPortraitIcon;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelPrinter;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelButton;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelColumn0;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelColumn1;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelConvertMachine;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelConvertMachineSpace;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelFormat;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelMachine;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelMachineSpace;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelMachineSpace1;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelMargins;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelMinimizeMachine;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelMinimizeMachineSpace;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelPageSetup;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelPrinter;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelReachableStates;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonConvertMachineConvertedGraph;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonConvertMachineOriginalGraph;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonConvertMachineTable;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonMachineGraph;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonMachinePDATable;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonMachineTable;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonMinimizeMachineMinimizedGraph;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonMinimizeMachineOriginalGraph;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonMinimizeMachineTable;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonPortrait;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonReachableStatesGraph;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioButtonReachableStatesTable;
    public de.unisiegen.gtitool.ui.swing.JGTIRadioButton jGTIRadioLandscape;
    public javax.swing.JLabel jLabelMarginBottom;
    public javax.swing.JLabel jLabelMarginLeft;
    public javax.swing.JLabel jLabelMarginRight;
    public javax.swing.JLabel jLabelMarginTop;
    public javax.swing.JSpinner jSpinnerMarginBottom;
    public javax.swing.JSpinner jSpinnerMarginLeft;
    public javax.swing.JSpinner jSpinnerMarginRight;
    public javax.swing.JSpinner jSpinnerMarginTop;
    // End of variables declaration//GEN-END:variables


}
