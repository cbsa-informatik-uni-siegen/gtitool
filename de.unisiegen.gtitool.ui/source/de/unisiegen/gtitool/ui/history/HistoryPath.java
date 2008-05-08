package de.unisiegen.gtitool.ui.history;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;


/**
 * The history path.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class HistoryPath
{

  /**
   * The start {@link State}.
   */
  private State startState;


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
    this.startState = null;
    this.transitionList = new ArrayList < Transition > ();
    this.symbolList = new ArrayList < Symbol > ();
  }


  /**
   * Adds the values to the {@link HistoryPath}.
   * 
   * @param transition The {@link Transition}.
   * @param symbol The {@link Symbol}.
   */
  public final void add ( Transition transition, Symbol symbol )
  {
    if ( symbol != null && !transition.contains ( symbol ) )
    {
      throw new IllegalArgumentException (
          "the symbol is not a member of the transition" ); //$NON-NLS-1$
    }

    Transition newTransition = new DefaultTransition ();
    newTransition.setStateBegin ( transition.getStateBegin () );
    newTransition.setStateEnd ( transition.getStateEnd () );
    for ( Symbol current : transition.getSymbol () )
    {
      try
      {
        newTransition.add ( current );
      }
      catch ( TransitionSymbolNotInAlphabetException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
      catch ( TransitionSymbolOnlyOneTimeException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    }

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

    this.transitionList.add ( newTransition );
    this.symbolList.add ( symbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof HistoryPath )
    {
      HistoryPath path = ( HistoryPath ) other;

      if ( this.transitionList.size () != path.getTransitionList ().size () )
      {
        return false;
      }
      for ( int i = 0 ; i < this.transitionList.size () ; i++ )
      {
        if ( this.transitionList.get ( i ).size () != path.getTransitionList ()
            .get ( i ).size () )
        {
          return false;
        }
        for ( int j = 0 ; j < this.transitionList.get ( i ).size () ; j++ )
        {
          if ( !this.transitionList.get ( i ).getSymbol ( j ).equals (
              path.getTransitionList ().get ( i ).getSymbol ( j ) ) )
          {
            return false;
          }
        }
      }

      return true;
    }
    return false;
  }


  /**
   * Returns the start {@link State}.
   * 
   * @return The start {@link State}.
   * @see #startState
   */
  public final State getStartState ()
  {
    return this.startState;
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


  /**
   * Sets the start {@link State}.
   * 
   * @param startState The start {@link State} to set.
   * @see #startState
   */
  public final void setStartState ( State startState )
  {
    this.startState = startState;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    StringBuilder result = new StringBuilder ();

    for ( int i = 0 ; i < this.transitionList.size () ; i++ )
    {
      if ( i == 0 )
      {
        this.transitionList.get ( i ).getStateBegin ().getName ();
      }
      result.append ( " -> " ); //$NON-NLS-1$
      this.transitionList.get ( i ).getStateEnd ().getName ();
    }

    return result.toString ();
  }
}
