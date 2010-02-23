package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.StateMachine;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultStateMachineModel;
import de.unisiegen.gtitool.ui.model.ReachableStatesTableColumnModel;
import de.unisiegen.gtitool.ui.model.ReachableStatesTableModel;
import de.unisiegen.gtitool.ui.netbeans.ReachableStatesDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The {@link ReachableStatesDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ReachableStatesDialog implements
    LogicClass < ReachableStatesDialogForm >
{

  /**
   * Does the next step after a delay.
   * 
   * @author Christian Fehler
   */
  protected final class AutoStepTimerTask extends TimerTask
  {

    /**
     * {@inheritDoc}
     * 
     * @see TimerTask#run()
     */
    @Override
    public final void run ()
    {
      SwingUtilities.invokeLater ( new Runnable ()
      {

        public void run ()
        {
          if ( ReachableStatesDialog.this.endReached )
          {
            handleStop ();
          }
          else
          {
            performNextStep ( true );
          }
        }
      } );
    }
  }


  /**
   * The {@link Step} enum.
   * 
   * @author Christian Fehler
   */
  private enum Step
  {
    /**
     * The activate next {@link State} step.
     */
    ACTIVATE_NEXT_STATE,

    /**
     * The activate reachable {@link State}s step.
     */
    ACTIVATE_REACHABLE_STATES,

    /**
     * The activate start {@link State} step.
     */
    ACTIVATE_START_STATE,

    /**
     * The finish step.
     */
    FINISH;

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
        case ACTIVATE_START_STATE :
        {
          return "activate start state"; //$NON-NLS-1$
        }
        case ACTIVATE_NEXT_STATE :
        {
          return "activate next state"; //$NON-NLS-1$
        }
        case ACTIVATE_REACHABLE_STATES :
        {
          return "activate reachable states"; //$NON-NLS-1$
        }
        case FINISH :
        {
          return "finish"; //$NON-NLS-1$
        }
      }
      throw new RuntimeException ( "unsupported step" );//$NON-NLS-1$
    }
  }


  /**
   * The {@link StepItem}.
   * 
   * @author Christian Fehler
   */
  private class StepItem
  {

    /**
     * The {@link State}s which are calculated.
     */
    private ArrayList < State > activeCalculatedStates;


    /**
     * The active {@link State}s of the original {@link JGTIGraph}.
     */
    private ArrayList < State > activeStatesOriginal;


    /**
     * The active {@link State}s of the result {@link JGTIGraph}.
     */
    private ArrayList < State > activeStatesResult;


    /**
     * The active {@link Step}.
     */
    private Step activeStep;


    /**
     * The active {@link Symbol}s of the original {@link JGTIGraph}.
     */
    private ArrayList < Symbol > activeSymbolsOriginal;


    /**
     * The active {@link Symbol}s of the result {@link JGTIGraph}.
     */
    private ArrayList < Symbol > activeSymbolsResult;


    /**
     * The {@link State} which must be calculated.
     */
    private ArrayList < State > activeToCalculateStates;


    /**
     * The active {@link Transition}s of the original {@link JGTIGraph}.
     */
    private ArrayList < Transition > activeTransitionsOriginal;


    /**
     * The active {@link Transition}s of the result {@link JGTIGraph}.
     */
    private ArrayList < Transition > activeTransitionsResult;


    /**
     * The overwritten {@link Color} {@link DefaultStateView}s.
     */
    private ArrayList < DefaultStateView > overwrittenColorOriginal;


    /**
     * Allocates a new {@link StepItem}.
     * 
     * @param activeStep The active {@link Step}.
     * @param activeToCalculateStates The {@link State} which must be
     *          calculated.
     * @param activeCalculatedStates The {@link State}s which are calculated.
     * @param activeStatesOriginal The active {@link State}s of the original
     *          {@link JGTIGraph}.
     * @param activeStatesResult The active {@link State}s of the result
     *          {@link JGTIGraph}.
     * @param activeTransitionsOriginal The active {@link Transition}s of the
     *          original {@link JGTIGraph}.
     * @param activeTransitionsResult The active {@link Transition}s of the
     *          result {@link JGTIGraph}.
     * @param activeSymbolsOriginal The active {@link Symbol}s of the original
     *          {@link JGTIGraph}.
     * @param activeSymbolsResult The active {@link Symbol}s of the result
     *          {@link JGTIGraph}.
     * @param overwrittenColorOriginal The overwritten {@link Color}
     *          {@link DefaultStateView}s.
     */
    public StepItem ( Step activeStep,
        ArrayList < State > activeToCalculateStates,
        ArrayList < State > activeCalculatedStates,
        ArrayList < State > activeStatesOriginal,
        ArrayList < State > activeStatesResult,
        ArrayList < Transition > activeTransitionsOriginal,
        ArrayList < Transition > activeTransitionsResult,
        ArrayList < Symbol > activeSymbolsOriginal,
        ArrayList < Symbol > activeSymbolsResult,
        ArrayList < DefaultStateView > overwrittenColorOriginal )
    {
      this.activeStep = activeStep;
      this.activeToCalculateStates = activeToCalculateStates;
      this.activeCalculatedStates = activeCalculatedStates;
      this.activeStatesOriginal = activeStatesOriginal;
      this.activeStatesResult = activeStatesResult;
      this.activeTransitionsOriginal = activeTransitionsOriginal;
      this.activeTransitionsResult = activeTransitionsResult;
      this.activeSymbolsOriginal = activeSymbolsOriginal;
      this.activeSymbolsResult = activeSymbolsResult;
      this.overwrittenColorOriginal = overwrittenColorOriginal;
    }


    /**
     * Returns the activeCalculatedStates.
     * 
     * @return The activeCalculatedStates.
     * @see #activeCalculatedStates
     */
    public final ArrayList < State > getActiveCalculatedStates ()
    {
      return this.activeCalculatedStates;
    }


    /**
     * Returns the activeStatesOriginal.
     * 
     * @return The activeStatesOriginal.
     * @see #activeStatesOriginal
     */
    public final ArrayList < State > getActiveStatesOriginal ()
    {
      return this.activeStatesOriginal;
    }


    /**
     * Returns the activeStatesResult.
     * 
     * @return The activeStatesResult.
     * @see #activeStatesResult
     */
    public final ArrayList < State > getActiveStatesResult ()
    {
      return this.activeStatesResult;
    }


    /**
     * Returns the activeStep.
     * 
     * @return The activeStep.
     * @see #activeStep
     */
    public final Step getActiveStep ()
    {
      return this.activeStep;
    }


    /**
     * Returns the activeSymbolsOriginal.
     * 
     * @return The activeSymbolsOriginal.
     * @see #activeSymbolsOriginal
     */
    public final ArrayList < Symbol > getActiveSymbolsOriginal ()
    {
      return this.activeSymbolsOriginal;
    }


    /**
     * Returns the activeSymbolsResult.
     * 
     * @return The activeSymbolsResult.
     * @see #activeSymbolsResult
     */
    public final ArrayList < Symbol > getActiveSymbolsResult ()
    {
      return this.activeSymbolsResult;
    }


    /**
     * Returns the activeToCalculateStates.
     * 
     * @return The activeToCalculateStates.
     * @see #activeToCalculateStates
     */
    public final ArrayList < State > getActiveToCalculateStates ()
    {
      return this.activeToCalculateStates;
    }


    /**
     * Returns the activeTransitionsOriginal.
     * 
     * @return The activeTransitionsOriginal.
     * @see #activeTransitionsOriginal
     */
    public final ArrayList < Transition > getActiveTransitionsOriginal ()
    {
      return this.activeTransitionsOriginal;
    }


    /**
     * Returns the activeTransitionsResult.
     * 
     * @return The activeTransitionsResult.
     * @see #activeTransitionsResult
     */
    public final ArrayList < Transition > getActiveTransitionsResult ()
    {
      return this.activeTransitionsResult;
    }


    /**
     * Returns the overwrittenColorOriginal.
     * 
     * @return The overwrittenColorOriginal.
     * @see #overwrittenColorOriginal
     */
    public final ArrayList < DefaultStateView > getOverwrittenColorOriginal ()
    {
      return this.overwrittenColorOriginal;
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ReachableStatesDialog.class );


  /**
   * The reachable {@link Color}.
   */
  private final static Color REACHABLE_COLOR = Color.YELLOW;


  /**
   * The auto step {@link Timer}.
   */
  private Timer autoStepTimer = null;


  /**
   * The {@link State}s which are calculated.
   */
  private ArrayList < State > calculatedStates = new ArrayList < State > ();


  /**
   * Flag that indicates if the end is reached.
   */
  protected boolean endReached = false;


  /**
   * The {@link ReachableStatesDialogForm}.
   */
  private ReachableStatesDialogForm gui;


  /**
   * The original {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphOriginal;


  /**
   * The original {@link StateMachine}.
   */
  private StateMachine machineOriginal;


  /**
   * The {@link StateMachinePanel}.
   */
  private StateMachinePanel machinePanel;


  /**
   * The result {@link StateMachine}.
   */
  private StateMachine machineResult;


  /**
   * The original {@link DefaultStateMachineModel}.
   */
  private DefaultStateMachineModel modelOriginal;


  /**
   * The result {@link DefaultStateMachineModel}.
   */
  private DefaultStateMachineModel modelResult;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link ReachableStatesTableModel}.
   */
  private ReachableStatesTableModel reachableStatesTableModel;


  /**
   * The current {@link Step}.
   */
  private Step step = null;


  /**
   * The {@link StepItem} list.
   */
  private ArrayList < StepItem > stepItemList = new ArrayList < StepItem > ();


  /**
   * The {@link ReachableStatesTableColumnModel}.
   */
  private ReachableStatesTableColumnModel tableColumnModel = new ReachableStatesTableColumnModel ();


  /**
   * The {@link State} which must be calculated.
   */
  private ArrayList < State > toCalculateStates = new ArrayList < State > ();


  /**
   * Allocates a new {@link ReachableStatesDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machinePanel The {@link StateMachinePanel}.
   */
  public ReachableStatesDialog ( JFrame parent, StateMachinePanel machinePanel )
  {
    logger.debug ( "ReachableStatesDialog", //$NON-NLS-1$
        "allocate a new reachable states dialog" ); //$NON-NLS-1$

    if ( parent == null )
    {
      throw new IllegalArgumentException ( "parent is null" );//$NON-NLS-1$
    }
    if ( machinePanel == null )
    {
      throw new IllegalArgumentException ( "machine panel is null" );//$NON-NLS-1$
    }

    this.parent = parent;
    this.machinePanel = machinePanel;
  }


  /**
   * Adds a outline comment.
   * 
   * @param prettyString The {@link PrettyString} to add.
   */
  private final void addOutlineComment ( PrettyString prettyString )
  {
    this.reachableStatesTableModel.addRow ( prettyString );
    this.gui.jGTITableOutline.changeSelection ( this.reachableStatesTableModel
        .getRowCount () - 1, ReachableStatesTableModel.OUTLINE_COLUMN, false,
        false );
  }


  /**
   * Adds a {@link StepItem}.
   */
  private final void addStepItem ()
  {
    ArrayList < State > activeToCalculateStates = new ArrayList < State > ();
    ArrayList < State > activeCalculatedStates = new ArrayList < State > ();
    ArrayList < State > activeStatesOriginal = new ArrayList < State > ();
    ArrayList < State > activeStatesResult = new ArrayList < State > ();
    ArrayList < Transition > activeTransitionsOriginal = new ArrayList < Transition > ();
    ArrayList < Transition > activeTransitionsResult = new ArrayList < Transition > ();
    ArrayList < Symbol > activeSymbolsOriginal = new ArrayList < Symbol > ();
    ArrayList < Symbol > activeSymbolsResult = new ArrayList < Symbol > ();
    ArrayList < DefaultStateView > activeOverwrittenColorOriginal = new ArrayList < DefaultStateView > ();

    for ( State current : this.toCalculateStates )
    {
      activeToCalculateStates.add ( current );
    }
    for ( State current : this.calculatedStates )
    {
      activeCalculatedStates.add ( current );
    }
    for ( State current : this.machineOriginal.getState () )
    {
      if ( current.isActive () )
      {
        activeStatesOriginal.add ( current );
      }
    }
    for ( State current : this.machineResult.getState () )
    {
      if ( current.isActive () )
      {
        activeStatesResult.add ( current );
      }
    }
    for ( Transition current : this.machineOriginal.getTransition () )
    {
      if ( current.isActive () )
      {
        activeTransitionsOriginal.add ( current );
      }
    }
    for ( Transition current : this.machineResult.getTransition () )
    {
      if ( current.isActive () )
      {
        activeTransitionsResult.add ( current );
      }
    }
    for ( Transition currentTransition : this.machineOriginal.getTransition () )
    {
      for ( Symbol currentSymbol : currentTransition )
      {
        if ( currentSymbol.isActive () )
        {
          activeSymbolsOriginal.add ( currentSymbol );
        }
      }
    }
    for ( Transition currentTransition : this.machineResult.getTransition () )
    {
      for ( Symbol currentSymbol : currentTransition )
      {
        if ( currentSymbol.isActive () )
        {
          activeSymbolsResult.add ( currentSymbol );
        }
      }
    }

    for ( DefaultStateView current : this.modelOriginal.getStateViewList () )
    {
      if ( current.getOverwrittenColor () != null )
      {
        activeOverwrittenColorOriginal.add ( current );
      }
    }

    this.stepItemList.add ( new StepItem ( this.step, activeToCalculateStates,
        activeCalculatedStates, activeStatesOriginal, activeStatesResult,
        activeTransitionsOriginal, activeTransitionsResult,
        activeSymbolsOriginal, activeSymbolsResult,
        activeOverwrittenColorOriginal ) );
  }


  /**
   * Cancels the auto step timer.
   */
  private final void cancelAutoStepTimer ()
  {
    if ( this.autoStepTimer != null )
    {
      this.autoStepTimer.cancel ();
      this.autoStepTimer = null;
    }
  }


  /**
   * Clears the overwritten {@link State} highlighting of the original
   * {@link JGTIGraph}.
   */
  private final void clearOverwrittenColorOriginal ()
  {
    for ( DefaultStateView current : this.modelOriginal.getStateViewList () )
    {
      current.setOverwrittenColor ( null );
    }
  }


  /**
   * Clears the {@link State} highlighting of the original {@link JGTIGraph}.
   */
  private final void clearStateHighlightOriginal ()
  {
    for ( State currentState : this.machineOriginal.getState () )
    {
      currentState.setActive ( false );
    }
  }


  /**
   * Clears the {@link State} highlighting of the result {@link JGTIGraph}.
   */
  private final void clearStateHighlightResult ()
  {
    for ( State currentState : this.machineResult.getState () )
    {
      currentState.setActive ( false );
    }
  }


  /**
   * Clears the {@link Symbol} highlighting of the original {@link JGTIGraph}.
   */
  private final void clearSymbolHighlightOriginal ()
  {
    for ( Transition currentTransition : this.machineOriginal.getTransition () )
    {
      for ( Symbol currentSymbol : currentTransition.getSymbol () )
      {
        currentSymbol.setActive ( false );
      }
    }
  }


  /**
   * Clears the {@link Symbol} highlighting of the result {@link JGTIGraph}.
   */
  private final void clearSymbolHighlightResult ()
  {
    for ( Transition currentTransition : this.machineResult.getTransition () )
    {
      for ( Symbol currentSymbol : currentTransition.getSymbol () )
      {
        currentSymbol.setActive ( false );
      }
    }
  }


  /**
   * Clears the {@link Transition} highlighting of the original
   * {@link JGTIGraph}.
   */
  private final void clearTransitionHighlightOriginal ()
  {
    for ( Transition currentTransition : this.machineOriginal.getTransition () )
    {
      currentTransition.setActive ( false );
    }
  }


  /**
   * Clears the {@link Transition} highlighting of the result {@link JGTIGraph}.
   */
  private final void clearTransitionHighlightResult ()
  {
    for ( Transition currentTransition : this.machineResult.getTransition () )
    {
      currentTransition.setActive ( false );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final ReachableStatesDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the {@link StateMachinePanel}.
   * 
   * @return The {@link StateMachinePanel}.
   * @see #machinePanel
   */
  public StateMachinePanel getMachinePanel ()
  {
    return this.machinePanel;
  }


  /**
   * Returns the modelOriginal.
   * 
   * @return The modelOriginal.
   * @see #modelOriginal
   */
  public DefaultStateMachineModel getModelOriginal ()
  {
    return this.modelOriginal;
  }


  /**
   * Returns the reachableStatesTableModel.
   * 
   * @return The reachableStatesTableModel.
   * @see #reachableStatesTableModel
   */
  public ReachableStatesTableModel getReachableStatesTableModel ()
  {
    return this.reachableStatesTableModel;
  }


  /**
   * Returns the tableColumnModel.
   * 
   * @return The tableColumnModel.
   * @see #tableColumnModel
   */
  public ReachableStatesTableColumnModel getTableColumnModel ()
  {
    return this.tableColumnModel;
  }


  /**
   * Handles the action on the auto step button.
   */
  public final void handleAutoStep ()
  {
    logger.debug ( "handleAutoStep", "handle auto step" ); //$NON-NLS-1$ //$NON-NLS-2$

    setStatus ();

    startAutoStepTimer ();
  }


  /**
   * Handles the action on the begin step button.
   */
  public final void handleBeginStep ()
  {
    performBeginStep ();
  }


  /**
   * Handles the action on the cancel button.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$

    cancelAutoStepTimer ();

    PreferenceManager.getInstance ().setReachableStatesDialogPreferences (
        this.gui );
    this.gui.dispose ();
  }


  /**
   * Handles the action on the end step button.
   */
  public final void handleEndStep ()
  {
    performEndStep ();
  }


  /**
   * Handles the action on the next step button.
   */
  public final void handleNextStep ()
  {
    performNextStep ( true );
  }


  /**
   * Handles the action on the ok button.
   */
  public final void handleOk ()
  {
    logger.debug ( "handleOk", "handle ok" ); //$NON-NLS-1$ //$NON-NLS-2$

    cancelAutoStepTimer ();

    this.gui.setVisible ( false );

    while ( !this.endReached )
    {
      performNextStep ( false );
    }

    // remove the not reachable states
    ArrayList < DefaultStateView > statesToRemove = new ArrayList < DefaultStateView > ();
    for ( DefaultStateView current : this.modelResult.getStateViewList () )
    {
      if ( !current.getState ().isActive () )
      {
        statesToRemove.add ( current );
      }
    }
    for ( DefaultStateView current : statesToRemove )
    {
      this.modelResult.removeState ( current, false );
    }

    this.machinePanel.getMainWindow ().handleNew (
        this.modelResult.getElement (), false );

    PreferenceManager.getInstance ().setReachableStatesDialogPreferences (
        this.gui );
    this.gui.dispose ();
  }


  /**
   * Handles the action on the previous step button.
   */
  public final void handlePreviousStep ()
  {
    performPreviousStep ( true );
  }


  /**
   * Handle the print action.
   */
  public void handlePrint ()
  {
    PrintDialog dialog = new PrintDialog ( this.parent, this );
    dialog.show ();
  }


  /**
   * Handles the action on the stop button.
   */
  public final void handleStop ()
  {
    logger.debug ( "handleStop", "handle stop" ); //$NON-NLS-1$ //$NON-NLS-2$

    cancelAutoStepTimer ();

    this.gui.jGTIToolBarToggleButtonAutoStep.setSelected ( false );
    setStatus ();
  }


  /**
   * Performs the begin step.
   */
  private final void performBeginStep ()
  {
    logger.debug ( "performBeginStep", "handle begin step" ); //$NON-NLS-1$ //$NON-NLS-2$

    while ( !this.stepItemList.isEmpty () )
    {
      performPreviousStep ( false );
    }

    setStatus ();

    this.modelOriginal.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.modelOriginal.getGraphModel () ) );
    this.modelResult.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.modelResult.getGraphModel () ) );
  }


  /**
   * Performs the end step.
   */
  private final void performEndStep ()
  {
    logger.debug ( "performEndStep", "handle nfa to dfa end step" ); //$NON-NLS-1$ //$NON-NLS-2$

    while ( !this.endReached )
    {
      performNextStep ( false );
    }

    setStatus ();

    this.modelOriginal.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.modelOriginal.getGraphModel () ) );
    this.modelResult.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.modelResult.getGraphModel () ) );
  }


  /**
   * Performs the next step.
   * 
   * @param manualStep Flag that indicates if the {@link Step} is a manual
   *          {@link Step}.
   */
  protected final void performNextStep ( boolean manualStep )
  {
    addStepItem ();

    clearOverwrittenColorOriginal ();

    if ( this.step.equals ( Step.ACTIVATE_START_STATE ) )
    {
      State startState = null;
      for ( State current : this.machineOriginal.getState () )
      {
        if ( current.isStartState () )
        {
          startState = current;
          break;
        }
      }
      if ( startState == null )
      {
        throw new NullPointerException ( "no start state" ); //$NON-NLS-1$
      }

      if ( manualStep )
      {
        logger.debug ( "performNextStep", "perform next step: " + this.step //$NON-NLS-1$ //$NON-NLS-2$
            + ": " + startState.getName () ); //$NON-NLS-1$
      }

      startState.setActive ( true );

      // result
      for ( State current : this.machineResult.getState () )
      {
        if ( startState.equals ( current ) )
        {
          current.setActive ( true );
          break;
        }
      }

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString
          .add ( Messages
              .getPrettyString (
                  "ReachableStatesDialog.ActivateStartState", startState.toPrettyString () ) ); //$NON-NLS-1$
      addOutlineComment ( prettyString );

      this.step = Step.ACTIVATE_REACHABLE_STATES;
    }
    else if ( this.step.equals ( Step.ACTIVATE_NEXT_STATE ) )
    {
      State nextState = this.toCalculateStates.remove ( 0 );

      nextState.setActive ( true );

      if ( manualStep )
      {
        logger.debug ( "performNextStep", "perform next step: " + this.step //$NON-NLS-1$ //$NON-NLS-2$
            + ": " + nextState.getName () ); //$NON-NLS-1$
      }

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString
          .add ( Messages
              .getPrettyString (
                  "ReachableStatesDialog.ActivateNextState", nextState.toPrettyString () ) ); //$NON-NLS-1$
      addOutlineComment ( prettyString );

      this.step = Step.ACTIVATE_REACHABLE_STATES;
    }
    else if ( this.step.equals ( Step.ACTIVATE_REACHABLE_STATES ) )
    {
      ArrayList < State > reachableStates = new ArrayList < State > ();
      for ( State currentState : this.machineOriginal.getState () )
      {
        if ( currentState.isActive () )
        {
          this.calculatedStates.add ( currentState );
          for ( Transition currentTransition : currentState
              .getTransitionBegin () )
          {
            currentTransition.setActive ( true );

            // result
            for ( Transition current : this.machineResult.getTransition () )
            {
              if ( currentTransition.equals ( current ) )
              {
                current.setActive ( true );
                break;
              }
            }

            reachableStates.add ( currentTransition.getStateEnd () );
          }
          break;
        }
      }
      Collections.sort ( reachableStates );

      clearStateHighlightOriginal ();

      for ( State current : reachableStates )
      {
        if ( !this.calculatedStates.contains ( current )
            && !this.toCalculateStates.contains ( current ) )
        {
          this.toCalculateStates.add ( current );
        }
        current.setActive ( true );

        // result
        for ( State currentResult : this.machineResult.getState () )
        {
          if ( current.equals ( currentResult ) )
          {
            currentResult.setActive ( true );
            break;
          }
        }
      }
      Collections.sort ( this.toCalculateStates );

      if ( manualStep )
      {
        logger.debug ( "performNextStep", "perform next step: " + this.step //$NON-NLS-1$ //$NON-NLS-2$
            + ": " + reachableStates ); //$NON-NLS-1$
      }

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "ReachableStatesDialog.ActivateReachableStates" ) //$NON-NLS-1$
          + " ", Style.NONE ) ); //$NON-NLS-1$

      if ( reachableStates.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : reachableStates )
        {
          if ( !first )
          {
            prettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.add ( current );
        }
        prettyString.add ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }
      addOutlineComment ( prettyString );

      this.step = Step.FINISH;
    }
    else if ( this.step.equals ( Step.FINISH ) )
    {
      clearStateHighlightOriginal ();
      clearTransitionHighlightOriginal ();
      clearTransitionHighlightResult ();

      if ( manualStep )
      {
        logger.debug ( "performNextStep", "perform next step: " + this.step ); //$NON-NLS-1$ //$NON-NLS-2$
      }

      if ( this.toCalculateStates.size () == 0 )
      {
        this.endReached = true;
      }

      // outline

      PrettyString prettyString = new PrettyString ();
      if ( this.toCalculateStates.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( Messages
            .getString ( "ReachableStatesDialog.FinishAll" ), Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( Messages.getPrettyString (
            "ReachableStatesDialog.Finish", this.calculatedStates //$NON-NLS-1$
                .get ( this.calculatedStates.size () - 1 ).toPrettyString () ) );
        prettyString.add ( new PrettyToken ( " ", Style.NONE ) ); //$NON-NLS-1$
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : this.toCalculateStates )
        {
          if ( !first )
          {
            prettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.add ( current );
        }
        prettyString.add ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }
      addOutlineComment ( prettyString );

      this.step = Step.ACTIVATE_NEXT_STATE;
    }
    else
    {
      throw new RuntimeException ( "unsupported step" ); //$NON-NLS-1$
    }

    for ( State current : this.machineResult.getState () )
    {
      if ( current.isActive () )
      {
        DefaultStateView originalState = this.modelOriginal
            .getStateViewForState ( current );
        if ( !originalState.getState ().isActive () )
        {
          originalState.setOverwrittenColor ( REACHABLE_COLOR );
        }
      }
    }

    if ( manualStep )
    {
      setStatus ();

      this.modelOriginal.getGraphModel ().cellsChanged (
          DefaultGraphModel.getAll ( this.modelOriginal.getGraphModel () ) );
      this.modelResult.getGraphModel ().cellsChanged (
          DefaultGraphModel.getAll ( this.modelResult.getGraphModel () ) );
    }
  }


  /**
   * Performs the previous step.
   * 
   * @param manualStep Flag that indicates if the {@link Step} is a manual
   *          {@link Step}.
   */
  private final void performPreviousStep ( boolean manualStep )
  {
    if ( manualStep )
    {
      logger.debug ( "performPreviousStep",//$NON-NLS-1$
          "perform previous step" ); //$NON-NLS-1$
    }

    StepItem stepItem = this.stepItemList
        .remove ( this.stepItemList.size () - 1 );

    this.toCalculateStates.clear ();
    for ( State current : stepItem.getActiveToCalculateStates () )
    {
      this.toCalculateStates.add ( current );
    }

    this.calculatedStates.clear ();
    for ( State current : stepItem.getActiveCalculatedStates () )
    {
      this.calculatedStates.add ( current );
    }

    clearStateHighlightOriginal ();
    clearStateHighlightResult ();
    clearTransitionHighlightOriginal ();
    clearTransitionHighlightResult ();
    clearSymbolHighlightOriginal ();
    clearSymbolHighlightResult ();
    clearOverwrittenColorOriginal ();

    for ( State current : stepItem.getActiveStatesOriginal () )
    {
      current.setActive ( true );
    }
    for ( State current : stepItem.getActiveStatesResult () )
    {
      current.setActive ( true );
    }
    for ( Transition current : stepItem.getActiveTransitionsOriginal () )
    {
      current.setActive ( true );
    }
    for ( Transition current : stepItem.getActiveTransitionsResult () )
    {
      current.setActive ( true );
    }
    for ( Symbol current : stepItem.getActiveSymbolsOriginal () )
    {
      current.setActive ( true );
    }
    for ( Symbol current : stepItem.getActiveSymbolsResult () )
    {
      current.setActive ( true );
    }
    for ( DefaultStateView current : stepItem.getOverwrittenColorOriginal () )
    {
      current.setOverwrittenColor ( REACHABLE_COLOR );
    }
    this.step = stepItem.getActiveStep ();

    // outline
    this.reachableStatesTableModel.removeLastRow ();
    this.gui.jGTITableOutline.changeSelection ( this.reachableStatesTableModel
        .getRowCount () - 1, ReachableStatesTableModel.OUTLINE_COLUMN, false,
        false );

    this.endReached = false;

    if ( manualStep )
    {
      setStatus ();

      this.modelOriginal.getGraphModel ().cellsChanged (
          DefaultGraphModel.getAll ( this.modelOriginal.getGraphModel () ) );
      this.modelResult.getGraphModel ().cellsChanged (
          DefaultGraphModel.getAll ( this.modelResult.getGraphModel () ) );
    }
  }


  /**
   * Sets the button status.
   */
  private final void setStatus ()
  {
    if ( this.gui.jGTIToolBarToggleButtonAutoStep.isSelected () )
    {
      this.gui.jGTIToolBarButtonBeginStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonPreviousStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonNextStep.setEnabled ( false );
      this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonStop.setEnabled ( true );
      this.gui.jGTIToolBarButtonEndStep.setEnabled ( false );
    }
    else
    {
      boolean beginReached = this.stepItemList.isEmpty ();
      this.gui.jGTIToolBarButtonBeginStep.setEnabled ( !beginReached );
      this.gui.jGTIToolBarButtonPreviousStep.setEnabled ( !beginReached );
      this.gui.jGTIToolBarButtonNextStep.setEnabled ( !this.endReached );
      this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( !this.endReached );
      this.gui.jGTIToolBarButtonStop.setEnabled ( false );
      this.gui.jGTIToolBarButtonEndStep.setEnabled ( !this.endReached );
    }
  }


  /**
   * Shows the {@link ReachableStatesDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the reachable states dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.gui = new ReachableStatesDialogForm ( this, this.parent );

    // outline
    this.reachableStatesTableModel = new ReachableStatesTableModel ();
    this.gui.jGTITableOutline.setModel ( this.reachableStatesTableModel );
    this.gui.jGTITableOutline.setColumnModel ( this.tableColumnModel );
    this.gui.jGTITableOutline.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableOutline.getSelectionModel ().setSelectionMode (
        ListSelectionModel.SINGLE_SELECTION );

    Rectangle rect = PreferenceManager.getInstance ()
        .getReachableStatesDialogBounds ();
    this.gui.jGTISplitPaneOutline.setDividerLocation ( rect.width - 250 );

    try
    {
      this.modelOriginal = new DefaultStateMachineModel ( this.machinePanel
          .getModel ().getElement (), null );
      this.modelResult = new DefaultStateMachineModel ( this.machinePanel
          .getModel ().getElement (), null );
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( StoreException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    this.jGTIGraphOriginal = this.modelOriginal.getJGTIGraph ();
    this.jGTIGraphOriginal.setEnabled ( false );
    this.gui.jGTIScrollPaneGraph.setViewportView ( this.jGTIGraphOriginal );
    this.machineOriginal = this.modelOriginal.getMachine ();

    this.machineResult = this.modelResult.getMachine ();

    this.step = Step.ACTIVATE_START_STATE;

    setStatus ();

    if ( ( rect.x == PreferenceManager.DEFAULT_REACHABLE_STATES_DIALOG_POSITION_X )
        || ( rect.y == PreferenceManager.DEFAULT_REACHABLE_STATES_DIALOG_POSITION_Y ) )
    {
      rect.x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
          - ( this.gui.getWidth () / 2 );
      rect.y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
          - ( this.gui.getHeight () / 2 );
    }
    this.gui.setBounds ( rect );
    this.gui.setVisible ( true );
  }


  /**
   * Starts the auto step timer.
   */
  private final void startAutoStepTimer ()
  {
    cancelAutoStepTimer ();

    this.autoStepTimer = new Timer ();
    int time = PreferenceManager.getInstance ().getAutoStepItem ()
        .getAutoStepInterval ();
    this.autoStepTimer.schedule ( new AutoStepTimerTask (), time, time );
  }
}
