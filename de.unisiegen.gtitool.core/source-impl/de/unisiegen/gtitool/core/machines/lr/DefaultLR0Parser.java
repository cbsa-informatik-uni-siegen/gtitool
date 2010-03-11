package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LR0State;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.ShiftAction;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.core.machines.AbstractLRMachine;
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
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#onShift(de.unisiegen.gtitool.core.entities.Action)
   */
  @Override
  protected boolean onShift ( @SuppressWarnings ( "unused" ) final Action action )
  {
    this.lr0Automaton.nextSymbol ( currentTerminal () );
    nextSymbol ();
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#onReduce(de.unisiegen.gtitool.core.entities.Action)
   */
  @Override
  protected boolean onReduce ( final Action action )
  {
    int unwind = 0;
    try
    {
      for ( ; unwind < action.getReduceAction ().getProductionWord ().size () ; ++unwind )
        this.lr0Automaton.previousSymbol ();
    }
    catch ( RuntimeException e )
    {
      for ( int i = 0 ; i < unwind ; ++i )
        this.lr0Automaton.nextSymbol ();
      return false;
    }
    this.lr0Automaton.nextSymbol ( action.getReduceAction ()
        .getNonterminalSymbol () );
    return true;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean transit ( Action action )
  {
    ActionSet possibleActions = actions ( currentItems (), currentTerminal () );

    if ( !possibleActions.contains ( action ) )
      return false;

    return super.transit ( action );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractLRMachine#autoTransit()
   */
  @Override
  public Action autoTransit () throws MachineAmbigiousActionException
  {
    return assertTransit ( actions ( currentItems (), currentTerminal () ) );
  }


  /**
   * The current LR0ItemSet pointed to by the LR0 automaton
   * 
   * @return the lr0 item set
   */
  private LR0ItemSet currentItems ()
  {
    final State state = this.lr0Automaton.getCurrentState ();

    final LR0State lr0state = ( LR0State ) state;

    return lr0state.getLR0Items ();
  }


  /**
   * Calculate the actions set
   * 
   * @param items - The LR0 item set
   * @param symbol - The current terminal symbol
   * @return The actions set
   */
  public ActionSet actions ( LR0ItemSet items, TerminalSymbol symbol )
  {
    ActionSet ret = new DefaultActionSet ();

    try
    {
      for ( LR0Item item : items )
        if ( item.dotIsAtEnd () )
        {
          if ( item.getNonterminalSymbol ().isStart () )
          {
            if ( symbol == null )
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
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#start(de.unisiegen.gtitool.core.entities.Word)
   */
  @Override
  public void start ( final Word word )
  {
    super.start ( word );
    this.lr0Automaton.start ( new DefaultWord () );
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
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#getPossibleActions()
   */
  @Override
  public ActionSet getPossibleActions ()
  {
    ActionSet actions = new DefaultActionSet ();

    return actions;
  }
}
