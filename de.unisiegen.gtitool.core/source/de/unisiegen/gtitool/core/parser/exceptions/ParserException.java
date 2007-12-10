package de.unisiegen.gtitool.core.parser.exceptions;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;


/**
 * The parser exception class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class ParserException extends ScannerException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6750690466685873423L;


  /**
   * Throws a <code>ParserException</code> if the {@link Symbol} name is not
   * correct.
   * 
   * @param pLeft The left parser index.
   * @param pRight The right parser index.
   * @param pIllegalName The illegal name.
   */
  public static void throwSymbolException ( int pLeft, int pRight,
      String pIllegalName )
  {
    throw new ParserException ( pLeft, pRight, Messages.getString ( "Parser.6", //$NON-NLS-1$
        pIllegalName ) );
  }


  /**
   * Allocates a new <code>ParserException</code>.
   * 
   * @param pLeft The left parser index.
   * @param pRight The right parser index.
   * @param pMessage The message of the parser.
   */
  public ParserException ( int pLeft, int pRight, String pMessage )
  {
    super ( pLeft, pRight, pMessage );
  }
}
