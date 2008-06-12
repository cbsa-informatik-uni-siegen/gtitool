package de.unisiegen.gtitool.ui.logic;


import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
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
  private final class AutoStepTimerTask extends TimerTask
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

        @SuppressWarnings ( "synthetic-access" )
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
     * The activate start {@link State} step.
     */
    ACTIVATE_START_STATE,

    /**
     * The activate reachable {@link State}s step.
     */
    ACTIVATE_REACHABLE_STATES;

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
        case ACTIVATE_REACHABLE_STATES :
        {
          return "activate reachable states"; //$NON-NLS-1$
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
     * The active {@link Step}.
     */
    private Step activeStep;


    /**
     * The current active {@link Symbol}.
     */
    private Symbol activeSymbol;


    /**
     * The current active {@link State}.
     */
    private State activeState;


    /**
     * The active {@link State}s of the original {@link JGTIGraph}.
     */
    private ArrayList < State > activeStatesOriginal;


    /**
     * The active {@link State}s of the result {@link JGTIGraph}.
     */
    private ArrayList < State > activeStatesResult;


    /**
     * The active {@link Transition}s of the original {@link JGTIGraph}.
     */
    private ArrayList < Transition > activeTransitionsOriginal;


    /**
     * The active {@link Transition}s of the result {@link JGTIGraph}.
     */
    private ArrayList < Transition > activeTransitionsResult;


    /**
     * The active {@link Symbol}s of the original {@link JGTIGraph}.
     */
    private ArrayList < Symbol > activeSymbolsOriginal;


    /**
     * The active {@link Symbol}s of the result {@link JGTIGraph}.
     */
    private ArrayList < Symbol > activeSymbolsResult;


    /**
     * The added {@link DefaultStateView}.
     */
    private DefaultStateView addedDefaultStateView = null;


    /**
     * The added {@link DefaultTransitionView}.
     */
    private DefaultTransitionView addedDefaultTransitionView = null;


    /**
     * The added {@link Symbol}.
     */
    private ObjectPair < Transition, Symbol > addedSymbol = null;


    /**
     * Allocates a new {@link StepItem}.
     * 
     * @param activeStep The active {@link Step}.
     * @param currentActiveSymbol The current active {@link Symbol}.
     * @param currentActiveState The current active {@link State}.
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
     */
    public StepItem ( Step activeStep, Symbol currentActiveSymbol,
        State currentActiveState, ArrayList < State > activeStatesOriginal,
        ArrayList < State > activeStatesResult,
        ArrayList < Transition > activeTransitionsOriginal,
        ArrayList < Transition > activeTransitionsResult,
        ArrayList < Symbol > activeSymbolsOriginal,
        ArrayList < Symbol > activeSymbolsResult )
    {
      this.activeStep = activeStep;
      this.activeSymbol = currentActiveSymbol;
      this.activeState = currentActiveState;
      this.activeStatesOriginal = activeStatesOriginal;
      this.activeStatesResult = activeStatesResult;
      this.activeTransitionsOriginal = activeTransitionsOriginal;
      this.activeTransitionsResult = activeTransitionsResult;
      this.activeSymbolsOriginal = activeSymbolsOriginal;
      this.activeSymbolsResult = activeSymbolsResult;
    }


    /**
     * Returns the activeState.
     * 
     * @return The activeState.
     * @see #activeState
     */
    public final State getActiveState ()
    {
      return this.activeState;
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
     * Returns the activeSymbol.
     * 
     * @return The activeSymbol.
     * @see #activeSymbol
     */
    public final Symbol getActiveSymbol ()
    {
      return this.activeSymbol;
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
     * Returns the addedDefaultStateView.
     * 
     * @return The addedDefaultStateView.
     * @see #addedDefaultStateView
     */
    public final DefaultStateView getAddedDefaultStateView ()
    {
      return this.addedDefaultStateView;
    }


    /**
     * Returns the addedDefaultTransitionView.
     * 
     * @return The addedDefaultTransitionView.
     * @see #addedDefaultTransitionView
     */
    public final DefaultTransitionView getAddedDefaultTransitionView ()
    {
      return this.addedDefaultTransitionView;
    }


    /**
     * Returns the addedSymbol.
     * 
     * @return The addedSymbol.
     * @see #addedSymbol
     */
    public final ObjectPair < Transition, Symbol > getAddedSymbol ()
    {
      return this.addedSymbol;
    }


    /**
     * Sets the addedDefaultStateView.
     * 
     * @param addedDefaultStateView The addedDefaultStateView to set.
     * @see #addedDefaultStateView
     */
    public final void setAddedDefaultStateView (
        DefaultStateView addedDefaultStateView )
    {
      this.addedDefaultStateView = addedDefaultStateView;
    }


    /**
     * Sets the addedDefaultTransitionView.
     * 
     * @param addedDefaultTransitionView The addedDefaultTransitionView to set.
     * @see #addedDefaultTransitionView
     */
    public final void setAddedDefaultTransitionView (
        DefaultTransitionView addedDefaultTransitionView )
    {
      this.addedDefaultTransitionView = addedDefaultTransitionView;
    }


    /**
     * Sets the addedSymbol.
     * 
     * @param addedSymbol The addedSymbol to set.
     * @see #addedSymbol
     */
    public final void setAddedSymbol (
        ObjectPair < Transition, Symbol > addedSymbol )
    {
      this.addedSymbol = addedSymbol;
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ReachableStatesDialog.class );


  /**
   * The {@link ReachableStatesDialogForm}.
   */
  private ReachableStatesDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The original {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphOriginal;


  /**
   * The result {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphResult;


  /**
   * The {@link MachinePanel}.
   */
  private MachinePanel machinePanel;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelOriginal;


  /**
   * The result {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelResult;


  /**
   * The original {@link Machine}.
   */
  private Machine machineOriginal;


  /**
   * The result {@link Machine}.
   */
  private Machine machineResult;


  /**
   * The current {@link Symbol}.
   */
  private Symbol currentActiveSymbol;


  /**
   * The current {@link State}.
   */
  private State currentActiveState;


  /**
   * The current {@link Step}.
   */
  private Step step = null;


  /**
   * Flag that indicates if the end is reached.
   */
  private boolean endReached = false;


  /**
   * The {@link StepItem} list.
   */
  private ArrayList < StepItem > stepItemList = new ArrayList < StepItem > ();


  /**
   * The auto step {@link Timer}.
   */
  private Timer autoStepTimer = null;


  /**
   * The {@link ReachableStatesTableModel}.
   */
  private ReachableStatesTableModel reachableStatesTableModel;


  /**
   * Allocates a new {@link ReachableStatesDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machinePanel The {@link MachinePanel}.
   */
  public ReachableStatesDialog ( JFrame parent, MachinePanel machinePanel )
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
    ArrayList < State > activeStatesOriginal = new ArrayList < State > ();
    ArrayList < State > activeStatesResult = new ArrayList < State > ();
    ArrayList < Transition > activeTransitionsOriginal = new ArrayList < Transition > ();
    ArrayList < Transition > activeTransitionsResult = new ArrayList < Transition > ();
    ArrayList < Symbol > activeSymbolsOriginal = new ArrayList < Symbol > ();
    ArrayList < Symbol > activeSymbolsResult = new ArrayList < Symbol > ();

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

    this.stepItemList.add ( new StepItem ( this.step, this.currentActiveSymbol,
        this.currentActiveState, activeStatesOriginal, activeStatesResult,
        activeTransitionsOriginal, activeTransitionsResult,
        activeSymbolsOriginal, activeSymbolsResult ) );
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
    performBeginStep ( true );
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
    performEndStep ( true );
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

    this.machinePanel.getMainWindow ().handleNew (
        this.modelResult.getElement (), true );

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
   * 
   * @param manualStep Flag that indicates if the {@link Step} is a manual
   *          {@link Step}.
   */
  private final void performBeginStep ( boolean manualStep )
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
   * 
   * @param manualStep Flag that indicates if the {@link Step} is a manual
   *          {@link Step}.
   */
  private final void performEndStep ( boolean manualStep )
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
  private final void performNextStep ( boolean manualStep )
  {
    addStepItem ();

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

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.addPrettyString ( Messages.getPrettyString (
          "ReachableStatesDialog.ActivateStartState", false, startState ) ); //$NON-NLS-1$
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
          for ( Transition currentTransition : currentState
              .getTransitionBegin () )
          {
            reachableStates.add ( currentTransition.getStateEnd () );
          }
        }
      }

      clearStateHighlightOriginal ();

      for ( State current : reachableStates )
      {
        current.setActive ( true );
      }

      // TODO
      if ( manualStep )
      {
        logger.debug ( "performNextStep", "perform next step: " + this.step ); //$NON-NLS-1$ //$NON-NLS-2$
      }

      // TODO outline

      // TODO
      this.step = Step.ACTIVATE_REACHABLE_STATES;
    }
    else
    {
      throw new RuntimeException ( "unsupported step" ); //$NON-NLS-1$
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
    clearStateHighlightOriginal ();
    clearStateHighlightResult ();
    clearTransitionHighlightOriginal ();
    clearTransitionHighlightResult ();
    clearSymbolHighlightOriginal ();
    clearSymbolHighlightResult ();

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
    this.step = stepItem.getActiveStep ();
    this.currentActiveState = stepItem.getActiveState ();
    this.currentActiveSymbol = stepItem.getActiveSymbol ();

    if ( stepItem.getAddedDefaultStateView () != null )
    {
      this.modelResult.removeState ( stepItem.getAddedDefaultStateView (),
          false );
    }
    if ( stepItem.getAddedDefaultTransitionView () != null )
    {
      this.modelResult.removeTransition ( stepItem
          .getAddedDefaultTransitionView (), false );
    }
    if ( stepItem.getAddedSymbol () != null )
    {
      Transition transition = stepItem.getAddedSymbol ().getFirst ();
      Symbol symbol = stepItem.getAddedSymbol ().getSecond ();
      transition.remove ( symbol );
    }

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
    this.gui.jGTITableOutline
        .setColumnModel ( new ReachableStatesTableColumnModel () );
    this.gui.jGTITableOutline.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableOutline.getSelectionModel ().setSelectionMode (
        ListSelectionModel.SINGLE_SELECTION );

    this.gui.jGTISplitPaneGraph.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationReachableStates () );
    this.gui.jGTISplitPaneGraph.addPropertyChangeListener (
        JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void propertyChange ( PropertyChangeEvent event )
          {
            PreferenceManager.getInstance ().setDividerLocationReachableStates (
                ( ( Integer ) event.getNewValue () ).intValue () );
          }
        } );
    this.gui.jGTISplitPaneOutline.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationReachableStatesOutline () );
    this.gui.jGTISplitPaneOutline.addPropertyChangeListener (
        JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void propertyChange ( PropertyChangeEvent event )
          {
            PreferenceManager.getInstance ()
                .setDividerLocationReachableStatesOutline (
                    ( ( Integer ) event.getNewValue () ).intValue () );
          }
        } );

    try
    {
      this.modelOriginal = new DefaultMachineModel ( this.machinePanel
          .getModel ().getElement (), null );
      this.modelResult = new DefaultMachineModel ( this.machinePanel
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
    catch ( SymbolException exc )
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
    this.gui.jGTIScrollPaneOriginal.setViewportView ( this.jGTIGraphOriginal );
    this.machineOriginal = this.modelOriginal.getMachine ();

    this.jGTIGraphResult = this.modelResult.getJGTIGraph ();
    this.jGTIGraphResult.setEnabled ( false );
    this.gui.jGTIScrollPaneResult.setViewportView ( this.jGTIGraphResult );
    this.machineResult = this.modelResult.getMachine ();

    this.step = Step.ACTIVATE_START_STATE;

    setStatus ();

    Rectangle rect = PreferenceManager.getInstance ()
        .getReachableStatesDialogBounds ();
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
  @SuppressWarnings ( "synthetic-access" )
  private final void startAutoStepTimer ()
  {
    cancelAutoStepTimer ();

    this.autoStepTimer = new Timer ();
    int time = PreferenceManager.getInstance ().getAutoStepItem ()
        .getAutoStepInterval ();
    this.autoStepTimer.schedule ( new AutoStepTimerTask (), time, time );
  }
}
