package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.DefaultLRActionSet;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.LR1State;
import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.ShiftAction;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;
import de.unisiegen.gtitool.core.machines.AbstractLRMachine;
import de.unisiegen.gtitool.core.machines.dfa.LR1;


/**
 * TODO
 */
public class DefaultLR1Parser extends AbstractLRMachine implements LR1Parser
{

  /**
   * TODO
   * 
   * @param grammar - The grammar to parse
   * @throws AlphabetException
   */
  public DefaultLR1Parser ( final LR1Grammar grammar ) throws AlphabetException
  {
    super ( grammar.getAlphabet () );
    this.grammar = grammar;

    this.lr1Automaton = new LR1 ( grammar );
  }


  /**
   * @param lr1 - The LR1 parser to use (can be used to use an LALR1 automaton)
   * @param grammar
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
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#transit(de.unisiegen.gtitool.core.entities.Action)
   * @return true if the transition could be made
   */
  @Override
  public boolean transit ( Action action )
  {
    ActionSet possibleActions = actions ( currentItems (), currentTerminal () );

    if ( !possibleActions.contains ( action ) )
      return false;

    switch ( action.getTransitionType () )
    {
      case REDUCE :
        int unwind = 0;
        try
        {
          for ( ; unwind < action.getReduceAction ().getProductionWord ()
              .size () ; ++unwind )
            this.lr1Automaton.previousSymbol ();
        }
        catch ( RuntimeException e )
        {
          for ( int i = 0 ; i < unwind ; ++i )
            this.lr1Automaton.nextSymbol ();
          return false;
        }
        this.lr1Automaton.nextSymbol ( action.getReduceAction ()
            .getNonterminalSymbol () );
        break;
      case SHIFT :
        this.lr1Automaton.nextSymbol ( currentTerminal () );
        nextSymbol ();
        break;
      case ACCEPT :
        accept ();
        break;
    }

    return true;
  }


  /**
   * TODO
   * 
   * @throws MachineAmbigiousActionException
   * @see de.unisiegen.gtitool.core.machines.AbstractLRMachine#autoTransit()
   */
  @Override
  public void autoTransit () throws MachineAmbigiousActionException
  {
    this.assertTransit ( actions ( currentItems (), currentTerminal () ) );
  }


  /**
   * Get the current LR1 items
   * 
   * @return The items
   */
  private LR1ItemSet currentItems ()
  {
    final State state = this.lr1Automaton.getCurrentState ();

    final LR1State lr1state = ( LR1State ) state;

    return lr1state.getLR1Items ();
  }


  /**
   * Calculate the actions set
   * 
   * @param items - The LR0 item set
   * @param symbol - The current terminal symbol
   * @return The actions set
   */
  public ActionSet actions ( LR1ItemSet items, TerminalSymbol symbol )
  {
    ActionSet ret = new DefaultLRActionSet ();

    try
    {
      for ( LR1Item item : items )
        if ( item.dotIsAtEnd () )
        {
          if ( item.getNonterminalSymbol ().isStart () )
          {
            if ( symbol == null )
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
   * TODO
   * 
   * @param word
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#start(de.unisiegen.gtitool.core.entities.Word)
   */
  @Override
  public void start ( Word word )
  {
    super.start ( word );
    this.lr1Automaton.start ( new DefaultWord () );
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
   * TODO
   */
  private LR1Grammar grammar;


  /**
   * TODO
   */
  private LR1 lr1Automaton;


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#getPossibleActions()
   */
  @Override
  protected ActionSet getPossibleActions ()
  {
    ActionSet actions = new DefaultLRActionSet ();

    return actions;
  }

}
