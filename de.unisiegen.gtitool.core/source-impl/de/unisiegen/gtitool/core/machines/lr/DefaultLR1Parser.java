package de.unisiegen.gtitool.core.machines.lr;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.LR1State;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.ShiftAction;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;
import de.unisiegen.gtitool.core.machines.AbstractLRMachine;
import de.unisiegen.gtitool.core.machines.dfa.AbstractLR;
import de.unisiegen.gtitool.core.machines.dfa.LR1;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The {@link DefaultLR1Parser}
 */
public class DefaultLR1Parser extends AbstractLRMachine implements LR1Parser
{

  /**
   * Allocates a new {@link DefaultLR1Parser}
   * 
   * @param grammar - The {@link LR1Grammar} to parse
   * @throws AlphabetException
   */
  public DefaultLR1Parser ( final LR1Grammar grammar ) throws AlphabetException
  {
    super ( grammar.getAlphabet () );
    this.grammar = grammar;

    this.lr1Automaton = new LR1 ( grammar );
  }


  /**
   * Allocates a new {@link DefaultLR1Parser}
   * 
   * @param lr1 - The LR1 parser to use (can be used to use an LALR1 automaton)
   * @param grammar The {@link LR1Grammar}
   * @throws AlphabetException
   */
  public DefaultLR1Parser ( final LR1 lr1, final LR1Grammar grammar )
      throws AlphabetException
  {
    super ( grammar.getAlphabet () );

    this.grammar = grammar;

    this.lr1Automaton = lr1;
  }


  /**
   * Calculate the actions set
   * 
   * @param items - The LR0 item set
   * @param symbol - The current terminal symbol
   * @return The actions set
   */
  private ActionSet actions ( LR1ItemSet items, TerminalSymbol symbol )
  {
    ActionSet ret = new DefaultActionSet ();

    try
    {
      for ( LR1Item item : items )
        if ( item.dotIsAtEnd () )
        {
          if ( item.getNonterminalSymbol ().isStart () )
          {
            if ( symbol.equals ( DefaultTerminalSymbol.EndMarker ) )
              ret.add ( new AcceptAction () );
          }
          else if ( item.getLookAhead ().equals ( symbol ) )
            ret.add ( new ReduceAction ( item ) );
        }
        else if ( item.dotPrecedesTerminal ()
            && item.getProductionWordMemberAfterDot ().equals ( symbol ) )
          ret.add ( new ShiftAction () );
    }
    catch ( ActionSetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

    return ret;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getMachineType()
   */
  @Override
  public MachineType getMachineType ()
  {
    return MachineType.LR1Parser;
  }


  /**
   * The parser's associated grammar
   * 
   * @return the grammar
   */
  @Override
  public LR1Grammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * The {@link LR1Grammar}
   */
  private LR1Grammar grammar;


  /**
   * The {@link LR1} automaton
   */
  private LR1 lr1Automaton;


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractLRMachine#getAutomaton()
   */
  @Override
  public AbstractLR getAutomaton ()
  {
    return this.lr1Automaton;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.lr.LR1Parser#getLR1()
   */
  public LR1 getLR1 ()
  {
    return this.lr1Automaton;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractLRMachine#getTableCellStrings()
   */
  @Override
  public ArrayList < ArrayList < PrettyString >> getTableCellStrings ()
  {
    final ArrayList < ArrayList < PrettyString >> ret = new ArrayList < ArrayList < PrettyString >> ();

    for ( TerminalSymbol symbol : this.getGrammar ().getTerminalSymbolSet () )
    {
      final ArrayList < PrettyString > temp = new ArrayList < PrettyString > ();

      for ( State state : this.lr1Automaton.getState () )
      {
        final LR1State lrState = ( LR1State ) state;

        temp.add ( this.actions ( lrState.getLR1Items (), symbol )
            .toPrettyString () );
      }

      ret.add ( temp );
    }
    return ret;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected ActionSet actionSetBase ( final LRState state,
      final TerminalSymbol symbol )
  {
    return actions ( ( ( LR1State ) state ).getLR1Items (), symbol );
  }
}
