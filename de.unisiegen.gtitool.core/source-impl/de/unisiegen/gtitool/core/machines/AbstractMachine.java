package de.unisiegen.gtitool.core.machines;


import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.JTable;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultStack;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultStateSet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.StateSet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.listener.AlphabetChangedListener;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.StateChangedListener;
import de.unisiegen.gtitool.core.entities.listener.TransitionChangedListener;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAllSymbolsException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineEpsilonTransitionException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateFinalException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateNameException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateNotReachableException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateStartException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineTransitionStackOperationsException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.stateset.StateSetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordNotAcceptedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.core.machines.listener.MachineChangedListener;
import de.unisiegen.gtitool.core.machines.nfa.DefaultNFA;
import de.unisiegen.gtitool.core.machines.pda.DefaultPDA;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.core.util.ObjectTriple;
import de.unisiegen.gtitool.logger.Logger;


/**
 * The abstract class for all machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class AbstractMachine implements Machine
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( AbstractMachine.class );


  /**
   * The index of the {@link State} column.
   */
  private static final int STATE_COLUMN = 0;


  /**
   * The index of the epsilon {@link Transition} column.
   */
  private static final int EPSILON_COLUMN = 1;


  /**
   * The count of special columns.
   */
  private static final int SPECIAL_COLUMN_COUNT = 2;


  /**
   * Returns the {@link Machine} with the given {@link Machine} type.
   * 
   * @param machineType The {@link Machine} type.
   * @param alphabet The {@link Alphabet}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this
   *          {@link Transition}.
   * @param usePushDownAlphabet The use push down {@link Alphabet}.
   * @return The {@link Machine} with the given {@link Machine} type.
   * @throws StoreException If the {@link Machine} type is unknown.
   */
  public static Machine createMachine ( String machineType, Alphabet alphabet,
      Alphabet pushDownAlphabet, boolean usePushDownAlphabet )
      throws StoreException
  {
    if ( machineType.equals ( ( "DFA" ) ) ) //$NON-NLS-1$
    {
      return new DefaultDFA ( alphabet, pushDownAlphabet, usePushDownAlphabet );
    }
    if ( machineType.equals ( ( "NFA" ) ) ) //$NON-NLS-1$
    {
      return new DefaultNFA ( alphabet, pushDownAlphabet, usePushDownAlphabet );
    }
    if ( machineType.equals ( ( "ENFA" ) ) ) //$NON-NLS-1$
    {
      return new DefaultENFA ( alphabet, pushDownAlphabet, usePushDownAlphabet );
    }
    if ( machineType.equals ( ( "PDA" ) ) ) //$NON-NLS-1$
    {
      return new DefaultPDA ( alphabet, pushDownAlphabet, usePushDownAlphabet );
    }
    throw new StoreException ( Messages
        .getString ( "StoreException.WrongMachineType" ) ); //$NON-NLS-1$
  }


  /**
   * The active {@link State}s.
   */
  private TreeSet < State > activeStateSet;


  /**
   * The active {@link Transition}s.
   */
  private TreeSet < Transition > activeTransitionSet;


  /**
   * The active {@link Symbol}s.
   */
  private ArrayList < Symbol > activeSymbolList;


  /**
   * The {@link Alphabet} of this {@link AbstractMachine}.
   */
  private Alphabet alphabet;


  /**
   * The push down {@link Alphabet} of this {@link AbstractMachine}.
   */
  private Alphabet pushDownAlphabet;


  /**
   * The use push down {@link Alphabet} of this {@link AbstractMachine}.
   */
  private boolean usePushDownAlphabet;


  /**
   * The initial use push down {@link Alphabet} of this {@link AbstractMachine}.
   */
  private boolean initialUsePushDownAlphabet;


  /**
   * The current {@link State} id.
   */
  private int currentStateId = State.ID_NOT_DEFINED;


  /**
   * The current {@link Transition} id.
   */
  private int currentTransitionId = Transition.ID_NOT_DEFINED;


  /**
   * The history of this {@link AbstractMachine}.
   */
  private ArrayList < HistoryItem > history;


  /**
   * The list of the {@link State}s.
   */
  private ArrayList < State > stateList;


  /**
   * The initial list of the {@link State}s.
   */
  private ArrayList < State > initialStateList;


  /**
   * The list of the {@link Transition}.
   */
  private ArrayList < Transition > transitionList;


  /**
   * The list of the {@link Transition}.
   */
  private ArrayList < Transition > initialTransitionList;


  /**
   * The current {@link Word}.
   */
  private Word word = null;


  /**
   * The current {@link Stack}.
   */
  private Stack stack = null;


  /**
   * The validation element list.
   */
  private ArrayList < ValidationElement > validationElementList;


  /**
   * List of listeners
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link AlphabetChangedListener}.
   */
  private AlphabetChangedListener alphabetChangedListener;


  /**
   * The {@link TransitionChangedListener}.
   */
  private TransitionChangedListener transitionChangedListener;


  /**
   * The {@link StateChangedListener}.
   */
  private StateChangedListener stateChangedListener;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * The list of cached values which is needed because of the {@link Transition}
   * highlighting.
   */
  private ArrayList < ObjectTriple < Integer, Integer, Object >> cachedValueList;


  /**
   * Allocates a new {@link AbstractMachine}.
   * 
   * @param alphabet The {@link Alphabet} of this {@link AbstractMachine}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this
   *          {@link AbstractMachine}.
   * @param usePushDownAlphabet The use push down {@link Alphabet}.
   * @param validationElements The validation elements which indicates which
   *          validation elements should be checked during a validation.
   */
  public AbstractMachine ( Alphabet alphabet, Alphabet pushDownAlphabet,
      boolean usePushDownAlphabet, ValidationElement ... validationElements )
  {
    // Alphabet
    if ( alphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    this.alphabet = alphabet;
    // PushDownAlphabet
    if ( pushDownAlphabet == null )
    {
      throw new NullPointerException ( "push down alphabet is null" ); //$NON-NLS-1$
    }
    this.pushDownAlphabet = pushDownAlphabet;
    // UsePushDownAlphabet
    this.usePushDownAlphabet = usePushDownAlphabet;
    // Validation elements
    if ( validationElements == null )
    {
      throw new NullPointerException ( "validation elements is null" ); //$NON-NLS-1$
    }
    this.validationElementList = new ArrayList < ValidationElement > ();
    for ( ValidationElement current : validationElements )
    {
      this.validationElementList.add ( current );
    }
    // StateList
    this.stateList = new ArrayList < State > ();
    this.initialStateList = new ArrayList < State > ();
    // TransitionList
    this.transitionList = new ArrayList < Transition > ();
    this.initialTransitionList = new ArrayList < Transition > ();
    // Active
    this.activeStateSet = new TreeSet < State > ();
    this.activeTransitionSet = new TreeSet < Transition > ();
    this.activeSymbolList = new ArrayList < Symbol > ();
    // History
    this.history = new ArrayList < HistoryItem > ();
    // CachedValueList
    this.cachedValueList = new ArrayList < ObjectTriple < Integer, Integer, Object >> ();

    // AlphabetChangedListener
    this.alphabetChangedListener = new AlphabetChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void alphabetChanged ( @SuppressWarnings ( "unused" )
      Alphabet newAlphabet )
      {
        fireTableStructureChanged ();
      }
    };
    this.alphabet.addAlphabetChangedListener ( this.alphabetChangedListener );

    // TransitionChangedListener
    this.transitionChangedListener = new TransitionChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void transitionChanged ( @SuppressWarnings ( "unused" )
      Transition newTransition )
      {
        fireTableDataChanged ();
      }
    };

    // StateChangedListener
    this.stateChangedListener = new StateChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void stateChanged ( @SuppressWarnings ( "unused" )
      State newState )
      {
        fireTableDataChanged ();
      }
    };

    // ModifyStatusChangedListener
    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void modifyStatusChanged ( boolean modified )
      {
        fireModifyStatusChanged ( modified );
      }
    };

    this.alphabet
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    this.pushDownAlphabet
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );

    // Reset modify
    resetModify ();
  }


  /**
   * Adds the given {@link MachineChangedListener}.
   * 
   * @param listener The {@link MachineChangedListener}.
   */
  public final void addMachineChangedListener ( MachineChangedListener listener )
  {
    this.listenerList.add ( MachineChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Adds the {@link State}s to this {@link AbstractMachine}.
   * 
   * @param states The {@link State}s to add.
   */
  public final void addState ( Iterable < State > states )
  {
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    if ( !states.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    }
    for ( State current : states )
    {
      addState ( current );
    }
  }


  /**
   * Adds the {@link State} to this {@link AbstractMachine}.
   * 
   * @param state The {@link State} to add.
   */
  public final void addState ( State state )
  {
    if ( state == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    if ( ( state.isIdDefined () ) && ( this.stateList.contains ( state ) ) )
    {
      throw new IllegalArgumentException ( "state is already added" ); //$NON-NLS-1$
    }

    // Set the state alphabet if it is not set already.
    if ( state.getAlphabet () == null )
    {
      state.setAlphabet ( this.alphabet );
    }
    if ( !this.alphabet.equals ( state.getAlphabet () ) )
    {
      throw new IllegalArgumentException ( "not the same alphabet" ); //$NON-NLS-1$
    }

    // Set the state push down alphabet if it is not set already.
    if ( state.getPushDownAlphabet () == null )
    {
      state.setPushDownAlphabet ( this.pushDownAlphabet );
    }
    if ( !this.pushDownAlphabet.equals ( state.getPushDownAlphabet () ) )
    {
      throw new IllegalArgumentException ( "not the same push down alphabet" ); //$NON-NLS-1$
    }

    if ( state.getId () == State.ID_NOT_DEFINED )
    {
      state.setId ( ++this.currentStateId );
    }
    else
    {
      if ( state.getId () > this.currentStateId )
      {
        this.currentStateId = state.getId ();
      }
    }
    state.setDefaultName ();
    this.stateList.add ( state );
    link ( state );
    fireTableDataChanged ();
    state.addStateChangedListener ( this.stateChangedListener );
    state.addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    fireModifyStatusChanged ( true );
  }


  /**
   * Adds the {@link State}s to this {@link AbstractMachine}.
   * 
   * @param states The {@link State}s to add.
   */
  public final void addState ( State ... states )
  {
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    if ( states.length == 0 )
    {
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    }
    for ( State current : states )
    {
      addState ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#addTableModelListener(TableModelListener)
   */
  public final void addTableModelListener ( TableModelListener listener )
  {
    this.listenerList.add ( TableModelListener.class, listener );
  }


  /**
   * Adds the {@link Transition}s to this {@link AbstractMachine}.
   * 
   * @param transitions The {@link Transition}s to add.
   */
  public final void addTransition ( Iterable < Transition > transitions )
  {
    if ( transitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( !transitions.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    }
    for ( Transition current : transitions )
    {
      addTransition ( current );
    }
  }


  /**
   * Adds the {@link Transition} to this {@link AbstractMachine}.
   * 
   * @param transition The {@link Transition} to add.
   */
  public final void addTransition ( Transition transition )
  {
    if ( transition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( ( transition.isIdDefined () )
        && ( this.transitionList.contains ( transition ) ) )
    {
      throw new IllegalArgumentException ( "transition is already added" ); //$NON-NLS-1$
    }

    // Set the transition alphabet if it is not set already.
    if ( transition.getAlphabet () == null )
    {
      transition.setAlphabet ( this.alphabet );
    }
    if ( !this.alphabet.equals ( transition.getAlphabet () ) )
    {
      throw new IllegalArgumentException ( "not the same alphabet" ); //$NON-NLS-1$
    }

    // Set the transition push down alphabet if it is not set already.
    if ( transition.getPushDownAlphabet () == null )
    {
      transition.setPushDownAlphabet ( this.pushDownAlphabet );
    }
    if ( !this.pushDownAlphabet.equals ( transition.getPushDownAlphabet () ) )
    {
      throw new IllegalArgumentException ( "not the same push down alphabet" ); //$NON-NLS-1$
    }

    if ( transition.getId () == Transition.ID_NOT_DEFINED )
    {
      transition.setId ( ++this.currentTransitionId );
    }
    else
    {
      if ( transition.getId () > this.currentTransitionId )
      {
        this.currentTransitionId = transition.getId ();
      }
    }
    this.transitionList.add ( transition );
    link ( transition );
    fireTableDataChanged ();
    transition.addTransitionChangedListener ( this.transitionChangedListener );
    transition
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    fireModifyStatusChanged ( true );
  }


  /**
   * Adds the {@link Transition}s to this {@link AbstractMachine}.
   * 
   * @param transitions The {@link Transition}s to add.
   */
  public final void addTransition ( Transition ... transitions )
  {
    if ( transitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( transitions.length == 0 )
    {
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    }
    for ( Transition current : transitions )
    {
      addTransition ( current );
    }
  }


  /**
   * Checks if there is a {@link State}, which {@link Transition}s do not
   * contain all {@link Symbol}s.
   * 
   * @return The list of {@link MachineException}.
   */
  private final ArrayList < MachineException > checkAllSymbols ()
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();
    for ( State currentState : this.getState () )
    {
      TreeSet < Symbol > currentSymbolSet = new TreeSet < Symbol > ();
      for ( Transition currentTransition : currentState.getTransitionBegin () )
      {
        currentSymbolSet.addAll ( currentTransition.getSymbol () );
      }
      TreeSet < Symbol > notUsedSymbolSet = new TreeSet < Symbol > ();
      for ( Symbol currentSymbol : this.alphabet )
      {
        // The symbols must be cloned
        try
        {
          notUsedSymbolSet
              .add ( new DefaultSymbol ( currentSymbol.getName () ) );
        }
        catch ( SymbolException exc )
        {
          exc.printStackTrace ();
          System.exit ( 1 );
        }
      }
      for ( Symbol currentSymbol : currentSymbolSet )
      {
        notUsedSymbolSet.remove ( currentSymbol );
      }
      if ( notUsedSymbolSet.size () > 0 )
      {
        machineExceptionList.add ( new MachineAllSymbolsException (
            currentState, notUsedSymbolSet ) );
      }
    }
    return machineExceptionList;
  }


  /**
   * Checks if there is a {@link Transition} without a {@link Symbol}.
   * 
   * @return The list of {@link MachineException}.
   */
  private final ArrayList < MachineException > checkEpsilonTransition ()
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();
    for ( Transition currentTransition : this.getTransition () )
    {
      if ( currentTransition.getSymbol ().size () == 0 )
      {
        machineExceptionList.add ( new MachineEpsilonTransitionException (
            currentTransition ) );
      }
    }
    return machineExceptionList;
  }


  /**
   * Checks if there is no final state defined.
   * 
   * @return The list of {@link MachineException}.
   */
  private final ArrayList < MachineException > checkFinalState ()
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();
    boolean found = false;
    loop : for ( State currentState : this.stateList )
    {
      if ( currentState.isFinalState () )
      {
        found = true;
        break loop;
      }
    }
    if ( !found )
    {
      machineExceptionList.add ( new MachineStateFinalException () );
    }
    return machineExceptionList;
  }


  /**
   * Checks if there is more than one start state defined.
   * 
   * @return The list of {@link MachineException}.
   */
  private final ArrayList < MachineException > checkMoreThanOneStartState ()
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();
    ArrayList < State > startStateList = new ArrayList < State > ();
    for ( State current : this.stateList )
    {
      if ( current.isStartState () )
      {
        startStateList.add ( current );
      }
    }
    if ( startStateList.size () >= 2 )
    {
      machineExceptionList.add ( new MachineStateStartException (
          startStateList ) );
    }
    return machineExceptionList;
  }


  /**
   * Checks if there is no start state defined.
   * 
   * @return The list of {@link MachineException}.
   */
  private final ArrayList < MachineException > checkNoStartState ()
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();
    ArrayList < State > startStateList = new ArrayList < State > ();
    for ( State current : this.getState () )
    {
      if ( current.isStartState () )
      {
        startStateList.add ( current );
      }
    }
    if ( startStateList.size () == 0 )
    {
      machineExceptionList.add ( new MachineStateStartException (
          startStateList ) );
    }
    return machineExceptionList;
  }


  /**
   * Checks if there are {@link State}s with the same name.
   * 
   * @return The list of {@link MachineException}.
   */
  private final ArrayList < MachineException > checkStateName ()
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();
    ArrayList < State > duplicatedListAll = new ArrayList < State > ();
    firstLoop : for ( int i = 0 ; i < this.getState ().size () ; i++ )
    {
      if ( duplicatedListAll.contains ( this.getState ().get ( i ) ) )
      {
        continue firstLoop;
      }
      ArrayList < State > duplicatedListOne = new ArrayList < State > ();
      for ( int j = i + 1 ; j < this.getState ().size () ; j++ )
      {
        if ( this.getState ().get ( i ).getName ().equals (
            this.getState ().get ( j ).getName () ) )
        {
          duplicatedListOne.add ( this.getState ().get ( j ) );
        }
      }
      if ( duplicatedListOne.size () > 0 )
      {
        duplicatedListOne.add ( this.getState ().get ( i ) );
        for ( State current : duplicatedListOne )
        {
          duplicatedListAll.add ( current );
        }
        machineExceptionList.add ( new MachineStateNameException (
            duplicatedListOne ) );
      }
    }
    return machineExceptionList;
  }


  /**
   * Checks if there is a {@link State} which is not reachable.
   * 
   * @return The list of {@link MachineException}.
   */
  private final ArrayList < MachineException > checkStateNotReachable ()
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();
    for ( State current : this.getState () )
    {
      if ( ( current.getTransitionEnd ().size () == 0 )
          && ( !current.isStartState () ) )
      {
        machineExceptionList.add ( new MachineStateNotReachableException (
            current ) );
      }
      else
      {
        boolean warning = true;
        for ( Transition currentTransition : current.getTransitionEnd () )
        {
          if ( ! ( ( currentTransition.getStateBegin () == current ) && ( currentTransition
              .getStateEnd () == current ) ) )
          {
            warning = false;
            break;
          }
        }
        if ( warning && ( !current.isStartState () ) )
        {
          machineExceptionList.add ( new MachineStateNotReachableException (
              current ) );
        }
      }
    }
    return machineExceptionList;
  }


  /**
   * Checks if there is a {@link State} with {@link Transition}s with the same
   * {@link Symbol}.
   * 
   * @return The list of {@link MachineException}.
   */
  private final ArrayList < MachineException > checkSymbolOnlyOneTime ()
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();
    for ( State currentState : this.getState () )
    {
      for ( Symbol currentSymbol : this.alphabet )
      {
        ArrayList < Transition > transitions = new ArrayList < Transition > ();
        ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
        for ( Transition currentTransition : currentState.getTransitionBegin () )
        {
          if ( currentTransition.contains ( currentSymbol ) )
          {
            transitions.add ( currentTransition );
            for ( Symbol addSymbol : currentTransition.getSymbol () )
            {
              if ( addSymbol.equals ( currentSymbol ) )
              {
                symbols.add ( addSymbol );
              }
            }
          }
        }
        if ( transitions.size () > 1 )
        {
          machineExceptionList.add ( new MachineSymbolOnlyOneTimeException (
              currentState, symbols, transitions ) );
        }
      }
    }
    return machineExceptionList;
  }


  /**
   * Checks if there is a {@link Transition} with {@link Stack} operations.
   * 
   * @return The list of {@link MachineException}.
   */
  private final ArrayList < MachineException > checkTransitionStackOperation ()
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();
    for ( Transition currentTransition : this.getTransition () )
    {
      if ( ( currentTransition.getPushDownWordRead ().size () > 0 )
          || ( currentTransition.getPushDownWordWrite ().size () > 0 ) )
      {
        machineExceptionList
            .add ( new MachineTransitionStackOperationsException (
                currentTransition ) );
      }
    }
    return machineExceptionList;
  }


  /**
   * Clears the active {@link State}s.
   */
  private final void clearActiveState ()
  {
    this.activeStateSet.clear ();
    for ( State current : this.stateList )
    {
      current.setActive ( false );
    }
  }


  /**
   * Clears the active {@link Symbol}s.
   */
  private final void clearActiveSymbol ()
  {
    this.activeSymbolList.clear ();
    for ( Transition currentTransition : this.transitionList )
    {
      for ( Symbol currentSymbol : currentTransition )
      {
        currentSymbol.setActive ( false );
      }
    }
  }


  /**
   * Clears the active {@link Transition}s.
   */
  private final void clearActiveTransition ()
  {
    this.activeTransitionSet.clear ();
    for ( Transition current : this.transitionList )
    {
      current.setActive ( false );
    }
  }


  /**
   * Clears the history of this {@link AbstractMachine}.
   */
  private final void clearHistory ()
  {
    this.history.clear ();
  }


  /**
   * Clears all selected.
   */
  public final void clearSelectedTransition ()
  {
    // find the columns
    for ( int i = 0 ; i < getColumnCount () ; i++ )
    {
      for ( int j = 0 ; j < getRowCount () ; j++ )
      {
        Object value = getValueAt ( j, i );
        if ( value instanceof State )
        {
          State state = ( State ) value;
          state.setSelected ( false );
        }
        else if ( value instanceof StateSet )
        {
          StateSet stateSet = ( StateSet ) value;
          for ( State currentState : stateSet )
          {
            currentState.setSelected ( false );
          }
        }
        else
        {
          throw new RuntimeException ( "not supported table value class: " //$NON-NLS-1$
              + value.getClass ().getSimpleName () );
        }
      }
    }
  }


  /**
   * Let the listeners know that the editing is startet.
   */
  private final void fireMachineChangedStartEditing ()
  {
    logger.debug ( "fireMachineChangedStartEditing", "start editing" ); //$NON-NLS-1$ //$NON-NLS-2$
    MachineChangedListener [] listeners = this.listenerList
        .getListeners ( MachineChangedListener.class );
    for ( MachineChangedListener current : listeners )
    {
      current.startEditing ();
    }
  }


  /**
   * Let the listeners know that the editing is stopped.
   */
  private final void fireMachineChangedStopEditing ()
  {
    logger.debug ( "fireMachineChangedStopEditing", "stop editing" ); //$NON-NLS-1$ //$NON-NLS-2$
    MachineChangedListener [] listeners = this.listenerList
        .getListeners ( MachineChangedListener.class );
    for ( MachineChangedListener current : listeners )
    {
      current.stopEditing ();
    }
  }


  /**
   * Let the listeners know that {@link Symbol}s were added to the
   * {@link Transition}.
   * 
   * @param transition The modified {@link Transition}.
   * @param addedSymbols The added {@link Symbol}s.
   */
  private final void fireMachineChangedSymbolAdded ( Transition transition,
      ArrayList < Symbol > addedSymbols )
  {
    logger.debug ( "fireMachineChangedSymbolAdded", "symbol added: " //$NON-NLS-1$ //$NON-NLS-2$
        + addedSymbols + " to " + transition ); //$NON-NLS-1$
    MachineChangedListener [] listeners = this.listenerList
        .getListeners ( MachineChangedListener.class );
    for ( MachineChangedListener current : listeners )
    {
      current.symbolAdded ( transition, addedSymbols );
    }
  }


  /**
   * Let the listeners know that {@link Symbol}s were removed from the
   * {@link Transition}.
   * 
   * @param transition The modified {@link Transition}.
   * @param removedSymbols The removed {@link Symbol}s.
   */
  private final void fireMachineChangedSymbolRemoved ( Transition transition,
      ArrayList < Symbol > removedSymbols )
  {
    logger.debug ( "fireMachineChangedSymbolRemoved", "symbol removed: " //$NON-NLS-1$ //$NON-NLS-2$
        + removedSymbols + " from " + transition ); //$NON-NLS-1$
    MachineChangedListener [] listeners = this.listenerList
        .getListeners ( MachineChangedListener.class );
    for ( MachineChangedListener current : listeners )
    {
      current.symbolRemoved ( transition, removedSymbols );
    }
  }


  /**
   * Let the listeners know that a new {@link Transition} is added.
   * 
   * @param newTransition The {@link Transition} to add.
   */
  private final void fireMachineChangedTransitionAdded (
      Transition newTransition )
  {
    logger.debug ( "fireMachineChangedTransitionAdded", "transition added: " //$NON-NLS-1$//$NON-NLS-2$
        + newTransition );
    MachineChangedListener [] listeners = this.listenerList
        .getListeners ( MachineChangedListener.class );
    for ( MachineChangedListener current : listeners )
    {
      current.transitionAdded ( newTransition );
    }
  }


  /**
   * Let the listeners know that the {@link Transition} is removed.
   * 
   * @param transition The {@link Transition} to remove.
   */
  private final void fireMachineChangedTransitionRemoved ( Transition transition )
  {
    logger.debug ( "fireMachineChangedTransitionRemoved", //$NON-NLS-1$
        "transition removed: " + transition ); //$NON-NLS-1$
    MachineChangedListener [] listeners = this.listenerList
        .getListeners ( MachineChangedListener.class );
    for ( MachineChangedListener current : listeners )
    {
      current.transitionRemoved ( transition );
    }
  }


  /**
   * Let the listeners know that the modify status has changed.
   * 
   * @param forceModify True if the modify is forced, otherwise false.
   */
  private final void fireModifyStatusChanged ( boolean forceModify )
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    if ( forceModify )
    {
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( true );
      }
    }
    else
    {
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( newModifyStatus );
      }
    }
  }


  /**
   * Forwards the given notification event to all {@link TableModelListener}s
   * that registered themselves as listeners for this table model.
   * 
   * @param event The event to be forwarded
   * @see #addTableModelListener
   * @see TableModelEvent
   * @see EventListenerList
   */
  private final void fireTableChanged ( TableModelEvent event )
  {
    TableModelListener [] listeners = this.listenerList
        .getListeners ( TableModelListener.class );
    for ( TableModelListener current : listeners )
    {
      current.tableChanged ( event );
    }
  }


  /**
   * Notifies all listeners that all cell values in the table's rows may have
   * changed. The number of rows may also have changed and the {@link JTable}
   * should redraw the table from scratch. The structure of the table (as in the
   * order of the columns) is assumed to be the same.
   * 
   * @see TableModelEvent
   * @see EventListenerList
   * @see JTable#tableChanged(TableModelEvent)
   */
  private final void fireTableDataChanged ()
  {
    fireTableChanged ( new TableModelEvent ( this ) );
  }


  /**
   * Notifies all listeners that the table's structure has changed.
   * 
   * @see TableModelEvent
   * @see EventListenerList
   */
  private final void fireTableStructureChanged ()
  {
    fireTableChanged ( new TableModelEvent ( this, TableModelEvent.HEADER_ROW ) );
  }


  /**
   * Returns the active {@link State}s.
   * 
   * @return The active {@link State}s.
   */
  public final TreeSet < State > getActiveState ()
  {
    return this.activeStateSet;
  }


  /**
   * Returns the active {@link Symbol}s.
   * 
   * @return The active {@link Symbol}s.
   */
  public final ArrayList < Symbol > getActiveSymbol ()
  {
    return this.activeSymbolList;
  }


  /**
   * Returns the active {@link Transition}s.
   * 
   * @return The active {@link Transition}s.
   */
  public final TreeSet < Transition > getActiveTransition ()
  {
    return this.activeTransitionSet;
  }


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   * @see #alphabet
   */
  public final Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getColumnClass(int)
   */
  public final Class < ? > getColumnClass ( @SuppressWarnings ( "unused" )
  int columnIndex )
  {
    return PrettyPrintable.class;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getColumnCount()
   */
  public final int getColumnCount ()
  {
    return SPECIAL_COLUMN_COUNT + this.alphabet.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getColumnName(int)
   */
  public final String getColumnName ( int columnIndex )
  {
    if ( columnIndex == STATE_COLUMN )
    {
      return ""; //$NON-NLS-1$
    }
    if ( columnIndex == EPSILON_COLUMN )
    {
      return "\u03B5"; //$NON-NLS-1$
    }
    return this.alphabet.get ( columnIndex - SPECIAL_COLUMN_COUNT ).toString ();
  }


  /**
   * Returns the current {@link Symbol}.
   * 
   * @return The current {@link Symbol}.
   * @throws WordFinishedException If something with the {@link Word} is not
   *           correct.
   * @throws WordResetedException If something with the {@link Word} is not
   *           correct.
   */
  public final Symbol getCurrentSymbol () throws WordFinishedException,
      WordResetedException
  {
    return this.word.getCurrentSymbol ();
  }


  /**
   * Returns the {@link HistoryItem}.
   * 
   * @return The {@link HistoryItem}.
   * @see #history
   */
  public final ArrayList < HistoryItem > getHistory ()
  {
    return this.history;
  }


  /**
   * Returns the {@link Machine} type.
   * 
   * @return The {@link Machine} type.
   */
  public abstract String getMachineType ();


  /**
   * Returns the {@link Symbol}s which are not removeable from the
   * {@link Alphabet}.
   * 
   * @return The {@link Symbol}s which are not removeable from the
   *         {@link Alphabet}.
   */
  public final TreeSet < Symbol > getNotRemoveableSymbolsFromAlphabet ()
  {
    TreeSet < Symbol > notRemoveableSymbols = new TreeSet < Symbol > ();
    for ( Transition current : this.transitionList )
    {
      notRemoveableSymbols.addAll ( current.getSymbol () );
    }
    return notRemoveableSymbols;
  }


  /**
   * Returns the {@link Symbol}s which are not removeable from the
   * {@link Alphabet}.
   * 
   * @return The {@link Symbol}s which are not removeable from the
   *         {@link Alphabet}.
   */
  public final TreeSet < Symbol > getNotRemoveableSymbolsFromPushDownAlphabet ()
  {
    TreeSet < Symbol > notRemoveableSymbols = new TreeSet < Symbol > ();
    for ( Transition current : this.transitionList )
    {
      for ( Symbol currentSymbol : current.getPushDownWordRead () )
      {
        notRemoveableSymbols.add ( currentSymbol );
      }
      for ( Symbol currentSymbol : current.getPushDownWordWrite () )
      {
        notRemoveableSymbols.add ( currentSymbol );
      }
    }
    return notRemoveableSymbols;
  }


  /**
   * Returns the push down {@link Alphabet}.
   * 
   * @return The push down {@link Alphabet}.
   * @see #pushDownAlphabet
   */
  public final Alphabet getPushDownAlphabet ()
  {
    return this.pushDownAlphabet;
  }


  /**
   * Returns the readed {@link Symbol}s.
   * 
   * @return The readed {@link Symbol}s.
   * @throws WordFinishedException If something with the {@link Word} is not
   *           correct.
   * @throws WordResetedException If something with the {@link Word} is not
   *           correct.
   */
  public final ArrayList < Symbol > getReadedSymbols ()
      throws WordFinishedException, WordResetedException
  {
    return this.word.getReadedSymbols ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getRowCount()
   */
  public final int getRowCount ()
  {
    return this.stateList.size ();
  }


  /**
   * Returns the {@link Stack}.
   * 
   * @return The {@link Stack}.
   * @see #stack
   */
  public final Stack getStack ()
  {
    return this.stack;
  }


  /**
   * Returns the {@link State} list.
   * 
   * @return The {@link State} list.
   * @see #stateList
   */
  public final ArrayList < State > getState ()
  {
    return this.stateList;
  }


  /**
   * Returns the {@link State} with the given index.
   * 
   * @param index The index to return.
   * @return The {@link State} list.
   * @see #stateList
   */
  public final State getState ( int index )
  {
    return this.stateList.get ( index );
  }


  /**
   * Returns the {@link TableColumnModel}.
   * 
   * @return The {@link TableColumnModel}.
   */
  public final TableColumnModel getTableColumnModel ()
  {
    DefaultTableColumnModel columnModel = new DefaultTableColumnModel ();

    TableColumn stateColumn = new TableColumn ( STATE_COLUMN );
    stateColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( "", //$NON-NLS-1$
        Style.NONE ) ) );
    stateColumn.setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    stateColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    columnModel.addColumn ( stateColumn );

    TableColumn epsilonColumn = new TableColumn ( EPSILON_COLUMN );
    epsilonColumn.setHeaderValue ( new PrettyString ( new PrettyToken (
        "\u03B5", Style.SYMBOL ) ) ); //$NON-NLS-1$
    epsilonColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    epsilonColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    columnModel.addColumn ( epsilonColumn );

    for ( int i = 0 ; i < this.alphabet.size () ; i++ )
    {
      TableColumn symbolColumn = new TableColumn ( i + SPECIAL_COLUMN_COUNT );
      symbolColumn.setHeaderValue ( this.alphabet.get ( i ) );
      symbolColumn
          .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
      symbolColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
      columnModel.addColumn ( symbolColumn );
    }

    return columnModel;
  }


  /**
   * Returns the {@link Transition} list.
   * 
   * @return The {@link Transition} list.
   * @see #transitionList
   */
  public final ArrayList < Transition > getTransition ()
  {
    return this.transitionList;
  }


  /**
   * Returns the {@link Transition} with the given index.
   * 
   * @param index pIndex The index to return.
   * @return The {@link Transition} list.
   * @see #transitionList
   */
  public final Transition getTransition ( int index )
  {
    return this.transitionList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getValueAt(int, int)
   */
  public final Object getValueAt ( int rowIndex, int columnIndex )
  {
    // State column
    if ( columnIndex == STATE_COLUMN )
    {
      for ( int i = 0 ; i < this.cachedValueList.size () ; i++ )
      {
        ObjectTriple < Integer, Integer, Object > cache = this.cachedValueList
            .get ( i );
        int cachedRowIndex = cache.getFirst ().intValue ();
        int cachedColumnIndex = cache.getSecond ().intValue ();
        if ( ( rowIndex == cachedRowIndex )
            && ( columnIndex == cachedColumnIndex ) )
        {
          State cachedState = ( State ) cache.getThird ();

          try
          {
            cachedState.setName ( this.stateList.get ( rowIndex ).getName () );
          }
          catch ( StateException exc )
          {
            exc.printStackTrace ();
            System.exit ( 1 );
          }

          return cache.getThird ();
        }
      }

      State newState = null;
      try
      {
        newState = new DefaultState ( this.stateList.get ( rowIndex )
            .getName () );
        newState.setId ( this.stateList.get ( rowIndex ).getId () );
      }
      catch ( StateException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
      this.cachedValueList.add ( new ObjectTriple < Integer, Integer, Object > (
          new Integer ( rowIndex ), new Integer ( columnIndex ), newState ) );

      return this.stateList.get ( rowIndex );
    }

    StateSet stateEndList = new DefaultStateSet ();
    State currentState = this.stateList.get ( rowIndex );

    // Epsilon column
    if ( columnIndex == EPSILON_COLUMN )
    {
      for ( Transition currentTransition : currentState.getTransitionBegin () )
      {
        if ( currentTransition.isEpsilonTransition () )
        {
          try
          {
            stateEndList.add ( currentTransition.getStateEnd () );
          }
          catch ( StateSetException exc )
          {
            exc.printStackTrace ();
            System.exit ( 1 );
          }
        }
      }
    }
    // Normal columns
    else
    {
      Symbol currentSymbol = this.alphabet.get ( columnIndex
          - SPECIAL_COLUMN_COUNT );
      for ( Transition currentTransition : currentState.getTransitionBegin () )
      {
        if ( currentTransition.contains ( currentSymbol ) )
        {
          try
          {
            stateEndList.add ( currentTransition.getStateEnd () );
          }
          catch ( StateSetException exc )
          {
            exc.printStackTrace ();
            System.exit ( 1 );
          }
        }
      }
    }

    for ( int i = 0 ; i < this.cachedValueList.size () ; i++ )
    {
      ObjectTriple < Integer, Integer, Object > cache = this.cachedValueList
          .get ( i );
      int cachedRowIndex = cache.getFirst ().intValue ();
      int cachedColumnIndex = cache.getSecond ().intValue ();
      if ( ( rowIndex == cachedRowIndex )
          && ( columnIndex == cachedColumnIndex ) )
      {
        StateSet cachedStateSet = ( StateSet ) cache.getThird ();

        if ( cachedStateSet.size () != stateEndList.size () )
        {
          this.cachedValueList.remove ( i );
          break;
        }

        for ( int j = 0 ; j < stateEndList.size () ; j++ )
        {
          try
          {
            cachedStateSet.get ( j ).setName (
                stateEndList.get ( j ).getName () );
          }
          catch ( StateException exc )
          {
            exc.printStackTrace ();
            System.exit ( 1 );
          }
        }
        return cache.getThird ();
      }
    }

    StateSet newStateEndList = new DefaultStateSet ();

    for ( State current : stateEndList )
    {
      try
      {
        State newState = new DefaultState ( current.getName () );
        newState.setId ( current.getId () );
        newStateEndList.add ( newState );
      }
      catch ( StateSetException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
      catch ( StateException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    }

    this.cachedValueList
        .add ( new ObjectTriple < Integer, Integer, Object > ( new Integer (
            rowIndex ), new Integer ( columnIndex ), newStateEndList ) );

    return newStateEndList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#isCellEditable(int, int)
   */
  public final boolean isCellEditable ( @SuppressWarnings ( "unused" )
  int rowIndex, int columnIndex )
  {
    return columnIndex > 0;
  }


  /**
   * Returns true if every {@link State} name is unique in this {@link Machine},
   * otherwise false.
   * 
   * @return True if every {@link State} name is unique in this {@link Machine},
   *         otherwise false.
   */
  public final boolean isEveryStateUnique ()
  {
    for ( int i = 0 ; i < this.stateList.size () ; i++ )
    {
      for ( int j = i + 1 ; j < this.stateList.size () ; j++ )
      {
        if ( this.stateList.get ( i ).getName ().equals (
            this.stateList.get ( j ).getName () ) )
        {
          return false;
        }
      }
    }
    return true;
  }


  /**
   * Returns true if the {@link Word} is finished, otherwise false.
   * 
   * @return True if this {@link Word} is finished, otherwise false.
   */
  public final boolean isFinished ()
  {
    return this.word.isFinished ()
        || ( ( this.activeStateSet.size () == 0 ) && ( !this.word.isReseted () ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    if ( !this.stateList.equals ( this.initialStateList ) )
    {
      return true;
    }
    if ( !this.transitionList.equals ( this.initialTransitionList ) )
    {
      return true;
    }
    if ( this.alphabet.isModified () )
    {
      return true;
    }
    if ( this.pushDownAlphabet.isModified () )
    {
      return true;
    }
    if ( this.usePushDownAlphabet != this.initialUsePushDownAlphabet )
    {
      return true;
    }
    for ( State current : this.stateList )
    {
      if ( current.isModified () )
      {
        return true;
      }
    }
    for ( Transition current : this.transitionList )
    {
      if ( current.isModified () )
      {
        return true;
      }
    }
    return false;
  }


  /**
   * Returns true if this {@link Word} is reseted, otherwise false.
   * 
   * @return True if this {@link Word} is reseted, otherwise false.
   */
  public final boolean isReseted ()
  {
    return this.word.isReseted () && this.history.isEmpty ();
  }


  /**
   * Returns true if the given {@link Symbol} can be removed from the push down
   * {@link Alphabet} of this {@link Machine}, otherwise false.
   * 
   * @param symbol The {@link Symbol} which should be checked.
   * @return True if the given {@link Symbol} can be removed from the push down
   *         {@link Alphabet} of this {@link Machine}, otherwise false.
   */
  public final boolean isSymbolRemoveableFromPushDownAlphabet ( Symbol symbol )
  {
    if ( !this.pushDownAlphabet.contains ( symbol ) )
    {
      throw new IllegalArgumentException (
          "symbol is not in the push down alphabet" ); //$NON-NLS-1$
    }
    for ( Transition current : this.transitionList )
    {
      if ( current.getPushDownWordRead ().contains ( symbol ) )
      {
        return false;
      }
      if ( current.getPushDownWordWrite ().contains ( symbol ) )
      {
        return false;
      }
    }
    return true;
  }


  /**
   * Returns the use push down {@link Alphabet}.
   * 
   * @return The use push down {@link Alphabet}.
   * @see #usePushDownAlphabet
   */
  public final boolean isUsePushDownAlphabet ()
  {
    return this.usePushDownAlphabet;
  }


  /**
   * Returns true if one of the active {@link State}s is a final {@link State},
   * otherwise false.
   * 
   * @return True if one of the active {@link State}s is a final {@link State},
   *         otherwise false.
   */
  public final boolean isWordAccepted ()
  {
    for ( State current : this.activeStateSet )
    {
      if ( current.isFinalState () )
      {
        return true;
      }
    }
    return false;
  }


  /**
   * Links the {@link Transition}s with the given {@link State}.
   * 
   * @param state The {@link State} to which the {@link Transition}s should be
   *          linked.
   */
  private final void link ( State state )
  {
    for ( Transition current : this.transitionList )
    {
      // State begin
      if ( current.getStateBegin () == null )
      {
        if ( current.getStateBeginId () == state.getId () )
        {
          current.setStateBegin ( state );
          state.addTransitionBegin ( current );
        }
      }
      else if ( current.getStateBegin ().equals ( state ) )
      {
        state.addTransitionBegin ( current );
      }
      // State end
      if ( current.getStateEnd () == null )
      {
        if ( current.getStateEndId () == state.getId () )
        {
          current.setStateEnd ( state );
          state.addTransitionEnd ( current );
        }
      }
      else if ( current.getStateEnd ().equals ( state ) )
      {
        state.addTransitionEnd ( current );
      }
    }
  }


  /**
   * Links the given {@link Transition} with the {@link State}s.
   * 
   * @param transition The {@link Transition} to link with the {@link State}s.
   */
  private final void link ( Transition transition )
  {
    for ( State currentState : this.stateList )
    {
      // State begin
      if ( transition.getStateBegin () == null )
      {
        if ( transition.getStateBeginId () == currentState.getId () )
        {
          transition.setStateBegin ( currentState );
          currentState.addTransitionBegin ( transition );
        }
      }
      else if ( transition.getStateBegin ().equals ( currentState ) )
      {
        currentState.addTransitionBegin ( transition );
      }
      // State end
      if ( transition.getStateEnd () == null )
      {
        if ( transition.getStateEndId () == currentState.getId () )
        {
          transition.setStateEnd ( currentState );
          currentState.addTransitionEnd ( transition );
        }
      }
      else if ( transition.getStateEnd ().equals ( currentState ) )
      {
        currentState.addTransitionEnd ( transition );
      }
    }
  }


  /**
   * Performs the next step and returns the list of {@link Transition}s, which
   * contains the {@link Symbol}.
   * 
   * @throws WordFinishedException If something with the {@link Word} is not
   *           correct.
   * @throws WordResetedException If something with the {@link Word} is not
   *           correct.
   * @throws WordNotAcceptedException If something with the {@link Word} is not
   *           correct.
   */
  public final void nextSymbol () throws WordFinishedException,
      WordResetedException, WordNotAcceptedException
  {
    if ( getActiveState ().size () == 0 )
    {
      clearActiveTransition ();
      clearActiveSymbol ();
      throw new WordNotAcceptedException ( this.word );
    }
    // Check for epsilon transitions
    boolean epsilonTransitionFound = false;
    stateLoop : for ( State activeState : getActiveState () )
    {
      for ( Transition current : activeState.getTransitionBegin () )
      {
        if ( current.isEpsilonTransition ()
            && ( !getActiveState ().contains ( current.getStateEnd () ) ) )
        {
          epsilonTransitionFound = true;
          break stateLoop;
        }
      }
    }
    TreeSet < State > newActiveStateSet = new TreeSet < State > ();
    TreeSet < Transition > newActiveTransitionSet = new TreeSet < Transition > ();
    ArrayList < Symbol > newActiveSymbolList = new ArrayList < Symbol > ();

    // The stack must be cloned
    Stack oldStack = new DefaultStack ();
    oldStack.push ( this.stack );

    // Epsilon transition found
    if ( epsilonTransitionFound )
    {
      for ( State activeState : getActiveState () )
      {
        for ( Transition current : activeState.getTransitionBegin () )
        {
          if ( current.isEpsilonTransition () )
          {
            newActiveStateSet.add ( activeState );
            newActiveStateSet.add ( current.getStateEnd () );
            newActiveTransitionSet.add ( current );
          }
        }
      }
    }
    // No epsilon transition found
    else
    {
      Symbol symbol;
      try
      {
        symbol = this.word.nextSymbol ();
      }
      catch ( WordFinishedException exc )
      {
        clearActiveTransition ();
        clearActiveSymbol ();
        throw exc;
      }
      for ( State activeState : getActiveState () )
      {
        transitionLoop : for ( Transition currentTransition : activeState
            .getTransitionBegin () )
        {
          // Stack
          Word readWord = currentTransition.getPushDownWordRead ();
          ArrayList < Symbol > stackSymbols = this.stack.peak ( readWord
              .size () );
          if ( readWord.size () != stackSymbols.size () )
          {
            continue transitionLoop;
          }
          for ( int i = 0 ; i < readWord.size () ; i++ )
          {
            if ( !readWord.get ( i ).equals ( stackSymbols.get ( i ) ) )
            {
              continue transitionLoop;
            }
          }

          for ( Symbol currentSymbol : currentTransition )
          {
            if ( currentSymbol.equals ( symbol ) )
            {
              newActiveStateSet.add ( currentTransition.getStateEnd () );
              newActiveTransitionSet.add ( currentTransition );
              newActiveSymbolList.add ( currentSymbol );

              // Stack
              this.stack.pop ( readWord.size () );
              this.stack.push ( currentTransition.getPushDownWordWrite () );
            }
          }
        }
      }
    }
    // Set sctive sets
    TreeSet < State > oldActiveStateSet = new TreeSet < State > ();
    oldActiveStateSet.addAll ( this.activeStateSet );

    // State
    clearActiveState ();
    for ( State current : newActiveStateSet )
    {
      current.setActive ( true );
      this.activeStateSet.add ( current );
    }

    // Transition
    clearActiveTransition ();
    for ( Transition current : newActiveTransitionSet )
    {
      current.setActive ( true );
      this.activeTransitionSet.add ( current );
    }

    // Symbol
    clearActiveSymbol ();
    for ( Symbol current : newActiveSymbolList )
    {
      current.setActive ( true );
      this.activeSymbolList.add ( current );
    }

    // No transition is found
    if ( this.activeStateSet.size () == 0 )
    {
      if ( !epsilonTransitionFound )
      {
        this.word.previousSymbol ();
      }
      clearActiveTransition ();
      clearActiveSymbol ();
      throw new WordNotAcceptedException ( this.word );
    }
    this.history.add ( new HistoryItem ( oldActiveStateSet,
        newActiveTransitionSet, newActiveSymbolList, oldStack ) );
  }


  /**
   * Removes the last step and returns the list of {@link Transition}s, which
   * contains the {@link Symbol}.
   * 
   * @throws WordFinishedException If something with the {@link Word} is not
   *           correct.
   * @throws WordResetedException If something with the {@link Word} is not
   *           correct.
   */
  public final void previousSymbol () throws WordFinishedException,
      WordResetedException
  {
    if ( this.history.size () == 0 )
    {
      clearActiveTransition ();
      clearActiveSymbol ();
      throw new WordResetedException ( this.word );
    }
    HistoryItem item = this.history.remove ( this.history.size () - 1 );
    TreeSet < Transition > newActiveTransitionSet = item.getTransitionSet ();
    TreeSet < State > newActiveStateSet = item.getStateSet ();
    ArrayList < Symbol > newActiveSymbolList = item.getSymbolSet ();

    // Check for epsilon transitions
    boolean epsilonTransitionFound = false;
    for ( Transition current : newActiveTransitionSet )
    {
      if ( current.isEpsilonTransition () )
      {
        epsilonTransitionFound = true;
        break;
      }
    }
    // No epsilon transition found
    if ( !epsilonTransitionFound )
    {
      this.word.previousSymbol ();
    }

    // State
    clearActiveState ();
    for ( State current : newActiveStateSet )
    {
      current.setActive ( true );
      this.activeStateSet.add ( current );
    }

    // Transition
    clearActiveTransition ();
    for ( Transition current : newActiveTransitionSet )
    {
      current.setActive ( true );
      this.activeTransitionSet.add ( current );
    }

    // Symbol
    clearActiveSymbol ();
    for ( Symbol current : newActiveSymbolList )
    {
      current.setActive ( true );
      this.activeSymbolList.add ( current );
    }

    // Stack
    this.stack.clear ();
    this.stack.push ( item.getStack () );
  }


  /**
   * Removes the given {@link MachineChangedListener}.
   * 
   * @param listener The {@link MachineChangedListener}.
   */
  public final void removeMachineChangedListener (
      MachineChangedListener listener )
  {
    this.listenerList.add ( MachineChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Removes the given {@link State}s from this {@link AbstractMachine}.
   * 
   * @param states The {@link State}s to remove.
   */
  public final void removeState ( Iterable < State > states )
  {
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    if ( !states.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    }
    for ( State current : states )
    {
      removeState ( current );
    }
  }


  /**
   * Removes the given {@link State} from this {@link AbstractMachine}.
   * 
   * @param state The {@link State} to remove.
   */
  public final void removeState ( State state )
  {
    this.stateList.remove ( state );
    for ( Transition current : state.getTransitionBegin () )
    {
      removeTransition ( current );
    }
    for ( Transition current : state.getTransitionEnd () )
    {
      removeTransition ( current );
    }
    fireTableDataChanged ();
    state.removeStateChangedListener ( this.stateChangedListener );
    state.removeModifyStatusChangedListener ( this.modifyStatusChangedListener );
    fireModifyStatusChanged ( false );
  }


  /**
   * Removes the given {@link State}s from this {@link AbstractMachine}.
   * 
   * @param states The {@link State}s to remove.
   */
  public final void removeState ( State ... states )
  {
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    if ( states.length == 0 )
    {
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    }
    for ( State current : states )
    {
      removeState ( current );
    }
  }


  /**
   * Removes the given {@link Symbol} from this {@link AbstractMachine}.
   * 
   * @param symbol The {@link Symbol} to remove.
   */
  public final void removeSymbol ( Symbol symbol )
  {
    this.alphabet.remove ( symbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#removeTableModelListener(TableModelListener)
   */
  public final void removeTableModelListener ( TableModelListener listener )
  {
    this.listenerList.remove ( TableModelListener.class, listener );
  }


  /**
   * Removes the given {@link Transition}s from this {@link AbstractMachine}.
   * 
   * @param transitions The {@link Transition}s to remove.
   */
  public final void removeTransition ( Iterable < Transition > transitions )
  {
    if ( transitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( !transitions.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    }
    for ( Transition current : transitions )
    {
      removeTransition ( current );
    }
  }


  /**
   * Removes the given {@link Transition} from this {@link AbstractMachine}.
   * 
   * @param transition The {@link Transition} to remove.
   */
  public final void removeTransition ( Transition transition )
  {
    this.transitionList.remove ( transition );
    for ( State current : this.stateList )
    {
      current.getTransitionBegin ().remove ( transition );
      current.getTransitionEnd ().remove ( transition );
    }
    fireTableDataChanged ();
    transition
        .removeTransitionChangedListener ( this.transitionChangedListener );
    transition
        .removeModifyStatusChangedListener ( this.modifyStatusChangedListener );
    fireModifyStatusChanged ( false );
  }


  /**
   * Removes the given {@link Transition}s from this {@link AbstractMachine}.
   * 
   * @param transitions The {@link Transition}s to remove.
   */
  public final void removeTransition ( Transition ... transitions )
  {
    if ( transitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( transitions.length == 0 )
    {
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    }
    for ( Transition current : transitions )
    {
      removeTransition ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.initialStateList.clear ();
    this.initialStateList.addAll ( this.stateList );
    this.initialTransitionList.clear ();
    this.initialTransitionList.addAll ( this.transitionList );
    this.alphabet.resetModify ();
    this.pushDownAlphabet.resetModify ();
    this.initialUsePushDownAlphabet = this.usePushDownAlphabet;
    for ( State current : this.stateList )
    {
      current.resetModify ();
    }
    for ( Transition current : this.transitionList )
    {
      current.resetModify ();
    }
  }


  /**
   * Sets the given {@link State} selected.
   * 
   * @param state The {@link State} which should be selected.
   */
  public final void setSelectedState ( State state )
  {
    // reset
    clearSelectedTransition ();

    // find the row
    int row = -1;
    for ( int i = 0 ; i < this.stateList.size () ; i++ )
    {
      if ( this.stateList.get ( i ).equals ( state ) )
      {
        row = i;
        break;
      }
    }

    State chachedState = ( State ) getValueAt ( row, STATE_COLUMN );
    chachedState.setSelected ( true );
  }


  /**
   * Sets the given {@link Transition} selected.
   * 
   * @param transition The {@link Transition} which should be selected.
   */
  public final void setSelectedTransition ( Transition transition )
  {
    // reset
    clearSelectedTransition ();

    // find the row
    int row = -1;
    for ( int i = 0 ; i < this.stateList.size () ; i++ )
    {
      if ( this.stateList.get ( i ).equals ( transition.getStateBegin () ) )
      {
        row = i;
        break;
      }
    }

    if ( transition.isEpsilonTransition () )
    {
      StateSet stateSet = ( StateSet ) getValueAt ( row, EPSILON_COLUMN );

      for ( State currentState : stateSet )
      {
        if ( currentState.equals ( transition.getStateEnd () ) )
        {
          currentState.setSelected ( true );
        }
      }
    }
    else
    {
      // find the columns
      for ( int i = 0 ; i < this.alphabet.size () ; i++ )
      {
        for ( int j = 0 ; j < transition.size () ; j++ )
        {
          if ( this.alphabet.get ( i ).equals ( transition.getSymbol ( j ) ) )
          {
            int column = i + SPECIAL_COLUMN_COUNT;
            StateSet stateSet = ( StateSet ) getValueAt ( row, column );

            for ( State currentState : stateSet )
            {
              if ( currentState.equals ( transition.getStateEnd () ) )
              {
                currentState.setSelected ( true );
              }
            }
          }
        }
      }
    }
  }


  /**
   * Sets the use push down {@link Alphabet}.
   * 
   * @param usePushDownAlphabet The use push down {@link Alphabet} to set.
   * @see #usePushDownAlphabet
   */
  public final void setUsePushDownAlphabet ( boolean usePushDownAlphabet )
  {
    this.usePushDownAlphabet = usePushDownAlphabet;
    fireModifyStatusChanged ( false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#setValueAt(Object, int, int)
   */
  public final void setValueAt ( Object value, int rowIndex, int columnIndex )
  {
    // State column
    if ( columnIndex == STATE_COLUMN )
    {
      throw new IllegalArgumentException (
          "the state column should not be editable" ); //$NON-NLS-1$
    }

    if ( value == null )
    {
      logger.debug ( "setValueAt", "value is null" ); //$NON-NLS-1$ //$NON-NLS-2$
      return;
    }

    State stateBegin = this.stateList.get ( rowIndex );
    StateSet stateSetNew = ( StateSet ) value;
    StateSet stateSetOld = ( StateSet ) getValueAt ( rowIndex, columnIndex );

    logger.debug ( "setValueAt", "state begin: " + stateBegin.getName () ); //$NON-NLS-1$ //$NON-NLS-2$
    logger.debug ( "setValueAt", "state set old: " + stateSetOld.toString () ); //$NON-NLS-1$ //$NON-NLS-2$
    logger.debug ( "setValueAt", "state set new: " + stateSetNew.toString () ); //$NON-NLS-1$//$NON-NLS-2$

    ArrayList < State > stateAdd = new ArrayList < State > ();
    ArrayList < State > stateRemove = new ArrayList < State > ();

    for ( State currentNew : stateSetNew )
    {
      boolean found = false;
      for ( State currentOld : stateSetOld )
      {
        if ( currentNew.getName ().equals ( currentOld.getName () ) )
        {
          found = true;
          break;
        }
      }
      if ( !found )
      {
        // Add the state which is already in the machine
        for ( State stateMember : this.stateList )
        {
          if ( stateMember.getName ().equals ( currentNew.getName () ) )
          {
            stateAdd.add ( stateMember );
            break;
          }
        }
      }
    }

    for ( State currentOld : stateSetOld )
    {
      boolean found = false;
      for ( State currentNew : stateSetNew )
      {
        if ( currentOld.getName ().equals ( currentNew.getName () ) )
        {
          found = true;
          break;
        }
      }
      if ( !found )
      {
        // Add the state which is already in the machine
        for ( State stateMember : this.stateList )
        {
          if ( stateMember.getName ().equals ( currentOld.getName () ) )
          {
            stateRemove.add ( stateMember );
            break;
          }
        }
      }
    }

    logger.debug ( "setValueAt", "state add: " + stateAdd ); //$NON-NLS-1$ //$NON-NLS-2$
    logger.debug ( "setValueAt", "state remove: " + stateRemove ); //$NON-NLS-1$ //$NON-NLS-2$

    ArrayList < Transition > transitionAdd = new ArrayList < Transition > ();
    ArrayList < Transition > transitionRemove = new ArrayList < Transition > ();
    ArrayList < ObjectPair < Transition, Symbol >> symbolsAdd = new ArrayList < ObjectPair < Transition, Symbol >> ();
    ArrayList < ObjectPair < Transition, Symbol >> symbolsRemove = new ArrayList < ObjectPair < Transition, Symbol >> ();

    // Add the transitions
    for ( State currentState : stateAdd )
    {
      try
      {
        // Epsilon transition
        if ( columnIndex == 1 )
        {
          logger.debug ( "setValueAt", "add transition: epsilon" ); //$NON-NLS-1$ //$NON-NLS-2$
          Transition newTransition = new DefaultTransition ( this.alphabet,
              this.pushDownAlphabet, new DefaultWord (), new DefaultWord (),
              stateBegin, currentState );
          transitionAdd.add ( newTransition );
        }
        // No epsilon transition
        else
        {
          Transition foundTransition = null;
          loopTransition : for ( Transition currentTransition : this.transitionList )
          {
            if ( ( !currentTransition.isEpsilonTransition () )
                && currentTransition.getStateBegin ().getName ().equals (
                    stateBegin.getName () )
                && currentTransition.getStateEnd ().getName ().equals (
                    currentState.getName () ) )
            {
              foundTransition = currentTransition;
              break loopTransition;
            }
          }
          if ( foundTransition == null )
          {
            logger.debug ( "setValueAt", //$NON-NLS-1$
                "add transition: no epsilon: transition not found" ); //$NON-NLS-1$
            Transition newTransition = new DefaultTransition ( this.alphabet,
                this.pushDownAlphabet, new DefaultWord (), new DefaultWord (),
                stateBegin, currentState, this.alphabet.get ( columnIndex
                    - SPECIAL_COLUMN_COUNT ) );
            transitionAdd.add ( newTransition );
          }
          else
          {
            logger.debug ( "setValueAt", //$NON-NLS-1$
                "add transition: no epsilon: transition found" ); //$NON-NLS-1$
            symbolsAdd.add ( new ObjectPair < Transition, Symbol > (
                foundTransition, this.alphabet.get ( columnIndex
                    - SPECIAL_COLUMN_COUNT ) ) );
          }
        }
      }
      catch ( TransitionSymbolNotInAlphabetException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
      catch ( TransitionSymbolOnlyOneTimeException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    }

    // Remove the transitions and symbols
    for ( State currentState : stateRemove )
    {
      for ( Transition currentTransition : this.transitionList )
      {
        if ( currentTransition.getStateBegin ().getName ().equals (
            stateBegin.getName () )
            && currentTransition.getStateEnd ().getName ().equals (
                currentState.getName () ) )
        {
          // Epsilon transition
          if ( ( columnIndex == EPSILON_COLUMN )
              && currentTransition.isEpsilonTransition () )
          {
            logger.debug ( "setValueAt", "remove transition: epsilon" ); //$NON-NLS-1$ //$NON-NLS-2$
            transitionRemove.add ( currentTransition );
          }
          // No epsilon transition
          else if ( columnIndex > EPSILON_COLUMN )
          {
            Symbol symbolColumn = this.alphabet.get ( columnIndex
                - SPECIAL_COLUMN_COUNT );
            Symbol symbolRemove = null;
            loopSymbol : for ( Symbol currentSymbol : currentTransition
                .getSymbol () )
            {
              if ( currentSymbol.equals ( symbolColumn ) )
              {
                symbolRemove = currentSymbol;
                break loopSymbol;
              }
            }

            if ( symbolRemove != null )
            {
              // The last symbol is removed
              if ( currentTransition.size () == 1 )
              {
                logger.debug ( "setValueAt", //$NON-NLS-1$
                    "remove transition: no epsilon: remove transition" ); //$NON-NLS-1$
                transitionRemove.add ( currentTransition );
              }
              // Only one symbol is removed
              else
              {
                logger.debug ( "setValueAt", //$NON-NLS-1$
                    "remove transition: no epsilon: remove symbol" ); //$NON-NLS-1$
                symbolsRemove.add ( new ObjectPair < Transition, Symbol > (
                    currentTransition, symbolRemove ) );
              }
            }
            else
            {
              logger.debug ( "setValueAt", //$NON-NLS-1$
                  "remove transition: no epsilon: nothing found" ); //$NON-NLS-1$
            }
          }
          else
          {
            logger.debug ( "setValueAt", //$NON-NLS-1$
                "remove transition: epsilon column: no epsilon transition" ); //$NON-NLS-1$
          }
        }
      }
    }

    if ( ( transitionAdd.size () > 0 ) || ( transitionRemove.size () > 0 )
        || ( symbolsAdd.size () > 0 ) || ( symbolsRemove.size () > 0 ) )
    {
      fireMachineChangedStartEditing ();

      for ( Transition current : transitionAdd )
      {
        addTransition ( current );
        fireMachineChangedTransitionAdded ( current );
      }

      for ( Transition current : transitionRemove )
      {
        removeTransition ( current );
        fireMachineChangedTransitionRemoved ( current );
      }

      for ( ObjectPair < Transition, Symbol > current : symbolsAdd )
      {
        try
        {
          current.getFirst ().add ( current.getSecond () );
        }
        catch ( TransitionSymbolNotInAlphabetException exc )
        {
          exc.printStackTrace ();
          System.exit ( 1 );
        }
        catch ( TransitionSymbolOnlyOneTimeException exc )
        {
          exc.printStackTrace ();
          System.exit ( 1 );
        }
        ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
        symbolList.add ( current.getSecond () );
        fireMachineChangedSymbolAdded ( current.getFirst (), symbolList );
      }

      for ( ObjectPair < Transition, Symbol > current : symbolsRemove )
      {
        current.getFirst ().remove ( current.getSecond () );
        ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
        symbolList.add ( current.getSecond () );
        fireMachineChangedSymbolRemoved ( current.getFirst (), symbolList );
      }

      fireMachineChangedStopEditing ();
    }
  }


  /**
   * Starts the {@link AbstractMachine} after a validation with the given
   * {@link Word}.
   * 
   * @param startWord The {@link Word} to start with.
   */
  public final void start ( Word startWord )
  {
    // Word
    if ( startWord == null )
    {
      throw new NullPointerException ( "word is null" ); //$NON-NLS-1$
    }
    this.word = startWord;
    this.word.start ();

    this.stack = new DefaultStack ();

    clearHistory ();
    // Set active states
    this.activeStateSet.clear ();
    for ( State current : this.stateList )
    {
      if ( current.isStartState () )
      {
        this.activeStateSet.add ( current );
      }
    }
  }


  /**
   * Validates that everything in the {@link AbstractMachine} is correct.
   * 
   * @throws MachineValidationException If the validation fails.
   */
  public final void validate () throws MachineValidationException
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();

    if ( this.validationElementList.contains ( ValidationElement.ALL_SYMBOLS ) )
    {
      machineExceptionList.addAll ( checkAllSymbols () );
    }

    if ( this.validationElementList
        .contains ( ValidationElement.EPSILON_TRANSITION ) )
    {
      machineExceptionList.addAll ( checkEpsilonTransition () );
    }

    if ( this.validationElementList
        .contains ( ValidationElement.TRANSITION_STACK_OPERATION ) )
    {
      machineExceptionList.addAll ( checkTransitionStackOperation () );
    }

    if ( this.validationElementList.contains ( ValidationElement.FINAL_STATE ) )
    {
      machineExceptionList.addAll ( checkFinalState () );
    }

    if ( this.validationElementList
        .contains ( ValidationElement.MORE_THAN_ONE_START_STATE ) )
    {
      machineExceptionList.addAll ( checkMoreThanOneStartState () );
    }

    if ( this.validationElementList
        .contains ( ValidationElement.NO_START_STATE ) )
    {
      machineExceptionList.addAll ( checkNoStartState () );
    }

    if ( this.validationElementList.contains ( ValidationElement.STATE_NAME ) )
    {
      machineExceptionList.addAll ( checkStateName () );
    }

    if ( this.validationElementList
        .contains ( ValidationElement.STATE_NOT_REACHABLE ) )
    {
      machineExceptionList.addAll ( checkStateNotReachable () );
    }

    if ( this.validationElementList
        .contains ( ValidationElement.SYMBOL_ONLY_ONE_TIME ) )
    {
      machineExceptionList.addAll ( checkSymbolOnlyOneTime () );
    }

    // Throw the exception if a warning or an error has occurred.
    if ( machineExceptionList.size () > 0 )
    {
      throw new MachineValidationException ( machineExceptionList );
    }
  }
}
