package de.unisiegen.gtitool.core.machines.dfa;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LR0State;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;


/**
 * TODO
 */
public class LR0 extends AbstractLR
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  /**
   * TODO
   * 
   * @param grammar
   * @throws AlphabetException
   */
  public LR0 ( final LR0Grammar grammar ) throws AlphabetException
  {
    super ( grammar );

    Alphabet alphabet = this.getAlphabet ();

    try
    {
      LR0State startState = new LR0State ( alphabet, true, grammar
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
        LR0State currentState = ( LR0State ) baseState;
        LR0ItemSet currentItemSet = currentState.getLR0Items ();

        for ( LR0Item item : currentItemSet )
        {
          if ( item.dotIsAtEnd () )
            continue;

          LR0ItemSet newItemSet = grammar.closure ( grammar.move (
              currentItemSet, item.getProductionWordMemberAfterDot () ) );
          try
          {
            LR0State newState = new LR0State ( alphabet, false, newItemSet );

            if ( !getState ().contains ( newState ) )
              addState ( newState );
            else
              newState = ( LR0State ) getState ().get (
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


  /**
   * Returns the machine's type
   * 
   * @return The machine's type
   * @see de.unisiegen.gtitool.core.machines.AbstractStateMachine#getMachineType()
   */
  @Override
  public MachineType getMachineType ()
  {
    return MachineType.LR0;
  }
}
