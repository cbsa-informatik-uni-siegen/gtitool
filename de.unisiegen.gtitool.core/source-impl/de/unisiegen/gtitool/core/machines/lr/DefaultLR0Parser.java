package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.DefaultLRActionSet;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LR0State;
import de.unisiegen.gtitool.core.entities.LRAcceptAction;
import de.unisiegen.gtitool.core.entities.LRAction;
import de.unisiegen.gtitool.core.entities.LRActionSet;
import de.unisiegen.gtitool.core.entities.LRReduceAction;
import de.unisiegen.gtitool.core.entities.LRShiftAction;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.lractionset.LRActionSetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.core.machines.AbstractLRMachine;
import de.unisiegen.gtitool.core.machines.dfa.LR0;


/**
 * TODO
 */
public class DefaultLR0Parser extends AbstractLRMachine implements LR0Parser
{

  /**
   * TODO
   * @param grammar 
   * @throws AlphabetException 
   */
  public DefaultLR0Parser ( final LR0Grammar grammar ) throws AlphabetException
  {
    super ( grammar.makeAutomatonAlphabet () );
    this.grammar = grammar;

    this.lr0Automaton = new LR0 ( grammar );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#transit(de.unisiegen.gtitool.core.entities.LRAction)
   * @return true if the transition could be made
   */
  @Override
  public boolean transit ( LRAction action )
  {
    LRActionSet possibleActions = actions ( currentItems (), currentTerminal () );

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
        break;
      case SHIFT :
        this.lr0Automaton.nextSymbol ( this.currentTerminal () );
        this.nextSymbol ();
        break;
      case ACCEPT :
        this.accept ();
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
    LRActionSet possibleActions = actions ( currentItems (), currentTerminal () );

    if ( possibleActions.size () != 1 )
      throw new MachineAmbigiousActionException ();

    if ( transit ( possibleActions.first () ) == false )
    {
      // shouldn't happen
      System.err.println ( "Internal parser error" ); //$NON-NLS-1$
      System.exit ( 1 );
    }
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
  public LRActionSet actions ( LR0ItemSet items, TerminalSymbol symbol )
  {
    LRActionSet ret = new DefaultLRActionSet ();

    try
    {
      for ( LR0Item item : items )
      {
        if ( item.dotIsAtEnd () )
        {
          if ( item.getNonterminalSymbol ().isStart () )
          {
            if ( symbol == null )
              ret.add ( new LRAcceptAction () );
          }
          else if ( this.followCondition ( item, symbol ) )
            ret.add ( new LRReduceAction ( item ) );
        }
        else if ( item.dotPrecedesTerminal ()
            && item.getProductionWordMemberAfterDot ().equals ( symbol ) )
          ret.add ( new LRShiftAction () );
      }
    }
    catch ( LRActionSetException e )
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
   * TODO
   *
   * @param word
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
  protected LR0Grammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * The associated grammar
   */
  private LR0Grammar grammar;


  /**
   * The LR0 automaton
   */
  private LR0 lr0Automaton;
}
