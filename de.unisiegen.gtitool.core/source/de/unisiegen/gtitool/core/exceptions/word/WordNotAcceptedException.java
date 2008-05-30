package de.unisiegen.gtitool.core.exceptions.word;


import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * The {@link WordNotAcceptedException} is used if the {@link Word} is not
 * accepted.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class WordNotAcceptedException extends WordException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2362277213977478886L;


  /**
   * Allocates a new {@link WordNotAcceptedException}.
   * 
   * @param word The {@link Word}.
   */
  public WordNotAcceptedException ( Word word )
  {
    super ( word );
    // Message and description
    setPrettyMessage ( Messages.getPrettyString ( "WordException.NotAcceptedMessage" ) ); //$NON-NLS-1$
    if ( word.toString ().equals ( "" ) ) //$NON-NLS-1$
    {
      setPrettyDescription ( Messages
          .getPrettyString ( "WordException.NotAcceptedDescriptionEmpty" ) ); //$NON-NLS-1$
    }
    else
    {
      setPrettyDescription ( Messages.getPrettyString (
          "WordException.NotAcceptedDescription", word ) ); //$NON-NLS-1$
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
