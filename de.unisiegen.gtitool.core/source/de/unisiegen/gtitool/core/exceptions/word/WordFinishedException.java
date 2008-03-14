package de.unisiegen.gtitool.core.exceptions.word;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link WordFinishedException} is used if the {@link Word} is not correct.
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
   * Allocates a new {@link WordFinishedException}.
   * 
   * @param word The {@link Word}.
   */
  public WordFinishedException ( Word word )
  {
    super ( word );
    // Message and Description
    setMessage ( Messages.getString ( "WordException.FinishedMessage" ) ); //$NON-NLS-1$
    if ( word.toString ().equals ( "" ) ) //$NON-NLS-1$
    {
      setDescription ( Messages
          .getString ( "WordException.FinishedDescriptionEmpty" ) ); //$NON-NLS-1$
    }
    else
    {
      setDescription ( Messages.getString (
          "WordException.FinishedDescription", word ) ); //$NON-NLS-1$
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
