package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jgraph.JGraph;

import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.dfa.DFA;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.machines.enfa.ENFA;
import de.unisiegen.gtitool.core.machines.nfa.DefaultNFA;
import de.unisiegen.gtitool.core.machines.nfa.NFA;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel.MachineType;
import de.unisiegen.gtitool.ui.netbeans.ConvertMachineDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.utils.LayoutManager;


/**
 * The {@link ConvertMachineDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ConvertMachineDialog implements
    LogicClass < ConvertMachineDialogForm >, Converter
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

        @SuppressWarnings ( "synthetic-access" )
        public void run ()
        {
          if ( ConvertMachineDialog.this.endReached )
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
   * The convert {@link Machine} type enum.
   * 
   * @author Christian Fehler
   */
  public enum ConvertMachineType
  {
    /**
     * The {@link NFA} to {@link DFA} conversion type.
     */
    NFA_TO_DFA,

    /**
     * The {@link ENFA} to {@link NFA} conversion type.
     */
    ENFA_TO_NFA;
  }


  /**
   * The {@link Position}.
   * 
   * @author Christian Fehler
   */
  private class Position
  {

    /**
     * The x position.
     */
    private double x;


    /**
     * The y position.
     */
    private double y;


    /**
     * Allocates a new {@link Position}.
     * 
     * @param x The x position.
     * @param y The y position.
     */
    public Position ( double x, double y )
    {
      this.x = x;
      this.y = y;
    }


    /**
     * Returns the x position.
     * 
     * @return The x position.
     * @see #x
     */
    public final double getX ()
    {
      return this.x;
    }


    /**
     * Returns the y position.
     * 
     * @return The y position.
     * @see #y
     */
    public final double getY ()
    {
      return this.y;
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
     * The activate old {@link State}s step.
     */
    ACTIVATE_OLD_STATES,

    /**
     * The activate old closure {@link State}s step.
     */
    ACTIVATE_OLD_CLOSURE_STATES,

    /**
     * The activate {@link Symbol}s step.
     */
    ACTIVATE_SYMBOLS,

    /**
     * The activate new {@link State}s step.
     */
    ACTIVATE_NEW_STATES,

    /**
     * The activate new closure {@link State}s step.
     */
    ACTIVATE_NEW_CLOSURE_STATES,

    /**
     * The add {@link State} and {@link Transition} step.
     */
    ADD_STATE_AND_TRANSITION,

    /**
     * The clear step.
     */
    CLEAR;
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
     * The active {@link State}s of the original {@link JGraph}.
     */
    private ArrayList < State > activeStatesOriginal;


    /**
     * The active {@link State}s of the converted {@link JGraph}.
     */
    private ArrayList < State > activeStatesConverted;


    /**
     * The active {@link Transition}s of the original {@link JGraph}.
     */
    private ArrayList < Transition > activeTransitionsOriginal;


    /**
     * The active {@link Transition}s of the converted {@link JGraph}.
     */
    private ArrayList < Transition > activeTransitionsConverted;


    /**
     * The active {@link Symbol}s of the original {@link JGraph}.
     */
    private ArrayList < Symbol > activeSymbolsOriginal;


    /**
     * The active {@link Symbol}s of the converted {@link JGraph}.
     */
    private ArrayList < Symbol > activeSymbolsConverted;


    /**
     * The added {@link DefaultStateView}.
     */
    private DefaultStateView addedDefaultStateView = null;


    /**
     * The added {@link DefaultTransitionView}.
     */
    private DefaultTransitionView addedDefaultTransitionView = null;


    /**
     * Allocates a new {@link StepItem}.
     * 
     * @param activeStep The active {@link Step}.
     * @param currentActiveSymbol The current active {@link Symbol}.
     * @param currentActiveState The current active {@link State}.
     * @param activeStatesOriginal The active {@link State}s of the original
     *          {@link JGraph}.
     * @param activeStatesConverted The active {@link State}s of the converted
     *          {@link JGraph}.
     * @param activeTransitionsOriginal The active {@link Transition}s of the
     *          original {@link JGraph}.
     * @param activeTransitionsConverted The active {@link Transition}s of the
     *          converted {@link JGraph}.
     * @param activeSymbolsOriginal The active {@link Symbol}s of the original
     *          {@link JGraph}.
     * @param activeSymbolsConverted The active {@link Symbol}s of the
     *          converted {@link JGraph}.
     */
    public StepItem ( Step activeStep, Symbol currentActiveSymbol,
        State currentActiveState, ArrayList < State > activeStatesOriginal,
        ArrayList < State > activeStatesConverted,
        ArrayList < Transition > activeTransitionsOriginal,
        ArrayList < Transition > activeTransitionsConverted,
        ArrayList < Symbol > activeSymbolsOriginal,
        ArrayList < Symbol > activeSymbolsConverted )
    {
      this.activeStep = activeStep;
      this.activeSymbol = currentActiveSymbol;
      this.activeState = currentActiveState;
      this.activeStatesOriginal = activeStatesOriginal;
      this.activeStatesConverted = activeStatesConverted;
      this.activeTransitionsOriginal = activeTransitionsOriginal;
      this.activeTransitionsConverted = activeTransitionsConverted;
      this.activeSymbolsOriginal = activeSymbolsOriginal;
      this.activeSymbolsConverted = activeSymbolsConverted;
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
     * Returns the activeStatesConverted.
     * 
     * @return The activeStatesConverted.
     * @see #activeStatesConverted
     */
    public final ArrayList < State > getActiveStatesConverted ()
    {
      return this.activeStatesConverted;
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
     * Returns the activeSymbolsConverted.
     * 
     * @return The activeSymbolsConverted.
     * @see #activeSymbolsConverted
     */
    public final ArrayList < Symbol > getActiveSymbolsConverted ()
    {
      return this.activeSymbolsConverted;
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
     * Returns the activeTransitionsConverted.
     * 
     * @return The activeTransitionsConverted.
     * @see #activeTransitionsConverted
     */
    public final ArrayList < Transition > getActiveTransitionsConverted ()
    {
      return this.activeTransitionsConverted;
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
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ConvertMachineDialog.class );


  /**
   * The initial position.
   */
  private static final int INITIAL_POSITION = 200;


  /**
   * The {@link ConvertMachineDialogForm}.
   */
  private ConvertMachineDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The original {@link JGraph} containing the diagramm.
   */
  private JGraph jGraphOriginal;


  /**
   * The converted {@link JGraph} containing the diagramm.
   */
  private JGraph jGraphConverted;


  /**
   * The {@link MachinePanel}.
   */
  private MachinePanel machinePanel;


  /**
   * The {@link ConvertMachineType}.
   */
  private ConvertMachineType convertMachineType;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelOriginal;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelConverted;


  /**
   * The original {@link Machine}.
   */
  private Machine machineOriginal;


  /**
   * The original {@link Machine}.
   */
  private Machine machineConverted;


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
   * The {@link Position} map.
   */
  private HashMap < String, Position > positionMap;


  /**
   * The auto step {@link Timer}.
   */
  private Timer autoStepTimer = null;


  /**
   * Allocates a new {@link ConvertMachineDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machinePanel The {@link MachinePanel}.
   * @param convertMachineType The {@link ConvertMachineType}.
   */
  public ConvertMachineDialog ( JFrame parent, MachinePanel machinePanel,
      ConvertMachineType convertMachineType )
  {
    logger.debug ( "ConvertMachineDialog", //$NON-NLS-1$
        "allocate a new convert machine dialog" ); //$NON-NLS-1$

    if ( parent == null )
    {
      throw new IllegalArgumentException ( "parent is null" );//$NON-NLS-1$
    }
    if ( machinePanel == null )
    {
      throw new IllegalArgumentException ( "machine panel is null" );//$NON-NLS-1$
    }
    if ( convertMachineType == null )
    {
      throw new IllegalArgumentException ( "convert machine type is null" );//$NON-NLS-1$
    }

    this.parent = parent;
    this.machinePanel = machinePanel;
    this.convertMachineType = convertMachineType;

    this.gui = new ConvertMachineDialogForm ( this, parent );
    this.gui.jGTISplitPaneGraph.setResizeWeight ( 0.25 );
    this.gui.jGTISplitPaneGraph.setDividerLocation ( 0.5 );

    try
    {
      this.modelOriginal = new DefaultMachineModel ( this.machinePanel
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
    this.jGraphOriginal = this.modelOriginal.getJGraph ();
    this.jGraphOriginal.setEnabled ( false );
    this.gui.jGTIScrollPaneOriginal.setViewportView ( this.jGraphOriginal );
    this.machineOriginal = this.modelOriginal.getMachine ();

    switch ( this.convertMachineType )
    {
      case NFA_TO_DFA :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultDFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );
        break;
      }
      case ENFA_TO_NFA :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultNFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );
        break;
      }
    }

    this.modelConverted.setUseStateSetView ( true );
    this.jGraphConverted = this.modelConverted.getJGraph ();
    this.jGraphConverted.setEnabled ( false );
    this.gui.jGTIScrollPaneConverted.setViewportView ( this.jGraphConverted );
    this.machineConverted = this.modelConverted.getMachine ();

    this.positionMap = new HashMap < String, Position > ();

    performStart ();
  }


  /**
   * Adds a {@link StepItem}.
   */
  private final void addStepItem ()
  {
    ArrayList < State > activeStatesOriginal = new ArrayList < State > ();
    ArrayList < State > activeStatesConverted = new ArrayList < State > ();
    ArrayList < Transition > activeTransitionsOriginal = new ArrayList < Transition > ();
    ArrayList < Transition > activeTransitionsConverted = new ArrayList < Transition > ();
    ArrayList < Symbol > activeSymbolsOriginal = new ArrayList < Symbol > ();
    ArrayList < Symbol > activeSymbolsConverted = new ArrayList < Symbol > ();

    for ( State current : this.machineOriginal.getState () )
    {
      if ( current.isActive () )
      {
        activeStatesOriginal.add ( current );
      }
    }
    for ( State current : this.machineConverted.getState () )
    {
      if ( current.isActive () )
      {
        activeStatesConverted.add ( current );
      }
    }
    for ( Transition current : this.machineOriginal.getTransition () )
    {
      if ( current.isActive () )
      {
        activeTransitionsOriginal.add ( current );
      }
    }
    for ( Transition current : this.machineConverted.getTransition () )
    {
      if ( current.isActive () )
      {
        activeTransitionsConverted.add ( current );
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
    for ( Transition currentTransition : this.machineConverted.getTransition () )
    {
      for ( Symbol currentSymbol : currentTransition )
      {
        if ( currentSymbol.isActive () )
        {
          activeSymbolsConverted.add ( currentSymbol );
        }
      }
    }

    this.stepItemList.add ( new StepItem ( this.step, this.currentActiveSymbol,
        this.currentActiveState, activeStatesOriginal, activeStatesConverted,
        activeTransitionsOriginal, activeTransitionsConverted,
        activeSymbolsOriginal, activeSymbolsConverted ) );
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
   * Clears the {@link State} highlighting of the converted {@link JGraph}.
   */
  private final void clearStateHighlightConverted ()
  {
    for ( State currentState : this.machineConverted.getState () )
    {
      currentState.setActive ( false );
    }
  }


  /**
   * Clears the {@link State} highlighting of the original {@link JGraph}.
   */
  private final void clearStateHighlightOriginal ()
  {
    for ( State currentState : this.machineOriginal.getState () )
    {
      currentState.setActive ( false );
    }
  }


  /**
   * Clears the {@link Symbol} highlighting of the converted {@link JGraph}.
   */
  private final void clearSymbolHighlightConverted ()
  {
    for ( Transition currentTransition : this.machineConverted.getTransition () )
    {
      for ( Symbol currentSymbol : currentTransition.getSymbol () )
      {
        currentSymbol.setActive ( false );
      }
    }
  }


  /**
   * Clears the {@link Symbol} highlighting of the original {@link JGraph}.
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
   * Clears the {@link Transition} highlighting of the converted {@link JGraph}.
   */
  private final void clearTransitionHighlightConverted ()
  {
    for ( Transition currentTransition : this.machineConverted.getTransition () )
    {
      currentTransition.setActive ( false );
    }
  }


  /**
   * Clears the {@link Transition} highlighting of the original {@link JGraph}.
   */
  private final void clearTransitionHighlightOriginal ()
  {
    for ( Transition currentTransition : this.machineOriginal.getTransition () )
    {
      currentTransition.setActive ( false );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Converter#convert(MachineType)
   */
  public final void convert ( @SuppressWarnings ( "unused" )
  MachineType machineType )
  {
    show ();
  }


  /**
   * Returns the epsilon closure of the given {@link State}s.
   * 
   * @param stateList The {@link State}s.
   * @return The epsilon closure of the given {@link State}s.
   */
  private final ArrayList < State > getClosure ( ArrayList < State > stateList )
  {
    ArrayList < State > result = new ArrayList < State > ();
    for ( State current : stateList )
    {
      for ( State currentResult : getClosure ( current ) )
      {
        if ( !result.contains ( currentResult ) )
        {
          result.add ( currentResult );
        }
      }
    }
    return result;
  }


  /**
   * Returns the epsilon closure of the given {@link State}.
   * 
   * @param state The {@link State}.
   * @return The epsilon closure of the given {@link State}.
   */
  private final ArrayList < State > getClosure ( State state )
  {
    return getClosure ( state, new ArrayList < State > () );
  }


  /**
   * Returns the epsilon closure of the given {@link State}.
   * 
   * @param state The {@link State}.
   * @param finishedStates The {@link State}s which are finished.
   * @return The epsilon closure of the given {@link State}.
   */
  private final ArrayList < State > getClosure ( State state,
      ArrayList < State > finishedStates )
  {
    ArrayList < State > result = new ArrayList < State > ();
    if ( finishedStates.contains ( state ) )
    {
      return result;
    }

    result.add ( state );
    finishedStates.add ( state );
    for ( Transition current : state.getTransitionBegin () )
    {
      if ( current.isEpsilonTransition ()
          && !result.contains ( current.getStateEnd () ) )
      {
        for ( State currentState : getClosure ( current.getStateEnd (),
            finishedStates ) )
        {
          if ( !result.contains ( currentState ) )
          {
            result.add ( currentState );
          }
        }
      }
    }
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final ConvertMachineDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the position of the {@link State} or null if the position is not
   * defined.
   * 
   * @param state The {@link State}.
   * @return The position of the {@link State} or null if the position is not
   *         defined.
   */
  private final Position getPosition ( State state )
  {
    return this.positionMap.get ( state.getName () );
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
        this.modelConverted.getElement () );

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

    this.jGraphOriginal.repaint ();
    this.jGraphConverted.repaint ();
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

    this.jGraphOriginal.repaint ();
    this.jGraphConverted.repaint ();
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

    if ( this.step.equals ( Step.ACTIVATE_OLD_STATES ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: activate old states" );//$NON-NLS-1$
      }

      for ( State current : this.machineOriginal.getState () )
      {
        if ( this.currentActiveState.getName ().contains ( current.getName () ) )
        {
          current.setActive ( true );
        }
      }

      this.currentActiveState.setActive ( true );

      switch ( this.convertMachineType )
      {
        case NFA_TO_DFA :
        {
          this.step = Step.ACTIVATE_SYMBOLS;
          break;
        }
        case ENFA_TO_NFA :
        {
          this.step = Step.ACTIVATE_OLD_CLOSURE_STATES;
          break;
        }
      }
    }
    else if ( this.step.equals ( Step.ACTIVATE_OLD_CLOSURE_STATES ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: activate old closure states" );//$NON-NLS-1$
      }

      ArrayList < State > activeStateList = new ArrayList < State > ();
      for ( State current : this.machineOriginal.getState () )
      {
        if ( current.isActive () )
        {
          activeStateList.add ( current );
        }
      }

      for ( State current : getClosure ( activeStateList ) )
      {
        current.setActive ( true );
      }

      this.step = Step.ACTIVATE_SYMBOLS;
    }
    else if ( this.step.equals ( Step.ACTIVATE_SYMBOLS ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: activate symbols" );//$NON-NLS-1$
      }

      clearSymbolHighlightOriginal ();

      for ( State currentState : this.machineOriginal.getState () )
      {
        if ( currentState.isActive () )
        {
          for ( Transition currentTransition : currentState
              .getTransitionBegin () )
          {
            loopSymbol : for ( Symbol currentSymbol : currentTransition
                .getSymbol () )
            {
              if ( currentSymbol.equals ( this.currentActiveSymbol ) )
              {
                currentSymbol.setActive ( true );
                break loopSymbol;
              }
            }
          }
        }
      }

      this.step = Step.ACTIVATE_NEW_STATES;
    }
    else if ( this.step.equals ( Step.ACTIVATE_NEW_STATES ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: activate new states" );//$NON-NLS-1$
      }

      ArrayList < Transition > transitionList = new ArrayList < Transition > ();
      for ( Transition currentTransition : this.machineOriginal
          .getTransition () )
      {
        loopSymbol : for ( Symbol currentSymbol : currentTransition
            .getSymbol () )
        {
          if ( currentSymbol.isActive () )
          {
            transitionList.add ( currentTransition );
            break loopSymbol;
          }
        }
      }

      clearStateHighlightOriginal ();
      clearSymbolHighlightOriginal ();

      for ( Transition currentTransition : transitionList )
      {
        currentTransition.getStateEnd ().setActive ( true );
      }

      switch ( this.convertMachineType )
      {
        case NFA_TO_DFA :
        {
          this.step = Step.ADD_STATE_AND_TRANSITION;
          break;
        }
        case ENFA_TO_NFA :
        {
          this.step = Step.ACTIVATE_NEW_CLOSURE_STATES;
          break;
        }
      }
    }
    else if ( this.step.equals ( Step.ACTIVATE_NEW_CLOSURE_STATES ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: activate new closure states" );//$NON-NLS-1$
      }

      ArrayList < State > activeStateList = new ArrayList < State > ();
      for ( State current : this.machineOriginal.getState () )
      {
        if ( current.isActive () )
        {
          activeStateList.add ( current );
        }
      }

      for ( State current : getClosure ( activeStateList ) )
      {
        current.setActive ( true );
      }

      this.step = Step.ADD_STATE_AND_TRANSITION;
    }
    else if ( this.step.equals ( Step.ADD_STATE_AND_TRANSITION ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep", //$NON-NLS-1$
            "perform next step: add state and transition" ); //$NON-NLS-1$
      }

      StringBuilder name = new StringBuilder ();
      name.append ( "{" ); //$NON-NLS-1$
      boolean first = true;
      boolean finalState = false;
      for ( State currentState : this.machineOriginal.getState () )
      {
        if ( currentState.isActive () )
        {
          if ( currentState.isFinalState () )
          {
            finalState = true;
          }
          if ( !first )
          {
            name.append ( ", " );//$NON-NLS-1$
          }
          first = false;
          name.append ( currentState.getName () );
        }
      }
      name.append ( "}" );//$NON-NLS-1$

      State stateFound = null;
      for ( State current : this.machineConverted.getState () )
      {
        if ( current.getName ().equals ( name.toString () ) )
        {
          stateFound = current;
          break;
        }
      }

      State newState;
      DefaultStateView newStateView;
      if ( stateFound == null )
      {
        try
        {
          newState = new DefaultState ( this.machineConverted.getAlphabet (),
              this.machineConverted.getPushDownAlphabet (), name.toString (),
              false, finalState );
          newState.setActive ( true );
        }
        catch ( StateException exc )
        {
          exc.printStackTrace ();
          System.exit ( 1 );
          return;
        }

        newStateView = this.modelConverted.createStateView ( INITIAL_POSITION,
            INITIAL_POSITION, newState, false );

        Position position = getPosition ( newState );
        if ( position != null )
        {
          newStateView.move ( position.getX (), position.getY () );
        }

        // add to step item
        this.stepItemList.get ( this.stepItemList.size () - 1 )
            .setAddedDefaultStateView ( newStateView );
      }
      else
      {
        newState = stateFound;
        newState.setActive ( true );
        newStateView = this.modelConverted.getStateViewForState ( newState );
      }

      Transition transition;
      try
      {
        transition = new DefaultTransition ( this.machineConverted
            .getAlphabet (), this.machineConverted.getPushDownAlphabet (),
            new DefaultWord (), new DefaultWord (), this.currentActiveState,
            newState, new DefaultSymbol ( this.currentActiveSymbol.getName () ) );
        transition.setActive ( true );
      }
      catch ( TransitionSymbolNotInAlphabetException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
        return;
      }
      catch ( TransitionSymbolOnlyOneTimeException exc )
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

      DefaultTransitionView newTransitionView = this.modelConverted
          .createTransitionView ( transition, this.modelConverted
              .getStateViewForState ( this.currentActiveState ), newStateView,
              false, false, true );

      // add to step item
      this.stepItemList.get ( this.stepItemList.size () - 1 )
          .setAddedDefaultTransitionView ( newTransitionView );

      this.step = Step.CLEAR;
    }
    else if ( this.step.equals ( Step.CLEAR ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: clear" );//$NON-NLS-1$
      }

      clearStateHighlightOriginal ();
      clearStateHighlightConverted ();
      clearTransitionHighlightOriginal ();
      clearTransitionHighlightConverted ();
      clearTransitionHighlightOriginal ();
      clearSymbolHighlightConverted ();

      // calculate next symbol
      boolean useNextState = false;
      int index = -1;
      for ( int i = 0 ; i < this.machineConverted.getAlphabet ().size () ; i++ )
      {
        if ( this.currentActiveSymbol == this.machineConverted.getAlphabet ()
            .get ( i ) )
        {
          index = i;
          if ( i == this.machineConverted.getAlphabet ().size () - 1 )
          {
            useNextState = true;
          }
          break;
        }
      }

      if ( useNextState )
      {
        State nextState = this.machineConverted
            .getState ( this.machineConverted.getState ().size () - 1 );

        if ( nextState == this.currentActiveState )
        {
          this.endReached = true;
        }
        this.currentActiveState = nextState;

        this.currentActiveSymbol = this.machineConverted.getAlphabet ()
            .get ( 0 );
      }
      else
      {
        this.currentActiveSymbol = this.machineConverted.getAlphabet ().get (
            index + 1 );
      }

      this.step = Step.ACTIVATE_OLD_STATES;
    }
    else
    {
      throw new RuntimeException ( "unsupported step" ); //$NON-NLS-1$
    }

    if ( manualStep )
    {
      setStatus ();

      this.jGraphOriginal.repaint ();
      this.jGraphConverted.repaint ();
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
    clearStateHighlightConverted ();
    clearTransitionHighlightOriginal ();
    clearTransitionHighlightConverted ();
    clearSymbolHighlightOriginal ();
    clearSymbolHighlightConverted ();

    for ( State current : stepItem.getActiveStatesOriginal () )
    {
      current.setActive ( true );
    }
    for ( State current : stepItem.getActiveStatesConverted () )
    {
      current.setActive ( true );
    }
    for ( Transition current : stepItem.getActiveTransitionsOriginal () )
    {
      current.setActive ( true );
    }
    for ( Transition current : stepItem.getActiveTransitionsConverted () )
    {
      current.setActive ( true );
    }
    for ( Symbol current : stepItem.getActiveSymbolsOriginal () )
    {
      current.setActive ( true );
    }
    for ( Symbol current : stepItem.getActiveSymbolsConverted () )
    {
      current.setActive ( true );
    }
    this.step = stepItem.getActiveStep ();
    this.currentActiveState = stepItem.getActiveState ();
    this.currentActiveSymbol = stepItem.getActiveSymbol ();

    if ( stepItem.getAddedDefaultStateView () != null )
    {
      this.modelConverted.removeState ( stepItem.getAddedDefaultStateView (),
          false );
    }
    if ( stepItem.getAddedDefaultTransitionView () != null )
    {
      this.modelConverted.removeTransition ( stepItem
          .getAddedDefaultTransitionView (), false );
    }

    this.endReached = false;

    if ( manualStep )
    {
      setStatus ();

      this.jGraphOriginal.repaint ();
      this.jGraphConverted.repaint ();
    }
  }


  /**
   * Performs the start.
   */
  private final void performStart ()
  {
    this.step = Step.ACTIVATE_OLD_STATES;

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

    State newState;
    try
    {
      newState = new DefaultState ( this.machineConverted.getAlphabet (),
          this.machineConverted.getPushDownAlphabet (), "{"//$NON-NLS-1$
              + startState.getName () + "}", true, startState.isFinalState () );//$NON-NLS-1$
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    DefaultStateView newStateView = this.modelConverted.createStateView (
        INITIAL_POSITION, INITIAL_POSITION, newState, false );

    // store the first values
    this.currentActiveState = newState;
    this.currentActiveSymbol = this.machineConverted.getAlphabet ().get ( 0 );

    while ( !this.endReached )
    {
      performNextStep ( false );
    }

    new LayoutManager ( this.modelConverted, null ).doLayout ();
    for ( DefaultStateView current : this.modelConverted.getStateViewList () )
    {
      this.positionMap.put ( current.getState ().getName (), new Position (
          current.getPositionX (), current.getPositionY () ) );
    }

    while ( !this.stepItemList.isEmpty () )
    {
      performPreviousStep ( false );
    }

    // move the start state
    Position position = getPosition ( newState );
    newStateView.move ( position.getX (), position.getY () );

    setStatus ();
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
   * Shows the {@link ConvertMachineDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the convert machine dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
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
