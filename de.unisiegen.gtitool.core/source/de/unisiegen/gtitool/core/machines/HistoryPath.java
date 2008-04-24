package de.unisiegen.gtitool.core.machines;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The history path.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class HistoryPath
{

  /**
   * The {@link State} list.
   */
  private ArrayList < State > stateList;


  /**
   * The {@link Transition} list.
   */
  private ArrayList < Transition > transitionList;


  /**
   * The {@link Symbol} list.
   */
  private ArrayList < Symbol > symbolList;


  /**
   * Allocates a new {@link HistoryPath}.
   */
  public HistoryPath ()
  {
    this.stateList = new ArrayList < State > ();
    this.transitionList = new ArrayList < Transition > ();
    this.symbolList = new ArrayList < Symbol > ();
  }


  /**
   * Adds the values to the {@link HistoryPath}.
   * 
   * @param beginState The begin {@link State}.
   * @param transition The {@link Transition}.
   * @param endState The end {@link State}.
   * @param symbol The {@link Symbol}.
   */
  public final void add ( State beginState, Transition transition,
      State endState, Symbol symbol )
  {
    if ( symbol != null && !transition.contains ( symbol ) )
    {
      throw new IllegalArgumentException (
          "the symbol is not a member of the transition" ); //$NON-NLS-1$
    }

    Transition newTransition = transition.clone ();

    if ( newTransition.isEpsilonTransition () )
    {
      newTransition.setActive ( true );
    }
    else
    {
      for ( Symbol current : newTransition )
      {
        if ( current.equals ( symbol ) )
        {
          current.setActive ( true );
          break;
        }
      }
    }

    if ( this.stateList.size () == 0 )
    {
      this.stateList.add ( beginState );
    }
    else
    {
      State lastState = this.stateList.get ( this.stateList.size () - 1 );
      if ( beginState != lastState )
      {
        throw new IllegalArgumentException (
            "the begin state is not equals to the last added state" ); //$NON-NLS-1$
      }
    }

    this.transitionList.add ( newTransition );
    this.stateList.add ( endState );
    this.symbolList.add ( symbol );
  }


  /**
   * Returns the {@link State} list.
   * 
   * @return The {@link State} list.
   * @see #stateList
   */
  public final ArrayList < State > getStateList ()
  {
    return this.stateList;
  }


  /**
   * Returns the {@link Symbol} list.
   * 
   * @return The {@link Symbol} list.
   * @see #symbolList
   */
  public final ArrayList < Symbol > getSymbolList ()
  {
    return this.symbolList;
  }


  /**
   * Returns the {@link Transition} list.
   * 
   * @return The {@link Transition} list.
   * @see #transitionList
   */
  public final ArrayList < Transition > getTransitionList ()
  {
    return this.transitionList;
  }
}
