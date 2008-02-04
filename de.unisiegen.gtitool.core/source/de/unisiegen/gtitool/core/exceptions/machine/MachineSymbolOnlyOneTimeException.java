package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.StatesInvolvedException;
import de.unisiegen.gtitool.core.exceptions.SymbolsInvolvedException;
import de.unisiegen.gtitool.core.exceptions.TransitionsInvolvedException;


/**
 * The {@link MachineSymbolOnlyOneTimeException} is used, if there is a
 * {@link State} with {@link Transition}s with the same {@link Symbol}.
 * 
 * @author Christian Fehler
 * @version $Id: MachineSymbolOnlyOneTimeException.java 115 2007-11-09 15:29:26Z
 *          fehler $
 */
public final class MachineSymbolOnlyOneTimeException extends MachineException
    implements StatesInvolvedException, TransitionsInvolvedException,
    SymbolsInvolvedException
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
   * The {@link Symbol}s.
   */
  private ArrayList < Symbol > symbols;


  /**
   * The {@link Transition}s.
   */
  private ArrayList < Transition > transitions;


  /**
   * Allocates a new {@link MachineEpsilonTransitionException}.
   * 
   * @param state The {@link State}.
   * @param symbols The {@link Symbol} list.
   * @param transitions The {@link Transition} list.
   */
  public MachineSymbolOnlyOneTimeException ( State state,
      ArrayList < Symbol > symbols, ArrayList < Transition > transitions )
  {
    super ();
    // State
    if ( state == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    this.state = state;
    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    this.symbols = symbols;
    // Transitions
    if ( transitions == null )
    {
      throw new NullPointerException ( "transitions is null" ); //$NON-NLS-1$
    }
    if ( transitions.size () < 2 )
    {
      throw new IllegalArgumentException ( "transitions size is too small" ); //$NON-NLS-1$
    }
    this.transitions = transitions;
    // Message and Description
    setMessage ( Messages
        .getString ( "MachineSymbolOnlyOneTimeException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "MachineSymbolOnlyOneTimeException.Description", state.getName (), //$NON-NLS-1$
        symbols.get ( 0 ).getName () ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StatesInvolvedException#getState()
   */
  public final ArrayList < State > getState ()
  {
    ArrayList < State > result = new ArrayList < State > ( 1 );
    result.add ( this.state );
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see SymbolsInvolvedException#getSymbol()
   */
  public final ArrayList < Symbol > getSymbol ()
  {
    return this.symbols;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransitionsInvolvedException#getTransition()
   */
  public final ArrayList < Transition > getTransition ()
  {
    return this.transitions;
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
    for ( int i = 0 ; i < this.transitions.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result.append ( this.symbols.get ( i ) );
    }
    result.append ( lineBreak );
    result.append ( "Transition:  " ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.transitions.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result.append ( this.transitions.get ( i ).getStateBegin ().getName () );
      result.append ( " -> " ); //$NON-NLS-1$
      result.append ( this.transitions.get ( i ).getStateEnd ().getName () );
    }
    return result.toString ();
  }
}
