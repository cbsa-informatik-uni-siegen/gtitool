package de.unisiegen.gtitool.core.exceptions.word;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link WordResetedException} is used if the {@link Word} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class WordResetedException extends WordException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 779984045742886102L;


  /**
   * Allocates a new {@link WordFinishedException}.
   * 
   * @param word The {@link Word}.
   */
  public WordResetedException ( Word word )
  {
    super ( word );
    // Message and Description
    setMessage ( Messages.getString ( "WordException.ResetedMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString ( "WordException.ResetedDescription", //$NON-NLS-1$
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
