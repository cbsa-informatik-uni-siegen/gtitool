package de.unisiegen.gtitool.core.exceptions.word;


import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link WordException} is used if the {@link Word} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class WordException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6629160292930504905L;


  /**
   * The {@link Word}.
   */
  private Word word;


  /**
   * Allocates a new {@link WordException}.
   * 
   * @param word The {@link Word}.
   */
  public WordException ( Word word )
  {
    super ();
    // Word
    if ( word == null )
    {
      throw new NullPointerException ( "word is null" ); //$NON-NLS-1$
    }
    this.word = word;
  }


  /**
   * Returns the {@link Word}.
   * 
   * @return The {@link Word}.
   * @see #word
   */
  public final Word getWord ()
  {
    return this.word;
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
    result.append ( "Word:        " ); //$NON-NLS-1$
    result.append ( this.word );
    result.append ( lineBreak );
    return result.toString ();
  }
}
