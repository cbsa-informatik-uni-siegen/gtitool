package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The <code>MachineSymbolOnlyOneTimeException</code> is used, if there is a
 * {@link State} with {@link Transition}s with the same {@link Symbol}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MachineSymbolOnlyOneTimeException extends MachineException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6234514359747070032L;


  /**
   * The {@link State}.
   */
  private State state;


  /**
   * The {@link Symbol}.
   */
  private Symbol symbol;


  /**
   * The {@link Transition} list.
   */
  private ArrayList < Transition > transitionList;


  /**
   * Allocates a new <code>MachineEpsilonTransitionException</code>.
   * 
   * @param pState The {@link State}.
   * @param pSymbol The {@link Symbol}.
   * @param pTransitionList The {@link Transition} list.
   */
  public MachineSymbolOnlyOneTimeException ( State pState, Symbol pSymbol,
      ArrayList < Transition > pTransitionList )
  {
    super ();
    // State
    if ( pState == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    this.state = pState;
    // Symbol
    if ( pSymbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    this.symbol = pSymbol;
    // TransitionList
    if ( pTransitionList == null )
    {
      throw new NullPointerException ( "transition list is null" ); //$NON-NLS-1$
    }
    if ( pTransitionList.size () < 2 )
    {
      throw new IllegalArgumentException ( "transition list size is too small" ); //$NON-NLS-1$
    }
    this.transitionList = pTransitionList;

    setMessage ( Messages
        .getString ( "MachineSymbolOnlyOneTimeException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "MachineSymbolOnlyOneTimeException.Description", pState.getName (), //$NON-NLS-1$
        pSymbol.getName () ) );
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
   * Returns the {@link Symbol}.
   * 
   * @return The {@link Symbol}.
   * @see #symbol
   */
  public final Symbol getSymbol ()
  {
    return this.symbol;
  }


  /**
   * Returns the {@link Transition} at the specified position in the list of
   * {@link Transition}s.
   * 
   * @param pIndex The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the list of
   *         {@link Transition}s.
   */
  public final Transition getTransition ( int pIndex )
  {
    return this.transitionList.get ( pIndex );
  }


  /**
   * Returns the {@link Transition} list.
   * 
   * @return The {@link Transition} list.
   */
  public final ArrayList < Transition > getTransitionList ()
  {
    return this.transitionList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ();
    result.append ( "Message:     " + getMessage () + lineBreak ); //$NON-NLS-1$
    result.append ( "Description: " + getDescription () + lineBreak ); //$NON-NLS-1$
    result.append ( "State:       " + this.state.getName () + lineBreak ); //$NON-NLS-1$
    result.append ( "Symbol:      " + this.symbol.getName () + lineBreak ); //$NON-NLS-1$
    result.append ( "Transition:  " ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.transitionList.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result
          .append ( this.transitionList.get ( i ).getStateBegin ().getName () );
      result.append ( " -> " ); //$NON-NLS-1$
      result.append ( this.transitionList.get ( i ).getStateEnd ().getName () );
    }
    return result.toString ();
  }


  /**
   * Returns the number of {@link Transition}s in the list of
   * {@link Transition}s.
   * 
   * @return The number of {@link Transition}s in the list of
   *         {@link Transition}s.
   */
  public final int transitionSize ()
  {
    return this.transitionList.size ();
  }
}
