package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.DefaultLRActionSet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
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
import de.unisiegen.gtitool.core.exceptions.lractionset.LRActionSetException;
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
   * 
   * @param alphabet
   * @param pushDownAlphabet
   * @param usePushDownAlphabet
   * @param validationElements
   */
  public DefaultLR0Parser ( LR0Grammar grammar ) throws AlphabetException
  {
    this.grammar = grammar;

    this.lr0Automaton = new LR0 ( grammar );
  }


  /**
   * TODO
   * 
   * @param transition
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#transit(de.unisiegen.gtitool.core.entities.LRAction)
   * @return true if the transition could be made
   */
  public boolean transit ( LRAction action )
  {
    // first let the LR0 automaton run
    switch ( action.getTransitionType () )
    {
      case REDUCE :
        for ( int i = 0 ; i < action.getReduceAction ().getProductionWord ()
            .size () ; ++i )
          this.lr0Automaton.previousSymbol ();
        this.lr0Automaton.setWord ( new DefaultWord ( new DefaultSymbol (
            action.getReduceAction ().getNonterminalSymbol ().toString () ) ) );
        this.lr0Automaton.nextSymbol ();
        break;
      case SHIFT :
        this.lr0Automaton.nextSymbol ();
        break;
      case ACCEPT :
        this.lr0Automaton.nextSymbol ();
        // TODO
        break;
    }

    // then check which action we should perform
    State state = this.lr0Automaton.getCurrentState ();

    LR0State lr0state = ( LR0State ) state;

    LR0ItemSet items = lr0state.getLR0Items ();

    LRActionSet possibleActions = actions ( items, new DefaultTerminalSymbol (
        word.get ( this.wordIndex ).toString () ) );

    if ( !possibleActions.contains ( action ) )
      return false;

    ++this.wordIndex;
    // TODO
    return true;
  }


  private static LRActionSet actions ( LR0ItemSet items, TerminalSymbol symbol )
  {
    LRActionSet ret = new DefaultLRActionSet ();

    try
    {
      for ( LR0Item item : items )
      {
        if ( item.dotIsAtEnd () )
        {
          if ( item.getNonterminalSymbol ().isStart () )
            ret.add ( new LRAcceptAction () );
          else
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

    return ret;
  }


  public void start ( Word word )
  {
    this.word = word;
    this.wordIndex = 0;
    this.lr0Automaton.start ( word );
  }


  private LR0Grammar grammar;


  private LR0 lr0Automaton;


  private Word word;


  private int wordIndex;
}
