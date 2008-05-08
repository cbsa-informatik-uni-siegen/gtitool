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
   * The {@link State}.
   */
  private State state;


  /**
   * Allocates a new {@link HistoryPathPart}.
   * 
   * @param transitionSymbolList The {@link TransitionSymbolPair} list.
   * @param readedSymbolList The readed {@link Symbol} list
   */
  public HistoryPathPart (
      ArrayList < TransitionSymbolPair > transitionSymbolList,
      ArrayList < Symbol > readedSymbolList )
  {
    this ( transitionSymbolList, readedSymbolList, null );
  }


  /**
   * Allocates a new {@link HistoryPathPart}.
   * 
   * @param transitionSymbolList The {@link TransitionSymbolPair} list.
   * @param readedSymbolList The readed {@link Symbol} list
   * @param state The {@link State}.
   */
  public HistoryPathPart (
      ArrayList < TransitionSymbolPair > transitionSymbolList,
      ArrayList < Symbol > readedSymbolList, State state )
  {
    this.transitionSymbolList = transitionSymbolList;
    this.readedSymbolList = readedSymbolList;
    this.state = state;
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
}
