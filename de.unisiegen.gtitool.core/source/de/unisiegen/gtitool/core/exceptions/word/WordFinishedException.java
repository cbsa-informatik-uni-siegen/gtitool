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
   * Allocates a new <code>WordFinishedException</code>.
   * 
   * @param word The {@link Word}.
   */
  public WordFinishedException ( Word word )
  {
    super ( word );
    // Message and Description
    setMessage ( Messages.getString ( "WordException.FinishedMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString ( "WordException.FinishedDescription", //$NON-NLS-1$
        word ) );
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
}
