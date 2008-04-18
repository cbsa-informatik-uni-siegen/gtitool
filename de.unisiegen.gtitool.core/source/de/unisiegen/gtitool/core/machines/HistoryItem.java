package de.unisiegen.gtitool.core.machines;


import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The history item.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class HistoryItem
{

  /**
   * The {@link State} set.
   */
  private TreeSet < State > stateSet;


  /**
   * The {@link Transition} set.
   */
  private TreeSet < Transition > transitionSet;


  /**
   * The {@link Symbol} list.
   */
  private ArrayList < Symbol > symbolList;


  /**
   * The {@link Stack}.
   */
  private Stack oldStack;


  /**
   * Allocates a new {@link HistoryItem}.
   * 
   * @param stateSet The {@link State} set.
   * @param transitionSet The {@link Transition} set.
   * @param symbolList The {@link Symbol} list.
   * @param oldStack The {@link Stack}.
   */
  public HistoryItem ( TreeSet < State > stateSet,
      TreeSet < Transition > transitionSet, ArrayList < Symbol > symbolList,
      Stack oldStack )
  {
    this.transitionSet = transitionSet;
    this.stateSet = stateSet;
    this.symbolList = symbolList;
    this.oldStack = oldStack;
  }


  /**
   * Returns the {@link Stack}.
   * 
   * @return The {@link Stack}.
   * @see #oldStack
   */
  public final Stack getStack ()
  {
    return this.oldStack;
  }


  /**
   * Returns the {@link State} set.
   * 
   * @return The {@link State} set.
   * @see #stateSet
   */
  public final TreeSet < State > getStateSet ()
  {
    return this.stateSet;
  }


  /**
   * Returns the {@link Symbol} list.
   * 
   * @return The {@link Symbol} list.
   * @see #symbolList
   */
  public final ArrayList < Symbol > getSymbolSet ()
  {
    return this.symbolList;
  }


  /**
   * Returns the {@link Transition} set.
   * 
   * @return The {@link Transition} set.
   * @see #transitionSet
   */
  public final TreeSet < Transition > getTransitionSet ()
  {
    return this.transitionSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    // TODOCF
    return this.stateSet.toString ();
  }
}
