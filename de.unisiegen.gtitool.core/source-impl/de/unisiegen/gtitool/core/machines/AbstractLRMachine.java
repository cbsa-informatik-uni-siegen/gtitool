package de.unisiegen.gtitool.core.machines;


import java.util.ArrayList;
import java.util.Stack;

import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultLRStateStack;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.LRItemSet;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.LRStateStack;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.RejectAction;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.grammars.cfg.ExtendedGrammar;
import de.unisiegen.gtitool.core.machines.dfa.AbstractLR;
import de.unisiegen.gtitool.core.machines.lr.LRMachine;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * Represents an {@link AbstractLRMachine}
 */
public abstract class AbstractLRMachine extends AbstractStatelessMachine
    implements LRMachine
{

  /**
   * Represents the whole history of the LR automaton
   */
  private class HistoryEntry
  {

    /**
     * Creates a new history element
     * 
     * @param currentState
     * @param stateMachineHistory
     * @param stateStack
     */
    public HistoryEntry ( final StateMachineHistoryItem currentState,
        final ArrayList < StateMachineHistoryItem > stateMachineHistory,
        final LRStateStack stateStack )
    {
      this.currentState = currentState;
      this.stateMachineHistory = stateMachineHistory;
      this.stateStack = stateStack;
    }


    /**
     * Returns the entire machine history
     * 
     * @return the history
     */
    public ArrayList < StateMachineHistoryItem > getMachineHistory ()
    {
      return this.stateMachineHistory;
    }


    /**
     * Returns the current active history item
     * 
     * @return the item
     */
    public StateMachineHistoryItem getCurrentState ()
    {
      return this.currentState;
    }


    /**
     * Returns the LR state stack
     * 
     * @return the stack
     */
    public LRStateStack getStateStack ()
    {
      return this.stateStack;
    }


    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
      return this.stateMachineHistory.toString ();
    }


    /**
     * TODO
     */
    private StateMachineHistoryItem currentState;


    /**
     * TODO
     */
    private ArrayList < StateMachineHistoryItem > stateMachineHistory;


    /**
     * TODO
     */
    private LRStateStack stateStack;
  }


  /**
   * Allocates a new {@link AbstractLRMachine}
   * 
   * @param alphabet
   */
  public AbstractLRMachine ( final Alphabet alphabet )
  {
    super ( alphabet );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#autoTransit()
   */
  public final Action autoTransit () throws MachineAmbigiousActionException
  {
    return assertTransit ( this.getPossibleActions () );
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean transit ( final Action action )
  {
    final ActionSet possibleActions = this.getPossibleActions ();

    if ( !possibleActions.contains ( action ) )
      return false;

    return super.transit ( action );
  }


  /**
   * The current LR automaton's state
   * 
   * @return the state
   */
  private LRState currentState ()
  {
    return ( LRState ) this.getAutomaton ().getCurrentState ();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Element getElement ()
  {
    Element element = new Element ( "Grammar" ); //$NON-NLS-1$
    element.addElement ( getGrammar ().getElement () );
    return element;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#onShift(de.unisiegen.gtitool.core.entities.Action)
   */
  @Override
  protected final boolean onShift (
      @SuppressWarnings ( "unused" ) final Action action )
  {
    final TerminalSymbol symbol = currentTerminal ();

    getAutomaton ().nextSymbol ( symbol );
    nextSymbol ();

    getStack ().push ( new DefaultSymbol ( symbol ) );

    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#onReduce(de.unisiegen.gtitool.core.entities.Action)
   */
  @Override
  protected final boolean onReduce ( final Action action )
  {
    AbstractLR automaton = getAutomaton ();

    ArrayList < Symbol > cachedSymbols = new ArrayList < Symbol > ();

    int unwind = 0;
    try
    {
      for ( ; unwind < action.getReduceAction ().getProductionWord ().size () ; ++unwind )
      {
        automaton.previousSymbol ();
        cachedSymbols.add ( getStack ().pop () );
      }
    }
    catch ( RuntimeException e )
    {
      for ( int i = 0 ; i < unwind ; ++i )
      {
        automaton.nextSymbol ();
        getStack ().push ( cachedSymbols.get ( i ) );
      }
      return false;
    }
    final NonterminalSymbol nextSymbol = action.getReduceAction ()
        .getNonterminalSymbol ();

    automaton.nextSymbol ( nextSymbol );

    getStack ().push ( new DefaultSymbol ( nextSymbol ) );

    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#onAccept(de.unisiegen.gtitool.core.entities.Action)
   */
  @Override
  protected boolean onAccept ( final Action action )
  {
    finishAction ();

    return super.onAccept ( action );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#onReject(de.unisiegen.gtitool.core.entities.Action)
   */
  @Override
  protected boolean onReject ( final Action action )
  {
    finishAction ();

    return super.onAccept ( action );
  }


  /**
   * Extra work to do if this is the last action
   */
  private void finishAction ()
  {
    try
    {
      getWord ().nextSymbol ();
      getStack ().clear ();
    }
    catch ( WordFinishedException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#start(de.unisiegen.gtitool.core.entities.Word)
   */
  @Override
  public final void start ( final Word word )
  {
    final Symbol endMarkerSymbol = new DefaultSymbol (
        DefaultTerminalSymbol.EndMarker.getName () );
    word.add ( endMarkerSymbol );
    super.start ( word );

    getAutomaton ().start ( word );

    getStack ().push ( endMarkerSymbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#onStop()
   */
  @Override
  protected void onStop ()
  {
    getAutomaton ().stop ();
    this.history.clear ();
  }


  /**
   * Returns an Array of the LRItemSets
   * 
   * @return the sets
   */
  public ArrayList < LRItemSet > getItems ()
  {
    final ArrayList < LRItemSet > ret = new ArrayList < LRItemSet > ();
    for ( State state : getAutomaton ().getState () )
    {
      final LRState lrstate = ( LRState ) state;

      ret.add ( lrstate.getItems () );
    }

    return ret;
  }


  /**
   * Returns the contents for the parsing table
   * 
   * @return the strings
   */
  public ArrayList < ArrayList < PrettyString >> getTableCellStrings ()
  {
    final ArrayList < ArrayList < PrettyString >> ret = new ArrayList < ArrayList < PrettyString >> ();

    for ( TerminalSymbol symbol : getGrammar ().getTerminalSymbolSet () )
    {
      final ArrayList < PrettyString > temp = new ArrayList < PrettyString > ();

      for ( State state : getAutomaton ().getState () )
        temp.add ( actionSetBase ( ( LRState ) state, symbol )
            .toPrettyString () );

      ret.add ( temp );
    }
    return ret;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#createHistoryEntry()
   */
  @Override
  protected void createHistoryEntry ()
  {
    super.createHistoryEntry ();

    this.history.add ( new HistoryEntry ( getAutomaton ()
        .makeCurrentHistoryItem (), new ArrayList < StateMachineHistoryItem > (
        getAutomaton ().getHistory () ), new DefaultLRStateStack (
        getAutomaton ().getStateStack () ) ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#restoreHistoryEntry()
   */
  @Override
  protected void restoreHistoryEntry ()
  {
    super.restoreHistoryEntry ();

    final HistoryEntry entry = this.history.pop ();

    getAutomaton ().setHistory ( entry.getMachineHistory () );

    getAutomaton ().restoreHistoryItem ( entry.getCurrentState () );

    getAutomaton ().setStateStack ( entry.getStateStack () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#getPossibleActions()
   */
  @Override
  public ActionSet getPossibleActions ()
  {
    return handleEmptyActionSet ( actionSetBase ( currentState (),
        currentTerminal () ) );
  }


  /**
   * Handles an empty action set. Currently, if no action is possible, we have
   * to insert a RejectAction instead.
   * 
   * @param set The set (possibly empty)
   * @return A new set containing a RejectAction if it was previously empty
   */
  static private ActionSet handleEmptyActionSet ( final ActionSet set )
  {
    if ( set.size () == 0 )
      try
      {
        set.add ( new RejectAction () );
      }
      catch ( ActionSetException exc )
      {
        exc.printStackTrace ();
      }
    return set;
  }


  /**
   * Returns the action set for a given LR automaton state and a terminal
   * 
   * @param state the current LR automaton's state
   * @param symbol the current terminal symbol
   * @return the possible actions
   */
  protected abstract ActionSet actionSetBase ( LRState state,
      TerminalSymbol symbol );


  /**
   * Returns the ExtendedGrammar
   * 
   * @return the grammar
   */
  @Override
  public abstract ExtendedGrammar getGrammar ();


  /**
   * Returns the automaton
   * 
   * @return the automaton
   */
  public abstract AbstractLR getAutomaton ();


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getMachineType()
   */
  public abstract MachineType getMachineType ();


  /**
   * Saves the history of the automaton. This is necessary, because the history
   * of the automaton is already used to do reduce steps.
   */
  private Stack < HistoryEntry > history = new Stack < HistoryEntry > ();
}
