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
 * @param <E> The <code>Machine</code>.
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class Machine < E > implements Serializable
{

  /**
   * The {@link Alphabet} of this <code>Machine</code>.
   */
  protected Alphabet alphabet;


  /**
   * The list of the {@link State}s.
   */
  protected ArrayList < State > stateList;


  /**
   * The list of the {@link Transition}.
   */
  protected ArrayList < Transition > transitionList;


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
   * Validates that everything in the <code>Machine</code> is correct.
   * 
   * @throws MachineValidationException If the validation fails.
   */
  public abstract void validate () throws MachineValidationException;
}
