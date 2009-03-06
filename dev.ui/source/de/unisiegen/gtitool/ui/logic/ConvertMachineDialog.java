package de.unisiegen.gtitool.ui.logic;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.DisjunctionNode;
import de.unisiegen.gtitool.core.entities.regex.EpsilonNode;
import de.unisiegen.gtitool.core.entities.regex.KleeneNode;
import de.unisiegen.gtitool.core.entities.regex.OneChildNode;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.entities.regex.TwoChildNode;
import de.unisiegen.gtitool.core.entities.regex.UnfinishedNode;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
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
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.core.regex.DefaultRegex.RegexType;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.core.util.Util;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.jgraph.StateView;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableColumnModel;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultRegexModel;
import de.unisiegen.gtitool.ui.netbeans.ConvertMachineDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.swing.JGTIScrollPane;
import de.unisiegen.gtitool.ui.swing.JGTITextPane;
import de.unisiegen.gtitool.ui.utils.AlgorithmDocument;
import de.unisiegen.gtitool.ui.utils.LayoutManager;
import de.unisiegen.gtitool.ui.utils.PowerSet;


/**
 * The {@link ConvertMachineDialog}.
 * 
 * @author Christian Fehler
 * @version $Id: ConvertMachineDialog.java 1375 2008-11-03 08:28:07Z fehler $
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
     * The {@link DFA} to {@link Regex} conversion type.
     */
    DFA_TO_REGEX,

    /**
     * The {@link ENFA} to {@link DFA} conversion type.
     */
    ENFA_TO_DFA,

    /**
     * The {@link ENFA} to {@link DFA} complete conversion type.
     */
    ENFA_TO_DFA_COMPLETE,

    /**
     * The {@link ENFA} to {@link NFA} conversion type.
     */
    ENFA_TO_NFA,

    /**
     * The {@link ENFA} to {@link NFA} complete conversion type.
     */
    ENFA_TO_NFA_COMPLETE,

    /**
     * The {@link NFA} to {@link DFA} conversion type.
     */
    NFA_TO_DFA,

    /**
     * The {@link NFA} to {@link DFA} complete conversion type.
     */
    NFA_TO_DFA_COMPLETE;
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
     * The activate new closure {@link State}s step.
     */
    ACTIVATE_NEW_CLOSURE_STATES,

    /**
     * The activate new {@link State}s step.
     */
    ACTIVATE_NEW_STATES,

    /**
     * The activate old closure {@link State} step.
     */
    ACTIVATE_OLD_CLOSURE_STATE,

    /**
     * The activate old {@link State} step.
     */
    ACTIVATE_OLD_STATE,

    /**
     * The activate start closure {@link State} step.
     */
    ACTIVATE_START_CLOSURE_STATE,

    /**
     * The activate start {@link State} step.
     */
    ACTIVATE_START_STATE,

    /**
     * The activate {@link Symbol}s step.
     */
    ACTIVATE_SYMBOLS,

    /**
     * The add start {@link State} step.
     */
    ADD_START_STATE,

    /**
     * The add {@link State} and {@link Transition} step.
     */
    ADD_STATE_AND_TRANSITION,

    /**
     * The calculate new language step
     */
    CALCULATE_NEW_LANGUAGE,

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
        case CALCULATE_NEW_LANGUAGE :
        {
          return "calculate new language"; //$NON-NLS-1$
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
     * The current active {@link State}.
     */
    private State activeState;


    /**
     * The active {@link State}s of the converted {@link JGTIGraph}.
     */
    private ArrayList < State > activeStatesConverted;


    /**
     * The active {@link State}s of the original {@link JGTIGraph}.
     */
    private ArrayList < State > activeStatesOriginal;


    /**
     * The active {@link Step}.
     */
    private Step activeStep;


    /**
     * The current active {@link Symbol}.
     */
    private Symbol activeSymbol;


    /**
     * The active {@link Symbol}s of the converted {@link JGTIGraph}.
     */
    private ArrayList < Symbol > activeSymbolsConverted;


    /**
     * The active {@link Symbol}s of the original {@link JGTIGraph}.
     */
    private ArrayList < Symbol > activeSymbolsOriginal;


    /**
     * The active {@link Transition}s of the converted {@link JGTIGraph}.
     */
    private ArrayList < Transition > activeTransitionsConverted;


    /**
     * The active {@link Transition}s of the original {@link JGTIGraph}.
     */
    private ArrayList < Transition > activeTransitionsOriginal;


    /**
     * The added {@link DefaultStateView}.
     */
    private DefaultStateView addedDefaultStateView = null;


    /**
     * The added {@link DefaultTransitionView}.
     */
    private DefaultTransitionView addedDefaultTransitionView = null;


    /**
     * The actual {@link RegexNode}
     */
    private RegexNode actualRegex = null;


    /**
     * The last finals index
     */
    private int lastFinalsIndex = 0;


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
     * @param activeSymbolsConverted The active {@link Symbol}s of the converted
     *          {@link JGTIGraph}.
     * @param actualRegex The actual {@link RegexNode}
     * @param finalsIndex The last finals index
     */
    public StepItem ( Step activeStep, Symbol currentActiveSymbol,
        State currentActiveState, ArrayList < State > activeStatesOriginal,
        ArrayList < State > activeStatesConverted,
        ArrayList < Transition > activeTransitionsOriginal,
        ArrayList < Transition > activeTransitionsConverted,
        ArrayList < Symbol > activeSymbolsOriginal,
        ArrayList < Symbol > activeSymbolsConverted, RegexNode actualRegex,
        int finalsIndex )
    {
      /**
       * if ( activeStep == null ) { throw new IllegalArgumentException (
       * "active step is null" ); //$NON-NLS-1$ } if ( currentActiveSymbol ==
       * null ) { throw new IllegalArgumentException ( "active symbol is null"
       * ); //$NON-NLS-1$ }
       **/

      this.activeStep = activeStep;
      this.activeSymbol = currentActiveSymbol;
      this.activeState = currentActiveState;
      this.activeStatesOriginal = activeStatesOriginal;
      this.activeStatesConverted = activeStatesConverted;
      this.activeTransitionsOriginal = activeTransitionsOriginal;
      this.activeTransitionsConverted = activeTransitionsConverted;
      this.activeSymbolsOriginal = activeSymbolsOriginal;
      this.activeSymbolsConverted = activeSymbolsConverted;
      this.actualRegex = actualRegex;
      this.lastFinalsIndex = finalsIndex;
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
     * Returns the lastFinalsIndex.
     * 
     * @return The lastFinalsIndex.
     * @see #lastFinalsIndex
     */
    public int getFinalsIndex ()
    {
      return this.lastFinalsIndex;
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
     * Returns the actualRegex.
     * 
     * @return The actualRegex.
     * @see #actualRegex
     */
    public RegexNode getActualRegex ()
    {
      return this.actualRegex;
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
   * The initial position.
   */
  private static final int INITIAL_POSITION = 200;


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ConvertMachineDialog.class );


  /**
   * The algorithm
   */
  private String algorithm = ""; //$NON-NLS-1$


  /**
   * The algorithm window
   */
  private JFrame algorithmWindow;


  /**
   * The auto step {@link Timer}.
   */
  private Timer autoStepTimer = null;


  /**
   * The {@link ConvertMachineTableModel}.
   */
  private ConvertMachineTableModel convertMachineTableModel;


  /**
   * The {@link ConvertMachineType}.
   */
  private ConvertMachineType convertMachineType;


  /**
   * The current {@link State}.
   */
  private State currentActiveState;


  /**
   * The current {@link Symbol}.
   */
  private Symbol currentActiveSymbol;


  /**
   * The empty set {@link State}.
   */
  private State emptySetState = null;


  /**
   * Flag that indicates if the end is reached.
   */
  protected boolean endReached = false;


  /**
   * The final states
   */
  private ArrayList < State > finals = new ArrayList < State > ();


  /**
   * The index for the {@link ArrayList} of final states
   */
  private int finalsIndex = 0;


  /**
   * The {@link ConvertMachineDialogForm}.
   */
  private ConvertMachineDialogForm gui;


  /**
   * The converted {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphConverted;


  /**
   * The original {@link JGTIGraph} containing the diagramm.
   */
  private JGTIGraph jGTIGraphOriginal;


  /**
   * Varialbe used for {@link DFA} -> {@link Regex}
   */
  private int k;


  /**
   * The converted {@link Machine}.
   */
  private Machine machineConverted;


  /**
   * The original {@link Machine}.
   */
  private Machine machineOriginal;


  /**
   * The {@link MachinePanel}.
   */
  private MachinePanel machinePanel;


  /**
   * The converted {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelConverted;


  /**
   * The original {@link DefaultMachineModel}.
   */
  private DefaultMachineModel modelOriginal;


  /**
   * The converted {@link DefaultRegexModel}.
   */
  private DefaultRegexModel modelRegexConverted;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link Position} map.
   */
  private HashMap < String, Position > positionMap;


  /**
   * The start {@link State} of the original machine
   */
  private State start = null;


  /**
   * The current {@link Step}.
   */
  private Step step = null;


  /**
   * The {@link StepItem} list.
   */
  private ArrayList < StepItem > stepItemList = new ArrayList < StepItem > ();


  /**
   * The {@link ConvertMachineTableColumnModel}.
   */
  private ConvertMachineTableColumnModel tableColumnModel = new ConvertMachineTableColumnModel ();


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
   * Adds the power set {@link State}s.
   */
  private final void addPowerSetStates ()
  {
    ArrayList < State > nameList = new ArrayList < State > ();
    for ( State current : this.machineOriginal.getState () )
    {
      nameList.add ( current );
    }

    PowerSet < State > powerSet = new PowerSet < State > ( nameList );
    for ( Set < State > currentSet : powerSet )
    {
      // the empty set state is added at last
      if ( currentSet.size () > 0 )
      {
        boolean startState = false;
        if ( currentSet.size () == 1 )
        {
          State foundState = null;
          for ( State current : this.machineOriginal.getState () )
          {
            if ( current.isStartState () )
            {
              foundState = current;
              break;
            }
          }

          if ( foundState == null )
          {
            throw new RuntimeException ( "no start state found" ); //$NON-NLS-1$
          }

          if ( currentSet.iterator ().next ().getName ().equals (
              foundState.getName () ) )
          {
            startState = true;
          }
        }

        StringBuilder name = new StringBuilder ( "{" ); //$NON-NLS-1$
        boolean first = true;
        boolean finalState = false;
        for ( State currentState : currentSet )
        {
          if ( !first )
          {
            name.append ( ", " ); //$NON-NLS-1$
          }
          first = false;

          finalState = finalState || currentState.isFinalState ();

          name.append ( currentState.getName () );
        }
        name.append ( "}" ); //$NON-NLS-1$

        try
        {
          State newState = new DefaultState ( this.machineConverted
              .getAlphabet (), this.machineConverted.getPushDownAlphabet (),
              name.toString (), startState, finalState );

          if ( startState )
          {
            setCurrentActiveState ( newState, true );
          }

          this.modelConverted.createStateView ( INITIAL_POSITION,
              INITIAL_POSITION, newState, false );
        }
        catch ( StateException exc )
        {
          exc.printStackTrace ();
          System.exit ( 1 );
          return;
        }
      }
    }
    // empty set state
    try
    {
      this.emptySetState = new DefaultState ( this.machineConverted
          .getAlphabet (), this.machineConverted.getPushDownAlphabet (),
          "\u2205", false, false ); //$NON-NLS-1$
      this.modelConverted.createStateView ( INITIAL_POSITION, INITIAL_POSITION,
          this.emptySetState, false );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
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

    if ( !this.convertMachineType.equals ( ConvertMachineType.DFA_TO_REGEX ) )
    {
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
      for ( Transition currentTransition : this.machineOriginal
          .getTransition () )
      {
        for ( Symbol currentSymbol : currentTransition )
        {
          if ( currentSymbol.isActive () )
          {
            activeSymbolsOriginal.add ( currentSymbol );
          }
        }
      }
      for ( Transition currentTransition : this.machineConverted
          .getTransition () )
      {
        for ( Symbol currentSymbol : currentTransition )
        {
          if ( currentSymbol.isActive () )
          {
            activeSymbolsConverted.add ( currentSymbol );
          }
        }
      }
    }
    RegexNode node = null;
    if ( this.modelRegexConverted != null )
    {
      if ( this.modelRegexConverted.getRegex ().getRegexNode () != null )
      {
        node = this.modelRegexConverted.getRegex ().getRegexNode ().clone ();
      }
    }
    this.stepItemList
        .add ( new StepItem ( this.step, this.currentActiveSymbol,
            this.currentActiveState, activeStatesOriginal,
            activeStatesConverted, activeTransitionsOriginal,
            activeTransitionsConverted, activeSymbolsOriginal,
            activeSymbolsConverted, node, this.finalsIndex ) );
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
   * @see Converter#convert(EntityType, EntityType,boolean)
   */
  public final void convert ( EntityType fromEntityType,
      EntityType toEntityType, boolean complete )
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
      if ( complete )
      {
        this.convertMachineType = ConvertMachineType.NFA_TO_DFA_COMPLETE;
      }
      else
      {
        this.convertMachineType = ConvertMachineType.NFA_TO_DFA;
      }
    }
    else if ( fromEntityType.equals ( MachineType.ENFA )
        && toEntityType.equals ( MachineType.NFA ) )
    {
      if ( complete )
      {
        this.convertMachineType = ConvertMachineType.ENFA_TO_NFA_COMPLETE;
      }
      else
      {
        this.convertMachineType = ConvertMachineType.ENFA_TO_NFA;
      }
    }
    else if ( fromEntityType.equals ( MachineType.ENFA )
        && toEntityType.equals ( MachineType.DFA ) )
    {
      if ( complete )
      {
        this.convertMachineType = ConvertMachineType.ENFA_TO_DFA_COMPLETE;
      }
      else
      {
        this.convertMachineType = ConvertMachineType.ENFA_TO_DFA;
      }
    }
    else if ( fromEntityType.equals ( MachineType.DFA )
        && toEntityType.equals ( RegexType.REGEX ) )
    {
      this.convertMachineType = ConvertMachineType.DFA_TO_REGEX;
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
    this.gui.jGTITableOutline.setColumnModel ( this.tableColumnModel );
    this.gui.jGTITableOutline.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableOutline.getSelectionModel ().setSelectionMode (
        ListSelectionModel.SINGLE_SELECTION );

    Rectangle rect = PreferenceManager.getInstance ()
        .getConvertMachineDialogBounds ();
    this.gui.jGTISplitPaneGraph.setDividerLocation ( ( rect.height - 100 ) / 2 );
    this.gui.jGTISplitPaneOutline.setDividerLocation ( rect.width - 250 );

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

        this.gui.setTitle ( Messages.getString ( "ConvertMachineDialog.Title", //$NON-NLS-1$
            Messages.getString ( "ConvertMachineDialog.NFA" ), Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.DFA" ) ) );//$NON-NLS-1$
        break;
      }
      case NFA_TO_DFA_COMPLETE :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultDFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );

        this.gui.setTitle ( Messages.getString (
            "ConvertMachineDialog.CompleteTitle", Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.NFA" ), Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.DFA" ) ) );//$NON-NLS-1$
        break;
      }
      case ENFA_TO_NFA :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultNFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );

        this.gui.setTitle ( Messages.getString ( "ConvertMachineDialog.Title", //$NON-NLS-1$
            Messages.getString ( "ConvertMachineDialog.ENFA" ), Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.NFA" ) ) );//$NON-NLS-1$
        break;
      }
      case ENFA_TO_NFA_COMPLETE :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultNFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );

        this.gui.setTitle ( Messages.getString (
            "ConvertMachineDialog.CompleteTitle", Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.ENFA" ), Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.NFA" ) ) );//$NON-NLS-1$
        break;
      }
      case ENFA_TO_DFA :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultDFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );

        this.gui.setTitle ( Messages.getString ( "ConvertMachineDialog.Title", //$NON-NLS-1$
            Messages.getString ( "ConvertMachineDialog.ENFA" ), Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.DFA" ) ) );//$NON-NLS-1$
        break;
      }
      case ENFA_TO_DFA_COMPLETE :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultDFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );

        this.gui.setTitle ( Messages.getString (
            "ConvertMachineDialog.CompleteTitle", Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.ENFA" ), Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.DFA" ) ) );//$NON-NLS-1$
        break;
      }
      case DFA_TO_REGEX :
      {
        this.modelConverted = new DefaultMachineModel ( new DefaultDFA (
            this.machineOriginal.getAlphabet (), this.machineOriginal
                .getPushDownAlphabet (), this.machineOriginal
                .isUsePushDownAlphabet () ) );
        this.k = this.modelOriginal.getMachine ().getState ().size () + 1;
        try
        {
          this.modelRegexConverted = new DefaultRegexModel ( new DefaultRegex (
              new DefaultRegexAlphabet ( this.machineOriginal.getAlphabet ()
                  .get () ) ), false );
          this.modelRegexConverted.initializeGraph ();
          this.jGTIGraphConverted = this.modelRegexConverted.getJGTIGraph ();
        }
        catch ( AlphabetException exc )
        {
          exc.printStackTrace ();
        }

        this.gui.setTitle ( Messages.getString (
            "ConvertMachineDialog.Title", Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.DFA" ), Messages//$NON-NLS-1$
                .getString ( "ConvertMachineDialog.REGEX" ) ) );//$NON-NLS-1$
        break;
      }
    }

    if ( !this.convertMachineType.equals ( ConvertMachineType.DFA_TO_REGEX ) )
    {
      this.gui.styledRegexParserPanel.setVisible ( false );
      this.gui.jGTISplitPaneConverted.setDividerSize ( 0 );
      this.jGTIGraphConverted = this.modelConverted.getJGTIGraph ();
      this.machineConverted = this.modelConverted.getMachine ();
      this.jGTIGraphConverted.setEnabled ( false );

      this.positionMap = new HashMap < String, Position > ();
      this.step = Step.ACTIVATE_START_STATE;
      this.currentActiveSymbol = this.machineConverted.getAlphabet ().get ( 0 );
    }
    else
    {
      this.step = Step.CALCULATE_NEW_LANGUAGE;
    }
    this.gui.jGTIScrollPaneConverted.setViewportView ( this.jGTIGraphConverted );

    if ( isComplete () )
    {
      addPowerSetStates ();
    }

    if ( !this.convertMachineType.equals ( ConvertMachineType.DFA_TO_REGEX ) )
    {
      // auto layout
      while ( !this.endReached )
      {
        performNextStep ( false );
      }
      new LayoutManager ( this.modelConverted, null ).doLayout ();
      for ( DefaultStateView current : this.modelConverted.getStateViewList () )
      {
        int yOffset = current.getState ().isLoopTransition () ? StateView.LOOP_TRANSITION_OFFSET
            : 0;
        this.positionMap.put ( current.getState ().getName (), new Position (
            current.getPositionX (), current.getPositionY () + yOffset ) );
      }
      while ( !this.stepItemList.isEmpty () )
      {
        performPreviousStep ( false );
      }
    }
    setStatus ();

    show ();
  }


  /**
   * Creates a {@link DisjunctionNode} of the symbols
   * 
   * @param symbols The symbols for the {@link DisjunctionNode}
   * @return {@link DisjunctionNode} of the symbols
   */
  private RegexNode createDisjunction ( ArrayList < Symbol > symbols )
  {
    if ( symbols.size () < 1 )
    {
      return null;
    }
    Symbol s = symbols.remove ( 0 );
    RegexNode r;
    if ( s.equals ( new DefaultSymbol () ) )
    {
      r = new EpsilonNode ();
    }
    else
    {
      r = new TokenNode ( s.getName () );
    }
    if ( symbols.size () == 0 )
    {
      return r;
    }
    return new DisjunctionNode ( createDisjunction ( symbols ), r );
  }


  /**
   * Eliminates a {@link RegexNode} from a {@link RegexNode}
   * 
   * @param node The {@link RegexNode} to delete
   * @param regex The {@link RegexNode}
   */
  private void eliminateNodeFromRegex ( RegexNode node, RegexNode regex )
  {
    RegexNode parentRegex = regex.getParentNodeForNode ( node );

    if ( parentRegex instanceof OneChildNode )
    {
      eliminateNodeFromRegex ( parentRegex, regex );
    }
    if ( parentRegex instanceof ConcatenationNode )
    {
      eliminateNodeFromRegex ( parentRegex, regex );
    }
    if ( parentRegex instanceof DisjunctionNode )
    {
      RegexNode r;
      if ( ( ( TwoChildNode ) parentRegex ).getRegex1 ().equals ( node ) )
      {
        r = ( ( TwoChildNode ) parentRegex ).getRegex2 ();
      }
      else
      {
        r = ( ( TwoChildNode ) parentRegex ).getRegex1 ();
      }
      RegexNode parentParent = regex.getParentNodeForNode ( parentRegex );
      if ( parentParent instanceof TwoChildNode )
      {
        TwoChildNode p = ( TwoChildNode ) parentParent;
        if ( p.getRegex1 ().equals ( parentRegex ) )
        {
          p.setRegex1 ( r );
        }
        else
        {
          p.setRegex2 ( r );
        }
      }
      else
      {
        OneChildNode one = ( OneChildNode ) parentParent;
        one.setRegex ( r );
      }
    }
  }


  /**
   * Returns the RegexNode for the Languages
   * 
   * @param LKK The {@link RegexNode} for LKK
   * @param LKJ The {@link RegexNode} for LKJ
   * @return The RegexNode for the Languages
   */
  private RegexNode generateRegex ( RegexNode LKK, RegexNode LKJ )
  {
    RegexNode regex = LKJ;
    if ( LKK == null )
    {
      return null;
    }
    if ( regex == null )
    {
      return null;
    }
    return new ConcatenationNode ( new KleeneNode ( LKK ), LKJ );
  }


  /**
   * Returns the RegexNode for the Languages
   * 
   * @param LIK The {@link RegexNode} for LIK
   * @param LKK The {@link RegexNode} for LKK
   * @param LKJ The {@link RegexNode} for LKJ
   * @return The RegexNode for the Languages
   */
  private RegexNode generateRegex ( RegexNode LIK, RegexNode LKK, RegexNode LKJ )
  {
    RegexNode regex = generateRegex ( LKK, LKJ );
    if ( LIK == null )
    {
      return null;
    }
    if ( regex == null )
    {
      return null;
    }
    return new ConcatenationNode ( LIK, regex );
  }


  /**
   * Returns the RegexNode for the Languages
   * 
   * @param LIJ The {@link RegexNode} for LIJ
   * @param LIK The {@link RegexNode} for LIK
   * @param LKK The {@link RegexNode} for LKK
   * @param LKJ The {@link RegexNode} for LKJ
   * @return The RegexNode for the Languages
   */
  private RegexNode generateRegex ( RegexNode LIJ, RegexNode LIK,
      RegexNode LKK, RegexNode LKJ )
  {
    RegexNode regex = generateRegex ( LIK, LKK, LKJ );
    if ( LIJ == null )
    {
      return regex;
    }
    if ( regex == null )
    {
      return LIJ;
    }
    return new DisjunctionNode ( LIJ, regex );
  }


  /**
   * Returns the convertMachineTableModel.
   * 
   * @return The convertMachineTableModel.
   * @see #convertMachineTableModel
   */
  public ConvertMachineTableModel getConvertMachineTableModel ()
  {
    return this.convertMachineTableModel;
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
   * Returns the L1 for s0 to s1
   * 
   * @param s0 {@link State} s0
   * @param s1 {@link State} s1
   * @return The L1 for s0 to s1
   */
  private RegexNode getL1 ( State s0, State s1 )
  {
    ArrayList < Symbol > l1 = new ArrayList < Symbol > ();
    if ( s0.equals ( s1 ) )
    {
      l1.add ( new DefaultSymbol () );
    }
    for ( Transition t : this.machineOriginal.getTransition () )
    {
      if ( t.getStateBegin ().equals ( s0 ) && t.getStateEnd ().equals ( s1 ) )
      {
        l1.addAll ( t.getSymbol () );
      }
    }
    return createDisjunction ( l1 );
  }


  /**
   * Returns Lk for s0 to s1
   * 
   * @param s0 {@link State} s0
   * @param s1 {@link State} s1
   * @param tmpK The k
   * @return Lk for s0 to s1
   */
  private RegexNode getLK ( State s0, State s1, int tmpK )
  {
    int i = tmpK;
    if ( i-- != 1 )
    {
      State s = this.machineOriginal.getState ( i - 1 );

      RegexNode LIJ = new UnfinishedNode ( s0, s1, i );
      RegexNode LIK = new UnfinishedNode ( s0, s, i );
      RegexNode LKK = new UnfinishedNode ( s, s, i );
      RegexNode LKJ = new UnfinishedNode ( s, s1, i );

      RegexNode result = generateRegex ( LIJ, LIK, LKK, LKJ );
      return result;

    }
    RegexNode result = getL1 ( s0, s1 );
    return result;
  }


  /**
   * Returns the machinePanel.
   * 
   * @return The machinePanel.
   * @see #machinePanel
   */
  public MachinePanel getMachinePanel ()
  {
    return this.machinePanel;
  }


  /**
   * Returns the modelConverted.
   * 
   * @return The modelConverted.
   * @see #modelConverted
   */
  public DefaultMachineModel getModelConverted ()
  {
    return this.modelConverted;
  }


  /**
   * Returns the modelOriginal.
   * 
   * @return The modelOriginal.
   * @see #modelOriginal
   */
  public DefaultMachineModel getModelOriginal ()
  {
    return this.modelOriginal;
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
   * Returns the tableColumnModel.
   * 
   * @return The tableColumnModel.
   * @see #tableColumnModel
   */
  public ConvertMachineTableColumnModel getTableColumnModel ()
  {
    return this.tableColumnModel;
  }


  /**
   * Shows or dispose the Algorithm window
   * 
   * @param show Show or dispose
   */
  public final void handleAlgorithmWindowChanged ( boolean show )
  {
    if ( this.algorithmWindow == null )
    {
      this.algorithmWindow = new JFrame (  );
      java.awt.GridBagConstraints gridBagConstraints;

      JGTIScrollPane jScrollPaneAlgorithm = new JGTIScrollPane();
      JGTITextPane jGTITextPaneAlgorithm = new JGTITextPane();

      this.algorithmWindow.setDefaultCloseOperation ( WindowConstants.DO_NOTHING_ON_CLOSE );
      this.algorithmWindow.setTitle(Messages.getString ( "AlgorithmWindow.Title")); //$NON-NLS-1$
      this.algorithmWindow.setAlwaysOnTop(true);
      this.algorithmWindow.getContentPane().setLayout(new GridBagLayout());

      jGTITextPaneAlgorithm.setEditable(false);

      jGTITextPaneAlgorithm.setDocument ( new AlgorithmDocument () );
      jGTITextPaneAlgorithm.setText ( this.algorithm );
      jGTITextPaneAlgorithm.setHighlighter ( null );
      jScrollPaneAlgorithm.setViewportView(jGTITextPaneAlgorithm);

      gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.gridy = 0;
      gridBagConstraints.fill = GridBagConstraints.BOTH;
      gridBagConstraints.weightx = 1.0;
      gridBagConstraints.weighty = 1.0;
      gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
      this.algorithmWindow.getContentPane().add(jScrollPaneAlgorithm, gridBagConstraints);

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      this.algorithmWindow.setBounds((screenSize.width-533)/2, (screenSize.height-330)/2, 533, 330);
    }
    this.gui.setModal ( false );
    this.algorithmWindow.setVisible ( show );
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

    PreferenceManager.getInstance ().setConvertMachineDialogPreferences (
        this.gui );
    if ( this.algorithmWindow != null )
    {
      this.algorithmWindow.dispose ();
    }
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

    if ( !this.convertMachineType.equals ( ConvertMachineType.DFA_TO_REGEX ) )
    {
      this.machinePanel.getMainWindow ().handleNew (
          this.modelConverted.getElement (), true );
    }
    else
    {
      this.machinePanel.getMainWindow ().handleNew (
          this.modelRegexConverted.getElement (), true );
    }
    PreferenceManager.getInstance ().setConvertMachineDialogPreferences (
        this.gui );
    if ( this.algorithmWindow != null )
    {
      this.algorithmWindow.dispose ();
    }
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
   * Handle print action.
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
   * Returns true if the complete version is used, otherwise false.
   * 
   * @return True if the complete version is used, otherwise false.
   */
  private final boolean isComplete ()
  {
    switch ( this.convertMachineType )
    {
      case NFA_TO_DFA :
      {
        return false;
      }
      case NFA_TO_DFA_COMPLETE :
      {
        return true;
      }
      case ENFA_TO_NFA :
      {
        return false;
      }
      case ENFA_TO_NFA_COMPLETE :
      {
        return true;
      }
      case ENFA_TO_DFA :
      {
        return false;
      }
      case ENFA_TO_DFA_COMPLETE :
      {
        return true;
      }
      case DFA_TO_REGEX :
      {
        return false;
      }
    }
    throw new RuntimeException ( "unsupported convert machine type" ); //$NON-NLS-1$
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
   */
  private final void performBeginStep ()
  {
    logger.debug ( "performBeginStep", "handle begin step" ); //$NON-NLS-1$ //$NON-NLS-2$

    while ( !this.stepItemList.isEmpty () )
    {
      performPreviousStep ( false );
    }

    setStatus ();
    updateGraph ();
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
    updateGraph ();
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
      prettyString
          .add ( Messages
              .getPrettyString (
                  "ConvertMachineDialog.ActivateStartState", startState.toPrettyString () ) ); //$NON-NLS-1$
      addOutlineComment ( prettyString );

      switch ( this.convertMachineType )
      {
        case NFA_TO_DFA :
        {
          this.step = Step.ADD_START_STATE;
          break;
        }
        case NFA_TO_DFA_COMPLETE :
        {
          this.step = Step.ACTIVATE_SYMBOLS;
          break;
        }
        case ENFA_TO_NFA :
        case ENFA_TO_NFA_COMPLETE :
        case ENFA_TO_DFA :
        case ENFA_TO_DFA_COMPLETE :
        {
          this.step = Step.ACTIVATE_START_CLOSURE_STATE;
          break;
        }
        case DFA_TO_REGEX :
        {
          throw new IllegalStateException ( "should not be" ); //$NON-NLS-1$
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
      for ( State current : Util.getClosure ( activeStateList ) )
      {
        current.setActive ( true );
        activeClosureStateList.add ( current );
      }
      Collections.sort ( activeClosureStateList );

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateStartClosureState" ) //$NON-NLS-1$
          + " (", Style.NONE ) ); //$NON-NLS-1$

      if ( activeStateList.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeStateList )
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

      prettyString.add ( new PrettyToken ( ") = ", Style.NONE ) ); //$NON-NLS-1$

      if ( activeClosureStateList.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeClosureStateList )
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

      if ( isComplete () )
      {
        this.step = Step.ACTIVATE_SYMBOLS;
      }
      else
      {
        this.step = Step.ADD_START_STATE;
      }
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

      State stateFound = null;
      for ( State current : this.machineConverted.getState () )
      {
        if ( current.getName ().equals ( name.toString () ) )
        {
          stateFound = current;
          break;
        }
      }

      if ( stateFound == null )
      {
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

        // add to step item
        this.stepItemList.get ( this.stepItemList.size () - 1 )
            .setAddedDefaultStateView ( newStateView );

        stateFound = newState;
      }

      setCurrentActiveState ( stateFound, manualStep );

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.add ( Messages.getPrettyString (
          "ConvertMachineDialog.AddStartState", stateFound.toPrettyString () ) ); //$NON-NLS-1$
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
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateOldState" ) //$NON-NLS-1$
          + " ", Style.NONE ) ); //$NON-NLS-1$
      if ( activeStateList.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeStateList )
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

      switch ( this.convertMachineType )
      {
        case NFA_TO_DFA :
        {
          this.step = Step.ACTIVATE_SYMBOLS;
          break;
        }
        case NFA_TO_DFA_COMPLETE :
        {
          this.step = Step.ACTIVATE_SYMBOLS;
          break;
        }
        case ENFA_TO_NFA :
        {
          this.step = Step.ACTIVATE_OLD_CLOSURE_STATE;
          break;
        }
        case ENFA_TO_NFA_COMPLETE :
        {
          this.step = Step.ACTIVATE_OLD_CLOSURE_STATE;
          break;
        }
        case ENFA_TO_DFA :
        {
          this.step = Step.ACTIVATE_OLD_CLOSURE_STATE;
          break;
        }
        case ENFA_TO_DFA_COMPLETE :
        {
          this.step = Step.ACTIVATE_OLD_CLOSURE_STATE;
          break;
        }
        case DFA_TO_REGEX :
        {
          throw new IllegalStateException ( "should not be" ); //$NON-NLS-1$
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
      for ( State current : Util.getClosure ( activeStateList ) )
      {
        current.setActive ( true );
        activeClosureStateList.add ( current );
      }
      Collections.sort ( activeClosureStateList );

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateOldClosureState" ) //$NON-NLS-1$
          + " (", Style.NONE ) ); //$NON-NLS-1$
      if ( activeStateList.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeStateList )
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
      prettyString.add ( new PrettyToken ( ") = ", Style.NONE ) ); //$NON-NLS-1$

      if ( activeClosureStateList.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeClosureStateList )
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
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateSymbols" ) //$NON-NLS-1$
          + " (", Style.NONE ) ); //$NON-NLS-1$
      if ( activeStateList.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeStateList )
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
      prettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
      prettyString.add ( this.currentActiveSymbol );
      prettyString.add ( new PrettyToken ( ")", Style.NONE ) ); //$NON-NLS-1$
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
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateNewStates" )//$NON-NLS-1$
          + " (", Style.NONE ) );//$NON-NLS-1$
      if ( oldActiveStateList.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : oldActiveStateList )
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

      prettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
      prettyString.add ( this.currentActiveSymbol );
      prettyString.add ( new PrettyToken ( ") = ", Style.NONE ) ); //$NON-NLS-1$
      if ( newActiveStateList.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : newActiveStateList )
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

      switch ( this.convertMachineType )
      {
        case NFA_TO_DFA :
        {
          this.step = Step.ADD_STATE_AND_TRANSITION;
          break;
        }
        case NFA_TO_DFA_COMPLETE :
        {
          this.step = Step.ADD_STATE_AND_TRANSITION;
          break;
        }
        case ENFA_TO_NFA :
        {
          this.step = Step.ACTIVATE_NEW_CLOSURE_STATES;
          break;
        }
        case ENFA_TO_NFA_COMPLETE :
        {
          this.step = Step.ACTIVATE_NEW_CLOSURE_STATES;
          break;
        }
        case ENFA_TO_DFA :
        {
          this.step = Step.ACTIVATE_NEW_CLOSURE_STATES;
          break;
        }
        case ENFA_TO_DFA_COMPLETE :
        {
          this.step = Step.ACTIVATE_NEW_CLOSURE_STATES;
          break;
        }
        case DFA_TO_REGEX :
        {
          throw new IllegalStateException ( "should not be" ); //$NON-NLS-1$
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
      for ( State current : Util.getClosure ( activeStateList ) )
      {
        current.setActive ( true );
        activeClosureStateList.add ( current );
      }

      // outline
      PrettyString prettyString = new PrettyString ();
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "ConvertMachineDialog.ActivateNewClosureStates" ) //$NON-NLS-1$
          + " (", Style.NONE ) ); //$NON-NLS-1$

      if ( activeStateList.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeStateList )
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

      prettyString.add ( new PrettyToken ( ") = ", Style.NONE ) ); //$NON-NLS-1$

      if ( activeClosureStateList.size () == 0 )
      {
        prettyString.add ( new PrettyToken ( "\u2205", Style.NONE ) ); //$NON-NLS-1$
      }
      else
      {
        prettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
        boolean first = true;
        for ( State current : activeClosureStateList )
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
      boolean emptySetStateFound = true;
      for ( State currentState : this.machineOriginal.getState () )
      {
        if ( currentState.isActive () )
        {
          emptySetStateFound = false;

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

      if ( emptySetStateFound )
      {
        if ( manualStep )
        {
          logger.debug ( "performNextStep",//$NON-NLS-1$
              "empty set state found" );//$NON-NLS-1$
        }

        switch ( this.convertMachineType )
        {
          case NFA_TO_DFA :
          case NFA_TO_DFA_COMPLETE :
          {
            name.delete ( 0, name.length () );
            name.append ( "\u2205" );//$NON-NLS-1$
            break;
          }
          case ENFA_TO_NFA :
          case ENFA_TO_NFA_COMPLETE :
          {
            this.step = Step.FINISH;

            // outline
            PrettyString prettyString = new PrettyString ();
            prettyString
                .add ( new PrettyToken (
                    Messages
                        .getString ( "ConvertMachineDialog.AddStateAndTransitionEmptySet" ), //$NON-NLS-1$
                    Style.NONE ) );
            addOutlineComment ( prettyString );

            if ( manualStep )
            {
              setStatus ();
              updateGraph ();
            }
            return;
          }
          case ENFA_TO_DFA :
          case ENFA_TO_DFA_COMPLETE :
          {
            name.delete ( 0, name.length () );
            name.append ( "\u2205" );//$NON-NLS-1$
            break;
          }
          case DFA_TO_REGEX :
          {
            throw new IllegalStateException ( "should not be" ); //$NON-NLS-1$
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

      // set the empty set state
      if ( emptySetStateFound )
      {
        this.emptySetState = newState;

        // check if the empty set state transition is already added
        if ( this.emptySetState.getTransitionBegin ().size () == 0 )
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
                new DefaultWord (), new DefaultWord (), this.emptySetState,
                this.emptySetState, symbolList );
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

        DefaultTransitionView newTransitionView = this.modelConverted
            .createTransitionView ( transition, this.modelConverted
                .getStateViewForState ( this.currentActiveState ),
                newStateView, false, false, true );

        // add to step item
        this.stepItemList.get ( this.stepItemList.size () - 1 )
            .setAddedDefaultTransitionView ( newTransitionView );

        // outline
        PrettyString prettyString = new PrettyString ();
        prettyString.add ( Messages.getPrettyString (
            "ConvertMachineDialog.AddStateAndTransitionAdd", //$NON-NLS-1$
            transition.getStateBegin ().toPrettyString (), transition
                .getStateEnd ().toPrettyString (), transition.getSymbol ( 0 )
                .toPrettyString () ) );
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

        // add to step item
        this.stepItemList.get ( this.stepItemList.size () - 1 ).setAddedSymbol (
            new ObjectPair < Transition, Symbol > ( foundTransition, symbol ) );

        // outline
        PrettyString prettyString = new PrettyString ();
        prettyString.add ( Messages.getPrettyString (
            "ConvertMachineDialog.AddStateAndTransitionModify", //$NON-NLS-1$
            symbol.toPrettyString (), foundTransition.getStateBegin ()
                .toPrettyString (), foundTransition.getStateEnd ()
                .toPrettyString () ) );
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
        prettyString.add ( Messages.getPrettyString (
            "ConvertMachineDialog.FinishNextState", //$NON-NLS-1$
            this.currentActiveSymbol.toPrettyString (), this.currentActiveState
                .toPrettyString () ) );
        addOutlineComment ( prettyString );

        for ( int i = 0 ; i < this.machineConverted.getState ().size () ; i++ )
        {
          if ( this.currentActiveState == this.machineConverted.getState ( i ) )
          {
            // the active state was the last state
            if ( i == this.machineConverted.getState ().size () - 1 )
            {
              this.endReached = true;
            }
            // the next state is the empty set state, but this empty set state
            // is the last
            else if ( ( this.emptySetState == this.machineConverted
                .getState ( i + 1 ) )
                && ( i == this.machineConverted.getState ().size () - 2 ) )
            {
              this.endReached = true;
            }
            // the next state is the empty set state
            else if ( this.emptySetState == this.machineConverted
                .getState ( i + 1 ) )
            {
              if ( i < this.machineConverted.getState ().size () - 1 )
              {
                logger.debug ( "performNextStep",//$NON-NLS-1$
                    "skip empty set state" );//$NON-NLS-1$

                setCurrentActiveState (
                    this.machineConverted.getState ( i + 2 ), manualStep );
              }
              else
              {
                logger.debug ( "performNextStep",//$NON-NLS-1$
                    "skip empty set state -> end reached" );//$NON-NLS-1$

                this.endReached = true;
              }
            }
            // the normal next state
            else
            {
              setCurrentActiveState ( this.machineConverted.getState ( i + 1 ),
                  manualStep );
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
        prettyString.add ( Messages.getPrettyString (
            "ConvertMachineDialog.FinishNextSymbol", //$NON-NLS-1$
            this.currentActiveSymbol.toPrettyString () ) );
        addOutlineComment ( prettyString );

        this.currentActiveSymbol = this.machineConverted.getAlphabet ().get (
            index + 1 );
      }

      this.step = Step.ACTIVATE_OLD_STATE;
    }
    else if ( this.step.equals ( Step.CALCULATE_NEW_LANGUAGE ) )
    {
      RegexNode node = this.modelRegexConverted.getRegex ().getRegexNode ();
      if ( node == null )
      {
        this.finals.clear ();
        for ( State s : this.modelOriginal.getMachine ().getState () )
        {
          if ( s.isStartState () )
          {
            this.start = s;
          }
          if ( s.isFinalState () )
          {
            this.finals.add ( s );
          }
        }
        RegexNode newNode = getLK ( this.start, this.finals
            .get ( this.finalsIndex ), this.k );
        this.modelRegexConverted.getRegex ().setRegexNode ( newNode,
            newNode.toString () );
        PrettyString string = new PrettyString ();
        PrettyString kString = new PrettyString ( new PrettyToken ( String
            .valueOf ( this.k ), Style.REGEX_SYMBOL ) );
        string.add ( Messages.getPrettyString (
            "ConvertMachineDialog.CreateLanguage", this.start.toPrettyString () //$NON-NLS-1$
            , this.finals.get ( this.finalsIndex ).toPrettyString (), kString,
            newNode.toPrettyString () ) );
        addOutlineComment ( string );
      }
      else
      {
        UnfinishedNode uNode = node.getNextUnfinishedNode ();
        if ( uNode != null )
        {
          RegexNode newNode = null;
          RegexNode parentNode = node.getParentNodeForNode ( uNode );
          if ( parentNode instanceof OneChildNode )
          {
            RegexNode tmp = getLK ( uNode.getS0 (), uNode.getS1 (), uNode
                .getK () );
            newNode = tmp;
            if ( tmp != null )
            {
              ( ( OneChildNode ) parentNode ).setRegex ( tmp );
            }
            else
            {
              eliminateNodeFromRegex ( uNode, node );
            }

          }
          if ( parentNode instanceof TwoChildNode )
          {
            TwoChildNode two = ( TwoChildNode ) parentNode;

            RegexNode r = getLK ( uNode.getS0 (), uNode.getS1 (), uNode.getK () );
            newNode = r;
            if ( r != null )
            {
              if ( two.getRegex1 ().equals ( uNode ) )
              {
                two.setRegex1 ( r );
              }
              else
              {
                two.setRegex2 ( r );
              }
            }
            else
            {
              eliminateNodeFromRegex ( uNode, node );
            }
          }
          this.modelRegexConverted.getRegex ().setRegexNode ( node,
              node.toString () );

          PrettyString string = new PrettyString ();
          PrettyString kString = new PrettyString ( new PrettyToken ( String
              .valueOf ( uNode.getK () ), Style.REGEX_SYMBOL ) );
          if ( newNode != null )
          {
            string
                .add ( Messages
                    .getPrettyString (
                        "ConvertMachineDialog.CreateLanguage", uNode.getS0 ().toPrettyString () //$NON-NLS-1$
                        , uNode.getS1 ().toPrettyString (), kString, newNode
                            .toPrettyString () ) );
          }
          else
          {
            string
                .add ( Messages
                    .getPrettyString (
                        "ConvertMachineDialog.CreateLanguage", uNode.getS0 ().toPrettyString () //$NON-NLS-1$
                        , uNode.getS1 ().toPrettyString (), kString,
                        new PrettyString ( new PrettyToken (
                            "\u2205", Style.KEYWORD ) ) ) ); //$NON-NLS-1$
          }
          addOutlineComment ( string );
        }
        else
        {
          this.finalsIndex++ ;
          RegexNode n = getLK ( this.start,
              this.finals.get ( this.finalsIndex ), this.k );
          RegexNode newNode;
          if ( n == null )
          {
            newNode = node;
          }
          else
          {
            newNode = new DisjunctionNode ( node, n );
          }
          this.modelRegexConverted.getRegex ().setRegexNode ( newNode,
              newNode.toString () );

          PrettyString string = new PrettyString ();
          if ( n != null )
          {
            string.add ( Messages.getPrettyString (
                "ConvertMachineDialog.CreateDisjunction", this.finals.get ( //$NON-NLS-1$
                    this.finalsIndex ).toPrettyString (), node
                    .toPrettyString (), n.toPrettyString () ) );
          }
          addOutlineComment ( string );

        }
      }

      if ( this.modelRegexConverted.getRegex ().getRegexNode () != null )
      {

        RegexNode nextUnfishedNode = this.modelRegexConverted.getRegex ()
            .getRegexNode ().getNextUnfinishedNode ();
        if ( this.finalsIndex + 1 >= this.finals.size ()
            && nextUnfishedNode == null )
        {
          this.endReached = true;
        }
      }
    }
    else
    {
      throw new RuntimeException ( "unsupported step" ); //$NON-NLS-1$
    }

    if ( manualStep )
    {
      setStatus ();
      updateGraph ();
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
    if ( !this.convertMachineType.equals ( ConvertMachineType.DFA_TO_REGEX ) )
    {
      clearStateHighlightConverted ();
      clearTransitionHighlightConverted ();
      clearSymbolHighlightConverted ();
    }
    clearTransitionHighlightOriginal ();
    clearSymbolHighlightOriginal ();

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
    setCurrentActiveState ( stepItem.getActiveState (), manualStep );
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
    if ( this.convertMachineType.equals ( ConvertMachineType.DFA_TO_REGEX ) )
    {
      this.finalsIndex = stepItem.getFinalsIndex ();
      if ( stepItem.getActualRegex () != null )
      {
        RegexNode node = stepItem.getActualRegex ();
        this.modelRegexConverted.changeRegexNode ( node, node.toString () );
      }
      else
      {
        this.k = this.modelOriginal.getMachine ().getState ().size () + 1;
        try
        {
          this.modelRegexConverted = new DefaultRegexModel ( new DefaultRegex (
              new DefaultRegexAlphabet ( this.machineOriginal.getAlphabet ()
                  .get () ) ), false );
          this.modelRegexConverted.initializeGraph ();
          this.jGTIGraphConverted = this.modelRegexConverted.getJGTIGraph ();
        }
        catch ( Exception e )
        {
          e.printStackTrace ();
          System.exit ( 1 );
        }

      }
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
      updateGraph ();
    }
  }


  /**
   * Sets the current active {@link State}.
   * 
   * @param state The next active {@link State}.
   * @param manualStep Flag that indicates if the {@link Step} is a manual
   *          {@link Step}.
   */
  private final void setCurrentActiveState ( State state, boolean manualStep )
  {
    if ( manualStep )
    {
      logger.debug ( "setCurrentActiveState", //$NON-NLS-1$
          "set the current active state to: " + Messages.QUOTE + state //$NON-NLS-1$
              + Messages.QUOTE );
    }

    if ( ( state != null ) && ( state == this.emptySetState ) )
    {
      throw new IllegalArgumentException (
          "active state is the empty set state: " //$NON-NLS-1$
              + state.getName () );
    }

    this.currentActiveState = state;
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
    if ( ( rect.x == PreferenceManager.DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_X )
        || ( rect.y == PreferenceManager.DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_Y ) )
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


  /**
   * Updates the {@link JGTIGraph}s.
   */
  private final void updateGraph ()
  {
    this.modelOriginal.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.modelOriginal.getGraphModel () ) );
    this.modelConverted.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.modelConverted.getGraphModel () ) );
    if ( this.convertMachineType.equals ( ConvertMachineType.DFA_TO_REGEX ) )
    {
      this.modelRegexConverted.initializeGraph ();
      this.jGTIGraphConverted = this.modelRegexConverted.getJGTIGraph ();
      this.gui.jGTIScrollPaneConverted
          .setViewportView ( this.jGTIGraphConverted );
      if ( this.modelRegexConverted.getRegex ().getRegexNode () != null )
      {
        this.modelRegexConverted.createTree ();
        this.gui.styledRegexParserPanel.setText ( this.modelRegexConverted
            .getRegex ().getRegexNode ().toPrettyString ().toString () );
      }
      else
      {
        this.gui.styledRegexParserPanel.setText ( "" ); //$NON-NLS-1$
      }
    }
  }
}
