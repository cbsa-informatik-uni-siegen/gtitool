package de.unisiegen.gtitool.core.exceptions.transition;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>TransitionException</code> is used if the {@link Transition} is
 * not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class TransitionException extends CoreException
{

  /**
   * The {@link Transition}.
   */
  private Transition transition;


  /**
   * The {@link Symbol}.
   */
  private ArrayList < Symbol > symbolList;


  /**
   * Allocates a new <code>TransitionException</code>.
   * 
   * @param pTransition The {@link Transition}.
   * @param pSymbolList The {@link Symbol}s.
   */
  public TransitionException ( Transition pTransition,
      ArrayList < Symbol > pSymbolList )
  {
    super ();
    // Transition
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    this.transition = pTransition;
    // SymbolList
    if ( pSymbolList == null )
    {
      throw new NullPointerException ( "symbol list is null" ); //$NON-NLS-1$
    }
    if ( pSymbolList.size () < 2 )
    {
      throw new IllegalArgumentException (
          "symbol list must contain at least two elements" ); //$NON-NLS-1$
    }
    this.symbolList = pSymbolList;
  }


  /**
   * Returns the {@link Symbol}s.
   * 
   * @return The {@link Symbol}s.
   * @see #symbolList
   */
  public final ArrayList < Symbol > getSymbol ()
  {
    return this.symbolList;
  }


  /**
   * Returns the {@link Symbol} with the given index.
   * 
   * @param pIndex The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolList
   */
  public final Symbol getSymbol ( int pIndex )
  {
    return this.symbolList.get ( pIndex );
  }


  /**
   * Returns the {@link Transition}.
   * 
   * @return The {@link Transition}.
   * @see #transition
   */
  public final Transition getTransition ()
  {
    return this.transition;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ( super.toString () );
    result.append ( lineBreak );
    result.append ( "Transition:  " ); //$NON-NLS-1$
    result.append ( this.transition.getStateBegin ().getName () );
    result.append ( " -> " ); //$NON-NLS-1$
    result.append ( this.transition.getStateEnd ().getName () );
    result.append ( lineBreak );
    result.append ( "Symbols:     " ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.symbolList.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result.append ( this.symbolList.get ( i ) );
    }
    return result.toString ();
  }
}
