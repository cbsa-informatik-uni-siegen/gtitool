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
   * The active {@link State}s.
   */
  private ArrayList < State > activeStateList;


  /**
   * The {@link Alphabet} of this <code>Machine</code>.
   */
  private Alphabet alphabet;


  /**
   * The current {@link State} id.
   */
  private int currentStateId = State.ID_NOT_DEFINED;


  /**
   * The current {@link Transition} id.
   */
  private int currentTransitionId = Transition.ID_NOT_DEFINED;


  /**
   * The history of this <code>Machine</code>.
   */
  private ArrayList < ArrayList < Transition > > history;


  /**
   * The list of the {@link State}s.
   */
  private ArrayList < State > stateList;


  /**
   * The list of the {@link Transition}.
   */
  private ArrayList < Transition > transitionList;


  /**
   * The current {@link Word}.
   */
  private Word word = null;


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
   * Adds the {@link State}s to this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to add.
   */
  public final void addState ( Iterable < State > pStates )
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
    pState.setId ( ++this.currentStateId );
    pState.setDefaultName ();
    this.stateList.add ( pState );
    link ( pState );
  }


  /**
   * Adds the {@link State}s to this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to add.
   */
  public final void addState ( State ... pStates )
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
   * Adds the {@link Transition}s to this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to add.
   */
  public final void addTransition ( Iterable < Transition > pTransitions )
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
    pTransition.setId ( ++this.currentTransitionId );
    this.transitionList.add ( pTransition );
    link ( pTransition );
  }


  /**
   * Adds the {@link Transition}s to this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to add.
   */
  public final void addTransition ( Transition ... pTransitions )
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
   * Clears the history of this <code>Machine</code>.
   */
  protected final void clearHistory ()
  {
    this.history.clear ();
  }


  /**
   * Returns the active {@link State}s.
   * 
   * @return The active {@link State}s.
   */
  public final ArrayList < State > getActiveState ()
  {
    return this.activeStateList;
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
   * Returns the current {@link State} id.
   * 
   * @return The current {@link State} id.
   */
  public int getCurrentStateId ()
  {
    return this.currentStateId;
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
   * Returns the current {@link Transition} id.
   * 
   * @return The current {@link Transition} id.
   */
  public int getCurrentTransitionId ()
  {
    return this.currentTransitionId;
  }


  /**
   * Returns the {@link State} list.
   * 
   * @return The {@link State} list.
   * @see #stateList
   */
  public final ArrayList < State > getState ()
  {
    return this.stateList;
  }


  /**
   * Returns the {@link State} with the given index.
   * 
   * @param pIndex The index to return.
   * @return The {@link State} list.
   * @see #stateList
   */
  public final State getState ( int pIndex )
  {
    return this.stateList.get ( pIndex );
  }


  /**
   * Returns the {@link Transition} list.
   * 
   * @return The {@link Transition} list.
   * @see #transitionList
   */
  public final ArrayList < Transition > getTransition ()
  {
    return this.transitionList;
  }


  /**
   * Returns the {@link Transition} with the given index.
   * 
   * @param pIndex pIndex The index to return.
   * @return The {@link Transition} list.
   * @see #transitionList
   */
  public final Transition getTransition ( int pIndex )
  {
    return this.transitionList.get ( pIndex );
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
   * Links the {@link Transition}s with the given {@link State}.
   * 
   * @param pState The {@link State} to which the {@link Transition}s should be
   *          linked.
   */
  private final void link ( State pState )
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
   * Links the given {@link Transition} with the {@link State}s.
   * 
   * @param pTransition The {@link Transition} to link with the {@link State}s.
   */
  private final void link ( Transition pTransition )
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
   * Removes the given {@link State}s from this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to remove.
   */
  public final void removeState ( Iterable < State > pStates )
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
  public final void removeState ( State ... pStates )
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
   * Removes the given {@link Transition}s from this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to remove.
   */
  public final void removeTransition ( Iterable < Transition > pTransitions )
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
  public final void removeTransition ( Transition ... pTransitions )
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
  protected final void setActiveState ( ArrayList < State > pActiveStates )
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
   * Sets the active {@link State}.
   * 
   * @param pActiveState The active {@link State}.
   */
  protected final void setActiveState ( State pActiveState )
  {
    if ( pActiveState == null )
    {
      throw new NullPointerException ( "active state is null" ); //$NON-NLS-1$
    }
    this.activeStateList = new ArrayList < State > ();
    if ( !this.stateList.contains ( pActiveState ) )
    {
      throw new IllegalArgumentException (
          "active state is not in this machine" ); //$NON-NLS-1$
    }
    this.activeStateList.add ( pActiveState );
  }


  /**
   * Sets the active {@link State}s.
   * 
   * @param pActiveStates The active {@link State}s.
   */
  protected final void setActiveState ( State ... pActiveStates )
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
   * Sets the current {@link State} id.
   * 
   * @param pCurrentStateId The current {@link State} id.
   */
  public void setCurrentStateId ( int pCurrentStateId )
  {
    this.currentStateId = pCurrentStateId;
  }


  /**
   * Sets the current {@link Transition} id.
   * 
   * @param pCurrentTransitionId The current {@link Transition} id.
   */
  public void setCurrentTransitionId ( int pCurrentTransitionId )
  {
    this.currentTransitionId = pCurrentTransitionId;
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
