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
          {
            this.lr0Automaton.previousSymbol ();
            /*
             * Word newWord = new DefaultWord (); for ( int i = 0 ; i <
             * this.lr0Automaton.getWord ().size () - 1 ; ++i ) newWord.add (
             * this.lr0Automaton.getWord ().get ( i ) );
             * this.lr0Automaton.setWord ( newWord );
             */
          }
        }
        catch ( RuntimeException e )
        {
          for ( int i = 0 ; i < unwind ; ++i )
            this.lr0Automaton.nextSymbol ();
          return false;
        }
        /*
         * appendWord ( action.getReduceAction ().getNonterminalSymbol ()
         * .toString () ); this.lr0Automaton.nextSymbol ();
         */
        this.lr0Automaton.nextSymbol ( action.getReduceAction ()
            .getNonterminalSymbol () );
        break;
      case SHIFT :
        /* appendWord ( this.word.get ( this.wordIndex ).toString () ); */
        this.lr0Automaton.nextSymbol ( this.currentTerminal () );
        ++this.wordIndex;
        // this.lr0Automaton.nextSymbol ();
        break;
      case ACCEPT :
        this.wordAccepted = true;
        // this.lr0Automaton.nextSymbol ();
        // TODO
        break;
    }

    return true;
  }


  private void appendWord ( String toAppend )
  {
    Word oldWord = new DefaultWord ( this.lr0Automaton.getWord () );

    oldWord.add ( new DefaultSymbol ( toAppend ) );

    this.lr0Automaton.setWord ( oldWord );
    // this.lr0Automaton.setWord ( new DefaultWord ( new DefaultSymbol (
    // action.getReduceAction ().getNonterminalSymbol ().toString () ) ) );
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


  private LR0ItemSet currentItems ()
  {
    State state = this.lr0Automaton.getCurrentState ();

    LR0State lr0state = ( LR0State ) state;

    return lr0state.getLR0Items ();
  }


  private TerminalSymbol currentTerminal ()
  {
    return this.wordIndex < this.word.size () ? new DefaultTerminalSymbol (
        this.word.get ( this.wordIndex ).toString () ) : null;
  }


  /**
   * Calculate the actions set
   * 
   * @param items - The LR0 item set
   * @param symbol - The current terminal symbol
   * @return The actions set
   */
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
          {
            if ( symbol == null )
              ret.add ( new LRAcceptAction () );
          }
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
    this.wordAccepted = false;
    this.word = word;
    this.wordIndex = 0;
    this.lr0Automaton.start ( new DefaultWord () );// word );
  }


  public boolean isWordAccepted ()
  {
    return this.wordAccepted;
  }


  private LR0Grammar grammar;


  private LR0 lr0Automaton;


  private Word word;


  private int wordIndex;


  private boolean wordAccepted;
}
