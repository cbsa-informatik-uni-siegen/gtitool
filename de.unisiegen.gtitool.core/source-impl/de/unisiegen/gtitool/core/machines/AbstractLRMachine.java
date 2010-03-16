package de.unisiegen.gtitool.core.machines;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.grammars.cfg.ExtendedGrammar;
import de.unisiegen.gtitool.core.machines.dfa.AbstractLR;
import de.unisiegen.gtitool.core.machines.lr.LRMachine;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * Represents an {@link AbstractLRMachine}
 */
public abstract class AbstractLRMachine extends AbstractStatelessMachine
    implements LRMachine
{

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
  protected abstract AbstractLR getAutomaton ();


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getMachineType()
   */
  public abstract MachineType getMachineType ();
}
