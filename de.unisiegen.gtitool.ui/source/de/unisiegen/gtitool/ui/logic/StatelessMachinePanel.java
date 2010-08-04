package de.unisiegen.gtitool.ui.logic;


import java.awt.GridBagConstraints;
import java.io.File;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.RejectAction;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.AbstractLRMachine;
import de.unisiegen.gtitool.core.machines.AbstractStatelessMachine;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.StatelessMachine;
import de.unisiegen.gtitool.core.machines.dfa.AbstractLR;
import de.unisiegen.gtitool.core.machines.pda.DefaultTDP;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.MainWindow.ButtonState;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.DefaultStatelessMachineModel;
import de.unisiegen.gtitool.ui.model.LRMachineColumnModel;
import de.unisiegen.gtitool.ui.model.LRMachineTableModel;
import de.unisiegen.gtitool.ui.model.LRTableColumnModel;
import de.unisiegen.gtitool.ui.model.LRTableModel;
import de.unisiegen.gtitool.ui.model.PTTableColumnModel;
import de.unisiegen.gtitool.ui.model.PTTableModel;
import de.unisiegen.gtitool.ui.model.StatelessMachineTableModel;
import de.unisiegen.gtitool.ui.model.TDPMachineColumnModel;
import de.unisiegen.gtitool.ui.model.TDPMachineTableModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.AcceptedStatus;
import de.unisiegen.gtitool.ui.swing.JGTIPanel;
import de.unisiegen.gtitool.ui.swing.JGTIScrollPane;
import de.unisiegen.gtitool.ui.swing.JGTITable;


/**
 * MachinePanel for the {@link StatelessMachine}s
 * 
 * @author Christian Uhrhan
 */
public class StatelessMachinePanel extends MachinePanel
{

  /**
   * Signals the machine action type
   * 
   * @author Christian Uhrhan
   */
  public enum MachineActionType implements EntityType
  {
    /**
     * The machine action type 'start' (machine.start)
     */
    START,
    /**
     * The machine action type 'stepnext'
     */
    STEPNEXT,
    /**
     * The machine action type 'stepprevious'
     */
    STEPPREVIOUS,
    /**
     * The machine action type 'accept'
     */
    ACCEPT,

    /**
     * The machine action type 'reject'
     */
    REJECT,
    /**
     * The machine action type 'stop'
     */
    STOP;

    /**
     * The file ending.
     * 
     * @return The file ending.
     */
    public final String getFileEnding ()
    {
      return toString ().toLowerCase ();
    }


    /**
     * {@inheritDoc}
     * 
     * @see Enum#toString()
     */
    @Override
    public final String toString ()
    {
      switch ( this )
      {
        case START :
        {
          return "START"; //$NON-NLS-1$
        }
        case STEPNEXT :
        {
          return "STEPNEXT"; //$NON-NLS-1$
        }
        case STEPPREVIOUS :
        {
          return "STEPPREVIOUS"; //$NON-NLS-1$
        }
        case ACCEPT :
        {
          return "ACCEPT"; //$NON-NLS-1$
        }
        case STOP :
        {
          return "STOP"; //$NON-NLS-1$
        }
        case REJECT :
        {
          return "REJECT"; //$NON-NLS-1$
        }
      }
      throw new IllegalArgumentException ( "unsupported machine type" ); //$NON-NLS-1$
    }
  }


  /**
   * the {@link DefaultStatelessMachineModel}
   */
  private DefaultStatelessMachineModel model;


  /**
   * the {@link StatelessMachine}
   */
  private StatelessMachine machine;


  /**
   * the {@link JGTITable}
   */
  private JGTITable jGTIStatelessMachineTable;


  /**
   * the active state of the choose dialog
   */
  private boolean chooseDialogActive = false;


