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
   * Flag that indicates if a word next step was performed.
   */
  private boolean nextWordStep = false;


  /**
   * Allocates a new {@link HistoryItem}.
   * 
   * @param stateSet The {@link State} set.
   * @param transitionSet The {@link Transition} set.
   * @param symbolList The {@link Symbol} list.
   * @param oldStack The {@link Stack}.
   * @param nextWordStep Flag that indicates if a word next step was performed.
   */
  public HistoryItem ( TreeSet < State > stateSet,
      TreeSet < Transition > transitionSet, ArrayList < Symbol > symbolList,
      Stack oldStack, boolean nextWordStep )
  {
    this.transitionSet = transitionSet;
    this.stateSet = stateSet;
    this.symbolList = symbolList;
    this.oldStack = oldStack;
    this.nextWordStep = nextWordStep;
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
   * Returns the nextWordStep.
   * 
   * @return The nextWordStep.
   * @see #nextWordStep
   */
  public final boolean isNextWordStep ()
  {
    return this.nextWordStep;
  }


  /**
   * Sets the nextWordStep.
   * 
   * @param nextWordStep The nextWordStep to set.
   * @see #nextWordStep
   */
  public final void setNextWordStep ( boolean nextWordStep )
  {
    this.nextWordStep = nextWordStep;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.stateSet + " | " + this.transitionSet + " | " + this.symbolList //$NON-NLS-1$ //$NON-NLS-2$
        + " | " + this.oldStack; //$NON-NLS-1$
  }
}
