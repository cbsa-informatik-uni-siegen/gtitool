package de.unisiegen.gtitool.ui.logic;


import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.machines.dfa.DFA;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.machines.enfa.ENFA;
import de.unisiegen.gtitool.core.machines.nfa.DefaultNFA;
import de.unisiegen.gtitool.core.machines.nfa.NFA;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableColumnModel;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
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
    ENFA_TO_NFA,

    /**
     * The {@link ENFA} to {@link DFA} conversion type.
     */
    ENFA_TO_DFA;
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


    /**
     * {@inheritDoc}
     * 
     * @see Object#toString()
     */
    @Override
    public final String toString ()
    {
      return "x: " + this.x + ", y: " + this.y; //$NON-NLS-1$//$NON-NLS-2$
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
     * The activate start closure {@link State} step.
     */
    ACTIVATE_START_CLOSURE_STATE,

    /**
     * The add start {@link State} step.
     */
    ADD_START_STATE,

    /**
     * The activate old {@link State} step.
     */
    ACTIVATE_OLD_STATE,

    /**
     * The activate old closure {@link State} step.
     */
    ACTIVATE_OLD_CLOSURE_STATE,

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
        case ACTIVATE_START_CLOSURE_STATE :
        {
          return "activate start closure state"; //$NON-NLS-1$
        }
        case ADD_START_STATE :
        {
          return "add start state"; //$NON-NLS-1$
        }
        case ACTIVATE_OLD_STATE :
        {
          return "activate old state"; //$NON-NLS-1$
        }
        case ACTIVATE_OLD_CLOSURE_STATE :
        {
          return "activate old closure state"; //$NON-NLS-1$
        }
        case ACTIVATE_SYMBOLS :
        {
          return "activate symbols"; //$NON-NLS-1$
        }
        case ACTIVATE_NEW_STATES :
        {
          return "activate new states"; //$NON-NLS-1$
        }
        case ACTIVATE_NEW_CLOSURE_STATES :
        {
          return "activate new closure states"; //$NON-NLS-1$
        }
        case ADD_STATE_AND_TRANSITION :
        {
          return "add state and transition"; //$NON-NLS-1$
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
     * The active {@link State}s of the converted {@link JGTIGraph}.
     */
    private ArrayList < State > activeStatesConverted;


    /**
     * The active {@link Transition}s of the original {@link JGTIGraph}.
     */
    private ArrayList < Transition > activeTransitionsOriginal;


    /**
     * The active {@link Transition}s of the converted {@link JGTIGraph}.
     */
    private ArrayList < Transition > activeTransitionsConverted;


    /**
     * The active {@link Symbol}s of the original {@link JGTIGraph}.
     */
    private ArrayList < Symbol > activeSymbolsOriginal;


    /**
     * The active {@link Symbol}s of the converted {@link JGTIGraph}.
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
     * @param activeStatesConverted The active {@link State}s of the converted
     *          {@link JGTIGraph}.
     * @param activeTransitionsOriginal The active {@link Transition}s of the
     *          original {@link JGTIGraph}.
     * @param activeTransitionsConverted The active {@link Transition}s of the
     *          converted {@link JGTIGraph}.
     * @param activeSymbolsOriginal The active {@link Symbol}s of the original
     *          {@link JGTIGraph}.
     * @param activeSymbolsConverted The active {@link Symbol}s of the
     *          converted {@link JGTIGraph}.
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
   * The original {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphOriginal;


  /**
   * The converted {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphConverted;


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
   * The junk {@link State}.
   */
  private State junkState = null;


  /**
   * The {@link ConvertMachineTableModel}.
   */
  private ConvertMachineTableModel convertMachineTableModel;


  /**
   * Allocates a new {@link ConvertMachineDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machinePanel The {@link MachinePanel}.
   */
  public ConvertMachineDialog ( JFrame parent, MachinePanel machinePanel )
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
    this.convertMachineTableModel.addRow ( prettyString );
    this.gui.jGTITableOutline.changeSelection ( this.convertMachineTableModel
        .getRowCount () - 1, ConvertMachineTableModel.OUTLINE_COLUMN, false,
        false );
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
   * Clears the {@link State} highlighting of the converted {@link JGTIGraph}.
   */
  private final void clearStateHighlightConverted ()
  {
    for ( State currentState : this.machineConverted.getState () )
    {
      currentState.setActive ( false );
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
   * Clears the {@link Symbol} highlighting of the converted {@link JGTIGraph}.
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
   * Clears the {@link Transition} highlighting of the converted
   * {@link JGTIGraph}.
   */
  private final void clearTransitionHighlightConverted ()
  {
    for ( Transition currentTransition : this.machineConverted.getTransition () )
    {
      currentTransition.setActive ( false );
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
   * {@inheritDoc}
   * 
   * @see Converter#convert(EntityType, EntityType)
   */
  public final void convert ( EntityType fromEntityType, EntityType toEntityType )
  {
    if ( fromEntityType == null )
    {
      throw new IllegalArgumentException ( "from entity type is null" );//$NON-NLS-1$
    }
    if ( toEntityType == null )
    {
      throw new IllegalArgumentException ( "to entity type is null" );//$NON-NLS-1$
    }

    if ( fromEntityType.equals ( MachineType.NFA )
        && toEntityType.equals ( MachineType.DFA ) )
    {
      this.convertMachineType = ConvertMachineType.NFA_TO_DFA;
    }
    else if ( fromEntityType.equals ( MachineType.ENFA )
        && toEntityType.equals ( MachineType.NFA ) )
    {
      this.convertMachineType = ConvertMachineType.ENFA_TO_NFA;
    }
    else if ( fromEntityType.equals ( MachineType.ENFA )
        && toEntityType.equals ( MachineType.DFA ) )
    {
      this.convertMachineType = ConvertMachineType.ENFA_TO_DFA;
    }
    else
    {
      throw new IllegalArgumentException ( "unsupported conversion from : " //$NON-NLS-1$
          + fromEntityType + " to " + toEntityType ); //$NON-NLS-1$
    }

    this.gui = new ConvertMachineDialogForm ( this, this.parent );

    // outline
    this.convertMachineTableModel = new ConvertMachineTableModel ();
    this.gui.jGTITableOutline.setModel ( this.convertMachineTableModel );
    this.gui.jGTITableOutline
        .setColumnModel ( new ConvertMachineTableColumnModel () );
    this.gui.jGTITableOutline.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableOutline.getSelectionModel ().setSelectionMode (
        ListSelectionModel.SINGLE_SELECTION );

    this.gui.jGTISplitPaneGraph.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationConvertMachine () );
    this.gui.jGTISplitPaneGraph.addPropertyChangeListener (
        JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void propertyChange ( PropertyChangeEvent event )
          {
            PreferenceManager.getInstance ().setDividerLocationConvertMachine (
                ( ( Integer ) event.getNewValue () ).intValue () );
          }
        } );
    this.gui.jGTISplitPaneOutline.setDividerLocation ( PreferenceManager
        .getInstance ().getDividerLocationConvertMachineOutline () );
    this.gui.jGTISplitPaneOutline.addPropertyChangeListener (
        JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void propertyChange ( PropertyChangeEvent event )
          {
            PreferenceManager.getInstance ()
                .setDividerLocationConvertMachineOutline (
                    ( ( Integer ) event.getNewValue () ).intValue () );
          }
        } );

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
    this.jGTIGraphOriginal = this.modelOriginal.getJGTIGraph ();
    this.jGTIGraphOriginal.setEnabled ( false );
    this.gui.jGTIScrollPaneOriginal.setViewportView ( this.jGTIGraphOriginal );
    this.machineOriginal = this.modelOriginal.getMachine ();

    switch ( this.convertMachineType )
    {
      case NFA_TO_DFA :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultDFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );

        this.gui.setTitle ( this.gui.getTitle () + ": " //$NON-NLS-1$
            + Messages.getString ( "ConvertMachineDialog.NFA" ) + " \u2192 " //$NON-NLS-1$ //$NON-NLS-2$
            + Messages.getString ( "ConvertMachineDialog.DFA" ) );//$NON-NLS-1$
        break;
      }
      case ENFA_TO_NFA :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultNFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );

        this.gui.setTitle ( this.gui.getTitle () + ": "//$NON-NLS-1$
            + Messages.getString ( "ConvertMachineDialog.ENFA" ) + " \u2192 "//$NON-NLS-1$ //$NON-NLS-2$
            + Messages.getString ( "ConvertMachineDialog.NFA" ) );//$NON-NLS-1$
        break;
      }
      case ENFA_TO_DFA :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultDFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );

        this.gui.setTitle ( this.gui.getTitle () + ": "//$NON-NLS-1$
            + Messages.getString ( "ConvertMachineDialog.ENFA" ) + " \u2192 "//$NON-NLS-1$ //$NON-NLS-2$
            + Messages.getString ( "ConvertMachineDialog.DFA" ) );//$NON-NLS-1$
        break;
      }
    }

    this.jGTIGraphConverted = this.modelConverted.getJGTIGraph ();
    this.jGTIGraphConverted.setEnabled ( false );
    this.gui.jGTIScrollPaneConverted.setViewportView ( this.jGTIGraphConverted );
    this.machineConverted = this.modelConverted.getMachine ();

    this.positionMap = new HashMap < String, Position > ();

    performStart ();

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

    PreferenceManager.getInstance ().setConvertMachineDialogPreferences (
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
        this.modelConverted.getElement (), true );

    PreferenceManager.getInstance ().setConvertMachineDialogPreferences (
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
   * Returns true if the normal {@link State} is a member of the {@link State}
   * set,otherwise false.
   * 
   * @param stateSet The {@link State} set.
   * @param normalState The normal {@link State}.
   * @return True if the normal {@link State} is a member of the {@link State}
   *         set,otherwise false.
   */
  private final boolean isStateMemberOfStateSet ( State stateSet,
      State normalState )
  {
    String name = stateSet.getName ();

    if ( name.charAt ( 0 ) != '{' )
    {
      throw new RuntimeException ( "not a state set name: " + name ); //$NON-NLS-1$
    }
    if ( name.charAt ( name.length () - 1 ) != '}' )
    {
      throw new RuntimeException ( "not a state set name: " + name ); //$NON-NLS-1$
    }

    String [] splitStateSet = name.substring ( 1, name.length () - 1 ).split (
        "," ); //$NON-NLS-1$

    for ( String current : splitStateSet )
    {
      String newName = current;

      if ( newName.length () == 0 )
      {
        throw new RuntimeException ( "name is empty" ); //$NON-NLS-1$
      }

      // remove spaces
      while ( newName.charAt ( 0 ) == ' ' )
      {
        newName = newName.substring ( 1 );

        if ( newName.length () == 0 )
        {
          throw new RuntimeException ( "name is empty" ); //$NON-NLS-1$
        }
      }
      while ( newName.charAt ( newName.length () - 1 ) == ' ' )
      {
        newName = newName.substring ( 0, newName.length () - 1 );

        if ( newName.length () == 0 )
        {
          throw new RuntimeException ( "name is empty" ); //$NON-NLS-1$
        }
      }

      if ( normalState.getName ().equals ( newName ) )
      {
        return true;
      }
    }
    return false;
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
    this.modelConverted.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.modelConverted.getGraphModel () ) );
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
    this.modelConverted.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.modelConverted.getGraphModel () ) );
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
          "ConvertMachineDialog.ActivateStartState", false, startState ) ); //$NON-NLS-1$
      addOutlineComment ( prettyString );

      switch ( this.convertMachineType )
      {
        case NFA_TO_DFA :
        {
          this.step = Step.ADD_START_STATE;
          break;
        }
        case ENFA_TO_NFA :
        {
          this.step = Step.ACTIVATE_START_CLOSURE_STATE;
          break;
        }
        case ENFA_TO_DFA :
        {
          this.step = Step.ACTIVATE_START_CLOSURE_STATE;
          break;
        }
      }
    }
    else if ( this.step.equals ( Step.ACTIVATE_START_CLOSURE_STATE ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: " + this.step );//$NON-NLS-1$
      }

      ArrayList < State > activeStateList = new ArrayList < State > ();
      for ( State current : this.machineOriginal.getState () )
      {
        if ( current.isActive () )
        {
          activeStateList.add ( current );
        }
      }
      Collections.sort ( activeStateList );

      ArrayList < State > activeClosureStateList = new ArrayList < State > ();
      for ( State current : getClosure ( activeStateList ) )
      {
        current.setActive ( true );
        activeClosureStateList.add ( current );
      }
      Collections.sort ( activeClosureStateList );

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.addPrettyToken ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateStartClosureState" ) //$NON-NLS-1$
          + " (", Style.NONE ) ); //$NON-NLS-1$

      if ( activeStateList.size () == 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeStateList )
        {
          if ( !first )
          {
            prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.addPrettyPrintable ( current );
        }
        prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }

      prettyString.addPrettyToken ( new PrettyToken ( ") = ", Style.NONE ) ); //$NON-NLS-1$

      if ( activeClosureStateList.size () == 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeClosureStateList )
        {
          if ( !first )
          {
            prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.addPrettyPrintable ( current );
        }
        prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }
      addOutlineComment ( prettyString );

      this.step = Step.ADD_START_STATE;
    }
    else if ( this.step.equals ( Step.ADD_START_STATE ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: " + this.step );//$NON-NLS-1$
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

      State newState;
      DefaultStateView newStateView;
      try
      {
        newState = new DefaultState ( this.machineConverted.getAlphabet (),
            this.machineConverted.getPushDownAlphabet (), name.toString (),
            true, finalState );
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

      this.currentActiveState = newState;
      this.currentActiveSymbol = this.machineConverted.getAlphabet ().get ( 0 );

      // add to step item
      this.stepItemList.get ( this.stepItemList.size () - 1 )
          .setAddedDefaultStateView ( newStateView );

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.addPrettyString ( Messages.getPrettyString (
          "ConvertMachineDialog.AddStartState", false, newState ) ); //$NON-NLS-1$
      addOutlineComment ( prettyString );

      this.step = Step.ACTIVATE_SYMBOLS;
    }
    else if ( this.step.equals ( Step.ACTIVATE_OLD_STATE ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: " + this.step );//$NON-NLS-1$
      }

      ArrayList < State > activeStateList = new ArrayList < State > ();
      for ( State current : this.machineOriginal.getState () )
      {
        if ( isStateMemberOfStateSet ( this.currentActiveState, current ) )
        {
          current.setActive ( true );
          activeStateList.add ( current );
        }
      }
      Collections.sort ( activeStateList );

      this.currentActiveState.setActive ( true );

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.addPrettyToken ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateOldState" ) //$NON-NLS-1$
          + " ", Style.NONE ) ); //$NON-NLS-1$
      if ( activeStateList.size () == 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeStateList )
        {
          if ( !first )
          {
            prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.addPrettyPrintable ( current );
        }
        prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }
      addOutlineComment ( prettyString );

      switch ( this.convertMachineType )
      {
        case NFA_TO_DFA :
        {
          this.step = Step.ACTIVATE_SYMBOLS;
          break;
        }
        case ENFA_TO_NFA :
        {
          this.step = Step.ACTIVATE_OLD_CLOSURE_STATE;
          break;
        }
        case ENFA_TO_DFA :
        {
          this.step = Step.ACTIVATE_OLD_CLOSURE_STATE;
          break;
        }
      }
    }
    else if ( this.step.equals ( Step.ACTIVATE_OLD_CLOSURE_STATE ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: " + this.step );//$NON-NLS-1$
      }

      ArrayList < State > activeStateList = new ArrayList < State > ();
      for ( State current : this.machineOriginal.getState () )
      {
        if ( current.isActive () )
        {
          activeStateList.add ( current );
        }
      }
      Collections.sort ( activeStateList );

      ArrayList < State > activeClosureStateList = new ArrayList < State > ();
      for ( State current : getClosure ( activeStateList ) )
      {
        current.setActive ( true );
        activeClosureStateList.add ( current );
      }
      Collections.sort ( activeClosureStateList );

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.addPrettyToken ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateOldClosureState" ) //$NON-NLS-1$
          + " (", Style.NONE ) ); //$NON-NLS-1$
      if ( activeStateList.size () == 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeStateList )
        {
          if ( !first )
          {
            prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.addPrettyPrintable ( current );
        }
        prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }
      prettyString.addPrettyToken ( new PrettyToken ( ") = ", Style.NONE ) ); //$NON-NLS-1$

      if ( activeClosureStateList.size () == 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeClosureStateList )
        {
          if ( !first )
          {
            prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.addPrettyPrintable ( current );
        }
        prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }
      addOutlineComment ( prettyString );

      this.step = Step.ACTIVATE_SYMBOLS;
    }
    else if ( this.step.equals ( Step.ACTIVATE_SYMBOLS ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: " + this.step );//$NON-NLS-1$
      }

      clearSymbolHighlightOriginal ();

      ArrayList < State > activeStateList = new ArrayList < State > ();
      for ( State currentState : this.machineOriginal.getState () )
      {
        if ( currentState.isActive () )
        {
          activeStateList.add ( currentState );
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
      Collections.sort ( activeStateList );

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.addPrettyToken ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateSymbols" ) //$NON-NLS-1$
          + " (", Style.NONE ) ); //$NON-NLS-1$
      if ( activeStateList.size () == 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeStateList )
        {
          if ( !first )
          {
            prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.addPrettyPrintable ( current );
        }
        prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }
      prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
      prettyString.addPrettyPrintable ( this.currentActiveSymbol );
      prettyString.addPrettyToken ( new PrettyToken ( ")", Style.NONE ) ); //$NON-NLS-1$
      addOutlineComment ( prettyString );

      this.step = Step.ACTIVATE_NEW_STATES;
    }
    else if ( this.step.equals ( Step.ACTIVATE_NEW_STATES ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: " + this.step );//$NON-NLS-1$
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
      ArrayList < State > oldActiveStateList = new ArrayList < State > ();
      for ( State current : this.machineOriginal.getState () )
      {
        if ( current.isActive () )
        {
          oldActiveStateList.add ( current );
        }
      }
      Collections.sort ( oldActiveStateList );

      clearStateHighlightOriginal ();
      clearSymbolHighlightOriginal ();

      ArrayList < State > newActiveStateList = new ArrayList < State > ();
      for ( Transition currentTransition : transitionList )
      {
        currentTransition.getStateEnd ().setActive ( true );
        newActiveStateList.add ( currentTransition.getStateEnd () );
      }
      Collections.sort ( newActiveStateList );

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.addPrettyToken ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateNewStates" )//$NON-NLS-1$
          + " (", Style.NONE ) );//$NON-NLS-1$
      if ( oldActiveStateList.size () == 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : oldActiveStateList )
        {
          if ( !first )
          {
            prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.addPrettyPrintable ( current );
        }
        prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }

      prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
      prettyString.addPrettyPrintable ( this.currentActiveSymbol );
      prettyString.addPrettyToken ( new PrettyToken ( ") = ", Style.NONE ) ); //$NON-NLS-1$
      if ( newActiveStateList.size () == 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : newActiveStateList )
        {
          if ( !first )
          {
            prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.addPrettyPrintable ( current );
        }
        prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }
      addOutlineComment ( prettyString );

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
        case ENFA_TO_DFA :
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
            "perform next step: " + this.step );//$NON-NLS-1$
      }

      ArrayList < State > activeStateList = new ArrayList < State > ();
      for ( State current : this.machineOriginal.getState () )
      {
        if ( current.isActive () )
        {
          activeStateList.add ( current );
        }
      }

      ArrayList < State > activeClosureStateList = new ArrayList < State > ();
      for ( State current : getClosure ( activeStateList ) )
      {
        current.setActive ( true );
        activeClosureStateList.add ( current );
      }

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.addPrettyToken ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateNewClosureStates" ) //$NON-NLS-1$
          + " (", Style.NONE ) ); //$NON-NLS-1$

      if ( activeStateList.size () == 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeStateList )
        {
          if ( !first )
          {
            prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.addPrettyPrintable ( current );
        }
        prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }

      prettyString.addPrettyToken ( new PrettyToken ( ") = ", Style.NONE ) ); //$NON-NLS-1$

      if ( activeClosureStateList.size () == 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeClosureStateList )
        {
          if ( !first )
          {
            prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          }
          first = false;
          prettyString.addPrettyPrintable ( current );
        }
        prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
      }
      addOutlineComment ( prettyString );

      this.step = Step.ADD_STATE_AND_TRANSITION;
    }
    else if ( this.step.equals ( Step.ADD_STATE_AND_TRANSITION ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: " + this.step );//$NON-NLS-1$
      }

      StringBuilder name = new StringBuilder ();
      name.append ( "{" ); //$NON-NLS-1$
      boolean first = true;
      boolean finalState = false;
      boolean junkStateFound = true;
      for ( State currentState : this.machineOriginal.getState () )
      {
        if ( currentState.isActive () )
        {
          junkStateFound = false;

          finalState = finalState || currentState.isFinalState ();
          if ( !first )
          {
            name.append ( ", " );//$NON-NLS-1$
          }
          first = false;
          name.append ( currentState.getName () );
        }
      }
      name.append ( "}" );//$NON-NLS-1$

      if ( junkStateFound )
      {
        if ( manualStep )
        {
          logger.debug ( "performNextStep",//$NON-NLS-1$
              "junk state found" );//$NON-NLS-1$
        }

        switch ( this.convertMachineType )
        {
          case NFA_TO_DFA :
          {
            name.delete ( 0, name.length () );
            name.append ( "\u2205" );//$NON-NLS-1$
            break;
          }
          case ENFA_TO_NFA :
          {
            this.step = Step.FINISH;

            if ( manualStep )
            {
              setStatus ();

              this.modelOriginal.getGraphModel ().cellsChanged (
                  DefaultGraphModel.getAll ( this.modelOriginal
                      .getGraphModel () ) );
              this.modelConverted.getGraphModel ().cellsChanged (
                  DefaultGraphModel.getAll ( this.modelConverted
                      .getGraphModel () ) );
            }
            return;
          }
          case ENFA_TO_DFA :
          {
            name.delete ( 0, name.length () );
            name.append ( "\u2205" );//$NON-NLS-1$
            break;
          }
        }
      }

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

      // set the junk state
      if ( junkStateFound )
      {
        this.junkState = newState;

        // check if the junk state transition is already added
        if ( this.junkState.getTransitionBegin ().size () == 0 )
        {
          // add transition
          Transition transition;
          try
          {
            ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
            for ( Symbol current : this.machineConverted.getAlphabet () )
            {
              symbolList.add ( new DefaultSymbol ( current.getName () ) );
            }

            transition = new DefaultTransition ( this.machineConverted
                .getAlphabet (), this.machineConverted.getPushDownAlphabet (),
                new DefaultWord (), new DefaultWord (), this.junkState,
                this.junkState, symbolList );
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

          this.modelConverted.createTransitionView ( transition, newStateView,
              newStateView, false, false, true );
        }
      }

      Transition foundTransition = null;

      for ( Transition current : this.currentActiveState.getTransitionBegin () )
      {
        if ( current.getStateEnd () == newState )
        {
          foundTransition = current;
          break;
        }
      }

      if ( foundTransition == null )
      {
        Transition transition;
        try
        {
          transition = new DefaultTransition ( this.machineConverted
              .getAlphabet (), this.machineConverted.getPushDownAlphabet (),
              new DefaultWord (), new DefaultWord (), this.currentActiveState,
              newState,
              new DefaultSymbol ( this.currentActiveSymbol.getName () ) );
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
                .getStateViewForState ( this.currentActiveState ),
                newStateView, false, false, true );

        // add to step item
        this.stepItemList.get ( this.stepItemList.size () - 1 )
            .setAddedDefaultTransitionView ( newTransitionView );

        // outline
        PrettyString prettyString = new PrettyString ();
        prettyString.addPrettyString ( Messages.getPrettyString (
            "ConvertMachineDialog.AddStateAndTransitionAdd", false, //$NON-NLS-1$
            transition.getStateBegin (), transition.getStateEnd (), transition
                .getSymbol ( 0 ) ) );
        addOutlineComment ( prettyString );
      }
      else
      {
        Symbol symbol;
        try
        {
          symbol = new DefaultSymbol ( this.currentActiveSymbol.getName () );
          foundTransition.add ( symbol );
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

        // add to step item
        this.stepItemList.get ( this.stepItemList.size () - 1 ).setAddedSymbol (
            new ObjectPair < Transition, Symbol > ( foundTransition, symbol ) );

        // outline
        PrettyString prettyString = new PrettyString ();
        prettyString.addPrettyString ( Messages.getPrettyString (
            "ConvertMachineDialog.AddStateAndTransitionModify", false, //$NON-NLS-1$
            symbol, foundTransition.getStateBegin (), foundTransition
                .getStateEnd () ) );
        addOutlineComment ( prettyString );
      }

      this.step = Step.FINISH;
    }
    else if ( this.step.equals ( Step.FINISH ) )
    {
      if ( manualStep )
      {
        logger.debug ( "performNextStep",//$NON-NLS-1$
            "perform next step: " + this.step );//$NON-NLS-1$
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
        // outline
        PrettyString prettyString = new PrettyString ();
        prettyString.addPrettyString ( Messages.getPrettyString (
            "ConvertMachineDialog.FinishNextState", false,//$NON-NLS-1$
            this.currentActiveSymbol, this.currentActiveState ) );
        addOutlineComment ( prettyString );

        for ( int i = 0 ; i < this.machineConverted.getState ().size () ; i++ )
        {
          if ( this.currentActiveState == this.machineConverted.getState ( i ) )
          {
            if ( i == this.machineConverted.getState ().size () - 1 )
            {
              this.endReached = true;
            }
            else if ( this.junkState == this.machineConverted.getState ( i + 1 ) )
            {
              if ( i < this.machineConverted.getState ().size () - 1 )
              {
                logger.debug ( "performNextStep",//$NON-NLS-1$
                    "skip junk state" );//$NON-NLS-1$

                this.currentActiveState = this.machineConverted
                    .getState ( i + 2 );
              }
              else
              {
                logger.debug ( "performNextStep",//$NON-NLS-1$
                    "skipt junk state -> end reached" );//$NON-NLS-1$

                this.endReached = true;
              }
            }
            else
            {
              this.currentActiveState = this.machineConverted.getState ( i + 1 );
            }
            break;
          }
        }

        this.currentActiveSymbol = this.machineConverted.getAlphabet ()
            .get ( 0 );
      }
      else
      {
        // outline
        PrettyString prettyString = new PrettyString ();
        prettyString.addPrettyString ( Messages.getPrettyString (
            "ConvertMachineDialog.FinishNextSymbol", false, //$NON-NLS-1$
            this.currentActiveSymbol ) );
        addOutlineComment ( prettyString );

        this.currentActiveSymbol = this.machineConverted.getAlphabet ().get (
            index + 1 );
      }

      this.step = Step.ACTIVATE_OLD_STATE;
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
      this.modelConverted.getGraphModel ().cellsChanged (
          DefaultGraphModel.getAll ( this.modelConverted.getGraphModel () ) );
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
    if ( stepItem.getAddedSymbol () != null )
    {
      Transition transition = stepItem.getAddedSymbol ().getFirst ();
      Symbol symbol = stepItem.getAddedSymbol ().getSecond ();
      transition.remove ( symbol );
    }

    // outline
    this.convertMachineTableModel.removeLastRow ();
    this.gui.jGTITableOutline.changeSelection ( this.convertMachineTableModel
        .getRowCount () - 1, ConvertMachineTableModel.OUTLINE_COLUMN, false,
        false );

    this.endReached = false;

    if ( manualStep )
    {
      setStatus ();

      this.modelOriginal.getGraphModel ().cellsChanged (
          DefaultGraphModel.getAll ( this.modelOriginal.getGraphModel () ) );
      this.modelConverted.getGraphModel ().cellsChanged (
          DefaultGraphModel.getAll ( this.modelConverted.getGraphModel () ) );
    }
  }


  /**
   * Performs the start.
   */
  private final void performStart ()
  {
    this.step = Step.ACTIVATE_START_STATE;

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

    Rectangle rect = PreferenceManager.getInstance ()
        .getConvertMachineDialogBounds ();
    if ( rect.x == PreferenceManager.DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_X
        || rect.y == PreferenceManager.DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_Y )
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
