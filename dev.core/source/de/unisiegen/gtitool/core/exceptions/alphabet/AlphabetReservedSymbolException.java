package de.unisiegen.gtitool.core.exceptions.alphabet;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * TODO
 */
public class AlphabetReservedSymbolException extends AlphabetException
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = 325496713879730627L;


  /**
   * Creates a new {@link AlphabetReservedSymbolException}
   * 
   * @param alphabet The {@link Alphabet}
   * @param symbolList The error Symbols
   */
  public AlphabetReservedSymbolException ( Alphabet alphabet,
      ArrayList < Symbol > symbolList )
  {
    super ( alphabet, symbolList );
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "AlphabetException.ReservedSymbolMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "AlphabetException.ReservedSymbolDescription", symbolList.get ( 0 ) //$NON-NLS-1$
            .toPrettyString (), alphabet.toPrettyString () ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public ErrorType getType ()
  {
    return ErrorType.ERROR;
  }

}
