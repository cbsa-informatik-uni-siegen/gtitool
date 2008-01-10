package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>MachineSymbolOnlyOneTimeException</code> is used, if there is a
 * {@link State} with {@link Transition}s with the same {@link Symbol}.
 * 
 * @author Christian Fehler
 * @version $Id: MachineSymbolOnlyOneTimeException.java 115 2007-11-09 15:29:26Z
 *          fehler $
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
   * @param state The {@link State}.
   * @param symbol The {@link Symbol}.
   * @param transitionList The {@link Transition} list.
   */
  public MachineSymbolOnlyOneTimeException ( State state, Symbol symbol,
      ArrayList < Transition > transitionList )
  {
    super ();
    // State
    if ( state == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    this.state = state;
    // Symbol
    if ( symbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    this.symbol = symbol;
    // TransitionList
    if ( transitionList == null )
    {
      throw new NullPointerException ( "transition list is null" ); //$NON-NLS-1$
    }
    if ( transitionList.size () < 2 )
    {
      throw new IllegalArgumentException ( "transition list size is too small" ); //$NON-NLS-1$
    }
    this.transitionList = transitionList;
    // Message and Description
    setMessage ( Messages
        .getString ( "MachineSymbolOnlyOneTimeException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "MachineSymbolOnlyOneTimeException.Description", state.getName (), //$NON-NLS-1$
        symbol.getName () ) );
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
   * Returns the {@link Transition} list.
   * 
   * @return The {@link Transition} list.
   */
  public final ArrayList < Transition > getTransition ()
  {
    return this.transitionList;
  }


  /**
   * Returns the {@link Transition} at the specified position in the list of
   * {@link Transition}s.
   * 
   * @param index The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the list of
   *         {@link Transition}s.
   */
  public final Transition getTransition ( int index )
  {
    return this.transitionList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public final ErrorType getType ()
  {
    return ErrorType.ERROR;
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
    StringBuilder result = new StringBuilder ( super.toString () );
    result.append ( lineBreak );
    result.append ( "State:       " ); //$NON-NLS-1$
    result.append ( this.state.getName () );
    result.append ( lineBreak );
    result.append ( "Symbol:      " ); //$NON-NLS-1$
    result.append ( this.symbol.getName () );
    result.append ( lineBreak );
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
}
