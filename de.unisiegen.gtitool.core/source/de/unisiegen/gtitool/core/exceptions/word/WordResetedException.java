package de.unisiegen.gtitool.core.exceptions.word;


import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.i18n.Messages;


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
    // Message and description
    setPrettyMessage ( Messages.getPrettyString ( "WordException.ResetedMessage" ) ); //$NON-NLS-1$
    if ( word.toString ().equals ( "" ) ) //$NON-NLS-1$
    {
      setPrettyDescription ( Messages.getPrettyString (
          "WordException.ResetedDescriptionEmpty", word ) ); //$NON-NLS-1$
    }
    else
    {
      setPrettyDescription ( Messages.getPrettyString (
          "WordException.ResetedDescription", word ) ); //$NON-NLS-1$
    }
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