  /**
   * allocates a new {@link StatelessMachinePanel}
   * 
   * @param mainWindowForm the {@link MainWindowForm}
   * @param model the {@link DefaultStatelessMachineModel}
   * @param file the {@link File}
   */
  public StatelessMachinePanel ( final MainWindowForm mainWindowForm,
      final DefaultStatelessMachineModel model, final File file )
  {
    super ( mainWindowForm, file, model );
    this.model = model;
    this.machine = this.model.getMachine ();

    this.modified = true;

    initializeMachineTable ();
    initializeStatelessMachineTable ();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void setupModelMachine ( final DefaultMachineModel model )
  {
    this.model = ( DefaultStatelessMachineModel ) model;
    this.machine = this.model.getMachine ();
  }


  /**
   * Initializes the machine action table
   */
  private final void initializeMachineTable ()
  {
    /*
     * initialize the machine table
     */
    if ( this.machine instanceof DefaultTDP )
    {
      this.gui.jGTITableMachine.setModel ( new PTTableModel (
          ( CFG ) this.model.getGrammar () ) );
      this.gui.jGTITableMachine.setColumnModel ( new PTTableColumnModel (
          this.model.getGrammar ().getTerminalSymbolSet () ) );
      // we don't need the pda stack operation table
      setVisiblePDATable ( false );
    }
    else if ( this.machine instanceof AbstractLRMachine )
    {
      final AbstractLRMachine lrMachine = ( AbstractLRMachine ) this.machine;

      final AbstractLR automaton = lrMachine.getAutomaton ();

      this.gui.jGTITableMachine.setModel ( new LRTableModel ( lrMachine
          .getAutomaton ().getStates (), this.model.getGrammar ()
          .getTerminalSymbolSet (), lrMachine.getTableCellStrings () ) );
      this.gui.jGTITableMachine.setColumnModel ( new LRTableColumnModel (
          this.model.getGrammar ().getTerminalSymbolSet () ) );

      this.gui.jGTITableMachinePDA.setModel ( automaton );
      this.gui.jGTITableMachinePDA.setColumnModel ( automaton
          .getTableColumnModel () );
    }
    this.gui.jGTITableMachine.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableMachine
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTITableMachine.setCellSelectionEnabled ( true );
  }


  /**
   * Initializes the parsing table
   */
  private final void initializeStatelessMachineTable ()
  {
    this.jGTIStatelessMachineTable = new JGTITable ();
    JGTIPanel jGTIStatelessMachineTablePanel = new JGTIPanel ();
    JGTIScrollPane jGTIStatelessMachineTablePanelScrollPane = new JGTIScrollPane ();
    GridBagConstraints gridBagConstraints = new GridBagConstraints ();

    // setup the machine table
    if ( this.machine instanceof DefaultTDP )
    {
      this.jGTIStatelessMachineTable.setModel ( new TDPMachineTableModel () );
      this.jGTIStatelessMachineTable
          .setColumnModel ( new TDPMachineColumnModel () );
    }
    else if ( this.machine instanceof AbstractLRMachine )
    {
      this.jGTIStatelessMachineTable.setModel ( new LRMachineTableModel () );
      this.jGTIStatelessMachineTable
          .setColumnModel ( new LRMachineColumnModel () );
    }
    this.jGTIStatelessMachineTable.getTableHeader ().setReorderingAllowed (
        false );
    this.jGTIStatelessMachineTable
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );

    // setup scrollpane + viewport
    jGTIStatelessMachineTablePanelScrollPane.setBorder ( null );
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jGTIStatelessMachineTablePanel.add (
        jGTIStatelessMachineTablePanelScrollPane, gridBagConstraints );
    jGTIStatelessMachineTablePanelScrollPane
        .setViewportView ( this.jGTIStatelessMachineTable );

    this.gui.jGTISplitPaneTable
        .setLeftComponent ( jGTIStatelessMachineTablePanel );

    int loc = getMainWindowForm ().getWidth () / 2;
    this.gui.jGTISplitPaneTable.setDividerLocation ( loc );

    this.gui.wordPanelForm.jGTILabelStatus.setVisible ( false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#getMachine()
   */
  @Override
  protected Machine getMachine ()
  {
    return this.machine;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#getModel()
   */
  @Override
  public DefaultModel getModel ()
  {
    return this.model;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleMouseAdapter()
   */
  @Override
  protected void handleMouseAdapter ()
  {
    return;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleToolbarEnd(boolean)
   */
  @Override
  protected void handleToolbarEnd ( @SuppressWarnings ( "unused" ) boolean state )
  {
    return;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleToolbarStart(boolean)
   */
  @Override
  protected void handleToolbarStart (
      @SuppressWarnings ( "unused" ) boolean state )
  {
    return;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleConsoleTableValueChanged()
   */
  @Override
  protected void onHandleConsoleTableValueChanged ()
  {
    return;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleConsoleTableValueChangedHighlight(javax.swing.JTable)
   */
  @Override
  protected void onHandleConsoleTableValueChangedHighlight (
      @SuppressWarnings ( "unused" ) JTable table )
  {
    return;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getConverter(de.unisiegen.gtitool.core.entities.InputEntity.EntityType)
   */
  public Converter getConverter (
      @SuppressWarnings ( "unused" ) EntityType destination )
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getJTabbedPaneConsole()
   */
  public JTabbedPane getJTabbedPaneConsole ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleEnterWord()
   */
  @Override
  public void handleEnterWord ()
  {
    this.gui.wordPanelForm.jGTILabelStack.setVisible ( false );
    this.gui.wordPanelForm.styledStackParserPanel.setVisible ( false );
    this.gui.wordPanelForm.jGTILabelPushDownAlphabet.setVisible ( false );
    this.gui.wordPanelForm.styledAlphabetParserPanelPushDown
        .setVisible ( false );
    super.handleEnterWord ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleEditMachine()
   */
  @Override
  public void handleEditMachine ()
  {
    handleWordStop ();
    super.handleEditMachine ();
    this.gui.wordPanelForm.jGTILabelStack.setVisible ( true );
    this.gui.wordPanelForm.styledStackParserPanel.setVisible ( true );
    this.gui.wordPanelForm.jGTILabelPushDownAlphabet.setVisible ( true );
    this.gui.wordPanelForm.styledAlphabetParserPanelPushDown.setVisible ( true );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleWordStart()
   */
  @Override
  public boolean handleWordStart ()
  {
    boolean result = super.handleWordStart ();
    if ( result )
      performMachineTableChanged ( MachineActionType.START, null );
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleWordStop()
   */
  @Override
  public final void handleWordStop ()
  {
    super.handleWordStop ();

    performMachineTableChanged ( MachineActionType.STOP, null );
    updateAcceptedState ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleWordNextStep()
   */
  @Override
  public void handleWordNextStep ()
  {
    Action action = null;
    try
    {
      action = this.machine.autoTransit ();
    }
    catch ( MachineAmbigiousActionException exc )
    {
      if ( this.machine instanceof AbstractStatelessMachine )
      {
        ActionSet actions = null;
        try
        {
          actions = ( ( AbstractStatelessMachine ) this.machine )
              .getPossibleActions ();
        }
        catch ( ActionSetException exc1 )
        {
          exc1.printStackTrace ();
          System.exit ( 1 );
        }

        try
        {
          ChooseNextActionDialog cnad = new ChooseNextActionDialog (
              this.mainWindowForm, actions,
              ChooseNextActionDialog.TitleForm.NORMAL );
          cnad
              .setSelectionMode ( ChooseNextActionDialog.SelectionMode.SINGLE_SELECTION );
          this.chooseDialogActive = true;
          cnad.show ();
          this.chooseDialogActive = false;

          if ( cnad.isConfirmed () )
          {
            // we only allow single selection so the result is the first entry
            action = cnad.getChosenAction ().get ( 0 );
            ( ( AbstractStatelessMachine ) this.machine ).transit ( action );
          }
          else
            return;
        }
        catch ( IllegalArgumentException e )
        {
          e.printStackTrace ();
          System.exit ( 1 );
        }
        catch ( ActionSetException ase )
        {
          ase.printStackTrace ();
          System.exit ( 1 );
        }
      }
      else
        throw new RuntimeException (
            "handleWordNextStep not defined for ambigious steps of instances other than AbstractStatelessMachine" ); //$NON-NLS-1$
    }

    if ( action instanceof AcceptAction )
      performMachineTableChanged ( MachineActionType.ACCEPT, action );
    else if ( action instanceof RejectAction )
      performMachineTableChanged ( MachineActionType.REJECT, action );
    else
      performMachineTableChanged ( MachineActionType.STEPNEXT, action );

    updateAcceptedState ();

    this.mainWindowForm.getLogic ().updateWordNavigationStates ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#handleWordPreviousStep()
   */
  @Override
  public void handleWordPreviousStep ()
  {
    this.machine.backTransit ();

    performMachineTableChanged ( MachineActionType.STEPPREVIOUS, null );

    this.mainWindowForm.getLogic ().updateWordNavigationStates ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleExchange()
   */
  public void handleExchange ()
  {
    // do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleRedo()
   */
  public void handleRedo ()
  {
    // do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleToolbarEditDocument()
   */
  public void handleToolbarEditDocument ()
  {
    // do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleUndo()
   */
  public void handleUndo ()
  {
    // do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#isRedoAble()
   */
  public boolean isRedoAble ()
  {
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#isUndoAble()
   */
  public boolean isUndoAble ()
  {
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return this.modified;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
    this.modified = false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleMachinePDATableFocusLost()
   */
  @Override
  protected void onHandleMachinePDATableFocusLost ()
  {
    // do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleMachinePDATableMouseExited()
   */
  @Override
  protected void onHandleMachinePDATableMouseExited ()
  {
    // do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleMachineTableFocusLost()
   */
  @Override
  protected void onHandleMachineTableFocusLost ()
  {
    // do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#onHandleMachineTableMouseExited()
   */
  @Override
  protected void onHandleMachineTableMouseExited ()
  {
    // do nothing
  }


  /**
   * Updates the gui machine table
   * 
   * @param actionType The {@link MachineActionType}
   * @param action The {@link Action} is is going to take place
   */
  public final void performMachineTableChanged (
      final MachineActionType actionType, final Action action )
  {
    StatelessMachineTableModel smtm = ( StatelessMachineTableModel ) this.jGTIStatelessMachineTable
        .getModel ();

    switch ( actionType )
    {
      case STEPPREVIOUS :
        smtm.removeLastRow ();
        break;
      case START :
      case STEPNEXT :
        try
        {
          if ( smtm instanceof TDPMachineTableModel )
            smtm.addRow ( this.machine.getStack (), this.machine.getWord ()
                .getRemainingWord (), action );
          else
          {
            final LRMachineTableModel lrModel = ( LRMachineTableModel ) smtm;

            final AbstractLRMachine lrMachine = ( AbstractLRMachine ) this.machine;

            lrModel.addRow ( this.machine.getStack (), this.machine.getWord ()
                .getRemainingWord (), action, lrMachine.getAutomaton ()
                .getStateStack () );
          }
        }
        catch ( WordFinishedException exc )
        {
          exc.printStackTrace ();
          System.exit ( 1 );
        }
        break;
      case ACCEPT :
        smtm.accept ( ( AcceptAction ) action );
        break;
      case STOP :
        smtm.clearData ();
        break;
      case REJECT :
        smtm.reject ( ( RejectAction ) action );
        break;
    }
    this.jGTIStatelessMachineTable.repaint ();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public final void updateAcceptedState ()
  {
    if ( this.machineMode.equals ( MachineMode.WORD_NAVIGATION )
        && !this.machine.isNextSymbolAvailable () )
    {
      // word accepted
      if ( this.machine.isWordAccepted () )
        this.gui.wordPanelForm.styledWordParserPanel
            .setAcceptedStatus ( AcceptedStatus.ACCEPTED );
      // word not accepted
      else
        this.gui.wordPanelForm.styledWordParserPanel
            .setAcceptedStatus ( AcceptedStatus.NOT_ACCEPTED );
    }
    // no status
    else
    {
      this.gui.wordPanelForm.styledWordParserPanel
          .setAcceptedStatus ( AcceptedStatus.NONE );

      this.gui.wordPanelForm.jGTILabelStatus.setText ( Messages
          .getString ( "WordPanel.StatusEmpty" ) ); //$NON-NLS-1$
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.MachinePanel#autoStepTimerRun()
   */
  @Override
  void autoStepTimerRun ()
  {
    if ( StatelessMachinePanel.this.machine.isNextSymbolAvailable () )
      if ( !this.chooseDialogActive )
        handleWordNextStep ();
      else
      {
        StatelessMachinePanel.this.mainWindowForm.getLogic ()
            .removeButtonState ( ButtonState.SELECTED_AUTO_STEP );
        StatelessMachinePanel.this.mainWindowForm.getLogic ()
            .updateWordNavigationStates ();
        cancelAutoStepTimer ();
      }
  }


  /**
   * Tells if this machine is modified Initialilly all parsers are modified. If
   * they are saved, the flag is set to false.
   */
  private boolean modified;
}
