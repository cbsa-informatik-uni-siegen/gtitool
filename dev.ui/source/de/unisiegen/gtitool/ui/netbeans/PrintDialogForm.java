/*
 * PrintDialog.java Created on June 25, 2008, 4:44 PM
 */

package de.unisiegen.gtitool.ui.netbeans;


import java.awt.Frame;

import javax.swing.JDialog;

import de.unisiegen.gtitool.ui.logic.PrintDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;


/**
 * The {@link PrintDialogForm}.
 * 
 * @author Benjamin Mies
 * @version $Id: PrintDialogForm.java 1101 2008-07-05 13:26:27Z fehler $
 */
@SuppressWarnings ( "all" )
public class PrintDialogForm extends javax.swing.JDialog implements
    GUIClass < PrintDialog >
{

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
   * Creates new form PrintDialog
   * 
   * @param logic The {@link PrintDialog}.
   * @param parent The parent {@link JDialog}.
   **/
  public PrintDialogForm ( PrintDialog logic, JDialog parent )
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


  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings ( "unchecked" )
  // <editor-fold defaultstate="collapsed"
  // desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents ()
  {
    java.awt.GridBagConstraints gridBagConstraints;

    this.buttonGroupMachinePanel = new javax.swing.ButtonGroup ();
    this.buttonGroupPageFormat = new javax.swing.ButtonGroup ();
    this.buttonGroupConvertMachine = new javax.swing.ButtonGroup ();
    this.buttonGroupMininizeMachine = new javax.swing.ButtonGroup ();
    this.buttonGroupReachableStates = new javax.swing.ButtonGroup ();
    this.jGTIPanelPrinter = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTILabelPrinter = new de.unisiegen.gtitool.ui.swing.JGTILabel ();
    this.jGTIComboBoxPrinter = new de.unisiegen.gtitool.ui.swing.JGTIComboBox ();
    this.jGTIPanelMachine = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIRadioButtonMachineGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIRadioButtonMachineTable = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIRadioButtonMachinePDATable = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIPanelMachineSpace = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIPanelConvertMachine = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIRadioButtonConvertMachineOriginalGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIRadioButtonConvertMachineConvertedGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIRadioButtonConvertMachineTable = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIPanelConvertMachineSpace = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIPanelMinimizeMachine = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIRadioButtonMinimizeMachineOriginalGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIRadioButtonMinimizeMachineMinimizedGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIRadioButtonMinimizeMachineTable = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIPanelMinimizeMachineSpace = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIPanelReachableStates = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIRadioButtonReachableStatesGraph = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIRadioButtonReachableStatesTable = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIPanelMachineSpace1 = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIPanelColumn0 = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIPanelColumn1 = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIPanelPageSetup = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIPanelFormat = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTILabelPortraitIcon = new de.unisiegen.gtitool.ui.swing.JGTILabel ();
    this.jGTIRadioButtonPortrait = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTIRadioLandscape = new de.unisiegen.gtitool.ui.swing.JGTIRadioButton ();
    this.jGTILabelLandscapeIcon = new de.unisiegen.gtitool.ui.swing.JGTILabel ();
    this.jGTIPanelMargins = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jLabelMarginLeft = new javax.swing.JLabel ();
    this.jSpinnerMarginLeft = new javax.swing.JSpinner ();
    this.jLabelMarginRight = new javax.swing.JLabel ();
    this.jSpinnerMarginRight = new javax.swing.JSpinner ();
    this.jLabelMarginTop = new javax.swing.JLabel ();
    this.jSpinnerMarginTop = new javax.swing.JSpinner ();
    this.jLabelMarginBottom = new javax.swing.JLabel ();
    this.jSpinnerMarginBottom = new javax.swing.JSpinner ();
    this.jGTIPanelButton = new de.unisiegen.gtitool.ui.swing.JGTIPanel ();
    this.jGTIButtonPrint = new de.unisiegen.gtitool.ui.swing.JGTIButton ();
    this.jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton ();

    setDefaultCloseOperation ( javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE );
    java.util.ResourceBundle bundle = java.util.ResourceBundle
        .getBundle ( "de/unisiegen/gtitool/ui/i18n/messages" ); // NOI18N
    setTitle ( bundle.getString ( "PrintDialog.Print" ) ); // NOI18N
    setResizable ( false );
    addWindowListener ( new java.awt.event.WindowAdapter ()
    {

      public void windowClosing ( java.awt.event.WindowEvent evt )
      {
        formWindowClosing ( evt );
      }
    } );
    getContentPane ().setLayout ( new java.awt.GridBagLayout () );

    this.jGTIPanelPrinter.setBorder ( javax.swing.BorderFactory
        .createLineBorder ( new java.awt.Color ( 0, 0, 0 ) ) );

    this.jGTILabelPrinter.setText ( bundle.getString ( "PrintDialog.Printer" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 0, 5 );
    this.jGTIPanelPrinter.add ( this.jGTILabelPrinter, gridBagConstraints );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 0, 5 );
    this.jGTIPanelPrinter.add ( this.jGTIComboBoxPrinter, gridBagConstraints );

    this.buttonGroupMachinePanel.add ( this.jGTIRadioButtonMachineGraph );
    this.jGTIRadioButtonMachineGraph.setSelected ( true );
    this.jGTIRadioButtonMachineGraph.setText ( bundle
        .getString ( "PrintDialog.PrintMachine" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 0, 0, 5 );
    this.jGTIPanelMachine.add ( this.jGTIRadioButtonMachineGraph,
        gridBagConstraints );

    this.buttonGroupMachinePanel.add ( this.jGTIRadioButtonMachineTable );
    this.jGTIRadioButtonMachineTable.setText ( bundle
        .getString ( "PrintDialog.PrintTable" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 5, 0, 5 );
    this.jGTIPanelMachine.add ( this.jGTIRadioButtonMachineTable,
        gridBagConstraints );

    this.buttonGroupMachinePanel.add ( this.jGTIRadioButtonMachinePDATable );
    this.jGTIRadioButtonMachinePDATable.setText ( bundle
        .getString ( "PrintDialog.PrintPrintPDATable" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 5, 0, 0 );
    this.jGTIPanelMachine.add ( this.jGTIRadioButtonMachinePDATable,
        gridBagConstraints );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    this.jGTIPanelMachine.add ( this.jGTIPanelMachineSpace, gridBagConstraints );

    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.insets = new java.awt.Insets ( 10, 5, 0, 5 );
    this.jGTIPanelPrinter.add ( this.jGTIPanelMachine, gridBagConstraints );

    this.buttonGroupConvertMachine
        .add ( this.jGTIRadioButtonConvertMachineOriginalGraph );
    this.jGTIRadioButtonConvertMachineOriginalGraph.setSelected ( true );
    this.jGTIRadioButtonConvertMachineOriginalGraph.setText ( bundle
        .getString ( "PrintDialog.ConvertMachineOriginalGraph" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 0, 0, 5 );
    this.jGTIPanelConvertMachine.add (
        this.jGTIRadioButtonConvertMachineOriginalGraph, gridBagConstraints );

    this.buttonGroupConvertMachine
        .add ( this.jGTIRadioButtonConvertMachineConvertedGraph );
    this.jGTIRadioButtonConvertMachineConvertedGraph.setText ( bundle
        .getString ( "PrintDialog.ConvertMachineConvertedGraph" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 5, 0, 5 );
    this.jGTIPanelConvertMachine.add (
        this.jGTIRadioButtonConvertMachineConvertedGraph, gridBagConstraints );

    this.buttonGroupConvertMachine
        .add ( this.jGTIRadioButtonConvertMachineTable );
    this.jGTIRadioButtonConvertMachineTable.setText ( bundle
        .getString ( "PrintDialog.ConvertMachineTable" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 5, 0, 0 );
    this.jGTIPanelConvertMachine.add ( this.jGTIRadioButtonConvertMachineTable,
        gridBagConstraints );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    this.jGTIPanelConvertMachine.add ( this.jGTIPanelConvertMachineSpace,
        gridBagConstraints );

    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.insets = new java.awt.Insets ( 10, 5, 0, 5 );
    this.jGTIPanelPrinter.add ( this.jGTIPanelConvertMachine,
        gridBagConstraints );

    this.buttonGroupMininizeMachine
        .add ( this.jGTIRadioButtonMinimizeMachineOriginalGraph );
    this.jGTIRadioButtonMinimizeMachineOriginalGraph.setSelected ( true );
    this.jGTIRadioButtonMinimizeMachineOriginalGraph.setText ( bundle
        .getString ( "PrintDialog.MinimizeMachineOriginalGraph" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 0, 0, 5 );
    this.jGTIPanelMinimizeMachine.add (
        this.jGTIRadioButtonMinimizeMachineOriginalGraph, gridBagConstraints );

    this.buttonGroupMininizeMachine
        .add ( this.jGTIRadioButtonMinimizeMachineMinimizedGraph );
    this.jGTIRadioButtonMinimizeMachineMinimizedGraph.setText ( bundle
        .getString ( "PrintDialog.MinimizeMachineMinimizedGraph" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 5, 0, 5 );
    this.jGTIPanelMinimizeMachine.add (
        this.jGTIRadioButtonMinimizeMachineMinimizedGraph, gridBagConstraints );

    this.buttonGroupMininizeMachine
        .add ( this.jGTIRadioButtonMinimizeMachineTable );
    this.jGTIRadioButtonMinimizeMachineTable.setText ( bundle
        .getString ( "PrintDialog.MinimizeMachineTable" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 5, 0, 0 );
    this.jGTIPanelMinimizeMachine.add (
        this.jGTIRadioButtonMinimizeMachineTable, gridBagConstraints );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    this.jGTIPanelMinimizeMachine.add ( this.jGTIPanelMinimizeMachineSpace,
        gridBagConstraints );

    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.insets = new java.awt.Insets ( 10, 5, 0, 5 );
    this.jGTIPanelPrinter.add ( this.jGTIPanelMinimizeMachine,
        gridBagConstraints );

    this.buttonGroupReachableStates
        .add ( this.jGTIRadioButtonReachableStatesGraph );
    this.jGTIRadioButtonReachableStatesGraph.setSelected ( true );
    this.jGTIRadioButtonReachableStatesGraph.setText ( bundle
        .getString ( "PrintDialog.PrintMachine" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 0, 0, 5 );
    this.jGTIPanelReachableStates.add (
        this.jGTIRadioButtonReachableStatesGraph, gridBagConstraints );

    this.buttonGroupReachableStates
        .add ( this.jGTIRadioButtonReachableStatesTable );
    this.jGTIRadioButtonReachableStatesTable.setText ( bundle
        .getString ( "PrintDialog.PrintTable" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 5, 0, 5 );
    this.jGTIPanelReachableStates.add (
        this.jGTIRadioButtonReachableStatesTable, gridBagConstraints );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    this.jGTIPanelReachableStates.add ( this.jGTIPanelMachineSpace1,
        gridBagConstraints );

    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.insets = new java.awt.Insets ( 10, 5, 0, 5 );
    this.jGTIPanelPrinter.add ( this.jGTIPanelReachableStates,
        gridBagConstraints );

    this.jGTIPanelColumn0.setMinimumSize ( new java.awt.Dimension ( 100, 5 ) );
    this.jGTIPanelColumn0.setPreferredSize ( new java.awt.Dimension ( 100, 5 ) );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 100;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weighty = 1.0;
    this.jGTIPanelPrinter.add ( this.jGTIPanelColumn0, gridBagConstraints );

    this.jGTIPanelColumn1.setMinimumSize ( new java.awt.Dimension ( 200, 5 ) );
    this.jGTIPanelColumn1.setPreferredSize ( new java.awt.Dimension ( 200, 5 ) );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 100;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    this.jGTIPanelPrinter.add ( this.jGTIPanelColumn1, gridBagConstraints );

    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.insets = new java.awt.Insets ( 16, 16, 5, 16 );
    getContentPane ().add ( this.jGTIPanelPrinter, gridBagConstraints );

    this.jGTIPanelPageSetup.setBorder ( javax.swing.BorderFactory
        .createLineBorder ( new java.awt.Color ( 0, 0, 0 ) ) );

    this.jGTILabelPortraitIcon.setIcon ( new javax.swing.ImageIcon (
        getClass ().getResource (
            "/de/unisiegen/gtitool/ui/icon/large/portrait.png" ) ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 0, 5, 5 );
    this.jGTIPanelFormat.add ( this.jGTILabelPortraitIcon, gridBagConstraints );

    this.buttonGroupPageFormat.add ( this.jGTIRadioButtonPortrait );
    this.jGTIRadioButtonPortrait.setSelected ( true );
    this.jGTIRadioButtonPortrait.setText ( bundle
        .getString ( "PrintDialog.Portrait" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 5, 5, 0 );
    this.jGTIPanelFormat
        .add ( this.jGTIRadioButtonPortrait, gridBagConstraints );

    this.buttonGroupPageFormat.add ( this.jGTIRadioLandscape );
    this.jGTIRadioLandscape.setText ( bundle
        .getString ( "PrintDialog.Landscape" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 0, 0 );
    this.jGTIPanelFormat.add ( this.jGTIRadioLandscape, gridBagConstraints );

    this.jGTILabelLandscapeIcon.setIcon ( new javax.swing.ImageIcon (
        getClass ().getResource (
            "/de/unisiegen/gtitool/ui/icon/large/landscape.png" ) ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 0, 0, 5 );
    this.jGTIPanelFormat.add ( this.jGTILabelLandscapeIcon, gridBagConstraints );

    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 5, 5 );
    this.jGTIPanelPageSetup.add ( this.jGTIPanelFormat, gridBagConstraints );

    this.jLabelMarginLeft.setText ( bundle
        .getString ( "PrintDialog.MarginLeft" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 0, 5, 5 );
    this.jGTIPanelMargins.add ( this.jLabelMarginLeft, gridBagConstraints );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 5, 5 );
    this.jGTIPanelMargins.add ( this.jSpinnerMarginLeft, gridBagConstraints );

    this.jLabelMarginRight.setText ( bundle
        .getString ( "PrintDialog.MarginRight" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 5, 5 );
    this.jGTIPanelMargins.add ( this.jLabelMarginRight, gridBagConstraints );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 5, 0 );
    this.jGTIPanelMargins.add ( this.jSpinnerMarginRight, gridBagConstraints );

    this.jLabelMarginTop
        .setText ( bundle.getString ( "PrintDialog.MarginTop" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 0, 5, 5 );
    this.jGTIPanelMargins.add ( this.jLabelMarginTop, gridBagConstraints );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 5, 5 );
    this.jGTIPanelMargins.add ( this.jSpinnerMarginTop, gridBagConstraints );

    this.jLabelMarginBottom.setText ( bundle
        .getString ( "PrintDialog.MarginBottom" ) ); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 5, 5 );
    this.jGTIPanelMargins.add ( this.jLabelMarginBottom, gridBagConstraints );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 5, 0 );
    this.jGTIPanelMargins.add ( this.jSpinnerMarginBottom, gridBagConstraints );

    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 5, 5, 5 );
    this.jGTIPanelPageSetup.add ( this.jGTIPanelMargins, gridBagConstraints );

    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 16, 5, 16 );
    getContentPane ().add ( this.jGTIPanelPageSetup, gridBagConstraints );

    this.jGTIButtonPrint.setMnemonic ( java.util.ResourceBundle.getBundle (
        "de/unisiegen/gtitool/ui/i18n/messages" ).getString (
        "PrintDialog.ButtonPrintMnemonic" ).charAt ( 0 ) );
    this.jGTIButtonPrint.setText ( bundle
        .getString ( "PrintDialog.ButtonPrint" ) ); // NOI18N
    this.jGTIButtonPrint.setToolTipText ( bundle
        .getString ( "PrintDialog.ButtonPrintToolTip" ) ); // NOI18N
    this.jGTIButtonPrint
        .addActionListener ( new java.awt.event.ActionListener ()
        {

          public void actionPerformed ( java.awt.event.ActionEvent evt )
          {
            jGTIButtonPrintActionPerformed ( evt );
          }
        } );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 0, 0, 5 );
    this.jGTIPanelButton.add ( this.jGTIButtonPrint, gridBagConstraints );

    this.jGTIButtonCancel.setMnemonic ( java.util.ResourceBundle.getBundle (
        "de/unisiegen/gtitool/ui/i18n/messages" ).getString (
        "PrintDialog.CancelMnemonic" ).charAt ( 0 ) );
    this.jGTIButtonCancel.setText ( bundle.getString ( "PrintDialog.Cancel" ) ); // NOI18N
    this.jGTIButtonCancel.setToolTipText ( bundle
        .getString ( "PrintDialog.CancelToolTip" ) ); // NOI18N
    this.jGTIButtonCancel
        .addActionListener ( new java.awt.event.ActionListener ()
        {

          public void actionPerformed ( java.awt.event.ActionEvent evt )
          {
            jGTIButtonCancelActionPerformed ( evt );
          }
        } );
    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets ( 0, 5, 0, 0 );
    this.jGTIPanelButton.add ( this.jGTIButtonCancel, gridBagConstraints );

    gridBagConstraints = new java.awt.GridBagConstraints ();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets ( 5, 16, 16, 16 );
    getContentPane ().add ( this.jGTIPanelButton, gridBagConstraints );

    pack ();
  }// </editor-fold>//GEN-END:initComponents


  private void formWindowClosing ( java.awt.event.WindowEvent evt )
  {// GEN-FIRST:event_formWindowClosing
    this.logic.handleCancel ();
  }// GEN-LAST:event_formWindowClosing


  private void jGTIButtonPrintActionPerformed ( java.awt.event.ActionEvent evt )
  {// GEN-FIRST:event_jGTIButtonPrintActionPerformed
    this.logic.handlePrint ();
  }// GEN-LAST:event_jGTIButtonPrintActionPerformed


  private void jGTIButtonCancelActionPerformed ( java.awt.event.ActionEvent evt )
  {// GEN-FIRST:event_jGTIButtonCancelActionPerformed
    this.logic.handleCancel ();
  }// GEN-LAST:event_jGTIButtonCancelActionPerformed


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
