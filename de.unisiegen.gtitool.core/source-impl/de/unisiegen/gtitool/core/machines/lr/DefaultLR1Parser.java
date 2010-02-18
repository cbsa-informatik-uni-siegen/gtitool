package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.DefaultLRActionSet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.LR1State;
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
   * @param alphabet
   * @param pushDownAlphabet
   * @param usePushDownAlphabet
   * @param validationElements
   */
  public DefaultLR1Parser ( LR1Grammar grammar ) throws AlphabetException
  {
    this.grammar = grammar;

    this.lr1Automaton = new LR1 ( grammar );
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
        this.lr1Automaton.nextSymbol ( this.currentTerminal () );
        nextSymbol();
        break;
      case ACCEPT :
        this.accept();
        break;
    }

    return true;
  }


  public void autoTransit ()
  {
    LRActionSet possibleActions = actions ( currentItems (), currentTerminal () );

    System.out.println ( possibleActions );

    if ( possibleActions.size () != 1 )
    {
      System.err.println ( "Multiple actions!" );
      System.exit ( 1 );// TODO
      // TODO throw
    }

    // System.out.println ( possibleActions );
    if ( transit ( possibleActions.first () ) == false )
    {
      System.err.println ( "Testtest" );
      System.exit ( 1 );
    }
  }


  private LR1ItemSet currentItems ()
  {
    State state = this.lr1Automaton.getCurrentState ();

    LR1State lr1state = ( LR1State ) state;

    return lr1state.getLR1Items ();
  }


  /**
   * Calculate the actions set
   * 
   * @param items - The LR0 item set
   * @param symbol - The current terminal symbol
   * @return The actions set
   */
  public LRActionSet actions ( LR1ItemSet items, TerminalSymbol symbol )
  {
    LRActionSet ret = new DefaultLRActionSet ();

    try
    {
      for ( LR1Item item : items )
      {
        if ( item.dotIsAtEnd () )
        {
          if ( item.getNonterminalSymbol ().isStart () )
          {
            if ( symbol == null )
              ret.add ( new LRAcceptAction () );
          }
          else if ( this.grammar.follow ( item.getNonterminalSymbol () )
              .contains ( symbol ) )
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


  public void start ( Word word )
  {
    super.start ( word );
    this.lr1Automaton.start ( new DefaultWord () );
  }


  private LR1Grammar grammar;


  private LR1 lr1Automaton;

}
