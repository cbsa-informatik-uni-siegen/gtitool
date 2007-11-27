package de.unisiegen.gtitool.core.exceptions.word;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>WordFinishedException</code> is used if the {@link Word} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class WordFinishedException extends WordException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8413119597869510363L;


  /**
   * The {@link Word}.
   */
  private Word word;


  /**
   * Allocates a new <code>WordFinishedException</code>.
   * 
   * @param pWord The {@link Word}.
   */
  public WordFinishedException ( Word pWord )
  {
    super ();
    // Word
    if ( pWord == null )
    {
      throw new NullPointerException ( "word is null" ); //$NON-NLS-1$
    }
    this.word = pWord;
    // Message and Description
    setMessage ( Messages.getString ( "WordException.FinishedMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString ( "WordException.FinishedDescription", //$NON-NLS-1$
        this.word ) );
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
    result.append ( "Position     " ); //$NON-NLS-1$
    result.append ( this.word.getCurrentPosition () );
    return result.toString ();
  }
}
