package de.unisiegen.gtitool.core.machines;


import java.io.Serializable;
import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.exceptions.word.WordException;


/**
 * The abstract class for all machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class Machine implements Serializable
{

  /**
   * The {@link Alphabet} of this <code>Machine</code>.
   */
  private Alphabet alphabet;


  /**
   * The list of the {@link State}s.
   */
  private ArrayList < State > stateList;


  /**
   * The list of the {@link Transition}.
   */
  private ArrayList < Transition > transitionList;


  /**
   * The active {@link State}s.
   */
  private ArrayList < State > activeStateList;


  /**
   * The current {@link Word}.
   */
  private Word word = null;


  /**
   * The history of this <code>Machine</code>.
   */
  private ArrayList < ArrayList < Transition > > history;


  /**
   * Allocates a new <code>Machine</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>Machine</code>.
   */
  public Machine ( Alphabet pAlphabet )
  {
    // Alphabet
    if ( pAlphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    this.alphabet = pAlphabet;
    // StateList
    this.stateList = new ArrayList < State > ();
    // TransitionList
    this.transitionList = new ArrayList < Transition > ();
    // ActiveStateList
    this.activeStateList = new ArrayList < State > ();
    // History
    this.history = new ArrayList < ArrayList < Transition > > ();
  }


  /**
   * Adds the {@link Transition}s to the history of this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to add.
   */
  protected final void addHistory ( Iterable < Transition > pTransitions )
  {
    if ( pTransitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( !pTransitions.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    }
    ArrayList < Transition > list = new ArrayList < Transition > ();
    for ( Transition current : pTransitions )
    {
      list.add ( current );
    }
    this.history.add ( list );
  }


  /**
   * Adds the {@link Transition} to the history of this <code>Machine</code>.
   * 
   * @param pTransition The {@link Transition} to add.
   */
  protected final void addHistory ( Transition pTransition )
  {
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    ArrayList < Transition > list = new ArrayList < Transition > ();
    list.add ( pTransition );
    this.history.add ( list );
  }


  /**
   * Adds the {@link Transition}s to the history of this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to add.
   */
  protected final void addHistory ( Transition ... pTransitions )
  {
    if ( pTransitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( pTransitions.length == 0 )
    {
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    }
    ArrayList < Transition > list = new ArrayList < Transition > ();
    for ( Transition current : pTransitions )
    {
      list.add ( current );
    }
    this.history.add ( list );
  }


  /**
   * Adds the {@link State} to this <code>Machine</code>.
   * 
   * @param pState The {@link State} to add.
   */
  public final void addState ( State pState )
  {
    if ( pState == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    if ( !this.alphabet.equals ( pState.getAlphabet () ) )
    {
      throw new IllegalArgumentException ( "not the same alphabet" ); //$NON-NLS-1$
    }
    this.stateList.add ( pState );
    addTransitionsToStates ( pState );
  }


  /**
   * Adds the {@link State}s to this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to add.
   */
  public final void addStates ( Iterable < State > pStates )
  {
    if ( pStates == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    if ( !pStates.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    }
    for ( State current : pStates )
    {
      addState ( current );
    }
  }


  /**
   * Adds the {@link State}s to this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to add.
   */
  public final void addStates ( State ... pStates )
  {
    if ( pStates == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    if ( pStates.length == 0 )
    {
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    }
    for ( State current : pStates )
    {
      addState ( current );
    }
  }


  /**
   * Adds the {@link Transition} to this <code>Machine</code>.
   * 
   * @param pTransition The {@link Transition} to add.
   */
  public final void addTransition ( Transition pTransition )
  {
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( this.transitionList.contains ( pTransition ) )
    {
      throw new IllegalArgumentException ( "transition is already added" ); //$NON-NLS-1$
    }
    if ( !this.alphabet.equals ( pTransition.getAlphabet () ) )
    {
      throw new IllegalArgumentException ( "not the same alphabet" ); //$NON-NLS-1$
    }
    this.transitionList.add ( pTransition );
    addTransitionsToStates ( pTransition );
  }


  /**
   * Adds the {@link Transition}s to this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to add.
   */
  public final void addTransitions ( Iterable < Transition > pTransitions )
  {
    if ( pTransitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( !pTransitions.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    }
    for ( Transition current : pTransitions )
    {
      addTransition ( current );
    }
  }


  /**
   * Adds the {@link Transition}s to this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to add.
   */
  public final void addTransitions ( Transition ... pTransitions )
  {
    if ( pTransitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( pTransitions.length == 0 )
    {
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    }
    for ( Transition current : pTransitions )
    {
      addTransition ( current );
    }
  }


  /**
   * Adds the {@link Transition}s to the given {@link State}.
   * 
   * @param pState The {@link State} to which the {@link Transition}s should be
   *          added.
   */
  private final void addTransitionsToStates ( State pState )
  {
    for ( Transition current : this.transitionList )
    {
      if ( current.getStateBegin ().equals ( pState ) )
      {
        pState.addTransitionBegin ( current );
      }
      if ( current.getStateEnd ().equals ( pState ) )
      {
        pState.addTransitionEnd ( current );
      }
    }
  }


  /**
   * Adds the {@link Transition}s to the {@link State}s.
   * 
   * @param pTransition The {@link Transition} to add to the {@link State}s.
   */
  private final void addTransitionsToStates ( Transition pTransition )
  {
    for ( State current : this.stateList )
    {
      if ( pTransition.getStateBegin ().equals ( current ) )
      {
        current.addTransitionBegin ( pTransition );
      }
      if ( pTransition.getStateEnd ().equals ( current ) )
      {
        current.addTransitionEnd ( pTransition );
      }
    }
  }


  /**
   * Clears the history of this <code>Machine</code>.
   */
  protected final void clearHistory ()
  {
    this.history.clear ();
  }


  /**
   * Returns the active {@link State} with the given index.
   * 
   * @param pIndex The index.
   * @return The active {@link State} with the given index.
   */
  protected final State getActiveState ( int pIndex )
  {
    return this.activeStateList.get ( pIndex );
  }


  /**
   * Returns the active {@link State}s.
   * 
   * @return The active {@link State}s.
   */
  public final ArrayList < State > getActiveStateList ()
  {
    return this.activeStateList;
  }


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   * @see #alphabet
   */
  public final Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * Returns the current {@link Symbol}.
   * 
   * @return The current {@link Symbol}.
   * @throws WordException If something with the <code>Word</code> is not
   *           correct.
   */
  public final Symbol getCurrentSymbol () throws WordException
  {
    return this.word.getCurrentSymbol ();
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
   * Returns the current {@link Word}.
   * 
   * @return The current {@link Word}.
   * @see #word
   */
  protected final Word getWord ()
  {
    return this.word;
  }


  /**
   * Returns true if one of the active {@link State}s is a final {@link State},
   * otherwise false.
   * 
   * @return True if one of the active {@link State}s is a final {@link State},
   *         otherwise false.
   */
  public final boolean isWordAccepted ()
  {
    for ( State current : this.activeStateList )
    {
      if ( current.isFinalState () )
      {
        return true;
      }
    }
    return false;
  }


  /**
   * Performs the next step and returns the list of {@link Transition}s, which
   * contains the {@link Symbol}.
   * 
   * @return The list of {@link Transition}s, which contains the {@link Symbol}.
   * @throws WordException If something with the {@link Word} is not correct.
   */
  public abstract ArrayList < Transition > nextSymbol () throws WordException;


  /**
   * Removes the last step and returns the list of {@link Transition}s, which
   * contains the {@link Symbol}.
   * 
   * @return The list of {@link Transition}s, which contains the {@link Symbol}.
   * @throws WordException If something with the {@link Word} is not correct.
   */
  public abstract ArrayList < Transition > previousSymbol ()
      throws WordException;


  /**
   * Removes and returns the last history element.
   * 
   * @return The last history element.
   */
  protected final ArrayList < Transition > removeHistory ()
  {
    return this.history.remove ( this.history.size () - 1 );
  }


  /**
   * Removes the given {@link State} from this <code>Machine</code>.
   * 
   * @param pState The {@link State} to remove.
   */
  public final void removeState ( State pState )
  {
    this.stateList.remove ( pState );
    for ( Transition current : pState.getTransitionBegin () )
    {
      removeTransition ( current );
    }
    for ( Transition current : pState.getTransitionEnd () )
    {
      removeTransition ( current );
    }
  }


  /**
   * Removes the given {@link State}s from this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to remove.
   */
  public final void removeStates ( Iterable < State > pStates )
  {
    if ( pStates == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    if ( !pStates.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    }
    for ( State current : pStates )
    {
      removeState ( current );
    }
  }


  /**
   * Removes the given {@link State}s from this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to remove.
   */
  public final void removeStates ( State ... pStates )
  {
    if ( pStates == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    if ( pStates.length == 0 )
    {
      throw new IllegalArgumentException ( "states is empty" ); //$NON-NLS-1$
    }
    for ( State current : pStates )
    {
      removeState ( current );
    }
  }


  /**
   * Removes the given {@link Transition} from this <code>Machine</code>.
   * 
   * @param pTransition The {@link Transition} to remove.
   */
  public final void removeTransition ( Transition pTransition )
  {
    this.transitionList.remove ( pTransition );
    for ( State current : this.stateList )
    {
      current.getTransitionBegin ().remove ( pTransition );
      current.getTransitionEnd ().remove ( pTransition );
    }
  }


  /**
   * Removes the given {@link Transition}s from this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to remove.
   */
  public final void removeTransitions ( Iterable < Transition > pTransitions )
  {
    if ( pTransitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( !pTransitions.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    }
    for ( Transition current : pTransitions )
    {
      removeTransition ( current );
    }
  }


  /**
   * Removes the given {@link Transition}s from this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to remove.
   */
  public final void removeTransitions ( Transition ... pTransitions )
  {
    if ( pTransitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( pTransitions.length == 0 )
    {
      throw new IllegalArgumentException ( "transitions is empty" ); //$NON-NLS-1$
    }
    for ( Transition current : pTransitions )
    {
      removeTransition ( current );
    }
  }


  /**
   * Sets the active {@link State}s.
   * 
   * @param pActiveStates The active {@link State}s.
   */
  protected final void setActiveStates ( ArrayList < State > pActiveStates )
  {
    if ( pActiveStates == null )
    {
      throw new NullPointerException ( "active states is null" ); //$NON-NLS-1$
    }
    this.activeStateList = new ArrayList < State > ();
    for ( State current : pActiveStates )
    {
      if ( !this.stateList.contains ( current ) )
      {
        throw new IllegalArgumentException (
            "active state is not in this machine" ); //$NON-NLS-1$
      }
      this.activeStateList.add ( current );
    }
  }


  /**
   * Sets the active {@link State}s.
   * 
   * @param pActiveStates The active {@link State}s.
   */
  protected final void setActiveStates ( State ... pActiveStates )
  {
    if ( pActiveStates == null )
    {
      throw new NullPointerException ( "active states is null" ); //$NON-NLS-1$
    }
    this.activeStateList.clear ();
    for ( State current : pActiveStates )
    {
      if ( !this.stateList.contains ( current ) )
      {
        throw new IllegalArgumentException (
            "active state is not in this machine" ); //$NON-NLS-1$
      }
      this.activeStateList.add ( current );
    }
  }


  /**
   * Sets the current {@link Word}.
   * 
   * @param pWord The current {@link Word} to set.
   * @see #word
   */
  protected final void setWord ( Word pWord )
  {
    this.word = pWord;
  }


  /**
   * Starts the <code>Machine</code> after a validation with the given
   * {@link Word}.
   * 
   * @param pWord The {@link Word} to start with.
   * @throws MachineValidationException If the validation fails.
   */
  public abstract void start ( Word pWord ) throws MachineValidationException;


  /**
   * Validates that everything in the <code>Machine</code> is correct.
   * 
   * @throws MachineValidationException If the validation fails.
   */
  public abstract void validate () throws MachineValidationException;
}
