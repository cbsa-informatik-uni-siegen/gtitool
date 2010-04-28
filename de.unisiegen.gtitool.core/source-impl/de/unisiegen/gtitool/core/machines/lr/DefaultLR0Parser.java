package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LR0State;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.ShiftAction;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.core.machines.AbstractLRMachine;
import de.unisiegen.gtitool.core.machines.dfa.AbstractLR;
import de.unisiegen.gtitool.core.machines.dfa.LR0;


/**
 * The {@link DefaultLR0Parser}
 */
public class DefaultLR0Parser extends AbstractLRMachine implements LR0Parser
{

  /**
   * Allocates a new {@link DefaultLR0Parser}
   * 
   * @param grammar The {@link LR0Grammar}
   * @throws AlphabetException
   */
  public DefaultLR0Parser ( final LR0Grammar grammar ) throws AlphabetException
  {
    super ( grammar.getAlphabet () );
    this.grammar = grammar;

    this.lr0Automaton = new LR0 ( grammar );
  }


  /**
   * Calculate the actions set
   * 
   * @param items - The LR0 item set
   * @param symbol - The current terminal symbol
   * @return The actions set
   */
  private ActionSet actions ( final LR0ItemSet items,
      final TerminalSymbol symbol )
  {
    ActionSet ret = new DefaultActionSet ();

    try
    {
      for ( LR0Item item : items )
        if ( item.dotIsAtEnd () )
        {
          if ( item.getNonterminalSymbol ().isStart () )
          {
            if ( symbol.equals ( DefaultTerminalSymbol.EndMarker ) )
              ret.add ( new AcceptAction () );
          }
          else if ( followCondition ( item, symbol ) )
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
    catch ( GrammarInvalidNonterminalException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    return ret;
  }


  /**
   * Calculates if the current reduce action should be allowed
   * 
   * @param item - The current lr0 item
   * @param symbol - The current terminal symbol lookahead
   * @return if the current reduce action should be allowed
   * @throws GrammarInvalidNonterminalException
   */
  @SuppressWarnings ( "unused" )
  protected boolean followCondition ( LR0Item item, TerminalSymbol symbol )
      throws GrammarInvalidNonterminalException
  {
    return true;
  }


  /**
   * The parser's associated grammar
   * 
   * @return the grammar
   */
  @Override
  public LR0Grammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getMachineType()
   */
  @Override
  public MachineType getMachineType ()
  {
    return MachineType.LR0Parser;
  }


  /**
   * The associated grammar
   */
  private LR0Grammar grammar;


  /**
   * The LR0 automaton
   */
  private LR0 lr0Automaton;


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractLRMachine#getAutomaton()
   */
  @Override
  public AbstractLR getAutomaton ()
  {
    return this.lr0Automaton;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected ActionSet actionSetBase ( final LRState state,
      final TerminalSymbol symbol )
  {
    return actions ( ( ( LR0State ) state ).getLR0Items (), symbol );
  }
}
