package de.unisiegen.gtitool.core.exceptions.word;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>WordNotAcceptedException</code> is used if the {@link Word} is
 * not accepted.
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
   * Allocates a new <code>WordNotAcceptedException</code>.
   * 
   * @param word The {@link Word}.
   */
  public WordNotAcceptedException ( Word word )
  {
    super ( word );
    // Message and Description
    setMessage ( Messages.getString ( "WordException.NotAcceptedMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "WordException.NotAcceptedDescription", word ) ); //$NON-NLS-1$
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
