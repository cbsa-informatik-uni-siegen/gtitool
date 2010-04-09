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
import de.unisiegen.gtitool.core.entities.Transition.TransitionType;
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
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.i18n.Messages;
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
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.core.util.ObjectTriple;
import de.unisiegen.gtitool.core.util.Util;
import de.unisiegen.gtitool.logger.Logger;


/**
 * The abstract class for all state machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class AbstractStateMachine implements StateMachine
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7657607466102471211L;


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( AbstractStateMachine.class );


  /**
   * Returns the {@link StateMachine} with the given {@link StateMachine} type.
   * 
   * @param machineType The {@link StateMachine} type.
   * @param alphabet The {@link Alphabet}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this
   *          {@link Transition}.
   * @param usePushDownAlphabet The use push down {@link Alphabet}.
   * @return The {@link StateMachine} with the given {@link StateMachine} type.
   * @throws StoreException If the {@link StateMachine} type is unknown.
   */
  public static final StateMachine createMachine ( String machineType,
      Alphabet alphabet, Alphabet pushDownAlphabet, boolean usePushDownAlphabet )
      throws StoreException
  {
    if ( machineType.equals ( ( "DFA" ) ) ) //$NON-NLS-1$
      return new DefaultDFA ( alphabet, pushDownAlphabet, usePushDownAlphabet );
    if ( machineType.equals ( ( "NFA" ) ) ) //$NON-NLS-1$
      return new DefaultNFA ( alphabet, pushDownAlphabet, usePushDownAlphabet );
    if ( machineType.equals ( ( "ENFA" ) ) ) //$NON-NLS-1$
      return new DefaultENFA ( alphabet, pushDownAlphabet, usePushDownAlphabet );
    if ( machineType.equals ( ( "PDA" ) ) ) //$NON-NLS-1$
      return new DefaultPDA ( alphabet, pushDownAlphabet, usePushDownAlphabet );
    throw new StoreException ( Messages
        .getString ( "StoreException.WrongMachineType" ) ); //$NON-NLS-1$
  }


  /**
   * The {@link Alphabet} of this {@link AbstractStateMachine}.
   */
  private final Alphabet alphabet;


  /**
   * The push down {@link Alphabet} of this {@link AbstractStateMachine}.
   */
  private final Alphabet pushDownAlphabet;


  /**
   * The use push down {@link Alphabet} of this {@link AbstractStateMachine}.
   */
  private boolean usePushDownAlphabet;


  /**
   * The initial use push down {@link Alphabet} of this
   * {@link AbstractStateMachine}.
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
   * The history of this {@link AbstractStateMachine}.
   */
  private ArrayList < StateMachineHistoryItem > history;


  /**
   * The list of the {@link State}s.
   */
  private final ArrayList < State > stateList;


  /**
   * The initial list of the {@link State}s.
   */
  private final ArrayList < State > initialStateList;


  /**
   * The list of the {@link Transition}.
   */
  private final ArrayList < Transition > transitionList;


  /**
   * The list of the {@link Transition}.
   */
  private final ArrayList < Transition > initialTransitionList;


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
  private final ArrayList < ValidationElement > validationElementList;


  /**
   * List of listeners
   */
  private final EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link AlphabetChangedListener}.
   */
  private final AlphabetChangedListener alphabetChangedListener;


  /**
   * The {@link TransitionChangedListener}.
   */
  private final TransitionChangedListener transitionChangedListener;


  /**
   * The {@link StateChangedListener}.
   */
  private final StateChangedListener stateChangedListener;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private final ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * The list of cached values which is needed because of the {@link Transition}
   * highlighting.
   */
  private final ArrayList < ObjectTriple < Integer, Integer, Object >> cachedValueList;


  /**
   * Gets the current history
   * 
   * @return the history
   */
  public ArrayList < StateMachineHistoryItem > getHistory ()
  {
    return this.history;
  }


  /**
   * Sets the history
   * 
   * @param history
   */
  public void setHistory ( final ArrayList < StateMachineHistoryItem > history )
  {
    this.history = history;
  }


  /**
   * Allocates a new {@link AbstractStateMachine}.
   * 
   * @param alphabet The {@link Alphabet} of this {@link AbstractStateMachine}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this
   *          {@link AbstractStateMachine}.
   * @param usePushDownAlphabet The use push down {@link Alphabet}.
   * @param validationElements The validation elements which indicates which
   *          validation elements should be checked during a validation.
   */
  public AbstractStateMachine ( Alphabet alphabet, Alphabet pushDownAlphabet,
      boolean usePushDownAlphabet, ValidationElement ... validationElements )
  {
    // alphabet
    if ( alphabet == null )
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    this.alphabet = alphabet;

    // push down alphabet
    if ( pushDownAlphabet == null )
      throw new NullPointerException ( "push down alphabet is null" ); //$NON-NLS-1$
    this.pushDownAlphabet = pushDownAlphabet;

    // use push down alphabet
    this.usePushDownAlphabet = usePushDownAlphabet;

    // validation elements
    if ( validationElements == null )
      throw new NullPointerException ( "validation elements is null" ); //$NON-NLS-1$
    this.validationElementList = new ArrayList < ValidationElement > ();
    for ( ValidationElement current : validationElements )
      this.validationElementList.add ( current );

    // state list
    this.stateList = new ArrayList < State > ();
    this.initialStateList = new ArrayList < State > ();

    // transition list
    this.transitionList = new ArrayList < Transition > ();
    this.initialTransitionList = new ArrayList < Transition > ();

    // stack
    this.stack = new DefaultStack ();

    // history
    this.history = new ArrayList < StateMachineHistoryItem > ();

    // cached value list
    this.cachedValueList = new ArrayList < ObjectTriple < Integer, Integer, Object >> ();

    // alphabet changed listener
    this.alphabetChangedListener = new AlphabetChangedListener ()
    {

      public void alphabetChanged (
          @SuppressWarnings ( "unused" ) Alphabet newAlphabet )
      {
        fireTableStructureChanged ();
      }
    };
    this.alphabet.addAlphabetChangedListener ( this.alphabetChangedListener );

    // transition changed listener
    this.transitionChangedListener = new TransitionChangedListener ()
    {

      public void transitionChanged (
          @SuppressWarnings ( "unused" ) Transition newTransition )
      {
        fireTableDataChanged ();
      }
    };

    // state changed listener
    this.stateChangedListener = new StateChangedListener ()
    {

      public void stateChanged ( boolean nameChanged,
          @SuppressWarnings ( "unused" ) boolean startChanged,
          @SuppressWarnings ( "unused" ) boolean loopTransitionChanged )
      {
        if ( nameChanged )
          fireTableDataChanged ();
      }
    };

    // modify status changed listener
    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      public void modifyStatusChanged ( boolean modified )
      {
        fireModifyStatusChanged ( modified );
      }
    };

    this.alphabet
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    this.pushDownAlphabet
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );

    // reset modify
    resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#addMachineChangedListener(MachineChangedListener)
   */
  public final void addMachineChangedListener ( MachineChangedListener listener )
  {
    this.listenerList.add ( MachineChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#addState(Iterable)
   */
  public final void addState ( Iterable < State > states )
  {
    if ( states == null )
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    if ( !states.iterator ().hasNext () )
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    for ( State current : states )
      addState ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#addState(State)
   */
  public final void addState ( State state )
  {
    if ( state == null )
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    if ( ( state.isIdDefined () ) && ( this.stateList.contains ( state ) ) )
      throw new IllegalArgumentException ( "state is already added" ); //$NON-NLS-1$

    // Set the state alphabet if it is not set already.
    if ( state.getAlphabet () == null )
      state.setAlphabet ( this.alphabet );
    if ( !this.alphabet.equals ( state.getAlphabet () ) )
      throw new IllegalArgumentException ( "not the same alphabet" ); //$NON-NLS-1$

    // Set the state push down alphabet if it is not set already.
    if ( state.getPushDownAlphabet () == null )
      state.setPushDownAlphabet ( this.pushDownAlphabet );
    if ( !this.pushDownAlphabet.equals ( state.getPushDownAlphabet () ) )
    {
      System.out.println ( this.pushDownAlphabet );
      System.out.println ( state.getPushDownAlphabet () );

      throw new IllegalArgumentException ( "not the same push down alphabet" ); //$NON-NLS-1$
    }

    if ( state.getId () == State.ID_NOT_DEFINED )
      state.setId ( ++this.currentStateId );
    else if ( state.getId () > this.currentStateId )
      this.currentStateId = state.getId ();

    this.stateList.add ( state );
    link ( state );
    fireTableDataChanged ();
    state.addStateChangedListener ( this.stateChangedListener );
    state.addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    fireModifyStatusChanged ( true );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#addState(State[])
   */
  public final void addState ( State ... states )
  {
    if ( states == null )
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    if ( states.length == 0 )
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    for ( State current : states )
      addState ( current );
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
   * {@inheritDoc}
   * 
   * @see StateMachine#addTransition(Iterable)
   */
  public final void addTransition ( Iterable < Transition > transitions )
  {
    if ( transitions == null )
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    if ( !transitions.iterator ().hasNext () )
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    for ( Transition current : transitions )
      addTransition ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#addTransition(Transition)
   */
  public final void addTransition ( Transition transition )
  {
    if ( transition == null )
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    if ( ( transition.isIdDefined () )
        && ( this.transitionList.contains ( transition ) ) )
      throw new IllegalArgumentException ( "transition is already added" ); //$NON-NLS-1$

    // Set the transition alphabet if it is not set already.
    if ( transition.getAlphabet () == null )
      transition.setAlphabet ( this.alphabet );
    if ( !this.alphabet.equals ( transition.getAlphabet () ) )
      throw new IllegalArgumentException ( "not the same alphabet" ); //$NON-NLS-1$

    // Set the transition push down alphabet if it is not set already.
    if ( transition.getPushDownAlphabet () == null )
      transition.setPushDownAlphabet ( this.pushDownAlphabet );
    if ( !this.pushDownAlphabet.equals ( transition.getPushDownAlphabet () ) )
      throw new IllegalArgumentException ( "not the same push down alphabet" ); //$NON-NLS-1$

    if ( transition.getId () == Transition.ID_NOT_DEFINED )
      transition.setId ( ++this.currentTransitionId );
    else if ( transition.getId () > this.currentTransitionId )
      this.currentTransitionId = transition.getId ();
    this.transitionList.add ( transition );
    link ( transition );
    fireTableDataChanged ();
    transition.addTransitionChangedListener ( this.transitionChangedListener );
    transition
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    fireModifyStatusChanged ( true );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#addTransition(Transition[])
   */
  public final void addTransition ( Transition ... transitions )
  {
    if ( transitions == null )
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    if ( transitions.length == 0 )
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    for ( Transition current : transitions )
      addTransition ( current );
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
        currentSymbolSet.addAll ( currentTransition.getSymbol () );
      TreeSet < Symbol > notUsedSymbolSet = new TreeSet < Symbol > ();
      for ( Symbol currentSymbol : this.alphabet )
        // the symbols must be cloned
        notUsedSymbolSet.add ( new DefaultSymbol ( currentSymbol.getName () ) );
      for ( Symbol currentSymbol : currentSymbolSet )
        notUsedSymbolSet.remove ( currentSymbol );
      if ( notUsedSymbolSet.size () > 0 )
        machineExceptionList.add ( new MachineAllSymbolsException (
            currentState, notUsedSymbolSet ) );
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
      if ( currentTransition.getTransitionType ().equals (
          TransitionType.EPSILON_ONLY )
          || currentTransition.getTransitionType ().equals (
              TransitionType.EPSILON_SYMBOL ) )
        machineExceptionList.add ( new MachineEpsilonTransitionException (
            currentTransition ) );
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
      if ( currentState.isFinalState () )
      {
        found = true;
        break loop;
      }
    if ( !found )
      machineExceptionList.add ( new MachineStateFinalException () );
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
      if ( current.isStartState () )
        startStateList.add ( current );
    if ( startStateList.size () >= 2 )
      machineExceptionList.add ( new MachineStateStartException (
          startStateList ) );
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
      if ( current.isStartState () )
        startStateList.add ( current );
    if ( startStateList.size () == 0 )
      machineExceptionList.add ( new MachineStateStartException (
          startStateList ) );
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
        continue firstLoop;
      ArrayList < State > duplicatedListOne = new ArrayList < State > ();
      for ( int j = i + 1 ; j < this.getState ().size () ; j++ )
        if ( this.getState ().get ( i ).getName ().equals (
            this.getState ().get ( j ).getName () ) )
          duplicatedListOne.add ( this.getState ().get ( j ) );
      if ( duplicatedListOne.size () > 0 )
      {
        duplicatedListOne.add ( this.getState ().get ( i ) );
        for ( State current : duplicatedListOne )
          duplicatedListAll.add ( current );
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

    for ( State current : getNotReachableStates () )
      machineExceptionList.add ( new MachineStateNotReachableException (
          current ) );

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
      for ( Symbol currentSymbol : this.alphabet )
      {
        ArrayList < Transition > transitions = new ArrayList < Transition > ();
        ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
        for ( Transition currentTransition : currentState.getTransitionBegin () )
          if ( currentTransition.contains ( currentSymbol ) )
          {
            transitions.add ( currentTransition );
            for ( Symbol addSymbol : currentTransition.getSymbol () )
              if ( addSymbol.equals ( currentSymbol ) )
                symbols.add ( addSymbol );
          }
        if ( transitions.size () > 1 )
          machineExceptionList.add ( new MachineSymbolOnlyOneTimeException (
              currentState, symbols, transitions ) );
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
      if ( ( currentTransition.getPushDownWordRead ().size () > 0 )
          || ( currentTransition.getPushDownWordWrite ().size () > 0 ) )
        machineExceptionList
            .add ( new MachineTransitionStackOperationsException (
                currentTransition ) );
    return machineExceptionList;
  }


  /**
   * Clears the active {@link State}s.
   */
  private final void clearActiveState ()
  {
    for ( State current : this.stateList )
      current.setActive ( false );
  }


  /**
   * Clears the active {@link Symbol}s.
   */
  private final void clearActiveSymbol ()
  {
    for ( Transition currentTransition : this.transitionList )
      for ( Symbol currentSymbol : currentTransition )
        currentSymbol.setActive ( false );
  }


  /**
   * Clears the active {@link Transition}s.
   */
  private final void clearActiveTransition ()
  {
    for ( Transition current : this.transitionList )
      current.setActive ( false );
  }


  /**
   * Clears the history of this {@link AbstractStateMachine}.
   */
  private final void clearHistory ()
  {
    this.history.clear ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#clearSelectedTransition()
   */
  public final void clearSelectedTransition ()
  {
    // transition
    for ( Transition current : this.transitionList )
      current.setSelected ( false );

    // find the columns
    for ( int i = 0 ; i < getColumnCount () ; i++ )
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
            currentState.setSelected ( false );
        }
        else
          throw new RuntimeException ( "not supported table value class: " //$NON-NLS-1$
              + value.getClass ().getSimpleName () );
      }
  }


  /**
   * Returns the cloned current {@link Stack}.
   * 
   * @return The cloned current {@link Stack}.
   */
  private final Stack cloneCurrentStack ()
  {
    ArrayList < Symbol > oldStackSymbolList = this.stack.peak ( this.stack
        .size () );
    Stack result = new DefaultStack ();
    for ( int i = oldStackSymbolList.size () - 1 ; i >= 0 ; i-- )
      result.push ( oldStackSymbolList.get ( i ) );
    return result;
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
      current.startEditing ();
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
      current.stopEditing ();
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
      current.symbolAdded ( transition, addedSymbols );
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
      current.symbolRemoved ( transition, removedSymbols );
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
      current.transitionAdded ( newTransition );
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
      current.transitionRemoved ( transition );
  }


  /**
   * Let the listeners know that the modify status has changed.
   * 
   * @param forceModify True if the modify is forced, otherwise false.
   */
  protected final void fireModifyStatusChanged ( boolean forceModify )
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    if ( forceModify )
      for ( ModifyStatusChangedListener current : listeners )
        current.modifyStatusChanged ( true );
    else
    {
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener current : listeners )
        current.modifyStatusChanged ( newModifyStatus );
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
      current.tableChanged ( event );
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
  protected final void fireTableDataChanged ()
  {
    fireTableChanged ( new TableModelEvent ( this ) );
  }


  /**
   * Notifies all listeners that the table's structure has changed.
   * 
   * @see TableModelEvent
   * @see EventListenerList
   */
  protected final void fireTableStructureChanged ()
  {
    fireTableChanged ( new TableModelEvent ( this, TableModelEvent.HEADER_ROW ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getAcceptedWords(int)
   */
  public final ArrayList < Word > getAcceptedWords ( int maxLength )
  {
    ArrayList < Word > result = new ArrayList < Word > ();
    ArrayList < Word > words = new ArrayList < Word > ();
    ArrayList < Word > tmpWords = new ArrayList < Word > ();
    int length = 0;
    while ( length <= maxLength )
    {
      if ( length == 0 )
      {
        Word newWord = new DefaultWord ();
        words.add ( newWord );
        if ( isWordAccepted ( newWord ) )
          result.add ( newWord );
      }
      else
      {
        tmpWords.clear ();
        for ( Word currentWord : words )
          for ( Symbol currentSymbol : this.alphabet )
          {
            Word newWord = new DefaultWord ( currentWord );
            newWord.add ( currentSymbol );
            tmpWords.add ( newWord );
            if ( isWordAccepted ( newWord ) )
              result.add ( newWord );
          }

        words.clear ();
        words.addAll ( tmpWords );
      }
      length++ ;
    }

    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getAlphabet()
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
  public final Class < ? > getColumnClass (
      @SuppressWarnings ( "unused" ) int columnIndex )
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
      return ""; //$NON-NLS-1$
    if ( columnIndex == EPSILON_COLUMN )
      return "\u03B5"; //$NON-NLS-1$
    return this.alphabet.get ( columnIndex - SPECIAL_COLUMN_COUNT ).toString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getMachineType()
   */
  public abstract MachineType getMachineType ();


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getNextStateName()
   */
  public final String getNextStateName ()
  {
    int result = -1;

    for ( State current : this.stateList )
      if ( current.getId () > result )
        result = current.getId ();

    result++ ;

    return "z" + result; //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getNotReachableStates()
   */
  public final ArrayList < State > getNotReachableStates ()
  {
    ArrayList < State > reachable = getReachableStates ();
    ArrayList < State > notReachable = new ArrayList < State > ();
    notReachable.addAll ( this.stateList );

    for ( State current : reachable )
      notReachable.remove ( current );
    return notReachable;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getNotRemoveableSymbolsFromAlphabet()
   */
  public final TreeSet < Symbol > getNotRemoveableSymbolsFromAlphabet ()
  {
    TreeSet < Symbol > notRemoveableSymbols = new TreeSet < Symbol > ();
    for ( Transition currentTransition : this.transitionList )
      for ( Symbol currentSymbol : currentTransition )
        if ( !currentSymbol.isEpsilon () )
          notRemoveableSymbols.add ( currentSymbol );
    return notRemoveableSymbols;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getNotRemoveableSymbolsFromPushDownAlphabet()
   */
  public final TreeSet < Symbol > getNotRemoveableSymbolsFromPushDownAlphabet ()
  {
    TreeSet < Symbol > notRemoveableSymbols = new TreeSet < Symbol > ();
    for ( Transition current : this.transitionList )
    {
      for ( Symbol currentSymbol : current.getPushDownWordRead () )
        notRemoveableSymbols.add ( currentSymbol );
      for ( Symbol currentSymbol : current.getPushDownWordWrite () )
        notRemoveableSymbols.add ( currentSymbol );
    }
    return notRemoveableSymbols;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getPossibleTransitions()
   */
  public final ArrayList < Transition > getPossibleTransitions ()
  {
    ArrayList < Transition > result = new ArrayList < Transition > ();

    ArrayList < State > activeStateList = new ArrayList < State > ();
    for ( State current : this.stateList )
      if ( current.isActive () )
        activeStateList.add ( current );

    for ( State currentState : activeStateList )
      transitionLoop : for ( Transition currentTransition : currentState
          .getTransitionBegin () )
        // epsilon
        if ( currentTransition.getTransitionType ().equals (
            TransitionType.EPSILON_ONLY )
            || currentTransition.getTransitionType ().equals (
                TransitionType.EPSILON_SYMBOL ) )
        {
          Word readWord = currentTransition.getPushDownWordRead ();
          ArrayList < Symbol > stackSymbols = this.stack.peak ( readWord
              .size () );

          // the read word must match
          if ( readWord.size () != stackSymbols.size () )
            continue transitionLoop;
          for ( int i = 0 ; i < readWord.size () ; i++ )
            if ( !readWord.get ( i ).equals ( stackSymbols.get ( i ) ) )
              continue transitionLoop;
          result.add ( currentTransition );
        }
        // no epsilon
        else
        {
          Symbol symbol;
          try
          {
            symbol = this.word.nextSymbol ();
            this.word.previousSymbol ();
          }
          catch ( WordFinishedException exc )
          {
            continue transitionLoop;
          }
          catch ( WordResetedException exc )
          {
            continue transitionLoop;
          }

          Word readWord = currentTransition.getPushDownWordRead ();
          ArrayList < Symbol > stackSymbols = this.stack.peak ( readWord
              .size () );

          // the read word must match
          if ( readWord.size () != stackSymbols.size () )
            continue transitionLoop;
          for ( int i = 0 ; i < readWord.size () ; i++ )
            if ( !readWord.get ( i ).equals ( stackSymbols.get ( i ) ) )
              continue transitionLoop;

          if ( currentTransition.contains ( symbol ) )
            result.add ( currentTransition );
        }

    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getPushDownAlphabet()
   */
  public final Alphabet getPushDownAlphabet ()
  {
    return this.pushDownAlphabet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getReachableStates()
   */
  public final ArrayList < State > getReachableStates ()
  {
    ArrayList < State > reachable = new ArrayList < State > ();
    ArrayList < State > todoList = new ArrayList < State > ();

    for ( State current : this.stateList )
      if ( current.isStartState () )
        todoList.add ( current );

    while ( todoList.size () > 0 )
    {
      State currentState = todoList.remove ( 0 );
      reachable.add ( currentState );

      for ( Transition currentTransition : currentState.getTransitionBegin () )
      {
        State endState = currentTransition.getStateEnd ();
        if ( !todoList.contains ( endState ) && !reachable.contains ( endState ) )
          todoList.add ( endState );
      }
    }

    return reachable;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getReadedSymbols()
   */
  public final ArrayList < Symbol > getReadedSymbols ()
      throws WordFinishedException, WordResetedException
  {
    return this.word.getReadSymbols ();
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
   * {@inheritDoc}
   * 
   * @see StateMachine#getStack()
   */
  public final Stack getStack ()
  {
    return this.stack;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getState()
   */
  public final ArrayList < State > getState ()
  {
    return this.stateList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getState(int)
   */
  public final State getState ( int index )
  {
    return this.stateList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getTableColumnModel()
   */
  public TableColumnModel getTableColumnModel ()
  {
    DefaultTableColumnModel columnModel = new DefaultTableColumnModel ();

    TableColumn stateColumn = new TableColumn ( STATE_COLUMN );
    stateColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( "", //$NON-NLS-1$
        Style.NONE ) ) );
    stateColumn.setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    stateColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    columnModel.addColumn ( stateColumn );

    if ( epsilonColumnNeeded () )
    {
      TableColumn epsilonColumn = new TableColumn ( EPSILON_COLUMN );
      epsilonColumn.setHeaderValue ( new PrettyString ( new PrettyToken (
          "\u03B5", Style.SYMBOL ) ) ); //$NON-NLS-1$
      epsilonColumn
          .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
      epsilonColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
      columnModel.addColumn ( epsilonColumn );
    }

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
   * Tells if an epsilon column is needed
   * 
   * @return the bool
   */
  public boolean epsilonColumnNeeded ()
  {
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getTransition()
   */
  public final ArrayList < Transition > getTransition ()
  {
    return this.transitionList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getTransition(int)
   */
  public final Transition getTransition ( int index )
  {
    return this.transitionList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StateMachine#hasTransition(de.unisiegen.gtitool.core.entities.Transition)
   */
  public boolean hasTransition ( final Transition transition )
  {
    for ( Transition nTransition : getTransition () )
      if ( nTransition.compareByStates ( transition ) )
        return true;
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( int rowIndex, int columnIndex )
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

    // epsilon column
    if ( columnIndex == EPSILON_COLUMN )
    {
      for ( Transition currentTransition : currentState.getTransitionBegin () )
        if ( currentTransition.getTransitionType ().equals (
            TransitionType.EPSILON_ONLY )
            || currentTransition.getTransitionType ().equals (
                TransitionType.EPSILON_SYMBOL ) )
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
    // normal columns
    else
    {
      Symbol currentSymbol = this.alphabet.get ( columnIndex
          - SPECIAL_COLUMN_COUNT );
      for ( Transition currentTransition : currentState.getTransitionBegin () )
        if ( currentTransition.contains ( currentSymbol ) )
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
        return cache.getThird ();
      }
    }

    StateSet newStateEndList = new DefaultStateSet ();

    for ( State current : stateEndList )
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

    this.cachedValueList
        .add ( new ObjectTriple < Integer, Integer, Object > ( new Integer (
            rowIndex ), new Integer ( columnIndex ), newStateEndList ) );

    return newStateEndList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#getWord()
   */
  public final Word getWord ()
  {
    return this.word;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#isCellEditable(int, int)
   */
  public final boolean isCellEditable (
      @SuppressWarnings ( "unused" ) int rowIndex, int columnIndex )
  {
    return columnIndex > 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#isEveryStateUnique()
   */
  public final boolean isEveryStateUnique ()
  {
    for ( int i = 0 ; i < this.stateList.size () ; i++ )
      for ( int j = i + 1 ; j < this.stateList.size () ; j++ )
        if ( this.stateList.get ( i ).getName ().equals (
            this.stateList.get ( j ).getName () ) )
          return false;
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    if ( this.stateList.size () != this.initialStateList.size () )
      return true;
    if ( this.transitionList.size () != this.initialTransitionList.size () )
      return true;

    boolean found;
    for ( int i = 0 ; i < this.stateList.size () ; i++ )
    {
      found = false;
      for ( int j = 0 ; j < this.initialStateList.size () ; j++ )
        if ( this.stateList.get ( i ) == this.initialStateList.get ( j ) )
        {
          found = true;
          break;
        }
      if ( !found )
        return true;
    }

    for ( int i = 0 ; i < this.transitionList.size () ; i++ )
    {
      found = false;
      for ( int j = 0 ; j < this.initialTransitionList.size () ; j++ )
        if ( this.transitionList.get ( i ) == this.initialTransitionList
            .get ( j ) )
        {
          found = true;
          break;
        }
      if ( !found )
        return true;
    }

    if ( this.alphabet.isModified () )
      return true;
    if ( this.pushDownAlphabet.isModified () )
      return true;
    if ( this.usePushDownAlphabet != this.initialUsePushDownAlphabet )
      return true;
    for ( State current : this.stateList )
      if ( current.isModified () )
        return true;
    for ( Transition current : this.transitionList )
      if ( current.isModified () )
        return true;
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#isNextSymbolAvailable()
   */
  public final boolean isNextSymbolAvailable ()
  {
    if ( this.word == null )
      return false;

    // special case for the pda navigation
    if ( getMachineType ().equals ( MachineType.PDA )
        && ( getPossibleTransitions ().size () > 0 ) )
      return true;

    // the exception is thrown if the word is finished
    try
    {
      nextSymbol ();
    }
    catch ( Exception exc )
    {
      previousSymbol ();
      return false;
    }

    // save the new active states
    ArrayList < State > newActiveStates = new ArrayList < State > ();
    for ( State current : this.stateList )
      if ( current.isActive () )
        newActiveStates.add ( current );

    if ( newActiveStates.size () == 0 )
    {
      previousSymbol ();
      return false;
    }

    previousSymbol ();
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#isPreviousSymbolAvailable()
   */
  public final boolean isPreviousSymbolAvailable ()
  {
    if ( this.word == null )
      return false;

    return !this.history.isEmpty ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#isUsePushDownAlphabet()
   */
  public final boolean isUsePushDownAlphabet ()
  {
    return this.usePushDownAlphabet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#isUserInputNeeded()
   */
  public final boolean isUserInputNeeded ()
  {
    if ( getMachineType ().equals ( MachineType.PDA ) )
      return getPossibleTransitions ().size () >= 2;
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#isNextStepAmbigious()
   */
  public boolean isNextStepAmbigious ()
  {
    return isUserInputNeeded ();
  }


  /**
   *{@inheritDoc}
   * 
   * @see StateMachine#isWordAccepted()
   */
  public final boolean isWordAccepted ()
  {
    if ( this.stack.size () > 0 )
      return false;

    for ( State current : this.stateList )
      if ( current.isActive () && current.isFinalState () )
        return true;

    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#isWordAccepted(Word)
   */
  public final boolean isWordAccepted ( Word testWord )
  {
    testWord.start ();

    ArrayList < State > activeStates = new ArrayList < State > ( this.stateList
        .size () );
    ArrayList < State > tmpActiveStates = new ArrayList < State > (
        this.stateList.size () );

    // start states
    for ( State current : this.stateList )
      if ( current.isStartState () )
        activeStates.addAll ( Util.getClosure ( current ) );

    try
    {
      while ( !testWord.isFinished () )
      {
        tmpActiveStates.clear ();
        Symbol symbol = testWord.nextSymbol ();
        for ( State currentState : activeStates )
          for ( Transition currentTransition : currentState
              .getTransitionBegin () )
            if ( currentTransition.contains ( symbol ) )
              if ( !tmpActiveStates.contains ( currentState ) )
              {
                tmpActiveStates.add ( currentTransition.getStateEnd () );
                break;
              }

        activeStates.clear ();
        activeStates.addAll ( Util.getClosure ( tmpActiveStates ) );
      }
    }
    catch ( WordFinishedException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return false;
    }

    for ( State current : activeStates )
      if ( current.isFinalState () )
        return true;

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
        state.addTransitionBegin ( current );
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
        state.addTransitionEnd ( current );
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
        currentState.addTransitionBegin ( transition );
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
        currentState.addTransitionEnd ( transition );
    }
  }


  /**
   * Sets the current word
   * 
   * @param newWord
   */
  public void setWord ( final Word newWord )
  {
    this.word = newWord;
  }


  /**
   * Creates the current history item
   * 
   * @return the item
   */
  public StateMachineHistoryItem makeCurrentHistoryItem ()
  {
    ArrayList < State > activeStateList = new ArrayList < State > ();
    for ( State current : this.stateList )
      if ( current.isActive () )
        activeStateList.add ( current );

    if ( activeStateList.size () == 0 )
      throw new RuntimeException ( "active state set is empty" ); //$NON-NLS-1$

    TreeSet < State > oldActiveStateSet = new TreeSet < State > ();
    TreeSet < Transition > oldActiveTransitionSet = new TreeSet < Transition > ();
    ArrayList < Symbol > oldActiveSymbolList = new ArrayList < Symbol > ();
    Stack oldStack = cloneCurrentStack ();

    for ( State current : this.stateList )
      if ( current.isActive () )
        oldActiveStateSet.add ( current );

    for ( Transition current : this.transitionList )
      if ( current.isActive () )
      {
        oldActiveTransitionSet.add ( current );
        for ( Symbol currentSymbol : current )
          if ( currentSymbol.isActive () )
            oldActiveSymbolList.add ( currentSymbol );
      }

    return new StateMachineHistoryItem ( oldActiveStateSet,
        oldActiveTransitionSet, oldActiveSymbolList, oldStack, false );
  }


  /**
   * Pushes the current state on the internal history stack
   * 
   * @return the old state set
   */
  private TreeSet < State > pushCurrentState ()
  {
    final StateMachineHistoryItem historyItem = makeCurrentHistoryItem ();
    this.history.add ( historyItem );

    return historyItem.getStateSet ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#nextSymbol()
   */
  public final void nextSymbol ()
  {
    TreeSet < State > oldActiveStateSet = pushCurrentState ();
    StateMachineHistoryItem historyItem = this.history.get ( this.history
        .size () - 1 );
    clearActiveState ();
    clearActiveTransition ();
    clearActiveSymbol ();

    // check for epsilon transitions
    boolean epsilonTransitionFound = false;
    stateLoop : for ( State activeState : oldActiveStateSet )
      transitionLoop : for ( Transition current : activeState
          .getTransitionBegin () )
        if ( ( current.getTransitionType ().equals (
            TransitionType.EPSILON_ONLY ) || current.getTransitionType ()
            .equals ( TransitionType.EPSILON_SYMBOL ) )
            // special case for the pda
            && ( ( !oldActiveStateSet.contains ( current.getStateEnd () ) ) || getMachineType ()
                .equals ( MachineType.PDA ) ) )
        {
          Word readWord = current.getPushDownWordRead ();
          ArrayList < Symbol > stackSymbols = this.stack.peak ( readWord
              .size () );

          // the read word must match
          if ( readWord.size () != stackSymbols.size () )
            continue transitionLoop;
          for ( int i = 0 ; i < readWord.size () ; i++ )
            if ( !readWord.get ( i ).equals ( stackSymbols.get ( i ) ) )
              continue transitionLoop;

          epsilonTransitionFound = true;
          break stateLoop;
        }

    // epsilon transition found
    if ( epsilonTransitionFound )
      for ( State currentState : oldActiveStateSet )
      {
        // add the old state if not a pda
        if ( !getMachineType ().equals ( MachineType.PDA ) )
          currentState.setActive ( true );

        transitionLoop : for ( Transition currentTransition : currentState
            .getTransitionBegin () )
          if ( ( currentTransition.getTransitionType ().equals (
              TransitionType.EPSILON_ONLY ) || currentTransition
              .getTransitionType ().equals ( TransitionType.EPSILON_SYMBOL ) )
              && ( !oldActiveStateSet.contains ( currentTransition
                  .getStateEnd () ) ) )
          {
            Word readWord = currentTransition.getPushDownWordRead ();
            ArrayList < Symbol > stackSymbols = this.stack.peak ( readWord
                .size () );

            // the read word must match
            if ( readWord.size () != stackSymbols.size () )
              continue transitionLoop;
            for ( int i = 0 ; i < readWord.size () ; i++ )
              if ( !readWord.get ( i ).equals ( stackSymbols.get ( i ) ) )
                continue transitionLoop;

            // add the state begin if not a pda
            if ( !getMachineType ().equals ( MachineType.PDA ) )
              currentTransition.getStateBegin ().setActive ( true );

            currentTransition.setActive ( true );

            currentTransition.getStateEnd ().setActive ( true );

            for ( Symbol currentSymbol : currentTransition )
              if ( currentSymbol.isEpsilon () )
              {
                currentSymbol.setActive ( true );
                break;
              }

            replaceStack ( readWord.size (), currentTransition
                .getPushDownWordWrite () );
          }
      }
    else
    {
      Symbol symbol;
      try
      {
        symbol = this.word.nextSymbol ();
        historyItem.setNextWordStep ( true );
      }
      catch ( WordFinishedException exc )
      {
        throw new RuntimeException ( exc.getMessage () );
      }

      for ( State currentState : oldActiveStateSet )
        transitionLoop : for ( Transition currentTransition : currentState
            .getTransitionBegin () )
        {
          Word readWord = currentTransition.getPushDownWordRead ();
          ArrayList < Symbol > stackSymbols = this.stack.peak ( readWord
              .size () );

          // the read word must match
          if ( readWord.size () != stackSymbols.size () )
            continue transitionLoop;
          for ( int i = 0 ; i < readWord.size () ; i++ )
            if ( !readWord.get ( i ).equals ( stackSymbols.get ( i ) ) )
              continue transitionLoop;

          if ( currentTransition.contains ( symbol ) )
          {
            currentTransition.setActive ( true );

            currentTransition.getStateEnd ().setActive ( true );

            for ( Symbol currentSymbol : currentTransition )
              if ( !currentSymbol.isEpsilon ()
                  && currentSymbol.getName ().equals ( symbol.getName () ) )
              {
                currentSymbol.setActive ( true );
                break;
              }

            replaceStack ( readWord.size (), currentTransition
                .getPushDownWordWrite () );
          }
        }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#nextSymbol(Transition)
   */
  public void nextSymbol ( Transition transition )
  {
    ArrayList < State > activeStateList = new ArrayList < State > ();
    for ( State current : this.stateList )
      if ( current.isActive () )
        activeStateList.add ( current );

    if ( activeStateList.size () == 0 )
      throw new RuntimeException ( "active state set is empty" ); //$NON-NLS-1$

    TreeSet < State > oldActiveStateSet = new TreeSet < State > ();
    TreeSet < Transition > oldActiveTransitionSet = new TreeSet < Transition > ();
    ArrayList < Symbol > oldActiveSymbolList = new ArrayList < Symbol > ();
    Stack oldStack = cloneCurrentStack ();

    for ( State current : this.stateList )
      if ( current.isActive () )
        oldActiveStateSet.add ( current );

    for ( Transition current : this.transitionList )
      if ( current.isActive () )
      {
        oldActiveTransitionSet.add ( current );
        for ( Symbol currentSymbol : current )
          if ( currentSymbol.isActive () )
            oldActiveSymbolList.add ( currentSymbol );
      }

    StateMachineHistoryItem historyItem = new StateMachineHistoryItem (
        oldActiveStateSet, oldActiveTransitionSet, oldActiveSymbolList,
        oldStack, false );
    this.history.add ( historyItem );

    clearActiveState ();
    clearActiveTransition ();
    clearActiveSymbol ();

    // epsilon
    if ( transition.getTransitionType ().equals ( TransitionType.EPSILON_ONLY )
        || transition.getTransitionType ().equals (
            TransitionType.EPSILON_SYMBOL ) )
    {
      Word readWord = transition.getPushDownWordRead ();

      transition.getStateBegin ().setActive ( true );

      transition.setActive ( true );

      transition.getStateEnd ().setActive ( true );

      for ( Symbol currentSymbol : transition )
        if ( currentSymbol.isEpsilon () )
        {
          currentSymbol.setActive ( true );
          break;
        }

      replaceStack ( readWord.size (), transition.getPushDownWordWrite () );
    }
    // no epsilon
    else
    {
      Word readWord = transition.getPushDownWordRead ();

      transition.setActive ( true );

      transition.getStateEnd ().setActive ( true );

      replaceStack ( readWord.size (), transition.getPushDownWordWrite () );
    }
  }


  /**
   * Restores the whole history
   * 
   * @param historyItem
   */
  public final void restoreHistoryItem (
      final StateMachineHistoryItem historyItem )
  {
    clearActiveState ();
    clearActiveTransition ();
    clearActiveSymbol ();
    this.stack.clear ();

    ArrayList < Symbol > historyStackSymbolList = historyItem.getStack ().peak (
        historyItem.getStack ().size () );
    for ( int i = historyStackSymbolList.size () - 1 ; i >= 0 ; i-- )
      this.stack.push ( historyStackSymbolList.get ( i ) );

    for ( State current : historyItem.getStateSet () )
      current.setActive ( true );
    for ( Transition current : historyItem.getTransitionSet () )
      current.setActive ( true );
    for ( Symbol current : historyItem.getSymbolSet () )
      current.setActive ( true );
  }


  /**
   * Restores the previous history item
   * 
   * @return the restored history item
   */
  private final StateMachineHistoryItem restorePreviousHistoryItem ()
  {
    if ( this.history.size () == 0 )
      throw new RuntimeException ( "history is empty" ); //$NON-NLS-1$

    final StateMachineHistoryItem historyItem = this.history
        .remove ( this.history.size () - 1 );

    restoreHistoryItem ( historyItem );

    return historyItem;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#previousSymbol()
   */
  public void previousSymbol ()
  {
    final StateMachineHistoryItem historyItem = restorePreviousHistoryItem ();

    if ( historyItem.isNextWordStep () )
      try
      {
        this.word.previousSymbol ();
      }
      catch ( WordResetedException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
        return;
      }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#removeMachineChangedListener(MachineChangedListener)
   */
  public final void removeMachineChangedListener (
      MachineChangedListener listener )
  {
    this.listenerList.add ( MachineChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#removeState(java.lang.Iterable)
   */
  public final void removeState ( Iterable < State > states )
  {
    if ( states == null )
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    if ( !states.iterator ().hasNext () )
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    for ( State current : states )
      removeState ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#removeState(State)
   */
  public final void removeState ( State state )
  {
    this.stateList.remove ( state );
    for ( Transition current : state.getTransitionBegin () )
      removeTransition ( current );
    for ( Transition current : state.getTransitionEnd () )
      removeTransition ( current );
    fireTableDataChanged ();
    state.removeStateChangedListener ( this.stateChangedListener );
    state.removeModifyStatusChangedListener ( this.modifyStatusChangedListener );
    fireModifyStatusChanged ( false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#removeState(State[])
   */
  public final void removeState ( State ... states )
  {
    if ( states == null )
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    if ( states.length == 0 )
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    for ( State current : states )
      removeState ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#removeSymbol(Symbol)
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
   * {@inheritDoc}
   * 
   * @see StateMachine#removeTransition(java.lang.Iterable)
   */
  public final void removeTransition ( Iterable < Transition > transitions )
  {
    if ( transitions == null )
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    if ( !transitions.iterator ().hasNext () )
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    for ( Transition current : transitions )
      removeTransition ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#removeTransition(Transition)
   */
  public final void removeTransition ( Transition transition )
  {
    this.transitionList.remove ( transition );
    for ( State current : this.stateList )
    {
      if ( current.getTransitionBegin ().contains ( transition ) )
        current.removeTransitionBegin ( transition );

      if ( current.getTransitionEnd ().contains ( transition ) )
        current.removeTransitionEnd ( transition );
    }
    fireTableDataChanged ();
    transition
        .removeTransitionChangedListener ( this.transitionChangedListener );
    transition
        .removeModifyStatusChangedListener ( this.modifyStatusChangedListener );
    fireModifyStatusChanged ( false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#removeTransition(Transition[])
   */
  public final void removeTransition ( Transition ... transitions )
  {
    if ( transitions == null )
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    if ( transitions.length == 0 )
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    for ( Transition current : transitions )
      removeTransition ( current );
  }


  /**
   * Replaces the first size members of the {@link Stack} with the given
   * {@link Word}.
   * 
   * @param size The number of {@link Symbol}s to replace.
   * @param replacement The replacement.
   */
  private final void replaceStack ( int size, Word replacement )
  {
    for ( int i = 0 ; i < size ; i++ )
      this.stack.pop ();
    for ( int i = replacement.size () - 1 ; i >= 0 ; i-- )
      this.stack.push ( replacement.get ( i ) );
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
      current.resetModify ();
    for ( Transition current : this.transitionList )
      current.resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#setSelectedState(State)
   */
  public final void setSelectedState ( State state )
  {
    // reset
    clearSelectedTransition ();

    // find the row
    int row = -1;
    for ( int i = 0 ; i < this.stateList.size () ; i++ )
      if ( this.stateList.get ( i ).equals ( state ) )
      {
        row = i;
        break;
      }

    State chachedState = ( State ) getValueAt ( row, STATE_COLUMN );
    chachedState.setSelected ( true );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#setSelectedTransition(ArrayList)
   */
  public final void setSelectedTransition (
      ArrayList < Transition > transitionList )
  {
    // reset
    clearSelectedTransition ();

    // transition
    for ( Transition current : transitionList )
      current.setSelected ( true );

    for ( Transition current : transitionList )
    {
      // find the row
      int row = -1;
      for ( int i = 0 ; i < this.stateList.size () ; i++ )
        if ( this.stateList.get ( i ).equals ( current.getStateBegin () ) )
        {
          row = i;
          break;
        }

      if ( current.getTransitionType ().equals ( TransitionType.EPSILON_ONLY ) )
      {
        StateSet stateSet = ( StateSet ) getValueAt ( row, EPSILON_COLUMN );

        for ( State currentState : stateSet )
          if ( currentState.equals ( current.getStateEnd () ) )
            currentState.setSelected ( true );
      }
      else if ( current.getTransitionType ().equals (
          TransitionType.EPSILON_SYMBOL ) )
      {
        StateSet stateSet = ( StateSet ) getValueAt ( row, EPSILON_COLUMN );

        for ( State currentState : stateSet )
          if ( currentState.equals ( current.getStateEnd () ) )
            currentState.setSelected ( true );

        // find the columns
        for ( int i = 0 ; i < this.alphabet.size () ; i++ )
          for ( int j = 0 ; j < current.size () ; j++ )
            if ( this.alphabet.get ( i ).equals ( current.getSymbol ( j ) ) )
            {
              int column = i + SPECIAL_COLUMN_COUNT;
              stateSet = ( StateSet ) getValueAt ( row, column );

              for ( State currentState : stateSet )
                if ( currentState.equals ( current.getStateEnd () ) )
                  currentState.setSelected ( true );
            }
      }
      else if ( current.getTransitionType ().equals ( TransitionType.SYMBOL ) )
      {
        // find the columns
        for ( int i = 0 ; i < this.alphabet.size () ; i++ )
          for ( int j = 0 ; j < current.size () ; j++ )
            if ( this.alphabet.get ( i ).equals ( current.getSymbol ( j ) ) )
            {
              int column = i + SPECIAL_COLUMN_COUNT;
              StateSet stateSet = ( StateSet ) getValueAt ( row, column );

              for ( State currentState : stateSet )
                if ( currentState.equals ( current.getStateEnd () ) )
                  currentState.setSelected ( true );
            }
      }
      else
        throw new RuntimeException ( "unsupported transition type" ); //$NON-NLS-1$
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#setUsePushDownAlphabet(boolean)
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
      throw new IllegalArgumentException (
          "the state column should not be editable" ); //$NON-NLS-1$

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
        if ( currentNew.getName ().equals ( currentOld.getName () ) )
        {
          found = true;
          break;
        }
      if ( !found )
        for ( State stateMember : this.stateList )
          if ( stateMember.getName ().equals ( currentNew.getName () ) )
          {
            stateAdd.add ( stateMember );
            break;
          }
    }

    for ( State currentOld : stateSetOld )
    {
      boolean found = false;
      for ( State currentNew : stateSetNew )
        if ( currentOld.getName ().equals ( currentNew.getName () ) )
        {
          found = true;
          break;
        }
      if ( !found )
        for ( State stateMember : this.stateList )
          if ( stateMember.getName ().equals ( currentOld.getName () ) )
          {
            stateRemove.add ( stateMember );
            break;
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
      try
      {
        // Epsilon transition
        if ( columnIndex == EPSILON_COLUMN )
        {
          logger.debug ( "setValueAt", "add transition: epsilon" ); //$NON-NLS-1$ //$NON-NLS-2$

          Transition foundTransition = null;
          loopTransition : for ( Transition currentTransition : this.transitionList )
            if ( ( ! ( currentTransition.getTransitionType ().equals (
                TransitionType.EPSILON_ONLY ) || currentTransition
                .getTransitionType ().equals ( TransitionType.EPSILON_SYMBOL ) ) )
                && currentTransition.getStateBegin ().getName ().equals (
                    stateBegin.getName () )
                && currentTransition.getStateEnd ().getName ().equals (
                    currentState.getName () ) )
            {
              foundTransition = currentTransition;
              break loopTransition;
            }

          if ( foundTransition == null )
          {
            Transition newTransition = new DefaultTransition ( this.alphabet,
                this.pushDownAlphabet, new DefaultWord (), new DefaultWord (),
                stateBegin, currentState );
            newTransition.add ( new DefaultSymbol () );
            transitionAdd.add ( newTransition );
          }
          else
            symbolsAdd.add ( new ObjectPair < Transition, Symbol > (
                foundTransition, new DefaultSymbol () ) );
        }
        // No epsilon transition
        else
        {
          Transition foundTransition = null;
          loopTransition : for ( Transition currentTransition : this.transitionList )
            if ( currentTransition.getStateBegin ().getName ().equals (
                stateBegin.getName () )
                && currentTransition.getStateEnd ().getName ().equals (
                    currentState.getName () ) )
            {
              foundTransition = currentTransition;
              break loopTransition;
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

    // Remove the transitions and symbols
    for ( State currentState : stateRemove )
      for ( Transition currentTransition : this.transitionList )
        if ( currentTransition.getStateBegin ().getName ().equals (
            stateBegin.getName () )
            && currentTransition.getStateEnd ().getName ().equals (
                currentState.getName () ) )
          // Epsilon transition
          if ( ( columnIndex == EPSILON_COLUMN )
              && currentTransition.getTransitionType ().equals (
                  TransitionType.EPSILON_ONLY ) )
          {
            logger.debug ( "setValueAt", "remove transition: epsilon" ); //$NON-NLS-1$ //$NON-NLS-2$
            transitionRemove.add ( currentTransition );
          }
          else if ( ( columnIndex == EPSILON_COLUMN )
              && currentTransition.getTransitionType ().equals (
                  TransitionType.EPSILON_SYMBOL ) )
          {
            logger.debug (
                "setValueAt", "remove transition: only epsilon symbol" ); //$NON-NLS-1$ //$NON-NLS-2$
            for ( Symbol epsilonSymbol : currentTransition )
              if ( epsilonSymbol.isEpsilon () )
              {
                symbolsRemove.add ( new ObjectPair < Transition, Symbol > (
                    currentTransition, epsilonSymbol ) );
                break;
              }
          }
          // No epsilon transition
          else if ( columnIndex > EPSILON_COLUMN )
          {
            Symbol symbolColumn = this.alphabet.get ( columnIndex
                - SPECIAL_COLUMN_COUNT );
            Symbol symbolRemove = null;
            loopSymbol : for ( Symbol currentSymbol : currentTransition
                .getSymbol () )
              if ( currentSymbol.equals ( symbolColumn ) )
              {
                symbolRemove = currentSymbol;
                break loopSymbol;
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
              logger.debug ( "setValueAt", //$NON-NLS-1$
                  "remove transition: no epsilon: nothing found" ); //$NON-NLS-1$
          }
          else
            logger.debug ( "setValueAt", //$NON-NLS-1$
                "remove transition: epsilon column: no epsilon transition" ); //$NON-NLS-1$

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
   * {@inheritDoc}
   * 
   * @see StateMachine#start(Word)
   */
  public final void start ( Word startWord )
  {
    // Word
    if ( startWord == null )
      throw new NullPointerException ( "word is null" ); //$NON-NLS-1$
    this.word = startWord;
    this.word.start ();

    this.stack.clear ();

    clearHistory ();

    for ( State current : this.stateList )
      if ( current.isStartState () )
        current.setActive ( true );

    onStart ();
  }


  /**
   * Does a custom start action
   */
  protected void onStart ()
  {
    // do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#stop
   */
  public final void stop ()
  {
    this.word = null;

    clearActiveState ();
    clearActiveTransition ();
    clearActiveSymbol ();
    this.stack.clear ();

    clearHistory ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateMachine#validate()
   */
  public final void validate () throws MachineValidationException
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();

    if ( this.validationElementList.contains ( ValidationElement.ALL_SYMBOLS ) )
      machineExceptionList.addAll ( checkAllSymbols () );

    if ( this.validationElementList
        .contains ( ValidationElement.EPSILON_TRANSITION ) )
      machineExceptionList.addAll ( checkEpsilonTransition () );

    if ( this.validationElementList
        .contains ( ValidationElement.TRANSITION_STACK_OPERATION ) )
      machineExceptionList.addAll ( checkTransitionStackOperation () );

    if ( this.validationElementList.contains ( ValidationElement.FINAL_STATE ) )
      machineExceptionList.addAll ( checkFinalState () );

    if ( this.validationElementList
        .contains ( ValidationElement.MORE_THAN_ONE_START_STATE ) )
      machineExceptionList.addAll ( checkMoreThanOneStartState () );

    if ( this.validationElementList
        .contains ( ValidationElement.NO_START_STATE ) )
      machineExceptionList.addAll ( checkNoStartState () );

    if ( this.validationElementList.contains ( ValidationElement.STATE_NAME ) )
      machineExceptionList.addAll ( checkStateName () );

    if ( this.validationElementList
        .contains ( ValidationElement.STATE_NOT_REACHABLE ) )
      machineExceptionList.addAll ( checkStateNotReachable () );

    if ( this.validationElementList
        .contains ( ValidationElement.SYMBOL_ONLY_ONE_TIME ) )
      machineExceptionList.addAll ( checkSymbolOnlyOneTime () );

    // Throw the exception if a warning or an error has occurred.
    if ( machineExceptionList.size () > 0 )
      throw new MachineValidationException ( machineExceptionList );
  }


  /**
   * Returns the current active state
   * 
   * @return The current active state or throws
   */
  public State getCurrentState ()
  {
    State ret = null;
    for ( State state : this.stateList )
      if ( state.isActive () )
      {
        if ( ret != null )
          throw new RuntimeException ( "Multiple states active!" ); //$NON-NLS-1$

        ret = state;
      }
    if ( ret == null )
      throw new RuntimeException ( "No state active!" ); //$NON-NLS-1$
    return ret;

  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Storable#getElement()
   */
  public Element getElement ()
  {
    return null; // nothing to return
  }
}
