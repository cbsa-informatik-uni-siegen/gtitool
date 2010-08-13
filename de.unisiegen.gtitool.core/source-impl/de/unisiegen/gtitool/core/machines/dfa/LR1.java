package de.unisiegen.gtitool.core.machines.dfa;


import java.util.Iterator;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.LR1State;
import de.unisiegen.gtitool.core.entities.LR1StateSet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;


/**
 * TODO
 */
public class LR1 extends AbstractLR
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
   * @throws TransitionSymbolOnlyOneTimeException
   * @throws TransitionSymbolNotInAlphabetException
   * @throws StateException
   */
  public LR1 ( final LR1Grammar grammar ) throws AlphabetException,
      TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException, StateException
  {
    super ( grammar );

    this.grammar = grammar;

    Alphabet alphabet = this.getAlphabet ();

    int index = 0;

    // cache to look up already constructed states
    final LR1StateSet allStates = new LR1StateSet ();

    LR1StateSet currentStates = new LR1StateSet ();

    {
      final LR1State startState = new LR1State ( alphabet, true, grammar
          .closure ( grammar.startProduction () ) );

      this.addState ( startState );

      startState.setIndex ( index++ );

      currentStates.add ( startState );
    }

    while ( !currentStates.isEmpty () )
    {
      final LR1StateSet newStates = new LR1StateSet ();

      for ( LR1State currentState : currentStates )
      {
        final LR1ItemSet currentItemSet = currentState.getLR1Items ();

        final LR1StateSet addedDesintations = new LR1StateSet ();

        for ( LR1Item item : currentItemSet )
        {
          if ( item.dotIsAtEnd () )
            continue;

          final LR1ItemSet destinationSet = grammar.closure ( grammar.move (
              currentItemSet, item.getProductionWordMemberAfterDot () ) );

          final LR1StateSet.AddPair status = allStates
              .addOrReturn ( new LR1State ( alphabet, false, destinationSet ) );

          final LR1State newState = status.getState ();

          if ( status.getIsNew () )
          {
            newStates.add ( newState );

            newState.setIndex ( index++ );

            this.addState ( newState );
          }

          if ( !addedDesintations.addIfNonExistant ( newState ) )
            continue;

          addTransition ( new DefaultTransition ( alphabet,
              new DefaultAlphabet (), new DefaultWord (), new DefaultWord (),
              currentState, newState, new DefaultSymbol ( item
                  .getProductionWordMemberAfterDot ().toString () ) ) );
        }
      }

      allStates.add ( newStates );

      currentStates = newStates;
    }
  }


  /**
   * Tag class for the second constructor
   */
  protected class DontConstructTheStates
  {
    // / does nothing
  }


  /**
   * Constructs the LR1 parser without constructing its states
   * 
   * @param alphabet
   * @param grammar
   * @param temp The tag
   */
  private LR1 ( final Alphabet alphabet, final LR1Grammar grammar,
      @SuppressWarnings ( "unused" ) final DontConstructTheStates temp )
  {
    super ( alphabet, grammar );

    this.grammar = grammar;
  }


  /**
   * Try to convert this automaton to an equivalent LALR1 automaton
   * 
   * @return the new automaton
   * @throws StateException
   * @throws TransitionSymbolOnlyOneTimeException 
   * @throws TransitionSymbolNotInAlphabetException 
   */
  public LR1 toLALR1 () throws StateException, TransitionSymbolNotInAlphabetException, TransitionSymbolOnlyOneTimeException
  {
    final LR1 ret = new LR1 ( this.getAlphabet (), this.grammar,
        new DontConstructTheStates () );

    final LR1StateSet currentStates = new LR1StateSet ();

    for ( State state : this.getState () )
      currentStates.add ( ( LR1State ) state );

    int stateIndex = 0;

    while ( !currentStates.isEmpty () )
    {
      final LR1State currentState = currentStates.front ();

      final LR0ItemSet lr0part = currentState.getLR0Part ();

      final LR1ItemSet resultItems = new LR1ItemSet ();

      for ( Iterator < LR1State > iterator = currentStates.iterator () ; iterator
          .hasNext () ; )
      {
        final LR1State state = iterator.next ();

        if ( state.getLR0Part ().equals ( lr0part ) )
        {
          resultItems.addIfNonExistant ( state.getLR1Items () );
          iterator.remove ();
        }
      }

      final LR1State newState = new LR1State ( this.getAlphabet (),
          currentState.isStartState (), resultItems );

      newState.setIndex ( stateIndex++ );

      ret.addState ( newState );
    }
    // add all transitions
    for ( Transition transition : this.getTransition () )
    {
      final LR1State beginState = ( LR1State ) transition.getStateBegin ();

      final LR1State endState = ( LR1State ) transition.getStateEnd ();

      final LR1State realBeginState = getEqualLALRState ( ret.getState (),
          beginState );

      final LR1State realEndState = getEqualLALRState ( ret.getState (),
          endState );

      final Transition newTransition = new DefaultTransition ( transition
          .getAlphabet (), transition.getPushDownAlphabet (), transition
          .getPushDownWordRead (), transition.getPushDownWordWrite (),
          realBeginState, realEndState, transition.getSymbol () );

      if ( ret.hasTransition ( newTransition ) )
        continue;
      ret.addTransition ( newTransition );
    }

    return ret;
  }


  /**
   * Find a state that contains the given state's LR0 part
   * 
   * @param states
   * @param state
   * @return the state or nul
   */
  static private LR1State getEqualLALRState ( final Iterable < State > states,
      final LR1State state )
  {
    for ( State nState : states )
    {
      final LR1State lr1State = ( LR1State ) nState;
      if ( lr1State.getLR0Part ().equals ( state.getLR0Part () ) )
        return lr1State;
    }
    return null;
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
    return MachineType.LR1;
  }


  /**
   * The grammar
   */
  private LR1Grammar grammar;
}
