package de.unisiegen.gtitool.core.exceptions.grammar;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link GrammarValidationException} is thrown, if the validation of a
 * grammar fails.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class GrammarValidationException extends CoreException implements
    Iterable < GrammarException >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 891334153869124257L;


  /**
   * The list of {@link GrammarException}s.
   */
  private ArrayList < GrammarException > grammarExceptionList = new ArrayList < GrammarException > ();


  /**
   * Allocates a new {@link GrammarValidationException}.
   * 
   * @param grammarExceptionList The list of {@link GrammarException}s.
   */
  public GrammarValidationException (
      ArrayList < GrammarException > grammarExceptionList )
  {
    if ( grammarExceptionList == null )
    {
      throw new NullPointerException ( "grammar exception list is null" ); //$NON-NLS-1$
    }
    if ( grammarExceptionList.size () == 0 )
    {
      throw new IllegalArgumentException ( "grammar exception list is empty" ); //$NON-NLS-1$
    }
    this.grammarExceptionList = grammarExceptionList;
  }


  /**
   * Returns the {@link GrammarException} list.
   * 
   * @return The {@link GrammarException} list.
   */
  public final ArrayList < GrammarException > getGrammarException ()
  {
    return this.grammarExceptionList;
  }


  /**
   * Returns the {@link GrammarException} at the specified position in the list
   * of {@link GrammarException}s.
   * 
   * @param index The index of the {@link GrammarException} to return.
   * @return The {@link GrammarException} at the specified position in the list
   *         of {@link GrammarException}s.
   */
  public final GrammarException getGrammarException ( int index )
  {
    return this.grammarExceptionList.get ( index );
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
  public final Iterator < GrammarException > iterator ()
  {
    return this.grammarExceptionList.iterator ();
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
    for ( int i = 0 ; i < this.grammarExceptionList.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( lineBreak );
      }
      result.append ( this.grammarExceptionList.get ( i ).getClass ()
          .getSimpleName ()
          + lineBreak );
      result.append ( this.grammarExceptionList.get ( i ).toString () );
    }
    return result.toString ();
  }
}
