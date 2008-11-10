package de.unisiegen.gtitool.core.exceptions.alphabet;

import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * TODO
 *
 */
public class AlphabetReservedSymbolException extends AlphabetException
{

  
  /**
   * TODO
   *
   */
  public AlphabetReservedSymbolException (Alphabet alphabet,
      ArrayList < Symbol > symbolList)
  {
    super(alphabet, symbolList);
 // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "AlphabetException.ReservedSymbolMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "AlphabetException.ReservedSymbolDescription", symbolList.get ( 0 ) //$NON-NLS-1$
            .toPrettyString (), alphabet.toPrettyString () ) );
  }
  
  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.exceptions.CoreException#getType()
   */
  @Override
  public ErrorType getType ()
  {
    return ErrorType.ERROR;
  }

}
