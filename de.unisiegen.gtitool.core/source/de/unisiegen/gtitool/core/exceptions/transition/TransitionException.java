package de.unisiegen.gtitool.core.exceptions.transition;


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
  private Symbol symbol;


  /**
   * Allocates a new <code>TransitionException</code>.
   * 
   * @param pTransition The {@link Transition}.
   * @param pSymbol The {@link Symbol}.
   */
  public TransitionException ( Transition pTransition, Symbol pSymbol )
  {
    super ();
    // Transition
    if ( pTransition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    this.transition = pTransition;

    // Symbol
    if ( pSymbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    this.symbol = pSymbol;
    // Message and Description
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
    result.append ( "Symbol:      " ); //$NON-NLS-1$
    result.append ( this.symbol );
    return result.toString ();
  }
}
