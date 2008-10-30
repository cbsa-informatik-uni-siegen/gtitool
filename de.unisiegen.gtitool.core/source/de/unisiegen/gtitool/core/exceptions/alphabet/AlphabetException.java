package de.unisiegen.gtitool.core.exceptions.alphabet;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link AlphabetException} is used if the {@link Alphabet} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class AlphabetException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -1506538633279948561L;


  /**
   * The {@link Alphabet}.
   */
  private Alphabet alphabet;


  /**
   * The {@link Symbol}.
   */
  private ArrayList < Symbol > symbolList;


  /**
   * Allocates a new {@link AlphabetException}.
   * 
   * @param alphabet The {@link Alphabet}.
   * @param symbolList The {@link Symbol}s.
   */
  public AlphabetException ( Alphabet alphabet, ArrayList < Symbol > symbolList )
  {
    super ();
    
    // alphabet
    if ( alphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    this.alphabet = alphabet;
    
    // symbolList
    if ( symbolList == null )
    {
      throw new NullPointerException ( "symbol list is null" ); //$NON-NLS-1$
    }
    if ( symbolList.size () < 2 )
    {
      throw new IllegalArgumentException (
          "symbol list must contain at least two elements" ); //$NON-NLS-1$
    }
    this.symbolList = symbolList;
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
   * @param index The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolList
   */
  public final Symbol getSymbol ( int index )
  {
    return this.symbolList.get ( index );
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
    result.append ( "Alphabet:    " ); //$NON-NLS-1$
    result.append ( this.alphabet );
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
