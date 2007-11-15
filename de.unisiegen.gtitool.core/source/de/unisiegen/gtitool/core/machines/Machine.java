package de.unisiegen.gtitool.core.machines;


import java.io.Serializable;
import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;


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
  }


  /**
   * Adds the {@link Entity}s to this <code>Machine</code>.
   * 
   * @param pEntities The {@link Entity}s to add.
   */
  public final void addEntities ( Entity ... pEntities )
  {
    if ( pEntities == null )
    {
      throw new NullPointerException ( "entities is null" ); //$NON-NLS-1$
    }
    if ( pEntities.length == 0 )
    {
      throw new IllegalArgumentException ( "entities is empty" ); //$NON-NLS-1$
    }
    for ( Entity current : pEntities )
    {
      addEntity ( current );
    }
  }


  /**
   * Adds the {@link Entity}s to this <code>Machine</code>.
   * 
   * @param pEntities The {@link Entity}s to add.
   */
  public final void addEntities ( Iterable < Entity > pEntities )
  {
    if ( pEntities == null )
    {
      throw new NullPointerException ( "entities is null" ); //$NON-NLS-1$
    }
    if ( !pEntities.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "entities is empty" ); //$NON-NLS-1$
    }
    for ( Entity current : pEntities )
    {
      addEntity ( current );
    }
  }


  /**
   * Adds the {@link Entity} to this <code>Machine</code>.
   * 
   * @param pEntity The {@link Entity} to add.
   */
  public final void addEntity ( Entity pEntity )
  {
    if ( pEntity == null )
    {
      throw new NullPointerException ( "entity is null" ); //$NON-NLS-1$
    }
    if ( pEntity instanceof State )
    {
      addState ( ( State ) pEntity );
    }
    else if ( pEntity instanceof Transition )
    {
      addTransition ( ( Transition ) pEntity );
    }
    else
    {
      throw new IllegalArgumentException (
          "entity is not a state and not a transition" ); //$NON-NLS-1$
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
   * Removes the {@link Entity}s from this <code>Machine</code>.
   * 
   * @param pEntities The {@link Entity}s to remove.
   */
  public final void removeEntities ( Entity ... pEntities )
  {
    if ( pEntities == null )
    {
      throw new NullPointerException ( "entities is null" ); //$NON-NLS-1$
    }
    if ( pEntities.length == 0 )
    {
      throw new IllegalArgumentException ( "entities is empty" ); //$NON-NLS-1$
    }
    for ( Entity current : pEntities )
    {
      removeEntity ( current );
    }
  }


  /**
   * Removes the {@link Entity}s from this <code>Machine</code>.
   * 
   * @param pEntities The {@link Entity}s to remove.
   */
  public final void removeEntities ( Iterable < Entity > pEntities )
  {
    if ( pEntities == null )
    {
      throw new NullPointerException ( "entities is null" ); //$NON-NLS-1$
    }
    if ( !pEntities.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "entities is empty" ); //$NON-NLS-1$
    }
    for ( Entity current : pEntities )
    {
      removeEntity ( current );
    }
  }


  /**
   * Removes the {@link Entity} from this <code>Machine</code>.
   * 
   * @param pEntity The {@link Entity} to remove.
   */
  public final void removeEntity ( Entity pEntity )
  {
    if ( pEntity == null )
    {
      throw new NullPointerException ( "entity is null" ); //$NON-NLS-1$
    }
    if ( pEntity instanceof State )
    {
      removeState ( ( State ) pEntity );
    }
    else if ( pEntity instanceof Transition )
    {
      removeTransition ( ( Transition ) pEntity );
    }
    else
    {
      throw new IllegalArgumentException (
          "entity is not a state and not a transition" ); //$NON-NLS-1$
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
    for ( Transition current : pState.getTransitionBeginList () )
    {
      removeTransition ( current );
    }
    for ( Transition current : pState.getTransitionEndList () )
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
      current.getTransitionBeginList ().remove ( pTransition );
      current.getTransitionEndList ().remove ( pTransition );
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
   * Validates that everything in the <code>Machine</code> is correct.
   * 
   * @throws MachineValidationException If the validation fails.
   */
  public abstract void validate () throws MachineValidationException;
}
