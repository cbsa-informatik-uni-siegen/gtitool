package de.unisiegen.gtitool.core.machines.dfa;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.LR1State;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;
import de.unisiegen.gtitool.core.machines.AbstractMachine;


/**
 * TODO
 */
public class LR1 extends AbstractMachine implements DFA
{

  public LR1 ( LR1Grammar grammar ) throws AlphabetException
  {
    super ( grammar.makeAutomatonAlphabet (), new DefaultAlphabet (), false,
        ValidationElement.FINAL_STATE, ValidationElement.STATE_NAME,
        ValidationElement.SYMBOL_ONLY_ONE_TIME );

    Alphabet alphabet = this.getAlphabet ();

    try
    {
      LR1State startState = new LR1State ( alphabet, true, grammar
          .closure ( grammar.startProduction () ) );

      addState ( startState );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
    }

    for ( int oldSize = getState ().size (), newSize = 0 ; oldSize != newSize ; newSize = getState ()
        .size () )
    {
      ArrayList < State > oldStates = new ArrayList < State > ( getState () );

      oldSize = oldStates.size ();

      for ( State baseState : oldStates )
      {
        LR1State currentState = ( LR1State ) baseState;
        LR1ItemSet currentItemSet = currentState.getLR1Items ();

        for ( LR1Item item : currentItemSet )
        {
          if ( item.dotIsAtEnd () )
            continue;

          LR1ItemSet newItemSet = grammar.closure ( grammar.move (
              currentItemSet, item.getProductionWordMemberAfterDot () ) );
          try
          {
            LR1State newState = new LR1State ( alphabet, false, newItemSet );

            if ( !getState ().contains ( newState ) )
              addState ( newState );
            else
              newState = ( LR1State ) getState ().get (
                  getState ().indexOf ( newState ) );

            Transition newTransition = new DefaultTransition ( alphabet,
                new DefaultAlphabet (), new DefaultWord (), new DefaultWord (),
                currentState, newState, new DefaultSymbol ( item
                    .getProductionWordMemberAfterDot ().toString () ) );

            boolean transitionAlreadyExists = false;

            for ( Transition trans : getTransition () )
            {
              if ( trans.compareByStates ( newTransition ) )
              {
                transitionAlreadyExists = true;
                break;
              }
            }

            if ( !transitionAlreadyExists )
              addTransition ( newTransition );
          }
          catch ( StateException e )
          {
            e.printStackTrace (); // shouldn't happen
          }
          catch ( TransitionSymbolOnlyOneTimeException e )
          {
            e.printStackTrace ();
          }
          catch ( TransitionSymbolNotInAlphabetException e )
          {
            e.printStackTrace ();
          }
        }
      }
    }
  }


  public void nextSymbol ( ProductionWordMember symbol )
  {
    State state = null;
    for ( State curState : this.getState () )
      if ( curState.isActive () )
        state = curState; // TODO: how can we get to the current active state
    // with less overhead?

    for ( Transition transition : this.getTransition () )
      if ( transition.getStateBegin ().equals ( state )
          && transition.getSymbol ().contains (
              new DefaultSymbol ( symbol.toString () ) ) )
      {
        this.nextSymbol ( transition );
        return;
      }

    System.err.println ( "OH NO TODO" );
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.machines.AbstractMachine#getMachineType()
   */
  @Override
  public MachineType getMachineType ()
  {
    return MachineType.DFA;
  }
}
