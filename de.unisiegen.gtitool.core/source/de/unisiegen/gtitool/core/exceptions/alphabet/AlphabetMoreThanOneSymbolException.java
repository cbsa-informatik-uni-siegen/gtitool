package de.unisiegen.gtitool.core.exceptions.alphabet;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * The {@link AlphabetMoreThanOneSymbolException} is used if the
 * {@link Alphabet} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id: AlphabetMoreThanOneSymbolException.java 189 2007-11-17
 *          15:55:30Z fehler $
 */
public final class AlphabetMoreThanOneSymbolException extends AlphabetException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8267857615201989774L;


  /**
   * Allocates a new {@link AlphabetMoreThanOneSymbolException}.
   * 
   * @param alphabet The {@link Alphabet}.
   * @param symbolList The {@link Symbol}s.
   */
  public AlphabetMoreThanOneSymbolException ( Alphabet alphabet,
      ArrayList < Symbol > symbolList )
  {
    super ( alphabet, symbolList );
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "AlphabetException.MoreThanOneSymbolMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "AlphabetException.MoreThanOneSymbolDescription", symbolList.get ( 0 ) //$NON-NLS-1$
            .toPrettyString (), alphabet.toPrettyString () ) );
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
