package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The <code>MachineAllSymbolsException</code> is used, if there is a
 * {@link State}, which {@link Transition}s do not contain all {@link Symbol}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MachineAllSymbolsException extends MachineException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7532859006177052311L;


  /**
   * The {@link State}.
   */
  private State state;


  /**
   * The set of {@link Symbol}s which are not member of a {@link Transition} of
   * the given {@link State}.
   */
  private TreeSet < Symbol > symbolSet;


  /**
   * Allocates a new <code>MachineAllSymbolsException</code>.
   * 
   * @param pState The {@link State}.
   * @param pSymbolSet The set of {@link Symbol}s which are not member of a
   *          {@link Transition} of the given {@link State}.
   */
  public MachineAllSymbolsException ( State pState,
      TreeSet < Symbol > pSymbolSet )
  {
    super ();
    // State
    if ( pState == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    this.state = pState;
    // SymbolSet
    if ( pSymbolSet == null )
    {
      throw new NullPointerException ( "symbol set is null" ); //$NON-NLS-1$
    }
    if ( pSymbolSet.size () == 0 )
    {
      throw new IllegalArgumentException ( "no exception: set size too small" ); //$NON-NLS-1$
    }
    this.symbolSet = pSymbolSet;
    // Message and Description
    if ( pSymbolSet.size () == 1 )
    {
      setMessage ( Messages
          .getString ( "MachineAllSymbolsException.SingleMessage" ) ); //$NON-NLS-1$
      setDescription ( Messages.getString (
          "MachineAllSymbolsException.SingleDescription", //$NON-NLS-1$
          this.state.getName (), this.symbolSet.first ().getName () ) );
    }
    else
    {
      StringBuilder symbols = new StringBuilder ();
      Iterator < Symbol > iter = this.symbolSet.iterator ();
      int index = 0;
      while ( iter.hasNext () )
      {
        Symbol current = iter.next ();
        symbols.append ( "\"" ); //$NON-NLS-1$
        symbols.append ( current.getName () );
        symbols.append ( "\"" ); //$NON-NLS-1$
        if ( index < this.symbolSet.size () - 2 )
        {
          symbols.append ( ", " ); //$NON-NLS-1$
        }
        if ( index == this.symbolSet.size () - 2 )
        {
          symbols.append ( " " + Messages.getString ( "And" ) + " " ); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
        }
        index++ ;
      }
      setMessage ( Messages
          .getString ( "MachineAllSymbolsException.MultiMessage" ) ); //$NON-NLS-1$
      setDescription ( Messages.getString (
          "MachineAllSymbolsException.MultiDescription", this.state.getName (), //$NON-NLS-1$
          symbols.toString () ) );
    }
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
   * Returns the {@link Symbol} with the given index.
   * 
   * @param pIndex The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolSet
   */
  public final Symbol getSymbol ( int pIndex )
  {
    Iterator < Symbol > iterator = this.symbolSet.iterator ();
    for ( int i = 0 ; i < pIndex ; i++ )
    {
      iterator.next ();
    }
    return iterator.next ();
  }


  /**
   * Returns the symbolSet.
   * 
   * @return The symbolSet.
   * @see #symbolSet
   */
  public final TreeSet < Symbol > getSymbolSet ()
  {
    return this.symbolSet;
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
    result.append ( getState ().getName () );
    result.append ( lineBreak );
    result.append ( "Symbol:      " ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.symbolSet.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result.append ( getSymbol ( i ).getName () );
    }
    return result.toString ();
  }
}
