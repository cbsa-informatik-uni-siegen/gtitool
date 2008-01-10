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
   * @param left The left parser index.
   * @param right The right parser index.
   * @param illegalName The illegal name.
   */
  public static void throwSymbolException ( int left, int right,
      String illegalName )
  {
    throw new ParserException ( left, right, Messages.getString ( "Parser.6", //$NON-NLS-1$
        illegalName ) );
  }


  /**
   * Allocates a new <code>ParserException</code>.
   * 
   * @param left The left parser index.
   * @param right The right parser index.
   * @param message The message of the parser.
   */
  public ParserException ( int left, int right, String message )
  {
    super ( left, right, message );
  }
}
