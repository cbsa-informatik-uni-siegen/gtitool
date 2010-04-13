package de.unisiegen.gtitool.core.machines.dfa;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.LR1State;
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
   */
  public LR1 ( final LR1Grammar grammar ) throws AlphabetException
  {
    super ( grammar );

    this.grammar = grammar;

    Alphabet alphabet = this.getAlphabet ();

    int index = 0;

    try
    {
      LR1State startState = new LR1State ( alphabet, true, grammar
          .closure ( grammar.startProduction () ) );

      startState.setIndex ( index++ );

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
            {
              addState ( newState );
              newState.setIndex ( index++ );
            }
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


  /**
   * Tag class for the second constructor
   */
  protected class DontConstructTheStates
  {
    // / does nothing
  }


  /**
   * Constructs the LR1 parser without constructing its states
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
   */
  public LR1 toLALR1 () throws StateException
  {
    LR1 ret = new LR1 ( this.getAlphabet (), this.grammar,
        new DontConstructTheStates () );

    int stateIndex = 0;

    for ( int index = 0 ; index < this.getState ().size () ; ++index )
    {
      final LR1State state = ( LR1State ) this.getState ( index );

      final LR0ItemSet lr0part = state.getLR0Part ();

      LR1ItemSet resultItems = new LR1ItemSet ();

      for ( int inner = 0 ; inner < this.getState ().size () ; ++inner )
      {
        final LR1State otherState = ( LR1State ) this.getState ( inner );

        if ( otherState.getLR0Part ().equals ( lr0part ) )
          resultItems.addIfNonExistant ( otherState.getLR1Items () );
      }

      final LR1State newState = new LR1State ( this.getAlphabet (), state
          .isStartState (), resultItems );

      if ( !ret.getState ().contains ( newState ) )
      {
        ret.addState ( newState );

        newState.setIndex ( stateIndex++ );
      }
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

      try
      {
        final Transition newTransition = new DefaultTransition ( transition
            .getAlphabet (), transition.getPushDownAlphabet (), transition
            .getPushDownWordRead (), transition.getPushDownWordWrite (),
            realBeginState, realEndState, transition.getSymbol () );

        if ( ret.hasTransition ( newTransition ) )
          continue;
        ret.addTransition ( newTransition );
      }
      catch ( TransitionSymbolNotInAlphabetException exc )
      {
        exc.printStackTrace ();
      }
      catch ( TransitionSymbolOnlyOneTimeException exc )
      {
        exc.printStackTrace ();
      }
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
