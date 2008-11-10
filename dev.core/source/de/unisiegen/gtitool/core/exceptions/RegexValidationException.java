package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link RegexValidationException} is thrown, if the validation of a regex
 * fails.
 * 
 * @author Simon Meurer
 * @version
 */
public final class RegexValidationException extends CoreException implements
    Iterable < RegexException >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2096426788079569057L;


  /**
   * The list of {@link RegexException}s.
   */
  private ArrayList < RegexException > regexExceptionList = new ArrayList < RegexException > ();


  /**
   * Allocates a new {@link RegexValidationException}.
   * 
   * @param regexExceptionList The list of {@link RegexException}s.
   */
  public RegexValidationException (
      ArrayList < RegexException > regexExceptionList )
  {
    if ( regexExceptionList == null )
    {
      throw new NullPointerException ( "grammar exception list is null" ); //$NON-NLS-1$
    }
    if ( regexExceptionList.size () == 0 )
    {
      throw new IllegalArgumentException ( "grammar exception list is empty" ); //$NON-NLS-1$
    }
    this.regexExceptionList = regexExceptionList;
  }


  /**
   * Returns the {@link RegexException} list.
   * 
   * @return The {@link RegexException} list.
   */
  public final ArrayList < RegexException > getGrammarException ()
  {
    return this.regexExceptionList;
  }


  /**
   * Returns the {@link RegexException} at the specified position in the list of
   * {@link RegexException}s.
   * 
   * @param index The index of the {@link RegexException} to return.
   * @return The {@link RegexException} at the specified position in the list of
   *         {@link RegexException}s.
   */
  public final RegexException getRegexException ( int index )
  {
    return this.regexExceptionList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public final ErrorType getType ()
  {
    return ErrorType.COLLECTION;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < RegexException > iterator ()
  {
    return this.regexExceptionList.iterator ();
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
    for ( int i = 0 ; i < this.regexExceptionList.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( lineBreak );
      }
      result.append ( this.regexExceptionList.get ( i ).getClass ()
          .getSimpleName ()
          + lineBreak );
      result.append ( this.regexExceptionList.get ( i ).toString () );
    }
    return result.toString ();
  }
}
