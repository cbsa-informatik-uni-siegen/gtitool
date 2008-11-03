package de.unisiegen.gtitool.core.util;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Transition.TransitionType;


/**
 * This helper class contains different usefull methods.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class Util
{

  /**
   * Returns the epsilon closure of the given {@link State}s.
   * 
   * @param stateList The {@link State}s.
   * @return The epsilon closure of the given {@link State}s.
   */
  public static final ArrayList < State > getClosure (
      ArrayList < State > stateList )
  {
    ArrayList < State > result = new ArrayList < State > ();
    for ( State current : stateList )
    {
      for ( State currentResult : getClosure ( current ) )
      {
        if ( !result.contains ( currentResult ) )
        {
          result.add ( currentResult );
        }
      }
    }
    return result;
  }


  /**
   * Returns the epsilon closure of the given {@link State}.
   * 
   * @param state The {@link State}.
   * @return The epsilon closure of the given {@link State}.
   */
  public static final ArrayList < State > getClosure ( State state )
  {
    return getClosure ( state, new ArrayList < State > () );
  }


  /**
   * Returns the epsilon closure of the given {@link State}.
   * 
   * @param state The {@link State}.
   * @param finishedStates The {@link State}s which are finished.
   * @return The epsilon closure of the given {@link State}.
   */
  private static final ArrayList < State > getClosure ( State state,
      ArrayList < State > finishedStates )
  {
    ArrayList < State > result = new ArrayList < State > ();
    if ( finishedStates.contains ( state ) )
    {
      return result;
    }

    result.add ( state );
    finishedStates.add ( state );
    for ( Transition current : state.getTransitionBegin () )
    {
      if ( ( current.getTransitionType ().equals ( TransitionType.EPSILON_ONLY ) || current
          .getTransitionType ().equals ( TransitionType.EPSILON_SYMBOL ) )
          && !result.contains ( current.getStateEnd () ) )
      {
        for ( State currentState : getClosure ( current.getStateEnd (),
            finishedStates ) )
        {
          if ( !result.contains ( currentState ) )
          {
            result.add ( currentState );
          }
        }
      }
    }
    return result;
  }
}
