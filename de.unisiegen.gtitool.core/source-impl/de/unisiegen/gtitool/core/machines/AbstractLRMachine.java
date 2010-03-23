package de.unisiegen.gtitool.core.machines;


import java.util.ArrayList;
import java.util.Stack;

import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.LRItemSet;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;
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
   * TODO
   */
  private class HistoryEntry
  {

    /**
     * TODO
     * 
     * @param currentState
     * @param stateMachineHistory
     */
    public HistoryEntry ( final StateMachineHistoryItem currentState,
        final ArrayList < StateMachineHistoryItem > stateMachineHistory )
    {
      this.currentState = currentState;
      this.stateMachineHistory = stateMachineHistory;
    }


    /**
     * TODO
     * 
     * @return
     */
    public ArrayList < StateMachineHistoryItem > getMachineHistory ()
    {
      return this.stateMachineHistory;
    }


    public StateMachineHistoryItem getCurrentState ()
    {
      return this.currentState;
    }


    @Override
    public String toString ()
    {
      return this.stateMachineHistory.toString ();
    }


    private StateMachineHistoryItem currentState;


    /**
     * TODO
     */
    private ArrayList < StateMachineHistoryItem > stateMachineHistory;
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
  public abstract Action autoTransit () throws MachineAmbigiousActionException;


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

    this.getStack ().push ( new DefaultSymbol ( symbol ) );

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
        cachedSymbols.add ( this.getStack ().pop () );
      }
    }
    catch ( RuntimeException e )
    {
      for ( int i = 0 ; i < unwind ; ++i )
      {
        automaton.nextSymbol ();
        this.getStack ().push ( cachedSymbols.get ( i ) );
      }
      return false;
    }
    final NonterminalSymbol nextSymbol = action.getReduceAction ()
        .getNonterminalSymbol ();

    automaton.nextSymbol ( nextSymbol );

    this.getStack ().push ( new DefaultSymbol ( nextSymbol ) );

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
    try
    {
      this.getWord ().nextSymbol ();
      this.getStack ().clear ();
    }
    catch ( WordFinishedException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    return super.onAccept ( action );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#start(de.unisiegen.gtitool.core.entities.Word)
   */
  @Override
  public final void start ( final Word word )
  {
    Symbol endMarkerSymbol = new DefaultSymbol (
        DefaultTerminalSymbol.EndMarker.getName () );
    word.add ( endMarkerSymbol );
    super.start ( word );

    this.getAutomaton ().start ( word );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#onStop()
   */
  @Override
  protected void onStop ()
  {
    this.getAutomaton ().stop ();
  }


  /**
   * Returns an Array of the LRItemSets
   * 
   * @return the sets
   */
  public ArrayList < LRItemSet > getItems ()
  {
    final ArrayList < LRItemSet > ret = new ArrayList < LRItemSet > ();
    for ( State state : this.getAutomaton ().getState () )
    {
      final LRState lrstate = ( LRState ) state;

      ret.add ( lrstate.getItems () );
    }

    return ret;
  }


  /**
   * @return
   */
  public ArrayList < ArrayList < PrettyString >> getTableCellStrings ()
  {
    final ArrayList < ArrayList < PrettyString >> ret = new ArrayList < ArrayList < PrettyString >> ();

    for ( TerminalSymbol symbol : this.getGrammar ().getTerminalSymbolSet () )
    {
      final ArrayList < PrettyString > temp = new ArrayList < PrettyString > ();

      for ( State state : this.getAutomaton ().getState () )
        temp.add ( actionSetBase ( ( LRState ) state, symbol )
            .toPrettyString () );

      ret.add ( temp );
    }
    return ret;
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#createHistoryEntry()
   */
  @Override
  protected void createHistoryEntry ()
  {
    super.createHistoryEntry ();

    this.history.add ( new HistoryEntry ( this.getAutomaton ()
        .makeCurrentHistoryItem (), new ArrayList < StateMachineHistoryItem > (
        this.getAutomaton ().getHistory () ) ) );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#restoreHistoryEntry()
   */
  @Override
  protected void restoreHistoryEntry ()
  {
    super.restoreHistoryEntry ();

    final HistoryEntry entry = this.history.pop ();

    this.getAutomaton ().setHistory ( entry.getMachineHistory () );

    this.getAutomaton ().restoreHistoryItem ( entry.getCurrentState () );
  }


  /**
   * TODO
   * 
   * @param state
   * @param symbol
   * @return
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
