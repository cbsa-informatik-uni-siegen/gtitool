package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.exceptions.state.StateEmptyNameException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.state.StateIllegalNameException;


/**
 * The <code>State</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class State implements Entity
{

  /**
   * The serial verion uid.
   */
  private static final long serialVersionUID = -1156609272782310462L;


  /**
   * The start offset of this <code>Symbol</code> in the source code.
   * 
   * @see #getParserStartOffset()
   * @see #setParserStartOffset(int)
   */
  protected int parserStartOffset = -1;


  /**
   * The end offset of this <code>Symbol</code> in the source code.
   * 
   * @see #getParserEndOffset()
   * @see #setParserEndOffset(int)
   */
  protected int parserEndOffset = -1;


  /**
   * The {@link Alphabet} of this <code>State</code>.
   */
  private Alphabet alphabet = null;


  /**
   * The name of this <code>State</code>.
   */
  private String name;


  /**
   * This <code>State</code> is a start <code>State</code>.
   */
  private boolean startState = false;


  /**
   * This <code>State</code> is a final <code>State</code>.
   */
  private boolean finalState = false;


  /**
   * The list of {@link Transition}s, which begin in this <code>State</code>.
   */
  private ArrayList < Transition > transitionBeginList;


  /**
   * The list of {@link Transition}s, which end in this <code>State</code>.
   */
  private ArrayList < Transition > transitionEndList;


  /**
   * Allocates a new <code>State</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>State</code>.
   * @param pName The name of this <code>State</code>.
   * @param pStartState This <code>State</code> is a start <code>State</code>.
   * @param pFinalState This <code>State</code> is a final <code>State</code>.
   * @throws StateException If something with the <code>State</code> is not
   *           correct.
   */
  public State ( Alphabet pAlphabet, String pName, boolean pStartState,
      boolean pFinalState ) throws StateException
  {
    this ( pName );
    // Alphabet
    setAlphabet ( pAlphabet );
    // StartState
    setStartState ( pStartState );
    // FinalState
    setFinalState ( pFinalState );
  }


  /**
   * Allocates a new <code>State</code>.
   * 
   * @param pName The name of this <code>State</code>.
   * @throws StateException If something with the <code>State</code> is not
   *           correct.
   */
  public State ( String pName ) throws StateException
  {
    // Name
    setName ( pName );
    // TransitionBegin
    this.transitionBeginList = new ArrayList < Transition > ();
    // TransitionEnd
    this.transitionEndList = new ArrayList < Transition > ();
  }


  /**
   * Adds the {@link Transition} to the list of {@link Transition}s, which
   * begin in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to add.
   */
  public final void addTransitionBegin ( Transition pTransition )
  {
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( this.transitionBeginList.contains ( pTransition ) )
    {
      throw new IllegalArgumentException (
          "transition begins already in this state" ); //$NON-NLS-1$
    }
    if ( pTransition.getStateBegin () != this )
    {
      throw new IllegalArgumentException (
          "transition begins not in this state" ); //$NON-NLS-1$
    }
    this.transitionBeginList.add ( pTransition );
  }


  /**
   * Adds the {@link Transition} to the list of {@link Transition}s, which end
   * in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to add.
   */
  public final void addTransitionEnd ( Transition pTransition )
  {
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( this.transitionEndList.contains ( pTransition ) )
    {
      throw new IllegalArgumentException (
          "transition ends already in this state" ); //$NON-NLS-1$
    }
    if ( pTransition.getStateEnd () != this )
    {
      throw new IllegalArgumentException ( "transition ends not in this state" ); //$NON-NLS-1$
    }
    this.transitionEndList.add ( pTransition );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final State clone ()
  {
    try
    {
      State newState = new State ( this.alphabet.clone (), this.name,
          this.startState, this.finalState );
      for ( Transition current : this.transitionBeginList )
      {
        newState.addTransitionBegin ( current );
      }
      for ( Transition current : this.transitionEndList )
      {
        newState.addTransitionEnd ( current );
      }
      return newState;
    }
    catch ( StateException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
      return null;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof State )
    {
      State other = ( State ) pOther;
      return ( ( this.name.equals ( other.name ) )
          && ( this.alphabet.equals ( other.alphabet ) )
          && ( this.startState == other.startState ) && ( this.finalState == other.finalState ) );
    }
    return false;
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
   * Returns the name.
   * 
   * @return The name.
   * @see #name
   */
  public final String getName ()
  {
    return this.name;
  }


  /**
   * Returns the parserEndOffset.
   * 
   * @return The parserEndOffset.
   * @see #parserEndOffset
   * @see #setParserEndOffset(int)
   */
  public final int getParserEndOffset ()
  {
    return this.parserEndOffset;
  }


  /**
   * Returns the parserStartOffset.
   * 
   * @return The parserStartOffset.
   * @see #parserStartOffset
   * @see #setParserStartOffset(int)
   */
  public final int getParserStartOffset ()
  {
    return this.parserStartOffset;
  }


  /**
   * Returns the {@link Transition} at the specified position in the
   * {@link Transition} begin list.
   * 
   * @param pIndex The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} begin list.
   */
  public final Transition getTransitionBegin ( int pIndex )
  {
    return this.transitionBeginList.get ( pIndex );
  }


  /**
   * Returns the {@link Transition} begin list.
   * 
   * @return The {@link Transition} begin list.
   */
  public final ArrayList < Transition > getTransitionBegin ()
  {
    return this.transitionBeginList;
  }


  /**
   * Returns the {@link Transition} at the specified position in the
   * {@link Transition} end list.
   * 
   * @param pIndex The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} end list.
   */
  public final Transition getTransitionEnd ( int pIndex )
  {
    return this.transitionEndList.get ( pIndex );
  }


  /**
   * Returns the {@link Transition} end list.
   * 
   * @return The {@link Transition} end list.
   */
  public final ArrayList < Transition > getTransitionEnd ()
  {
    return this.transitionEndList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.name.hashCode () + this.alphabet.hashCode ()
        + ( this.startState ? 1 : 0 ) + ( this.finalState ? 1 : 0 );
  }


  /**
   * Returns the finalState.
   * 
   * @return The finalState.
   * @see #finalState
   */
  public final boolean isFinalState ()
  {
    return this.finalState;
  }


  /**
   * Returns the startState.
   * 
   * @return The startState.
   * @see #startState
   */
  public final boolean isStartState ()
  {
    return this.startState;
  }


  /**
   * Removes the {@link Transition} from the list of {@link Transition}s, which
   * begin in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to remove.
   */
  public final void removeTransitionBegin ( Transition pTransition )
  {
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( !this.transitionBeginList.contains ( pTransition ) )
    {
      throw new IllegalArgumentException (
          "transition begins not in this state" ); //$NON-NLS-1$
    }
    this.transitionBeginList.remove ( pTransition );
  }


  /**
   * Removes the {@link Transition} from the list of {@link Transition}s, which
   * end in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to remove.
   */
  public final void removeTransitionEnd ( Transition pTransition )
  {
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( !this.transitionEndList.contains ( pTransition ) )
    {
      throw new IllegalArgumentException ( "transition ends not in this state" ); //$NON-NLS-1$
    }
    this.transitionEndList.remove ( pTransition );
  }


  /**
   * Sets the {@link Alphabet} of this <code>State</code>.
   * 
   * @param pAlphabet The {@link Alphabet} to set.
   */
  public final void setAlphabet ( Alphabet pAlphabet )
  {
    if ( pAlphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    this.alphabet = pAlphabet;
  }


  /**
   * Sets the finalState value.
   * 
   * @param pFinalState The finalState to set.
   */
  public final void setFinalState ( boolean pFinalState )
  {
    this.finalState = pFinalState;
  }


  /**
   * Sets the name of this <code>State</code>.
   * 
   * @param pName The name to set.
   * @throws StateException If something with the <code>State</code> is not
   *           correct.
   */
  public final void setName ( String pName ) throws StateException
  {
    if ( pName == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    if ( pName.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new StateEmptyNameException ();
    }
    if ( !Character.isJavaIdentifierStart ( pName.charAt ( 0 ) ) )
    {
      throw new StateIllegalNameException ( pName );
    }
    for ( int i = 1 ; i < pName.length () ; i++ )
    {
      if ( !Character.isJavaIdentifierPart ( pName.charAt ( i ) ) )
      {
        throw new StateIllegalNameException ( pName );
      }
    }
    this.name = pName;
  }


  /**
   * Sets the parser end offset.
   * 
   * @param pParserEndOffset The new parser end offset.
   * @see #getParserEndOffset()
   * @see #parserEndOffset
   */
  public final void setParserEndOffset ( int pParserEndOffset )
  {
    this.parserEndOffset = pParserEndOffset;
  }


  /**
   * Sets the parser start offset.
   * 
   * @param pParserStartOffset The new parser start offset.
   * @see #getParserStartOffset()
   * @see #parserStartOffset
   */
  public final void setParserStartOffset ( int pParserStartOffset )
  {
    this.parserStartOffset = pParserStartOffset;
  }


  /**
   * Sets the startState value.
   * 
   * @param pStartState The startState to set.
   */
  public final void setStartState ( boolean pStartState )
  {
    this.startState = pStartState;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ();
    result.append ( "Name:        " + this.name + lineBreak ); //$NON-NLS-1$
    result.append ( "Alphabet:    " + this.alphabet.toString () + lineBreak ); //$NON-NLS-1$
    result.append ( "Start state: " + this.startState + lineBreak ); //$NON-NLS-1$
    result.append ( "Final state: " + this.finalState + lineBreak ); //$NON-NLS-1$
    result.append ( "Transition begin:" + lineBreak ); //$NON-NLS-1$
    for ( Transition current : this.transitionBeginList )
    {
      result.append ( "- Alphabet:    " + current.getAlphabet ().toString () //$NON-NLS-1$
          + lineBreak );
      result.append ( "  Begin state: " + current.getStateBegin ().getName () //$NON-NLS-1$
          + lineBreak );
      result.append ( "  End state:   " + current.getStateEnd ().getName () //$NON-NLS-1$
          + lineBreak );
      result.append ( "  Symbols:     " + current.getSymbol ().toString () //$NON-NLS-1$
          + lineBreak );
    }
    result.append ( "Transition end:" + lineBreak ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.transitionEndList.size () ; i++ )
    {
      result.append ( "- Alphabet:    " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getAlphabet ().toString ()
          + lineBreak );
      result.append ( "  Begin state: " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getStateBegin ().getName ()
          + lineBreak );
      result.append ( "  End state:   " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getStateEnd ().getName ()
          + lineBreak );
      result.append ( "  Symbols:     " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getSymbol ().toString () );
      if ( i < this.transitionEndList.size () - 1 )
      {
        result.append ( lineBreak );
      }
    }
    return result.toString ();
  }


  /**
   * Returns the number of {@link Transition}s in the {@link Transition} begin
   * list.
   * 
   * @return The number of {@link Transition}s in the {@link Transition} begin
   *         list.
   */
  public final int transitionBeginSize ()
  {
    return this.transitionBeginList.size ();
  }


  /**
   * Returns the number of {@link Transition}s in the {@link Transition} end
   * list.
   * 
   * @return The number of {@link Transition}s in the {@link Transition} end
   *         list.
   */
  public final int transitionEndSize ()
  {
    return this.transitionEndList.size ();
  }

}
