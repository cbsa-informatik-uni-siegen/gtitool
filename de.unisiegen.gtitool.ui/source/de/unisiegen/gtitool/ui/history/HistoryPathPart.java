package de.unisiegen.gtitool.ui.history;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;


/**
 * The {@link HistoryPathPart}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class HistoryPathPart
{

  /**
   * The {@link TransitionSymbolPair} list.
   */
  private ArrayList < TransitionSymbolPair > transitionSymbolList;


  /**
   * The readed {@link Symbol} list.
   */
  private ArrayList < Symbol > readedSymbolList;


  /**
   * The complete readed {@link Symbol} list.
   */
  private ArrayList < Symbol > completeReadedSymbolList;


  /**
   * The {@link State}.
   */
  private State state;


  /**
   * Allocates a new {@link HistoryPathPart}.
   * 
   * @param transitionSymbolList The {@link TransitionSymbolPair} list.
   * @param readedSymbolList The readed {@link Symbol} list.
   * @param state The {@link State}.
   * @param completeReadedSymbolList The complete readed {@link Symbol} list.
   */
  public HistoryPathPart (
      ArrayList < TransitionSymbolPair > transitionSymbolList,
      ArrayList < Symbol > readedSymbolList, State state,
      ArrayList < Symbol > completeReadedSymbolList )
  {
    this.transitionSymbolList = transitionSymbolList;
    this.readedSymbolList = readedSymbolList;
    this.state = state;
    this.completeReadedSymbolList = completeReadedSymbolList;
  }


  /**
   * Returns the readed {@link Symbol} list.
   * 
   * @return The readed {@link Symbol} list.
   * @see #readedSymbolList
   */
  public final ArrayList < Symbol > getReadedSymbolList ()
  {
    return this.readedSymbolList;
  }


  /**
   * Returns the {@link State}.
   * 
   * @return The {@link State}.
   * @see #state
   */
  public final State getState ()
  {
    return this.state;
  }


  /**
   * Returns the {@link TransitionSymbolPair} list.
   * 
   * @return The {@link TransitionSymbolPair} list.
   * @see #transitionSymbolList
   */
  public final ArrayList < TransitionSymbolPair > getTransitionList ()
  {
    return this.transitionSymbolList;
  }


  /**
   * Returns true if there is a cycle detected, otherwise false.
   * 
   * @return True if there is a cycle detected, otherwise false.
   */
  public final boolean isCycleDetected ()
  {
    ArrayList < State > stateList = new ArrayList < State > ();
    if ( this.state != null )
    {
      stateList.add ( this.state );
    }

    for ( TransitionSymbolPair current : this.transitionSymbolList )
    {
      if ( this.readedSymbolList.equals ( this.completeReadedSymbolList )
          && stateList.contains ( current.getFirst ().getStateBegin () ) )
      {
        return true;
      }
      stateList.add ( current.getFirst ().getStateBegin () );
    }

    return false;
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

    if ( this.state != null )
    {
      result.append ( this.state.toString () );
    }

    for ( int i = this.transitionSymbolList.size () - 1 ; i >= 0 ; i-- )
    {
      result.append ( this.transitionSymbolList.get ( i ).getFirst ()
          .getStateBegin ().toString () );
      if ( i > 0 )
      {
        result.append ( " -> " ); //$NON-NLS-1$
      }
    }

    return result.toString ();
  }
}
